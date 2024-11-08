package com.leeknow.summapp.schedule.controller;

import com.leeknow.summapp.common.enums.Language;
import com.leeknow.summapp.schedule.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.leeknow.summapp.schedule.constant.ScheduleMessageConstant.RESTART_ALL_TASK_SUCCESSFULLY;
import static com.leeknow.summapp.schedule.constant.ScheduleMessageConstant.STOPPED_ALL_TASKS_SUCCESSFULLY;
import static com.leeknow.summapp.message.service.MessageService.getMessage;

@RestController
@RequestMapping("/schedule")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService service;

    @GetMapping("/restart")
    public ResponseEntity<?> restartAllScheduleTasks(@RequestHeader(name = "Accept-Language", defaultValue = "1") String lang) {
        service.startAllSchedules();
        return ResponseEntity.ok(getMessage(Language.getLanguageById(lang), RESTART_ALL_TASK_SUCCESSFULLY));
    }

    @GetMapping("/start/{id}")
    public ResponseEntity<?> startScheduleTask(@PathVariable Integer id,
                                               @RequestHeader(name = "Accept-Language", defaultValue = "1") String lang) {
        String message = service.startOneTask(id, Language.getLanguageById(lang));
        return ResponseEntity.ok(message);
    }

    @GetMapping("/stop/{id}")
    public ResponseEntity<?> stopScheduleTask(@PathVariable Integer id,
                                              @RequestHeader(name = "Accept-Language", defaultValue = "1") String lang) {
        String message = service.stopOneTask(id, Language.getLanguageById(lang));
        return ResponseEntity.ok(message);
    }

    @GetMapping("/stop-all")
    public ResponseEntity<?> stopAllScheduleTasks(@RequestHeader(name = "Accept-Language", defaultValue = "1") String lang) {
        service.stopAllSchedules();
        return ResponseEntity.ok(getMessage(Language.getLanguageById(lang), STOPPED_ALL_TASKS_SUCCESSFULLY));
    }
}
