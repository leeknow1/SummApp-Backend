package com.leeknow.summapp.web.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

import static com.leeknow.summapp.web.constant.KafkaConstant.APPLICATION_STATUS;

@Configuration
public class KafkaConfig {

    @Bean
    public NewTopic applicationStatus() {
        return TopicBuilder
                .name(APPLICATION_STATUS)
                .partitions(1)
                .replicas(1)
                .build();
    }
}
