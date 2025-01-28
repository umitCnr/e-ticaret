package com.caner.e_ticaret.controller;

import com.caner.e_ticaret.dtos.InformationDto;
import com.caner.e_ticaret.entities.musteri.CustomerEntity;
import com.caner.e_ticaret.service.customerService.CustomerInformationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/customer/information")
public class CustomerInformationController {

    private final CustomerInformationService customerInformationService;

    @PostMapping("/save")
    public CustomerEntity save(@RequestHeader("Authorization") String token
            , @RequestBody InformationDto informationDto
            , @RequestParam MultipartFile file) {

        try {
            return customerInformationService.saveInformation(token, informationDto, file);
        } catch (Exception e) {
            System.out.println("veri yüklenirken hata oluştı ->Controller");
            return null;
        }
    }
}
