package com.leeknow.summapp.service;

import com.leeknow.summapp.entity.Schedule;
import com.leeknow.summapp.enums.LogType;
import com.leeknow.summapp.enums.ScheduledTaskType;
import com.leeknow.summapp.repository.ScheduleRepository;
import com.leeknow.summapp.schedules.AbstractScheduledTask;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final LogService log;

    private final TaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
    private final Map<String, ScheduledFuture<?>> scheduledTasks = new HashMap<>();

    @PostConstruct
    public void init() {
        ((ThreadPoolTaskScheduler) taskScheduler).initialize();
        startAllSchedules();
    }

    private void startAllSchedules() {
        List<Schedule> schedules = scheduleRepository.findAll();
        for (Schedule schedule : schedules) {
            AbstractScheduledTask scheduledTask = getTask(schedule.getScheduleName());
            if (scheduledTask == null) {
                throw new RuntimeException("В системе есть задачи, для которых не прописана реализация!");
            } else {
                startTask(scheduledTask, schedule);
            }
        }
    }

    private void startTask(AbstractScheduledTask scheduledTask, Schedule schedule) {
        if (schedule.getEnable()) {
            log.save(LogType.SYSTEM.getId(), String.format("Запущена задача %s", schedule.getScheduleName()));
            ScheduledFuture<?> future = taskScheduler.schedule(
                    scheduledTask::run, new CronTrigger(schedule.getCronExpression())
            );
            scheduledTasks.put(schedule.getScheduleName(), future);
        }
    }

    private void stopTask(String name) {
        log.save(LogType.SYSTEM.getId(), String.format("Остановлена задача %s", name));
        ScheduledFuture<?> future = scheduledTasks.get(name);
        if (future != null) {
            future.cancel(false);
            scheduledTasks.remove(name);
        }
    }

    private AbstractScheduledTask getTask(String name) {
        return ScheduledTaskType.getByName(name);
    }
}
