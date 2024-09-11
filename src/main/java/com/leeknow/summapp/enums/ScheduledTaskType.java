package com.leeknow.summapp.enums;

import com.leeknow.summapp.schedules.AbstractScheduledTask;
import com.leeknow.summapp.schedules.OneMinuteSchedule;
import lombok.Getter;

@Getter
public enum ScheduledTaskType {
    ONE_MINUTE_TASK("one-minute", new OneMinuteSchedule()),
    ;

    private final String name;
    private final AbstractScheduledTask scheduledTask;

    ScheduledTaskType(String name, AbstractScheduledTask scheduledTask) {
        this.name = name;
        this.scheduledTask = scheduledTask;
    }

    public static AbstractScheduledTask getByName(String name) {
        for (ScheduledTaskType taskType : values()) {
            if (taskType.getName().equals(name)) {
                return taskType.getScheduledTask();
            }
        }
        return null;
    }
}
