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

    @GetMapping("/get")
    public InformationDto get(@RequestHeader("Authorization") String token) {

        try {
            return customerInformationService.getInformation(token);
        } catch (Exception e) {
            System.out.println("veri getirilirken hata oluştı ->Controller");
            return null;
        }
    }

    @PutMapping("/update")
    public InformationDto update(@RequestHeader("Authorization") String token,
                                 @RequestBody InformationDto dto,
                                 @RequestParam MultipartFile file) {
        try {
            return customerInformationService.UpdateInformation(token, dto, file);
        } catch (Exception e) {
            System.out.println("data güncellenemedi");
            return null;
        }

    }

    @DeleteMapping("/delete")
    public CustomerEntity delete(@RequestHeader("Authorization") String token,
                                 @RequestParam MultipartFile file) {
        try {
            return customerInformationService.deleteInformation(token, file);
        } catch (Exception e) {
            System.out.println("Resim Silinemedi");
            return null;
        }
    }
}
