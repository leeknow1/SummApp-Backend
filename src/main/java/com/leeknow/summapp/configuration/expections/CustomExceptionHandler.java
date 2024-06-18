package com.leeknow.summapp.configuration.expections;

import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        Map<String, String> map = new HashMap<>();
        exception.getBindingResult().getFieldErrors().stream().findFirst().ifPresent(fieldError -> map.put("message", fieldError.getDefaultMessage()));
        return map;
    }

    @ExceptionHandler(UserAlreadyExistException.class)
    public Map<String, String> handleUserAlreadyExistException(UserAlreadyExistException exception) {
        Map<String, String> map = new HashMap<>();
        map.put("message", exception.getMessage());
        return map;
    }

    @ExceptionHandler(BadCredentialsException.class)
    public Map<String, String> handleBadCredentialsException() {
        Map<String, String> map = new HashMap<>();
        map.put("message", "Почта или пароль введены неверно.");
        return map;
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public Map<String, String> handleIllegalArgumentException() {
        Map<String, String> map = new HashMap<>();
        map.put("message", "Неверно введены или отсутствует данные.");
        return map;
    }

    @ExceptionHandler({HttpMessageNotReadableException.class, PropertyReferenceException.class, InvalidDataAccessApiUsageException.class})
    public Map<String, String> handleHttpMessageNotReadableException() {
        Map<String, String> map = new HashMap<>();
        map.put("message", "Запрос не может быть обработан.");
        return map;
    }
}
