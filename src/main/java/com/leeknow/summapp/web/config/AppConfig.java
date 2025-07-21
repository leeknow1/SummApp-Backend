package com.leeknow.summapp.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

import static com.leeknow.summapp.schedule.constant.ScheduleAPIConstant.EXCHANGE_RATE_BASE_URL;

@Configuration
public class AppConfig {

    @Bean
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }

    @Bean("webClient")
    public WebClient webClient() {
        return WebClient.builder().build();
    }

    @Bean("exchangeAPIWebClient")
    public WebClient exchangeAPIWebClient(WebClient.Builder builder) {
        return builder.baseUrl(EXCHANGE_RATE_BASE_URL).build();
    }
}
