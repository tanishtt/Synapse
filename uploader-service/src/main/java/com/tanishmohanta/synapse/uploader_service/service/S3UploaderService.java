package com.tanishmohanta.synapse.uploader_service.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.GetUrlRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

import java.io.IOException;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class S3UploaderService implements UploaderService{
    private final S3Client s3Client;

    @Value("${aws.bucketName}")
    private String bucketName;


    @Override
    public String upload(MultipartFile file) {
        log.info("Uploading file {}",file.getOriginalFilename());
        try{
            String keyName = file.getOriginalFilename();
            if (keyName == null || keyName.isBlank()) {
                keyName = UUID.randomUUID().toString();
            }
            PutObjectResponse putObjectResponse = s3Client.putObject(PutObjectRequest.builder()
                            .bucket(bucketName)
                            .key(keyName)
                            .build(),
                    RequestBody.fromBytes(file.getBytes()));
            String uploadedUrl = s3Client.utilities()
                    .getUrl(GetUrlRequest.builder()
                            .bucket(bucketName)
                            .key(keyName)
                            .build())
                    .toExternalForm();
            log.info("Uploaded file {}",uploadedUrl);

            return uploadedUrl;
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }


    public byte[] downloadFile(String key) {
        ResponseBytes<GetObjectResponse> objectAsBytes = s3Client.getObjectAsBytes(
                builder -> builder.bucket(bucketName).key(key));

        return objectAsBytes.asByteArray();
    }
}
