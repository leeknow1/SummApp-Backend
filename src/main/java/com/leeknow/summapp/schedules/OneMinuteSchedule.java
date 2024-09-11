package com.leeknow.summapp.schedules;

public class OneMinuteSchedule extends AbstractScheduledTask {

    @Override
    public void run() {
        System.out.println("Run every one minute!");
    }
}
