package com.caner.e_ticaret.service;

import com.caner.e_ticaret.dtos.SellerAndCustomerDto;
import com.caner.e_ticaret.dtos.UserResponse;
import com.caner.e_ticaret.entities.musteri.CustomerEntity;
import com.caner.e_ticaret.repository.ICustomerRepository;
import com.caner.e_ticaret.utils.IFactory;
import com.caner.e_ticaret.utils.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerService implements IFactory {

    private final ICustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public ResponseEntity<CustomerEntity> save(String name, String password) throws RuntimeException {

        Optional<CustomerEntity> customerEntityOptional = customerRepository.findByName(name);

        if (customerEntityOptional.isPresent()) {
            throw new RuntimeException("kullanıcı adı kullanılıyor");

        } else {

            CustomerEntity customerEntity = new CustomerEntity();
            customerEntity.setName(name);
            customerEntity.setPassword(passwordEncoder.encode(password));

            CustomerEntity saveCustomer = customerRepository.save(customerEntity);

            if (saveCustomer == null) {
                throw new RuntimeException("Kullanıcı veritabanına kaydedilemedi");
            }

            return ResponseEntity.ok(saveCustomer);
        }
    }

    @Override
    public UserResponse login(SellerAndCustomerDto sellerAndCustomerDto) throws Exception {
        return null;
    }


    @Override
    public ResponseEntity<CustomerEntity> update(Long id) throws Exception {
        return null;
    }
}
