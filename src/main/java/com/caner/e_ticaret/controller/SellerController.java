package com.caner.e_ticaret.controller;

import com.caner.e_ticaret.dtos.SellerAndCustomerDto;
import com.caner.e_ticaret.dtos.UserResponse;
import com.caner.e_ticaret.entities.satici.SellerEntity;
import com.caner.e_ticaret.service.sellerService.SellerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/seller")
@RequiredArgsConstructor
public class SellerController {

    private final SellerService sellerService;

    @PostMapping("/save")
    public ResponseEntity<SellerEntity> save(@RequestBody SellerAndCustomerDto sellerAndCustomerDto) throws Exception {
        return sellerService.save(sellerAndCustomerDto.getName(), sellerAndCustomerDto.getPassword());
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponse> loginBySeller(@RequestBody SellerAndCustomerDto sellerAndCustomerDto) throws Exception {
        return ResponseEntity.ok(sellerService.login(sellerAndCustomerDto));
    }


}
