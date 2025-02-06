package com.leeknow.summapp.schedule.schedules;


import org.springframework.scheduling.annotation.Async;

public abstract class AbstractScheduledTask {

    @Async
    public abstract void run();
}
