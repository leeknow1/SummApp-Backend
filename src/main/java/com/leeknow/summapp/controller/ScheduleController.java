package com.leeknow.summapp.controller;

import com.leeknow.summapp.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/schedule")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService service;

    @GetMapping("/restart")
    public ResponseEntity<?> restartAllScheduleTasks() {
        service.startAllSchedules();
        return ResponseEntity.ok("Перезапуск всех задач в системе завершен.");
    }

    @GetMapping("/start/{id}")
    public ResponseEntity<?> startScheduleTask(@PathVariable Integer id) {
        String message = service.startOneTask(id);
        return ResponseEntity.ok(message);
    }

    @GetMapping("/stop/{id}")
    public ResponseEntity<?> stopScheduleTask(@PathVariable Integer id) {
        String message = service.stopOneTask(id);
        return ResponseEntity.ok(message);
    }

    @GetMapping("/stop-all")
    public ResponseEntity<?> stopAllScheduleTasks() {
        service.stopAllSchedules();
        return ResponseEntity.ok("Успешно остановлены все задачи в системе.");
    }
}
