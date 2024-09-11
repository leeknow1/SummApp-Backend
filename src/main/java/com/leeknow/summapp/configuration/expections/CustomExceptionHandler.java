package com.leeknow.summapp.configuration.expections;

import com.leeknow.summapp.enums.LogType;
import com.leeknow.summapp.service.LogService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@RequiredArgsConstructor
public class CustomExceptionHandler {

    private final LogService log;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        Map<String, String> map = new HashMap<>();
        exception.getBindingResult().getFieldErrors().stream().findFirst().ifPresent(fieldError -> map.put("message", fieldError.getDefaultMessage()));
        log.save(LogType.NORMAL.getId(), exception);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(map);
    }

    @ExceptionHandler(UserAlreadyExistException.class)
    public ResponseEntity<?> handleUserAlreadyExistException(UserAlreadyExistException exception) {
        Map<String, String> map = new HashMap<>();
        map.put("message", exception.getMessage());
        log.save(LogType.NORMAL.getId(), exception);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(map);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<?> handleBadCredentialsException(BadCredentialsException exception) {
        Map<String, String> map = new HashMap<>();
        map.put("message", "Почта или пароль введены неверно.");
        log.save(LogType.NORMAL.getId(), exception);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(map);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> handleIllegalArgumentException(IllegalArgumentException exception) {
        Map<String, String> map = new HashMap<>();
        map.put("message", "Неверно введены или отсутствует данные.");
        log.save(LogType.NORMAL.getId(), exception);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(map);
    }

    @ExceptionHandler({HttpMessageNotReadableException.class, PropertyReferenceException.class, InvalidDataAccessApiUsageException.class})
    public ResponseEntity<?> handleHttpMessageNotReadableException(Throwable exception) {
        Map<String, String> map = new HashMap<>();
        map.put("message", "Запрос не может быть обработан.");
        log.save(LogType.CRITICAL.getId(), exception);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(map);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<?> handleAccessDeniedException(AccessDeniedException exception) {
        Map<String, String> map = new HashMap<>();
        map.put("message", exception.getMessage());
        log.save(LogType.NORMAL.getId(), exception);
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(map);
    }

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<?> handleAnyException(Throwable exception) {
        Map<String, String> map = new HashMap<>();
        map.put("message", "Произошла ошибка.");
        log.save(LogType.CRITICAL.getId(), exception);
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(map);
    }
}
