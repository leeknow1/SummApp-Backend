package com.leeknow.summapp.event.service;

import com.leeknow.summapp.common.dto.DataSearchDTO;
import com.leeknow.summapp.application.entity.Application;
import com.leeknow.summapp.event.entity.Event;
import com.leeknow.summapp.user.entity.User;
import com.leeknow.summapp.application.enums.ApplicationStatus;
import com.leeknow.summapp.event.enums.EventType;
import com.leeknow.summapp.common.enums.Language;
import com.leeknow.summapp.event.repository.EventRepository;
import com.leeknow.summapp.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;
    private final UserService userService;

    public Map<String, Page<Event>> findAll(DataSearchDTO searchDTO) {
        Map<String, Page<Event>> result = new HashMap<>();
        Page<Event> events = eventRepository.findAllByEventTimeBetween(
                new Timestamp(searchDTO.getStart().getTime()),
                new Timestamp(searchDTO.getFinish().getTime()),
                PageRequest.of(
                        searchDTO.getPage(),
                        searchDTO.getSize(),
                        Sort.by(searchDTO.getSort())));
        result.put("events", events);
        return result;
    }

    public void create(Integer typeId) {
        create(typeId, null, null);
    }

    public void create(Integer typeId, User user) {
        create(typeId, user, null);
    }

    public void create(Integer typeId, Application application) {
        create(typeId, null, application);
    }

    public void create(Integer typeId, User user, Application application) {
        if (user == null) user = userService.getCurrentUser();
        Event event = new Event();
        event.setEventTime(new Timestamp(System.currentTimeMillis()));
        event.setTypeId(typeId);
        event.setDescriptionRu(getDescription(typeId, user, application, Language.RUSSIAN));
        event.setDescriptionKz(getDescription(typeId, user, application, Language.KAZAKH));
        eventRepository.save(event);
    }

    private String getDescription(Integer typeId, User user, Application application, Language language) {
        if (typeId == EventType.SIGNING_IN.getId()) {
            return String.format(getSigningInMessage(language), getUserFullName(user), user.getUserId());
        } else if (typeId == EventType.NEW_USER.getId()) {
            return String.format(getNewUserMessage(language), getUserFullName(user), user.getUserId());
        } else if (typeId == EventType.APPLICATION_CREATED.getId()) {
            return String.format(getApplicationCreatedMessage(language),
                    application.getNumber(), application.getApplicationId(), getUserFullName(user), user.getUserId());
        } else if (typeId == EventType.APPLICATION_STATUS_CHANGED.getId()) {
            return String.format(getApplicationStatusChangedMessage(language),
                    ApplicationStatus.getNameById(application.getStatusId(), language),
                    application.getNumber(), application.getApplicationId(),
                    getUserFullName(user), user.getUserId());
        } else {
            return "";
        }
    }

    private String getSigningInMessage(Language language) {
        return language.equals(Language.RUSSIAN) ? "Пользователь %s (id = %d) вошел в систему." : "Пайдаланушы %s (id = %d) жүйеге кірді.";
    }

    private String getNewUserMessage(Language language) {
        return language.equals(Language.RUSSIAN) ? "Создан новый пользователь %s (id = %d)." : "Жаңа %s пайдаланушысы жасалды (id = %d).";
    }

    private String getApplicationCreatedMessage(Language language) {
        return language.equals(Language.RUSSIAN) ? "Создана новая заявка \"%s\" (id = %d) от пользователя %s (id = %d)" :
                "Жаңа \"%s\" тапсырысы жасалды (id = %d) пайдаланушыдан %s (id = %d)";
    }

    private String getApplicationStatusChangedMessage(Language language) {
        return language.equals(Language.RUSSIAN) ? "Присвоен новый статус \"%s\" для заявки \"%s\" (id = %d) от пользователя %s (id = %d)." :
                "Жаңа күй \"%s\" үшін тағайындалды \"%s\" тапсырысы үшін (id = %d)  пайдаланушыдан %s (id = %d)";
    }

    private String getUserFullName(User user) {
        return user.getLastName() + " " + user.getFirstName() + (StringUtils.isBlank(user.getMiddleName()) ? "" : " " + user.getMiddleName());
    }

}
