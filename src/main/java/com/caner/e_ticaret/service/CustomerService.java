package com.caner.e_ticaret.service;

import com.caner.e_ticaret.entities.musteri.CustomerEntity;
import com.caner.e_ticaret.enums.Enums;
import com.caner.e_ticaret.repository.ICustomerRepository;
import com.caner.e_ticaret.utils.IFactory;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.patterns.HasMemberTypePattern;
import org.checkerframework.checker.units.qual.C;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CustomerService implements IFactory {

    private final ICustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;
    private final MinioService minioService;


    @Override
    public ResponseEntity<CustomerEntity> save( String mail, String password) throws Exception {

        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setEmail(mail);
        customerEntity.setPassword(passwordEncoder.encode(password));

        CustomerEntity saveCustomer = customerRepository.save(customerEntity);

        return ResponseEntity.ok(saveCustomer);
    }


    @Override
    public ResponseEntity<Map<String, String>> get() throws Exception {

        CustomerEntity customerEntity = new CustomerEntity();

        HashMap<String, String> cusomer = new HashMap<>();

        cusomer.put(customerEntity.getName(), customerEntity.getPassword());


        return null;
    }

    @Override
    public ResponseEntity<CustomerEntity> update(Long id) throws Exception {
        return null;
    }
}
