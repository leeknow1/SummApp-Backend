package com.leeknow.summapp.service;

import com.leeknow.summapp.entity.FileEntity;
import com.leeknow.summapp.repository.FileRepository;
import lombok.RequiredArgsConstructor;
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

    public Map<String, Object> uploadFile(MultipartFile multipartFile) throws IOException {
        Map<String, Object> result = new HashMap<>();
        FileEntity file = new FileEntity();
        file.setFileName(multipartFile.getOriginalFilename());
        file.setFileType(multipartFile.getContentType());
        file.setFileSize(multipartFile.getSize());
        file.setContent(multipartFile.getBytes());

        fileRepository.save(file);
        result.put("message", "Файл сохранен.");
        return result;
    }

    public Optional<FileEntity> getFile(Integer id) {
        return fileRepository.findById(id);
    }
}
