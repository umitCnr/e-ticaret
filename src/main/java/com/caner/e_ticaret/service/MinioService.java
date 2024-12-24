package com.caner.e_ticaret.service;

import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import io.minio.errors.MinioException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Service
@RequiredArgsConstructor
public class MinioService {

    private final MinioClient minioClient;

    @Value("${minio.bucket-name}")
    private String bucketName;


    public MultipartFile saveFile(MultipartFile file, String path) throws IOException {

        try {
            String objectName = path + "/" + file.getOriginalFilename();

            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(bucketName)
                    .stream(file.getInputStream(), file.getSize(), -1)
                    .contentType(file.getContentType())
                    .object(objectName)
                    .build());

        } catch (MinioException | InvalidKeyException | NoSuchAlgorithmException e) {

            throw new IllegalStateException("The file cannot be upload on the internal storage. Please retry later", e);
        }

        return file;

    }

    public byte[] getFile(String fileName) {

        try {

            InputStream stream = minioClient.getObject(GetObjectArgs.builder()
                    .bucket(bucketName)
                    .object(fileName)
                    .build());
            return stream.readAllBytes();

        } catch (Exception e) {

            throw new RuntimeException(e.getMessage());
        }
    }

    public void deleteFile(String fileName) throws IOException {

        try {

            minioClient.removeObject(RemoveObjectArgs.builder()
                    .bucket(bucketName)
                    .object(fileName)
                    .build());

        }catch (MinioException | InvalidKeyException | NoSuchAlgorithmException e) {

            throw new IllegalStateException("The file cannot be delete on the internal storage. Please retry later", e);
        }
    }

}
