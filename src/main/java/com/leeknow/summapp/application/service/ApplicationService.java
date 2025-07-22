package com.leeknow.summapp.application.service;

import com.leeknow.summapp.application.dto.ApplicationRequestDTO;
import com.leeknow.summapp.application.dto.ApplicationResponseDTO;
import com.leeknow.summapp.application.dto.ApplicationSearchDTO;
import com.leeknow.summapp.application.entity.Application;
import com.leeknow.summapp.application.enums.ApplicationStatus;
import com.leeknow.summapp.application.repository.ApplicationRepository;
import com.leeknow.summapp.application.specification.ApplicationSpecification;
import com.leeknow.summapp.common.dto.DataSearchDTO;
import com.leeknow.summapp.common.enums.Language;
import com.leeknow.summapp.event.enums.EventType;
import com.leeknow.summapp.event.service.EventService;
import com.leeknow.summapp.message.service.MessageUtils;
import com.leeknow.summapp.user.entity.User;
import com.leeknow.summapp.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.leeknow.summapp.application.constant.ApplicationMessageConstant.APPLICATION_DELETED_SUCCESSFULLY;
import static com.leeknow.summapp.application.constant.ApplicationMessageConstant.APPLICATION_UPDATED_SUCCESSFULLY;
import static com.leeknow.summapp.application.mapper.ApplicationMapper.toResponseDtoApplication;

@Service
@RequiredArgsConstructor
public class ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final UserService userService;
    private final EventService eventService;
    private final ApplicationKafkaService kafkaService;
    private final MessageUtils messageUtils;

    @Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
    public Map<String, Object> findAll(ApplicationSearchDTO searchDTO, Language language) {
        Map<String, Object> result = new HashMap<>();

        Specification<Application> specification = ApplicationSpecification.getApplicationSpecification(searchDTO);

        PageRequest pageRequest = PageRequest.of(searchDTO.getPage() - 1, searchDTO.getSize(), Sort.by(searchDTO.getSort()));

        Page<Application> applications = applicationRepository.findAll(specification, pageRequest);

        result.put("applications", applications.getContent().stream().map(application -> toResponseDtoApplication(application, language)).toList());
        result.put("totalElements", applications.getTotalElements());
        result.put("totalPages", applications.getTotalPages());
        return result;
    }

    @Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
    public Map<String, Object> findAllByCurrentUser(DataSearchDTO searchDTO, Language language) {
        Map<String, Object> result = new HashMap<>();
        User user = userService.getCurrentUser();
        Page<Application> applications = applicationRepository.findAllByUser(user, PageRequest.of(
                searchDTO.getPage(),
                searchDTO.getSize(),
                Sort.by(searchDTO.getSort())));

        List<Application> content = applications.getContent();
        result.put("applications", toResponseDtoApplication(content, language));
        result.put("total", applications.getTotalElements());
        return result;
    }

    @Transactional(readOnly = true, isolation = Isolation.REPEATABLE_READ)
    @Cacheable(value = "APPLICATION_CACHE", key = "#id")
    public ApplicationResponseDTO findById(Integer id, Language language) {
        return toResponseDtoApplication(applicationRepository.findById(id).orElse(null), language);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    @CachePut(value = "APPLICATION_CACHE", key = "#result.applicationId")
    public ApplicationResponseDTO save(ApplicationRequestDTO applicationRequestDTO, Language language) {
        Application application = new Application();
        application.setUser(userService.getCurrentUser());
        application.setNumber(getRandomApplicationNumber());
        application.setStatusId(ApplicationStatus.CREATED.getId());
        application.setTypeId(applicationRequestDTO.getTypeId());
        application.setCreationDate(new Timestamp(System.currentTimeMillis()));
        application = applicationRepository.save(application);

        eventService.create(EventType.APPLICATION_CREATED.getId(), application);
        return toResponseDtoApplication(application, language);
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    @CachePut(value = "APPLICATION_CACHE", key = "#application.applicationId")
    public Map<String, Application> update(Application application) {
        Map<String, Application> result = new HashMap<>();
        result.put("application", applicationRepository.save(application));
        return result;
    }

    @CacheEvict(value = "APPLICATION_CACHE", key = "#id")
    public Map<String, String> delete(Integer id, Language language) {
        applicationRepository.deleteById(id);

        Map<String, String> result = new HashMap<>();
        result.put("message", messageUtils.getMessage(language, APPLICATION_DELETED_SUCCESSFULLY));
        return result;
    }

    private String getRandomApplicationNumber() {
        Random random = new Random();
        return new SimpleDateFormat("yyyyMMdd").format(new Date()) + String.format("%04d", random.nextInt(10000));
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    @CachePut(value = "APPLICATION_CACHE", key = "#id")
    public Map<String, String> setStatus(Integer id, Integer status, Language language) {
        Map<String, String> result = new HashMap<>();
        Application application = applicationRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        application.setStatusId(status);
        applicationRepository.save(application);

        kafkaService.updateApplicationStatus(id, status);

        result.put("message", messageUtils.getMessage(language, APPLICATION_UPDATED_SUCCESSFULLY));
        eventService.create(EventType.APPLICATION_STATUS_CHANGED.getId(), application);
        return result;
    }
}
