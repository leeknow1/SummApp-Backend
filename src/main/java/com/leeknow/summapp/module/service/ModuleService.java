package com.leeknow.summapp.module.service;

import com.leeknow.summapp.module.repository.ModuleRepository;
import com.leeknow.summapp.role.dto.RoleRightDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ModuleService {

    private final ModuleRepository moduleRepository;

    public void setUsersRight(Integer moduleId, List<RoleRightDTO> roles) {
        for (RoleRightDTO role : roles) {
            moduleRepository.deleteUserRightForModule(moduleId, role.getRoleId());
            moduleRepository.setUserRightForModule(moduleId, role.getRoleId(), role.getRight());
        }
    }
}
