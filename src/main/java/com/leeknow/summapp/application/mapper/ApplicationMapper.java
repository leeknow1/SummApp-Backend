package com.leeknow.summapp.application.mapper;

import com.leeknow.summapp.application.dto.ApplicationResponseDTO;
import com.leeknow.summapp.application.entity.Application;
import com.leeknow.summapp.application.enums.ApplicationStatus;
import com.leeknow.summapp.application.enums.ApplicationType;
import com.leeknow.summapp.common.enums.Language;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.leeknow.summapp.user.mapper.UserMapper.toResponseDtoUser;

public class ApplicationMapper {

    public static ApplicationResponseDTO toResponseDtoApplication(Application application, Language language) {
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

    public static List<ApplicationResponseDTO> toResponseDtoApplication(List<Application> applications, Language language) {
        if (applications != null) {
            return applications.stream().map(application -> toResponseDtoApplication(application, language)).collect(Collectors.toList());
        }
        return null;
    }

    public static String formatDate(Timestamp date) {
        if (date != null) {
            return new SimpleDateFormat("yyyy-MM-dd HH:ss").format(new Date(date.getTime()));
        }
        return "";
    }
}
