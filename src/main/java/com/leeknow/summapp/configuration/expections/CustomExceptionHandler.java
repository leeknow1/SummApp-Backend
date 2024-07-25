package com.leeknow.summapp.configuration.expections;

import com.leeknow.summapp.enums.LogType;
import com.leeknow.summapp.service.LogService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@RequiredArgsConstructor
public class CustomExceptionHandler {

    private final LogService logService;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        Map<String, String> map = new HashMap<>();
        exception.getBindingResult().getFieldErrors().stream().findFirst().ifPresent(fieldError -> map.put("message", fieldError.getDefaultMessage()));
        logService.save(LogType.NORMAL.getId(), exception);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(map);
    }

    @ExceptionHandler(UserAlreadyExistException.class)
    public ResponseEntity<?> handleUserAlreadyExistException(UserAlreadyExistException exception) {
        Map<String, String> map = new HashMap<>();
        map.put("message", exception.getMessage());
        logService.save(LogType.NORMAL.getId(), exception);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(map);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<?> handleBadCredentialsException(BadCredentialsException exception) {
        Map<String, String> map = new HashMap<>();
        map.put("message", "Почта или пароль введены неверно.");
        logService.save(LogType.NORMAL.getId(), exception);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(map);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> handleIllegalArgumentException(IllegalArgumentException exception) {
        Map<String, String> map = new HashMap<>();
        map.put("message", "Неверно введены или отсутствует данные.");
        logService.save(LogType.NORMAL.getId(), exception);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(map);
    }

    @ExceptionHandler({HttpMessageNotReadableException.class, PropertyReferenceException.class, InvalidDataAccessApiUsageException.class})
    public ResponseEntity<?> handleHttpMessageNotReadableException(Throwable exception) {
        Map<String, String> map = new HashMap<>();
        map.put("message", "Запрос не может быть обработан.");
        logService.save(LogType.CRITICAL.getId(), exception);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(map);
    }

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<?> handleAnyException(Throwable exception) {
        Map<String, String> map = new HashMap<>();
        map.put("message", "Произошла ошибка.");
        logService.save(LogType.CRITICAL.getId(), exception);
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(map);
    }
}
