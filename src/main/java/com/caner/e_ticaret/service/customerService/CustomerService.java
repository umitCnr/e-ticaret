package com.caner.e_ticaret.service.customerService;

import com.caner.e_ticaret.dtos.SellerAndCustomerDto;
import com.caner.e_ticaret.dtos.UserResponse;
import com.caner.e_ticaret.entities.musteri.CustomerEntity;
import com.caner.e_ticaret.entities.satici.SellerEntity;
import com.caner.e_ticaret.enums.Enums;
import com.caner.e_ticaret.repository.ICustomerRepository;
import com.caner.e_ticaret.utils.IFactory;
import com.caner.e_ticaret.utils.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Base64;
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
            customerEntity.setRoles(Enums.ROLES.CUSTOMER);

            CustomerEntity saveCustomer = customerRepository.save(customerEntity);

            if (saveCustomer == null) {
                throw new RuntimeException("Kullanıcı veritabanına kaydedilemedi");
            }

            return ResponseEntity.ok(saveCustomer);
        }
    }

    @Override
    public UserResponse login(SellerAndCustomerDto sellerAndCustomerDto) throws Exception {

        try {

            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(sellerAndCustomerDto.getName(), sellerAndCustomerDto.getPassword()));

            Optional<CustomerEntity> user = customerRepository.findByName(sellerAndCustomerDto.getName());

            if (user.isEmpty()) {
                throw new Exception("User not found");
            }

            String token = jwtService.generateToken(user.get());

            System.out.println("Giriş tokenı: " + token);

            return UserResponse.builder()
                    .token(token)
                    .build();
        } catch (AuthenticationException e) {
            throw new Exception("Authentication failed: Invalid username or password", e);
        } catch (Exception e) {
            throw new Exception("An error occurred while processing your request", e);
        }
    }


    @Override
    public ResponseEntity<CustomerEntity> update(Long id ,String token) throws Exception {



        return null;
    }

    public CustomerEntity getInformationCustomer(String token) {


        String jwt = token;
        if (token.startsWith("Bearer ")) {
            jwt = token.substring(7); // Bearer kısmını atla
        } else {
            throw new RuntimeException("Token formatı yanlış.");
        }

        String username = jwtService.findUsername(jwt);

        if (username == null || username.isEmpty()) {
            throw new RuntimeException("Kullanıcı adı token içinde bulunamadı.");
        }

        Optional<CustomerEntity> customerEntityOptional = customerRepository.findByName(username);

        return customerEntityOptional.orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı: " + username));
    }

    public boolean checkOldPassword(String token, String oldPassword) {

        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        } else {
            throw new RuntimeException("Token formatı yanlış.");
        }

        String username = jwtService.findUsername(token);
        if (username == null || username.isEmpty()) {
            throw new RuntimeException("Kullanıcı adı token içinde bulunamadı.");
        }


        Optional<CustomerEntity> customerEntityOptional = customerRepository.findByName(username);
        if (!customerEntityOptional.isPresent()) {
            throw new RuntimeException("Kullanıcı bulunamadı.");
        }

        CustomerEntity customerEntity = customerEntityOptional.get();

        if (passwordEncoder.matches(oldPassword, customerEntity.getPassword())) {
            return true;
        } else {
            return false;
        }
    }




}
