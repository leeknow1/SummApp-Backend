package com.leeknow.summapp.service;

import com.leeknow.summapp.dto.DataSearchDTO;
import com.leeknow.summapp.entity.Application;
import com.leeknow.summapp.entity.User;
import com.leeknow.summapp.repository.ApplicationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ApplicationService implements CommonService<Application> {

    private final ApplicationRepository applicationRepository;
    private final UserService userService;

    @Override
    public Map<String, Page<Application>> findAll(DataSearchDTO searchDTO) {
        Map<String, Page<Application>> result = new HashMap<>();
        Page<Application> applications = applicationRepository.findAll(PageRequest.of(
                searchDTO.getPage(),
                searchDTO.getSize(),
                Sort.by(searchDTO.getSort())));
        result.put("applications", applications);
        return result;
    }

    @Override
    public Map<String, Application> findById(Integer id) {
        Map<String, Application> result = new HashMap<>();
        result.put("application", applicationRepository.findById(id).orElse(null));
        return result;
    }

    @Override
    public Map<String, Application> save(Application application) {
        Map<String, Application> result = new HashMap<>();
        result.put("application", applicationRepository.save(application));
        return result;
    }

    @Override
    public Map<String, Application> update(Application application) {
        Map<String, Application> result = new HashMap<>();
        result.put("application", applicationRepository.save(application));
        return result;
    }

    @Override
    public void delete(Integer id) {
        applicationRepository.deleteById(id);
    }

    public Map<String, Page<Application>> findAllByCurrentUser(DataSearchDTO searchDTO) {
        Map<String, Page<Application>> result = new HashMap<>();
        User user = userService.getCurrentUser();
        Page<Application> applications = applicationRepository.findAllByUser(user, PageRequest.of(
                searchDTO.getPage(),
                searchDTO.getSize(),
                Sort.by(searchDTO.getSort())));
        result.put("applications", applications);
        return result;
    }


}
