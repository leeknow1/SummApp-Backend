package com.leeknow.summapp.user.service;

import com.leeknow.summapp.application.dto.ApplicationResponseDTO;
import com.leeknow.summapp.application.entity.Application;
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
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.leeknow.summapp.application.mapper.ApplicationMapper.toResponseDtoApplication;
import static com.leeknow.summapp.user.mapper.UserMapper.toUserDTO;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final ApplicationRepository applicationRepository;

    public Map<String, Page<User>> findAll(DataSearchDTO searchDTO) {
        return null;
    }

    @Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
    public List<UserDTO> findAll() {
        return toUserDTO(userRepository.findAll());
    }

    @Transactional(readOnly = true, isolation = Isolation.REPEATABLE_READ)
    public Optional<User> findById(Integer id) {
        return userRepository.findById(id);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public User save(User user) {
        user = userRepository.save(user);
        return user;
    }

    public Map<String, User> update(User user) {
        return null;
    }

    public void delete(Integer id) {
        userRepository.deleteById(id);
    }

    @Transactional(readOnly = true, isolation = Isolation.REPEATABLE_READ)
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User getCurrentUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
    public List<ApplicationResponseDTO> findApplicationByUser(UserDTO user) {
        Optional<User> optionalUser = findById(user.getUserId());
        List<ApplicationResponseDTO> result = new ArrayList<>();

        if (optionalUser.isPresent()) {
            List<Application> applications = applicationRepository.findAllByUser(optionalUser.get());
            result.addAll(toResponseDtoApplication(applications, Language.RUSSIAN));
        }
        return result;
    }
}
