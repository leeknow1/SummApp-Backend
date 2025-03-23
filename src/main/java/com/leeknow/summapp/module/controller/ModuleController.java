package com.leeknow.summapp.module.controller;

import com.leeknow.summapp.common.enums.Language;
import com.leeknow.summapp.message.service.MessageUtils;
import com.leeknow.summapp.module.service.ModuleService;
import com.leeknow.summapp.role.dto.RoleRightDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.leeknow.summapp.module.constant.ModuleMessageConstant.MODULE_RIGHT_SUCCESSFULLY_ESTABLISHED;

@RestController
@RequestMapping("/module")
@RequiredArgsConstructor
public class ModuleController {

    private final ModuleService moduleService;
    private final MessageUtils messageUtils;

    @PostMapping("/{id}/rights")
    public ResponseEntity<?> setRights(@PathVariable Integer id,
                                       @RequestBody List<RoleRightDTO> roles,
                                       @RequestHeader(name = "Accept-Language", defaultValue = "ru") String lang) {
        moduleService.setUsersRight(id, roles);
        return ResponseEntity.ok(messageUtils.getMessage(Language.getLanguageByCode(lang), MODULE_RIGHT_SUCCESSFULLY_ESTABLISHED));
    }
}
