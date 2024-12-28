package com.caner.e_ticaret.service;

import com.caner.e_ticaret.entities.satici.SellerEntity;
import com.caner.e_ticaret.enums.Enums;
import com.caner.e_ticaret.repository.ISellerRepository;
import com.caner.e_ticaret.utils.AutRequest;
import com.caner.e_ticaret.utils.IFactory;
import com.caner.e_ticaret.utils.JwtToken;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class SellerService implements IFactory {

    private final ISellerRepository sellerRepository;
    private final PasswordEncoder passwordEncoder;
    private final DetailSecurityService detailSecurityService;


    @Override
    public ResponseEntity<SellerEntity> save(String name, String password) throws Exception {

        Optional<SellerEntity> sellerEntityOptional = sellerRepository.findByName(name);

        if (sellerEntityOptional.isPresent()) {
            throw new Exception("Bu kullanıcı adı kullanılmıştır");
        } else {

            SellerEntity sellerEntity = new SellerEntity();
            sellerEntity.setName(name);
            sellerEntity.setPassword(passwordEncoder.encode(password));


            SellerEntity savedSeller = sellerRepository.save(sellerEntity);

            if (savedSeller == null) {
                throw new RuntimeException("Satıcı veritabanına kaydedilemedi");
            }

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(savedSeller);
        }
    }


    @Override
    public ResponseEntity<String> get() throws Exception {
       return null;
    }

    @Override
    public ResponseEntity<?> update(Long id) throws Exception {
        return null;
    }
}
