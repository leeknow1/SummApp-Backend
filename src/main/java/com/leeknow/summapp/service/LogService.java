package com.leeknow.summapp.service;

import com.leeknow.summapp.entity.Log;
import com.leeknow.summapp.repository.LogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
@Slf4j
@RequiredArgsConstructor
public class LogService {

    private final LogRepository logRepository;

    public void save(int type, Throwable exception) {
        Log logToSave = new Log();
        logToSave.setLogType(type);
        logToSave.setLogDate(new Timestamp(System.currentTimeMillis()));

        StringBuilder builder = new StringBuilder();
        builder.append(exception).append("\n");
        for (StackTraceElement element : exception.getStackTrace()) {
            builder.append(element.toString()).append("\n");
        }
        logToSave.setLog(builder.toString());

        logToSave = logRepository.save(logToSave);
        log.info("added log --> " + logToSave.getLogId(), exception);
    }

    public void save(int type, String message) {
        Log logToSave = new Log();
        logToSave.setLogType(type);
        logToSave.setLogDate(new Timestamp(System.currentTimeMillis()));
        logToSave.setLog(message);

        logToSave = logRepository.save(logToSave);
        log.info("system log --> " + logToSave.getLogId() + "," + message);
    }
}
