package com.leeknow.summapp.schedule.controller;

import com.leeknow.summapp.common.enums.Language;
import com.leeknow.summapp.message.service.MessageUtils;
import com.leeknow.summapp.schedule.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.leeknow.summapp.schedule.constant.ScheduleMessageConstant.SCHEDULE_RESTART_ALL_TASK_SUCCESSFULLY;
import static com.leeknow.summapp.schedule.constant.ScheduleMessageConstant.SCHEDULE_STOPPED_ALL_TASKS_SUCCESSFULLY;

@RestController
@RequestMapping("/schedule")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService service;
    private final MessageUtils messageUtils;

    @GetMapping("/restart")
    public ResponseEntity<?> restartAllScheduleTasks(@RequestHeader(name = "Accept-Language", defaultValue = "ru") String lang) {
        service.startAllSchedules();
        return ResponseEntity.ok(messageUtils.getMessage(Language.getLanguageByCode(lang), SCHEDULE_RESTART_ALL_TASK_SUCCESSFULLY));
    }

    @GetMapping("/start/{id}")
    public ResponseEntity<?> startScheduleTask(@PathVariable Integer id,
                                               @RequestHeader(name = "Accept-Language", defaultValue = "ru") String lang) {
        String message = service.startOneTask(id, Language.getLanguageByCode(lang));
        return ResponseEntity.ok(message);
    }

    @GetMapping("/stop/{id}")
    public ResponseEntity<?> stopScheduleTask(@PathVariable Integer id,
                                              @RequestHeader(name = "Accept-Language", defaultValue = "ru") String lang) {
        String message = service.stopOneTask(id, Language.getLanguageByCode(lang));
        return ResponseEntity.ok(message);
    }

    @GetMapping("/stop-all")
    public ResponseEntity<?> stopAllScheduleTasks(@RequestHeader(name = "Accept-Language", defaultValue = "ru") String lang) {
        service.stopAllSchedules();
        return ResponseEntity.ok(messageUtils.getMessage(Language.getLanguageByCode(lang), SCHEDULE_STOPPED_ALL_TASKS_SUCCESSFULLY));
    }
}
