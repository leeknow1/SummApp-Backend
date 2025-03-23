package com.leeknow.summapp.file.controller;

import com.leeknow.summapp.common.enums.Language;
import com.leeknow.summapp.file.entity.FileEntity;
import com.leeknow.summapp.file.service.FileService;
import com.leeknow.summapp.message.service.MessageUtils;
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

import static com.leeknow.summapp.file.constant.FileMessageConstant.FILE_NOT_FOUND;

@RestController
@RequestMapping("/file")
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;
    private final MessageUtils messageUtils;

    @PostMapping("/upload/{applicationId}")
    public ResponseEntity<?> uploadFile(@PathVariable("applicationId") Integer applicationId,
                                        @RequestParam("file") MultipartFile file,
                                        @RequestHeader(name = "Accept-Language", defaultValue = "ru") String lang) throws IOException {
        Map<String, Object> result = fileService.uploadFile(applicationId, file, Language.getLanguageByCode((lang)));
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getFile(@PathVariable Integer id,
                                     @RequestHeader(name = "Accept-Language", defaultValue = "ru") String lang) {
        Language language = Language.getLanguageByCode((lang));
        Optional<FileEntity> file = fileService.getFile(id, language);
        if (file.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(messageUtils.getMessage(language, FILE_NOT_FOUND));

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(file.get().getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + URLEncoder.encode(file.get().getFileName(), StandardCharsets.UTF_8) + "\"")
                .body(new ByteArrayResource(file.get().getContent()));
    }

    @GetMapping("/view/{id}")
    public ResponseEntity<?> viewPdf(@PathVariable Integer id,
                                     @RequestHeader(name = "Accept-Language", defaultValue = "ru") String lang) {
        Language language = Language.getLanguageByCode((lang));
        Optional<FileEntity> file = fileService.getFile(id, language);
        if (file.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(messageUtils.getMessage(language, FILE_NOT_FOUND));

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline;filename=" + URLEncoder.encode(file.get().getFileName(), StandardCharsets.UTF_8))
                .body(new ByteArrayResource(file.get().getContent()));
    }
}
