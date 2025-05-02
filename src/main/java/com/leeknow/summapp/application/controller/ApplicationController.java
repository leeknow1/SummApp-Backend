package com.leeknow.summapp.application.controller;

import com.leeknow.summapp.application.dto.ApplicationRequestDTO;
import com.leeknow.summapp.application.dto.ApplicationResponseDTO;
import com.leeknow.summapp.application.dto.ApplicationSearchDTO;
import com.leeknow.summapp.application.service.ApplicationService;
import com.leeknow.summapp.common.dto.DataSearchDTO;
import com.leeknow.summapp.common.enums.Language;
import com.leeknow.summapp.module.annotation.ModuleChecker;
import com.leeknow.summapp.module.enums.ModuleEnums;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/application")
@RequiredArgsConstructor
@ModuleChecker(ModuleEnums.APPLICATIONS)
public class ApplicationController {

    private final ApplicationService applicationService;

    @PostMapping("/all")
    public ResponseEntity<?> getAllApplication(@RequestBody ApplicationSearchDTO searchDTO,
                                               @RequestHeader(name = "Accept-Language", defaultValue = "ru") String lang) {
        Map<String, Object> result =
                applicationService.findAll(searchDTO, Language.getLanguageByCode((lang)));
        return ResponseEntity.ok().body(result);
    }

    @PostMapping("/user")
    public ResponseEntity<?> getAllApplicationByCurrentUser(@RequestBody DataSearchDTO searchDTO,
                                                            @RequestHeader(name = "Accept-Language", defaultValue = "ru") String lang) {
        Map<String, Object> result =
                applicationService.findAllByCurrentUser(searchDTO, Language.getLanguageByCode((lang)));
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getApplicationById(@PathVariable("id") Integer id,
                                                @RequestHeader(name = "Accept-Language", defaultValue = "ru") String lang) {
        ApplicationResponseDTO application = applicationService.findById(id, Language.getLanguageByCode((lang)));
        Map<String, Object> result = new HashMap<>();
        result.put("application", application);
        result.put("status", application == null ? HttpStatus.NOT_FOUND.value() : HttpStatus.OK.value());
        return ResponseEntity.status((int) result.get("status")).body(result);
    }

    @PostMapping
    public ResponseEntity<?> createApplication(@RequestBody @Valid ApplicationRequestDTO applicationRequestDTO,
                                               @RequestHeader(name = "Accept-Language", defaultValue = "ru") String lang) {
        Map<String, ApplicationResponseDTO> result =
                applicationService.save(applicationRequestDTO, Language.getLanguageByCode((lang)));
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @PostMapping("/{id}/status")
    public ResponseEntity<?> setApplicationStatus(@PathVariable("id") Integer id,
                                                  @RequestBody Integer status,
                                                  @RequestHeader(name = "Accept-Language", defaultValue = "ru") String lang) {
        Map<String, String> result = applicationService.setStatus(id, status, Language.getLanguageByCode((lang)));
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }
}
