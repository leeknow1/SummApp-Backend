package com.leeknow.summapp.web.expections;

import com.leeknow.summapp.common.enums.Language;
import com.leeknow.summapp.log.enums.LogType;
import com.leeknow.summapp.log.service.LogService;
import com.leeknow.summapp.message.service.MessageUtils;
import jakarta.servlet.http.HttpServletRequest;
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

import static com.leeknow.summapp.web.constant.ExceptionMessageConstant.*;

@RestControllerAdvice
@RequiredArgsConstructor
public class CustomExceptionHandler {

    private final LogService log;
    private final MessageUtils messageUtils;

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
    public ResponseEntity<?> handleBadCredentialsException(BadCredentialsException exception, HttpServletRequest request) {
        Map<String, String> map = getMapMessage(Language.getLanguageByCode(request.getHeader("Accept-Language")), EXCEPTION_EMAIL_OR_PASSWORD_INCORRECT);
        log.save(LogType.NORMAL.getId(), exception);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(map);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> handleIllegalArgumentException(IllegalArgumentException exception, HttpServletRequest request) {
        Map<String, String> map = getMapMessage(Language.getLanguageByCode(request.getHeader("Accept-Language")), EXCEPTION_INCORRECT_OR_NO_DATA);
        log.save(LogType.NORMAL.getId(), exception);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(map);
    }

    @ExceptionHandler({HttpMessageNotReadableException.class, PropertyReferenceException.class, InvalidDataAccessApiUsageException.class})
    public ResponseEntity<?> handleHttpMessageNotReadableException(Throwable exception, HttpServletRequest request) {
        Map<String, String> map = getMapMessage(Language.getLanguageByCode(request.getHeader("Accept-Language")), EXCEPTION_REQUEST_CAN_NOT_BE_HANDLED);
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
    public ResponseEntity<?> handleAnyException(Throwable exception, HttpServletRequest request) {
        Map<String, String> map = getMapMessage(Language.getLanguageByCode(request.getHeader("Accept-Language")), EXCEPTION_AN_ERROR_OCCUR);
        log.save(LogType.CRITICAL.getId(), exception);
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(map);
    }

    private Map<String, String> getMapMessage(Language language, String message) {
        Map<String, String> map = new HashMap<>();
        map.put("message", messageUtils.getMessage(language, message));
        return map;
    }
}
