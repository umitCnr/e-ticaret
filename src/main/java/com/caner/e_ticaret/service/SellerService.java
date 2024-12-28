package com.caner.e_ticaret.service;

import com.caner.e_ticaret.dtos.SellerAndCustomerDto;
import com.caner.e_ticaret.dtos.UserResponse;
import com.caner.e_ticaret.entities.satici.SellerEntity;
import com.caner.e_ticaret.enums.Enums;
import com.caner.e_ticaret.repository.ISellerRepository;
import com.caner.e_ticaret.utils.IFactory;
import com.caner.e_ticaret.utils.JwtService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class SellerService implements IFactory {

    private final ISellerRepository sellerRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;




    @Override
    public ResponseEntity<SellerEntity> save(String name, String password) throws Exception {

        Optional<SellerEntity> sellerEntityOptional = sellerRepository.findByName(name);

        if (sellerEntityOptional.isPresent()) {
            throw new Exception("Bu kullanıcı adı kullanılmıştır");
        } else {

            SellerEntity sellerEntity = new SellerEntity();
            sellerEntity.setName(name);
            sellerEntity.setPassword(passwordEncoder.encode(password));
            sellerEntity.setRoles(Enums.ROLES.SELLER);


            SellerEntity savedSeller = sellerRepository.save(sellerEntity);

            if (savedSeller == null) {
                throw new RuntimeException("Satıcı veritabanına kaydedilemedi");
            }
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(savedSeller);
        }
    }



    @Override
    public UserResponse login(SellerAndCustomerDto sellerAndCustomerDto) throws Exception {
        try {

            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(sellerAndCustomerDto.getName(), sellerAndCustomerDto.getPassword()));

            Optional<SellerEntity> user = sellerRepository.findByName(sellerAndCustomerDto.getName());

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
    public ResponseEntity<?> update(Long id) throws Exception {
        return null;
    }
}
