package com.leeknow.summapp.service;

import com.leeknow.summapp.entity.Schedule;
import com.leeknow.summapp.enums.LogType;
import com.leeknow.summapp.repository.ScheduleRepository;
import com.leeknow.summapp.schedules.AbstractScheduledTask;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ScheduledFuture;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final LogService log;
    private final ApplicationContext context;

    private final TaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
    private final Map<String, ScheduledFuture<?>> scheduledTasks = new HashMap<>();

    @PostConstruct
    public void init() {
        ((ThreadPoolTaskScheduler) taskScheduler).initialize();
        startAllSchedules();
    }

    public void startAllSchedules() {
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
            if (scheduledTasks.containsKey(schedule.getScheduleName())) {
                stopTask(schedule.getScheduleName());
            }
            ScheduledFuture<?> future = taskScheduler.schedule(
                    scheduledTask::run, new CronTrigger(schedule.getCronExpression())
            );
            log.save(LogType.SYSTEM.getId(), String.format("Запущена задача %s", schedule.getScheduleName()));
            scheduledTasks.put(schedule.getScheduleName(), future);
        }
    }

    private void stopTask(String name) {
        ScheduledFuture<?> future = scheduledTasks.get(name);
        if (future != null) {
            future.cancel(false);
            log.save(LogType.SYSTEM.getId(), String.format("Остановлена задача %s", name));
            scheduledTasks.remove(name);
        }
    }

    public String startOneTask(Integer id) {
        Optional<Schedule> schedule = scheduleRepository.findById(id);
        if (schedule.isPresent()) {
            AbstractScheduledTask scheduledTask = getTask(schedule.get().getScheduleName());
            if (scheduledTask == null) {
                throw new RuntimeException(String.format("Для задачи %s не прописана реализация!", schedule.get().getScheduleName()));
            } else {
                startTask(scheduledTask, schedule.get());
            }
            return "Задача успешна запущена!";
        } else {
            return "Не удалось найти задачу в базе данных!";
        }
    }

    public String stopOneTask(Integer id) {
        Optional<Schedule> schedule = scheduleRepository.findById(id);
        if (schedule.isPresent()) {
            stopTask(schedule.get().getScheduleName());
            return "Задача успешна остановлена!";
        } else {
            return "Не удалось найти задачу в базе данных!";
        }
    }

    public void stopAllSchedules() {
        List<Schedule> schedules = scheduleRepository.findAll();
        for (Schedule schedule : schedules) {
            stopTask(schedule.getScheduleName());
        }
    }

    private AbstractScheduledTask getTask(String name) {
        AbstractScheduledTask task;
         try {
             task = (AbstractScheduledTask) context.getBean(name);
         } catch (BeansException exception) {
             throw new RuntimeException("Ошибка с бинами при запуске задач!");
         }
         return task;
    }
}
