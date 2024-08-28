package com.leeknow.summapp.controller;

import com.leeknow.summapp.entity.FileEntity;
import com.leeknow.summapp.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/file")
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;

    @PostMapping("/upload/{applicationId}")
    public ResponseEntity<?> uploadFile(@PathVariable("applicationId") Integer applicationId,
                                        @RequestParam("file") MultipartFile file) throws IOException {
        Map<String, Object> result = fileService.uploadFile(applicationId, file);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getFile(@PathVariable Integer id) {

        Optional<FileEntity> file = fileService.getFile(id);
        if (file.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Файл не найден!");

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(file.get().getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + URLEncoder.encode(file.get().getFileName(), StandardCharsets.UTF_8) + "\"")
                .body(new ByteArrayResource(file.get().getContent()));
    }
}
