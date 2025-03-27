package com.leeknow.summapp.application.service;

import com.leeknow.summapp.application.dto.ApplicationKafkaDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

import static com.leeknow.summapp.web.constant.KafkaConstant.APPLICATION_STATUS;

@Service
@RequiredArgsConstructor
public class ApplicationKafkaService {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void updateApplicationStatus(int applicationId, int statusId) {
        ApplicationKafkaDTO dto = new ApplicationKafkaDTO(applicationId, statusId, getFormattedDate());
        kafkaTemplate.send(APPLICATION_STATUS, dto);
    }

    @KafkaListener(topics = "application-status", groupId = "application-group")
    public void listenApplicationStatus(ApplicationKafkaDTO status) {
        System.out.println(status);
    }

    private String getFormattedDate() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }
}
