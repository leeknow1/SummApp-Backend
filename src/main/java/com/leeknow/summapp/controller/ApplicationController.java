package com.leeknow.summapp.controller;

import com.leeknow.summapp.dto.ApplicationRequestDTO;
import com.leeknow.summapp.dto.ApplicationResponseDTO;
import com.leeknow.summapp.dto.DataSearchDTO;
import com.leeknow.summapp.enums.Language;
import com.leeknow.summapp.enums.ModuleEnums;
import com.leeknow.summapp.interfaces.ModuleChecker;
import com.leeknow.summapp.service.ApplicationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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
    public ResponseEntity<?> getAllApplication(@RequestBody DataSearchDTO searchDTO,
                                               @RequestHeader(name = "Accept-Language", defaultValue = "1") String lang) {
        Map<String, Page<ApplicationResponseDTO>> result =
                applicationService.findAll(searchDTO, Language.getLanguageById(Integer.parseInt(lang)));
        return ResponseEntity.ok().body(result);
    }

    @PostMapping("/user")
    public ResponseEntity<?> getAllApplicationByCurrentUser(@RequestBody DataSearchDTO searchDTO,
                                                            @RequestHeader(name = "Accept-Language", defaultValue = "1") String lang) {
        Map<String, Page<ApplicationResponseDTO>> result =
                applicationService.findAllByCurrentUser(searchDTO, Language.getLanguageById(Integer.parseInt(lang)));
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getApplicationById(@PathVariable("id") Integer id,
                                                @RequestHeader(name = "Accept-Language", defaultValue = "1") String lang) {
        ApplicationResponseDTO application = applicationService.findById(id, Language.getLanguageById(Integer.parseInt(lang)));
        Map<String, Object> result = new HashMap<>();
        result.put("application", application);
        result.put("status", application == null ? HttpStatus.NOT_FOUND.value() : HttpStatus.OK.value());
        return ResponseEntity.status((int) result.get("status")).body(result);
    }

    @PostMapping
    public ResponseEntity<?> createApplication(@RequestBody @Valid ApplicationRequestDTO applicationRequestDTO,
                                               @RequestHeader(name = "Accept-Language", defaultValue = "1") String lang) {
        Map<String, ApplicationResponseDTO> result =
                applicationService.save(applicationRequestDTO, Language.getLanguageById(Integer.parseInt(lang)));
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @PostMapping("/{id}/status")
    public ResponseEntity<?> setApplicationStatus(@PathVariable("id") Integer id, @RequestBody Integer status) {
        Map<String, String> result = applicationService.setStatus(id, status);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }
}
