package com.leeknow.summapp.file.service;

import com.leeknow.summapp.application.entity.Application;
import com.leeknow.summapp.application.repository.ApplicationRepository;
import com.leeknow.summapp.common.enums.Language;
import com.leeknow.summapp.file.entity.FileEntity;
import com.leeknow.summapp.file.repository.FileRepository;
import com.leeknow.summapp.message.service.MessageUtils;
import com.leeknow.summapp.role.enums.RoleEnums;
import com.leeknow.summapp.user.entity.User;
import com.leeknow.summapp.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static com.leeknow.summapp.file.constant.FileMessageConstant.FILE_IS_FORBIDDEN;
import static com.leeknow.summapp.file.constant.FileMessageConstant.FILE_SAVED;

@Service
@RequiredArgsConstructor
public class FileService {

    private final FileRepository fileRepository;
    private final ApplicationRepository applicationRepository;
    private final UserService userService;
    private final MessageUtils messageUtils;

    @CachePut(value = "FILE_CACHE", key = "#result.fileId")
    public FileEntity uploadFile(Integer applicationId, MultipartFile multipartFile, Language language) throws IOException {

        FileEntity file = new FileEntity();
        file.setFileName(multipartFile.getOriginalFilename());
        file.setFileType(multipartFile.getContentType());
        file.setFileSize(multipartFile.getSize());
        file.setContent(multipartFile.getBytes());

        Optional<Application> application = applicationRepository.findById(applicationId);
        if (application.isPresent()) {
            file.setApplication(application.get());
            file.setUser(application.get().getUser());
        } else {
            throw new IllegalArgumentException(); // Если заявка не найдена
        }

        if (!file.getFileName().endsWith(".pdf")) {
            throw new IllegalArgumentException(); // Неверный формат файла
        }
        file = fileRepository.save(file);
        return file;
    }

    @Cacheable(value = "FILE_CACHE", key = "#id")
    public Optional<FileEntity> getFile(Integer id, Language lang) {
        User user = userService.getCurrentUser();
        Optional<FileEntity> file = fileRepository.findById(id);

        if (file.isPresent()
                && user.getRoles().stream().anyMatch(role -> role.getRoleId().equals(RoleEnums.USER.getRoleId()))
                && !file.get().getUser().getUserId().equals(user.getUserId())) {
            throw new AccessDeniedException(messageUtils.getMessage(lang, FILE_IS_FORBIDDEN));
        }
        return file;
    }
}
