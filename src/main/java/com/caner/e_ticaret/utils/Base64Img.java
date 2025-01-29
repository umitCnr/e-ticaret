package com.caner.e_ticaret.utils;

import com.caner.e_ticaret.service.MinioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Base64;

@RequiredArgsConstructor
@Service
public class Base64Img {

    private final MinioService minioService;

    public String base64ToImg(String url){

        byte[] getImg = minioService.getFile(url);
        String base64 = Base64.getEncoder().encodeToString(getImg);
        return base64;
    }
}
