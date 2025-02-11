package com.caner.e_ticaret.controller;

import com.caner.e_ticaret.dtos.ImgDto;
import com.caner.e_ticaret.dtos.InformationDto;
import com.caner.e_ticaret.entities.musteri.CustomerEntity;
import com.caner.e_ticaret.service.customerService.CustomerInformationService;
import com.caner.e_ticaret.service.customerService.CustomerMinioService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/customer/information")
public class CustomerInformationController {

    private final CustomerInformationService customerInformationService;
    private final CustomerMinioService customerMinioService;

    @PostMapping("/save")
    public CustomerEntity save(@RequestHeader("Authorization") String token,
                               @RequestPart InformationDto informationDto,
                               @RequestParam MultipartFile file
    ) {

        try {
            return customerInformationService.saveInformation(token, informationDto, file);
        } catch (Exception e) {
            System.out.println("veri yüklenirken hata oluştı ->Controller" + e);
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
            System.out.println("Resim Silinemedi ->");
            return null;
        }
    }

    @PostMapping("/saveImg")
    public ImgDto saveImg(@RequestParam("file") MultipartFile file, @RequestHeader("Authorization") String token) {

        try {

            return customerMinioService.saveImage(file, token);
        } catch (Exception e) {
            System.out.println("resim yüklenemedi:" + e);
            return null;

        }
    }

    @GetMapping("/getImg")
    public ImgDto getImg(@RequestHeader("Authorization") String token){
        try {

            return customerMinioService.getImage(token);
        } catch (Exception e) {
            System.out.println("gelmedi:" + e);
            return null;

        }
    }

    @DeleteMapping("/deleteImg")
    public String deleteImg(@RequestHeader("Authorization") String token){

        try {
            customerMinioService.deleteImage(token);

            return "resim silindi";


        } catch (Exception e) {

            System.out.println("silinemedi:" + e);

        }
        return "resim silinemedi";
    }
}
