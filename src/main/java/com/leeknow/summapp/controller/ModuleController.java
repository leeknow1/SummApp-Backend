package com.leeknow.summapp.controller;

import com.leeknow.summapp.constant.ModuleMessageConstant;
import com.leeknow.summapp.dto.RoleRightDTO;
import com.leeknow.summapp.enums.Language;
import com.leeknow.summapp.service.MessageService;
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
    public ResponseEntity<?> setRights(@PathVariable Integer id,
                                       @RequestBody List<RoleRightDTO> roles,
                                       @RequestHeader(name = "Accept-Language", defaultValue = "1") String lang) {
        moduleService.setUsersRight(id, roles);
        return ResponseEntity.ok(MessageService.getMessage(Language.getLanguageById(lang), ModuleMessageConstant.RIGHT_SUCCESSFULLY_ESTABLISHED));
    }
}
