package com.caner.e_ticaret.controller;

import com.caner.e_ticaret.dtos.SellerAndCustomerDto;
import com.caner.e_ticaret.entities.musteri.CustomerEntity;
import com.caner.e_ticaret.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customer")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping("/save")
    public ResponseEntity<CustomerEntity> save(@RequestBody SellerAndCustomerDto sellerAndCustomerDto) {
        System.out.println("yüklenen veri :" + sellerAndCustomerDto.getName() + " , " + sellerAndCustomerDto.getPassword());
        return customerService.save(sellerAndCustomerDto.getName(), sellerAndCustomerDto.getPassword());
    }
}
