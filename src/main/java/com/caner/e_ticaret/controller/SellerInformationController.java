package com.caner.e_ticaret.controller;

import com.caner.e_ticaret.dtos.InformationDto;
import com.caner.e_ticaret.entities.satici.SellerEntity;
import com.caner.e_ticaret.service.sellerService.SellerInformationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/seller")
@RequiredArgsConstructor
public class SellerInformationController {

    private final SellerInformationService sellerInformationService;

    @PostMapping("/save")
    public SellerEntity save(@RequestHeader("Authorization") String token
            , @RequestBody InformationDto informationDto) {
        return sellerInformationService.saveInformation(token, informationDto);
    }

    @GetMapping("get")
    public InformationDto get(@RequestHeader("Authorization") String token) {
        return sellerInformationService.getInformation(token);
    }
}
