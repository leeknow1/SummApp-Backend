package com.leeknow.summapp.event.controller;

import com.leeknow.summapp.common.dto.DataSearchDTO;
import com.leeknow.summapp.event.entity.Event;
import com.leeknow.summapp.event.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    @PostMapping
    public ResponseEntity<?> findAllEvents(@RequestBody DataSearchDTO searchDTO) {
        Map<String, Page<Event>> result = eventService.findAll(searchDTO);
        return ResponseEntity.ok().body(result);
    }
}
