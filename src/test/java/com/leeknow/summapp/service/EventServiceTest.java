package com.leeknow.summapp.service;

import com.leeknow.summapp.dto.DataSearchDTO;
import com.leeknow.summapp.entity.Application;
import com.leeknow.summapp.entity.Event;
import com.leeknow.summapp.entity.User;
import com.leeknow.summapp.enums.EventType;
import com.leeknow.summapp.repository.EventRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EventServiceTest {

    @InjectMocks
    private EventService eventService;

    @Mock
    private EventRepository eventRepository;

    @Mock
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void findAll() {
        //given
        DataSearchDTO searchDTO = new DataSearchDTO();
        searchDTO.setSize(10);
        searchDTO.setPage(0);
        searchDTO.setSort("eventId");
        searchDTO.setStart(new Date(System.currentTimeMillis()));
        searchDTO.setFinish(new Date(System.currentTimeMillis()));

        Event event = new Event();
        Event event2 = new Event();

        Page<Event> events = new PageImpl<>(List.of(event, event2));

        //Mock the calls
        when(eventRepository.findAllByEventTimeBetween(
                new Timestamp(searchDTO.getStart().getTime()),
                new Timestamp(searchDTO.getFinish().getTime()),
                PageRequest.of(
                        searchDTO.getPage(),
                        searchDTO.getSize(),
                        Sort.by(searchDTO.getSort())))).thenReturn(events);

        //when
        Map<String, Page<Event>> result = eventService.findAll(searchDTO);

        //then
        assertNotNull(result);
        assertTrue(result.containsKey("events"));
        assertNotNull(result.get("events"));
        assertEquals(result.get("events").getTotalElements(), 2);
        assertNotNull(result.get("events").getContent());
        assertTrue(result.get("events").getContent().containsAll(List.of(event, event2)));

        //Verify
        verify(eventRepository, times(1)).findAllByEventTimeBetween(
                new Timestamp(searchDTO.getStart().getTime()),
                new Timestamp(searchDTO.getFinish().getTime()),
                PageRequest.of(
                        searchDTO.getPage(),
                        searchDTO.getSize(),
                        Sort.by(searchDTO.getSort())));
    }

    @Test
    void create() {
        //given
        User user = new User();
        user.setLastName("Тестовый");
        user.setFirstName("Тестовый");
        user.setMiddleName("Тестовый");

        Application application = new Application();

        Event event = new Event();
        event.setEventTime(new Timestamp(System.currentTimeMillis()));
        event.setTypeId(EventType.NEW_USER.getId());
        event.setDescription("Описание");

        Event createdEvent = new Event();
        createdEvent.setEventId(1);
        createdEvent.setEventTime(event.getEventTime());
        createdEvent.setTypeId(EventType.NEW_USER.getId());
        createdEvent.setDescription("Описание");

        //Mock the calls
        when(eventRepository.save(event)).thenReturn(createdEvent);

        //when
        eventService.create(1, user, application);

        //then
        assertEquals(event.getEventTime(), createdEvent.getEventTime());
        assertEquals(event.getDescription(), createdEvent.getDescription());
        assertEquals(event.getTypeId(), createdEvent.getTypeId());
        assertEquals(createdEvent.getEventId(), 1);

        //Verify
        verify(eventRepository, times(1)).save(any(Event.class));
    }
}