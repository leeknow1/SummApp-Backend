package com.leeknow.summapp.service;

import com.leeknow.summapp.entity.Application;
import com.leeknow.summapp.entity.User;
import com.leeknow.summapp.repository.ApplicationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ApplicationService implements CommonService<Application> {

    private final ApplicationRepository applicationRepository;
    private final UserService userService;

    @Override
    public Page<Application> findAll(Pageable pageable) {
        return applicationRepository.findAll(pageable);
    }

    @Override
    public Application findById(Integer id) {
        return applicationRepository.findById(id).orElse(null);
    }

    @Override
    public Application save(Application application) {
        return applicationRepository.save(application);
    }

    @Override
    public Application update(Application application) {
        return applicationRepository.save(application);
    }

    @Override
    public void delete(Integer id) {
        applicationRepository.deleteById(id);
    }

    public Page<Application> findAllByCurrentUser(Pageable pageable) {
        User user = userService.getCurrentUser();
        return applicationRepository.findAllByUser(user, pageable);
    }
}
