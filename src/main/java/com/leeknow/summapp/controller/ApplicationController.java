package com.leeknow.summapp.controller;

import com.leeknow.summapp.dto.ApplicationRequestDTO;
import com.leeknow.summapp.dto.ApplicationResponseDTO;
import com.leeknow.summapp.dto.DataSearchDTO;
import com.leeknow.summapp.service.ApplicationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/application")
@RequiredArgsConstructor
public class ApplicationController {

    private final ApplicationService applicationService;

    @PostMapping("/all")
    public ResponseEntity<?> getALlApplication(@RequestBody DataSearchDTO searchDTO) {
        Map<String, Page<ApplicationResponseDTO>> result = applicationService.findAll(searchDTO);
        return ResponseEntity.ok().body(result);
    }

    @PostMapping("/user")
    public ResponseEntity<?> getAllApplicationByCurrentUser(@RequestBody DataSearchDTO searchDTO) {
        Map<String, Page<ApplicationResponseDTO>> result = applicationService.findAllByCurrentUser(searchDTO);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getApplicationById(@PathVariable("id") Integer id) {
        Map<String, Object> result = applicationService.findById(id);
        return ResponseEntity.status((int) result.get("status")).body(result);
    }

    @PostMapping
    public ResponseEntity<?> createApplication(@RequestBody @Valid ApplicationRequestDTO applicationRequestDTO) {
        Map<String, ApplicationResponseDTO> result = applicationService.save(applicationRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }
}
