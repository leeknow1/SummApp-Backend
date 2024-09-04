package com.leeknow.summapp.service;

import com.leeknow.summapp.entity.Application;
import com.leeknow.summapp.entity.FileEntity;
import com.leeknow.summapp.entity.User;
import com.leeknow.summapp.enums.Role;
import com.leeknow.summapp.repository.ApplicationRepository;
import com.leeknow.summapp.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FileService {

    private final FileRepository fileRepository;
    private final ApplicationRepository applicationRepository;
    private final UserService userService;

    public Map<String, Object> uploadFile(Integer applicationId, MultipartFile multipartFile) throws IOException {
        Map<String, Object> result = new HashMap<>();

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

        fileRepository.save(file);
        result.put("message", "Файл сохранен.");
        return result;
    }

    public Optional<FileEntity> getFile(Integer id) {
        User user = userService.getCurrentUser();
        Optional<FileEntity> file = fileRepository.findById(id);

        if (file.isPresent()
                && user.getRoleId().equals(Role.USER.getRoleId())
                && !file.get().getUser().getUserId().equals(user.getUserId())) {
            throw new AccessDeniedException("Доступ к файлу запрещен.");
        }
        return file;
    }
}
