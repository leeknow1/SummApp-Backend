package com.leeknow.summapp.service;

import com.leeknow.summapp.dto.RoleRightDTO;
import com.leeknow.summapp.repository.ModuleRepository;
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
