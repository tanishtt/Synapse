package com.tanishmohanta.synapse.uploader_service.controller;


import com.tanishmohanta.synapse.uploader_service.service.S3UploaderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/file")
@RequiredArgsConstructor
@Slf4j
public class UploaderController {
    private final S3UploaderService s3UploaderService;

    @PostMapping
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file){
        log.info("Uploading file");
        String url = s3UploaderService.upload(file);
        return ResponseEntity.ok(url);
    }
}
