package com.leeknow.summapp.service;

import com.leeknow.summapp.dto.DataSearchDTO;
import com.leeknow.summapp.entity.Application;
import com.leeknow.summapp.entity.Event;
import com.leeknow.summapp.entity.User;
import com.leeknow.summapp.enums.ApplicationStatus;
import com.leeknow.summapp.enums.EventType;
import com.leeknow.summapp.repository.EventRepository;
import lombok.RequiredArgsConstructor;
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
        event.setDescription(getDescription(typeId, user, application));
        eventRepository.save(event);
    }

    private String getDescription(Integer typeId, User user, Application application) {
        if (typeId == EventType.SIGNING_IN.getId()) {
            return "Пользователь " + getUserFullName(user) + "(" + user.getUserId() + ") вошёл в систему.";
        } else if (typeId == EventType.NEW_USER.getId()) {
            return "Создан новый пользователь " + getUserFullName(user) + "(" + user.getUserId() + ").";
        } else if (typeId == EventType.APPLICATION_CREATED.getId()) {
            return "Создана новая заявка " + application.getNumber() + "(" + application.getApplicationId() +
                    ") от пользователя" + getUserFullName(user) + "(" + user.getUserId() + ").";
        } else if (typeId == EventType.APPLICATION_STATUS_CHANGED.getId()) {
            return "Присвоен новый статус \"" + ApplicationStatus.getNameById(application.getStatusId()) + "\" для заявки "
                    + application.getNumber() + "(" + application.getApplicationId() +
                    ") от пользователя " + getUserFullName(user) + "(" + user.getUserId() + ").";
        } else {
            return "";
        }
    }

    private String getUserFullName(User user) {
        return user.getLastName() + " " + user.getFirstName() + (user.getMiddleName().isBlank() ? "" : " " + user.getMiddleName());
    }

}
