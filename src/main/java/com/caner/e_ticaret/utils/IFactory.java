package com.caner.e_ticaret.utils;

import com.caner.e_ticaret.dtos.SellerAndCustomerDto;
import com.caner.e_ticaret.dtos.UserResponse;
import com.caner.e_ticaret.entities.musteri.CustomerEntity;
import org.springframework.http.ResponseEntity;

public interface IFactory {

    public ResponseEntity<?> save(String name, String password) throws Exception;

    public UserResponse login(SellerAndCustomerDto sellerAndCustomerDto) throws Exception;

    public ResponseEntity<?> update(Long id) throws Exception;

}
