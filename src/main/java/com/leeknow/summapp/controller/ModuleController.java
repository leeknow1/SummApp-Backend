package com.leeknow.summapp.controller;

import com.leeknow.summapp.dto.UserRightDTO;
import com.leeknow.summapp.service.ModuleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/module")
@RequiredArgsConstructor
public class ModuleController {

    private final ModuleService moduleService;

    @PostMapping("/{id}/rights")
    public ResponseEntity<?> setRights(@PathVariable Integer id, @RequestBody List<UserRightDTO> users) {
        moduleService.setUsersRight(id, users);
        return ResponseEntity.ok("Права успешно установлены");
    }
}
