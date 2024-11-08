package com.leeknow.summapp.log.service;

import com.leeknow.summapp.log.entity.Log;
import com.leeknow.summapp.log.repository.LogRepository;
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
        log.info("ADDED LOG --> " + logToSave.getLogId(), exception);
    }

    public void save(int type, String message) {
        Log logToSave = new Log();
        logToSave.setLogType(type);
        logToSave.setLogDate(new Timestamp(System.currentTimeMillis()));
        logToSave.setLog(message);

        logToSave = logRepository.save(logToSave);
        log.info("SYSTEM LOG --> " + logToSave.getLogId() + "," + message);
    }
}
