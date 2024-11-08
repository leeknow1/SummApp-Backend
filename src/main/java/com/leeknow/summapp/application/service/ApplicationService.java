package com.leeknow.summapp.application.service;

import com.leeknow.summapp.application.dto.ApplicationRequestDTO;
import com.leeknow.summapp.application.dto.ApplicationResponseDTO;
import com.leeknow.summapp.common.dto.DataSearchDTO;
import com.leeknow.summapp.event.service.EventService;
import com.leeknow.summapp.user.dto.UserResponseDTO;
import com.leeknow.summapp.application.entity.Application;
import com.leeknow.summapp.user.entity.User;
import com.leeknow.summapp.application.enums.ApplicationStatus;
import com.leeknow.summapp.application.enums.ApplicationType;
import com.leeknow.summapp.event.enums.EventType;
import com.leeknow.summapp.common.enums.Language;
import com.leeknow.summapp.application.repository.ApplicationRepository;
import com.leeknow.summapp.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.leeknow.summapp.application.constant.ApplicationMessageConstant.APPLICATION_UPDATED_SUCCESSFULLY;
import static com.leeknow.summapp.message.service.MessageService.getMessage;

@Service
@RequiredArgsConstructor
public class ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final UserService userService;
    private final EventService eventService;

    public Map<String, Page<ApplicationResponseDTO>> findAll(DataSearchDTO searchDTO, Language language) {
        Map<String, Page<ApplicationResponseDTO>> result = new HashMap<>();
        Page<Application> applications = applicationRepository.findAll(PageRequest.of(
                searchDTO.getPage(),
                searchDTO.getSize(),
                Sort.by(searchDTO.getSort())));
        result.put("applications", applications.map(application -> toResponseDtoApplication(application, language)));
        return result;
    }

    public Map<String, Page<ApplicationResponseDTO>> findAllByCurrentUser(DataSearchDTO searchDTO, Language language) {
        Map<String, Page<ApplicationResponseDTO>> result = new HashMap<>();
        User user = userService.getCurrentUser();
        Page<Application> applications = applicationRepository.findAllByUser(user, PageRequest.of(
                searchDTO.getPage(),
                searchDTO.getSize(),
                Sort.by(searchDTO.getSort())));
        result.put("applications", applications.map(application -> toResponseDtoApplication(application, language)));
        return result;
    }

    public ApplicationResponseDTO findById(Integer id, Language language) {
        return toResponseDtoApplication(applicationRepository.findById(id).orElse(null), language);
    }

    public Map<String, ApplicationResponseDTO> save(ApplicationRequestDTO applicationRequestDTO, Language language) {
        Map<String, ApplicationResponseDTO> result = new HashMap<>();
        Application application = new Application();
        application.setUser(userService.getCurrentUser());
        application.setNumber(getRandomApplicationNumber());
        application.setStatusId(ApplicationStatus.CREATED.getId());
        application.setTypeId(applicationRequestDTO.getTypeId());
        application.setCreationDate(new Timestamp(System.currentTimeMillis()));
        application = applicationRepository.save(application);
        result.put("application", toResponseDtoApplication(application, language));
        eventService.create(EventType.APPLICATION_CREATED.getId(), application);
        return result;
    }

    public Map<String, Application> update(Application application) {
        Map<String, Application> result = new HashMap<>();
        result.put("application", applicationRepository.save(application));
        return result;
    }

    public void delete(Integer id) {
        applicationRepository.deleteById(id);
    }

    private String formatDate(Timestamp date) {
        if (date != null) {
            return new SimpleDateFormat("yyyy-MM-dd HH:ss").format(new Date(date.getTime()));
        }
        return "";
    }

    private String getRandomApplicationNumber() {
        StringBuilder randomNumber = new StringBuilder(new SimpleDateFormat("yyyyMMdd").format(new Date()));
        for (int i = 0; i < 4; i++) {
            randomNumber.append((int) (Math.random() * 6) + 1);
        }
        return randomNumber.toString();
    }

    private ApplicationResponseDTO toResponseDtoApplication(Application application, Language language) {
        if (application != null) {
            ApplicationResponseDTO responseDTO = new ApplicationResponseDTO();
            responseDTO.setApplicationId(application.getApplicationId());
            responseDTO.setNumber(application.getNumber());
            responseDTO.setCreationDate(formatDate(application.getCreationDate()));
            responseDTO.setFinishDate(formatDate(application.getFinishDate()));
            responseDTO.setStatus(ApplicationStatus.getNameById(application.getStatusId(), language));
            responseDTO.setType(ApplicationType.getNameById(application.getTypeId(), language));
            responseDTO.setUserResponseDTO(toResponseDtoUser(application.getUser()));
            return responseDTO;
        }
        return null;
    }

    private UserResponseDTO toResponseDtoUser(User user) {
        if (user != null) {
            UserResponseDTO userDto = new UserResponseDTO();
            userDto.setFirstName(user.getFirstName());
            userDto.setMiddleName(user.getMiddleName());
            userDto.setLastName(user.getLastName());
            return userDto;
        }
        return null;
    }

    public Map<String, String> setStatus(Integer id, Integer status, Language language) {
        Map<String, String> result = new HashMap<>();
        Application application = applicationRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        application.setStatusId(status);
        applicationRepository.save(application);
        result.put("message", getMessage(language, APPLICATION_UPDATED_SUCCESSFULLY));
        eventService.create(EventType.APPLICATION_STATUS_CHANGED.getId(), application);
        return result;
    }
}
