package com.leeknow.summapp.service;

import com.leeknow.summapp.dto.DataSearchDTO;
import com.leeknow.summapp.entity.User;
import com.leeknow.summapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public Map<String, Page<User>> findAll(DataSearchDTO searchDTO) {
        return null;
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
}
