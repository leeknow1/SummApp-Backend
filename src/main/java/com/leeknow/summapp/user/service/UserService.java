package com.leeknow.summapp.user.service;

import com.leeknow.summapp.application.dto.ApplicationResponseDTO;
import com.leeknow.summapp.application.entity.Application;
import com.leeknow.summapp.application.mapper.ApplicationMapper;
import com.leeknow.summapp.application.repository.ApplicationRepository;
import com.leeknow.summapp.common.dto.DataSearchDTO;
import com.leeknow.summapp.common.enums.Language;
import com.leeknow.summapp.user.dto.UserDTO;
import com.leeknow.summapp.user.entity.User;
import com.leeknow.summapp.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.leeknow.summapp.user.mapper.UserMapper.toUserDTO;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final ApplicationRepository applicationRepository;

    public Map<String, Page<User>> findAll(DataSearchDTO searchDTO) {
        return null;
    }

    public List<UserDTO> findAll() {
        List<User> all = userRepository.findAll();
        List<UserDTO> users = new ArrayList<>();
        for (User user : all) {
            users.add(toUserDTO(user));
        }
        return users;
    }

    public Optional<User> findById(Integer id) {
        return userRepository.findById(id);
    }

    public Map<String, User> save(User user) {
        Map<String, User> result = new HashMap<>();
        result.put("user", userRepository.save(user));
        return result;
    }

    public Map<String, User> update(User user) {
        return null;
    }

    public void delete(Integer id) {
        userRepository.deleteById(id);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(email);
    }

    public List<ApplicationResponseDTO> findApplicationByUser(UserDTO user) {
        Optional<User> optionalUser = findById(user.getUserId());
        List<ApplicationResponseDTO> result = new ArrayList<>();

        if (optionalUser.isPresent()) {
            List<Application> applications = applicationRepository.findAllByUser(optionalUser.get());
            result.addAll(ApplicationMapper.toResponseDtoApplication(applications, Language.RUSSIAN));
        }
        return result;
    }
}
