package com.leeknow.summapp.schedule.config;

import com.leeknow.summapp.application.repository.ApplicationRepository;
import com.leeknow.summapp.exchangerates.repository.ExchangeRatesRepository;
import com.leeknow.summapp.schedule.schedules.ExchangeRatesAPISchedule;
import com.leeknow.summapp.schedule.schedules.MonthlyReportSchedule;
import com.leeknow.summapp.user.repository.UserRepository;
import com.leeknow.summapp.log.service.LogService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class ScheduledTaskConfig {

    @Bean(name = "monthly-report")
    public MonthlyReportSchedule monthlyReportSchedule(LogService logService,
                                                       UserRepository userRepository,
                                                       ApplicationRepository applicationRepository) {
        return new MonthlyReportSchedule(logService, userRepository, applicationRepository);
    }

    @Bean(name = "exchange-rates")
    public ExchangeRatesAPISchedule exchangeRatesAPISchedule(WebClient webClient,
                                                             LogService logService,
                                                             ExchangeRatesRepository exchangeRatesRepository) {
        return new ExchangeRatesAPISchedule(webClient, logService, exchangeRatesRepository);
    }
}
