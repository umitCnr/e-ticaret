package com.caner.e_ticaret.controller;

import com.caner.e_ticaret.dtos.SellerAndCustomerDto;
import com.caner.e_ticaret.dtos.UserResponse;
import com.caner.e_ticaret.entities.musteri.CustomerEntity;
import com.caner.e_ticaret.service.customerService.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;

@RestController
@RequestMapping("/customer")
@RequiredArgsConstructor
public class CustomerController {


    private final CustomerService customerService;

    @PostMapping("/save")
    public ResponseEntity<CustomerEntity> save(@RequestBody SellerAndCustomerDto sellerAndCustomerDto) {
        return customerService.save(sellerAndCustomerDto.getName(), sellerAndCustomerDto.getPassword());
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponse> get(@RequestBody SellerAndCustomerDto sellerAndCustomerDto) throws Exception {
        return ResponseEntity.ok(customerService.login(sellerAndCustomerDto));
    }

    @GetMapping("/loginSuccessful")
    public CustomerEntity loginSuccessful(@RequestHeader("Authorization") String token) {
       return customerService.getInformationCustomer(token);

    }

}
