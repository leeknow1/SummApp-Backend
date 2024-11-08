package com.leeknow.summapp.module.controller;

import com.leeknow.summapp.role.dto.RoleRightDTO;
import com.leeknow.summapp.common.enums.Language;
import com.leeknow.summapp.module.service.ModuleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.leeknow.summapp.module.constant.ModuleMessageConstant.RIGHT_SUCCESSFULLY_ESTABLISHED;
import static com.leeknow.summapp.message.service.MessageService.getMessage;

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
        return ResponseEntity.ok(getMessage(Language.getLanguageById(lang), RIGHT_SUCCESSFULLY_ESTABLISHED));
    }
}
