package com.leeknow.summapp.service;

import com.leeknow.summapp.dto.UserRightDTO;
import com.leeknow.summapp.repository.ModuleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ModuleService {

    private final ModuleRepository moduleRepository;

    public void setUsersRight(Integer moduleId, List<UserRightDTO> users) {
        for (UserRightDTO user : users) {
            moduleRepository.deleteUserRightForModule(moduleId, user.getUserId());
            moduleRepository.setUserRightForModule(moduleId, user.getUserId(), user.getRight());
        }
    }
}
