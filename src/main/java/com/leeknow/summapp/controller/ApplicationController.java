package com.leeknow.summapp.controller;

import com.leeknow.summapp.dto.ApplicationRequestDTO;
import com.leeknow.summapp.dto.ApplicationResponseDTO;
import com.leeknow.summapp.dto.DataSearchDTO;
import com.leeknow.summapp.entity.Application;
import com.leeknow.summapp.service.ApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/application")
@RequiredArgsConstructor
public class ApplicationController {

    private final ApplicationService applicationService;

    @PostMapping("/all")
    public Map<String, Page<ApplicationResponseDTO>> getALlApplication(@RequestBody DataSearchDTO searchDTO) {
        return applicationService.findAll(searchDTO);
    }

    @PostMapping("/user")
    public Map<String, Page<ApplicationResponseDTO>> getAllApplicationByCurrentUser(@RequestBody DataSearchDTO searchDTO) {
        return applicationService.findAllByCurrentUser(searchDTO);
    }

    @GetMapping("/{id}")
    public Map<String, ApplicationResponseDTO> getApplicationById(@PathVariable("id") Integer id) {
        return applicationService.findById(id);
    }

    @PostMapping
    public Map<String, ApplicationResponseDTO> createApplication(@RequestBody ApplicationRequestDTO applicationRequestDTO) {
        return applicationService.save(applicationRequestDTO);
    }
}
