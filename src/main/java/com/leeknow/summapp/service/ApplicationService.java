package com.leeknow.summapp.service;

import com.leeknow.summapp.dto.ApplicationRequestDTO;
import com.leeknow.summapp.dto.ApplicationResponseDTO;
import com.leeknow.summapp.dto.DataSearchDTO;
import com.leeknow.summapp.dto.UserResponseDTO;
import com.leeknow.summapp.entity.Application;
import com.leeknow.summapp.entity.User;
import com.leeknow.summapp.enums.ApplicationStatus;
import com.leeknow.summapp.enums.EventType;
import com.leeknow.summapp.repository.ApplicationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final UserService userService;
    private final EventService eventService;

    public Map<String, Page<ApplicationResponseDTO>> findAll(DataSearchDTO searchDTO) {
        Map<String, Page<ApplicationResponseDTO>> result = new HashMap<>();
        Page<Application> applications = applicationRepository.findAll(PageRequest.of(
                searchDTO.getPage(),
                searchDTO.getSize(),
                Sort.by(searchDTO.getSort())));
        result.put("applications", applications.map(this::toResponseDtoApplication));
        return result;
    }

    public Map<String, Page<ApplicationResponseDTO>> findAllByCurrentUser(DataSearchDTO searchDTO) {
        Map<String, Page<ApplicationResponseDTO>> result = new HashMap<>();
        User user = userService.getCurrentUser();
        Page<Application> applications = applicationRepository.findAllByUser(user, PageRequest.of(
                searchDTO.getPage(),
                searchDTO.getSize(),
                Sort.by(searchDTO.getSort())));
        result.put("applications", applications.map(this::toResponseDtoApplication));
        return result;
    }

    public ApplicationResponseDTO findById(Integer id) {
        return toResponseDtoApplication(applicationRepository.findById(id).orElse(null));
    }

    public Map<String, ApplicationResponseDTO> save(ApplicationRequestDTO applicationRequestDTO) {
        Map<String, ApplicationResponseDTO> result = new HashMap<>();
        Application application = new Application();
        application.setUser(userService.getCurrentUser());
        application.setNumber(getRandomApplicationNumber());
        application.setStatusId(ApplicationStatus.CREATED.getId());
        application.setTypeId(applicationRequestDTO.getTypeId());
        application.setCreationDate(new Timestamp(System.currentTimeMillis()));
        application = applicationRepository.save(application);
        result.put("application", toResponseDtoApplication(application));
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

    private ApplicationResponseDTO toResponseDtoApplication(Application application) {
        if (application != null) {
            ApplicationResponseDTO responseDTO = new ApplicationResponseDTO();
            responseDTO.setApplicationId(application.getApplicationId());
            responseDTO.setNumber(application.getNumber());
            responseDTO.setCreationDate(formatDate(application.getCreationDate()));
            responseDTO.setFinishDate(formatDate(application.getFinishDate()));
            responseDTO.setStatusId(application.getStatusId());
            responseDTO.setTypeId(application.getTypeId());
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

    public Map<String, String> setStatus(Integer id, Integer status) {
        Map<String, String> result = new HashMap<>();
        Application application = applicationRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        application.setStatusId(status);
        applicationRepository.save(application);
        result.put("message", "Заявка успешно обновлена!");
        eventService.create(EventType.APPLICATION_STATUS_CHANGED.getId(), application);
        return result;
    }
}
