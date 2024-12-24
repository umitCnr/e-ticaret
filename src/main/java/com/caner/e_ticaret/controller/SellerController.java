package com.caner.e_ticaret.controller;

import com.caner.e_ticaret.dtos.SellerAndCustomerDto;
import com.caner.e_ticaret.entities.satici.SellerEntity;
import com.caner.e_ticaret.service.SellerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/seller")
@RequiredArgsConstructor
public class SellerController {

    private final SellerService sellerService;

    @PostMapping("/save")
    public ResponseEntity<SellerEntity> save(@RequestBody SellerAndCustomerDto sellerAndCustomerDto) throws Exception {
        System.out.println("Received data: " + sellerAndCustomerDto.getMail() + ", " + sellerAndCustomerDto.getPassword());
        return sellerService.save(sellerAndCustomerDto.getMail(), sellerAndCustomerDto.getPassword());
    }
}
