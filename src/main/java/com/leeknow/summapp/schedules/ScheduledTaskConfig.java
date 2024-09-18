package com.leeknow.summapp.schedules;

import com.leeknow.summapp.repository.ApplicationRepository;
import com.leeknow.summapp.repository.UserRepository;
import com.leeknow.summapp.service.LogService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ScheduledTaskConfig {

    @Bean(name = "monthly-report")
    public MonthlyReportSchedule monthlyReportSchedule(LogService logService,
                                                       UserRepository userRepository,
                                                       ApplicationRepository applicationRepository) {
        return new MonthlyReportSchedule(logService, userRepository, applicationRepository);
    }
}
