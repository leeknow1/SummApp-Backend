package com.leeknow.summapp.controller;

import com.leeknow.summapp.constant.FileMessageConstant;
import com.leeknow.summapp.entity.FileEntity;
import com.leeknow.summapp.enums.Language;
import com.leeknow.summapp.service.FileService;
import com.leeknow.summapp.service.MessageService;
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
                                        @RequestParam("file") MultipartFile file,
                                        @RequestHeader(name = "Accept-Language", defaultValue = "1") String lang) throws IOException {
        Map<String, Object> result = fileService.uploadFile(applicationId, file, Language.getLanguageById((lang)));
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getFile(@PathVariable Integer id,
                                     @RequestHeader(name = "Accept-Language", defaultValue = "1") String lang) {
        Language language = Language.getLanguageById((lang));
        Optional<FileEntity> file = fileService.getFile(id, language);
        if (file.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(MessageService.getMessage(language, FileMessageConstant.FILE_NOT_FOUND));

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(file.get().getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + URLEncoder.encode(file.get().getFileName(), StandardCharsets.UTF_8) + "\"")
                .body(new ByteArrayResource(file.get().getContent()));
    }

    @GetMapping("/view/{id}")
    public ResponseEntity<?> viewPdf(@PathVariable Integer id,
                                     @RequestHeader(name = "Accept-Language", defaultValue = "1") String lang) {
        Language language = Language.getLanguageById((lang));
        Optional<FileEntity> file = fileService.getFile(id, language);
        if (file.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(MessageService.getMessage(language, FileMessageConstant.FILE_NOT_FOUND));

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline;filename=" + URLEncoder.encode(file.get().getFileName(), StandardCharsets.UTF_8))
                .body(new ByteArrayResource(file.get().getContent()));
    }
}
