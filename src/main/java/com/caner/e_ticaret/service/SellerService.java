package com.caner.e_ticaret.service;

import com.caner.e_ticaret.entities.satici.SellerEntity;
import com.caner.e_ticaret.enums.Enums;
import com.caner.e_ticaret.repository.ISellerRepository;
import com.caner.e_ticaret.utils.IFactory;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class SellerService implements IFactory {

    private final ISellerRepository sellerRepository;
    private final PasswordEncoder passwordEncoder;
    private final MinioService minioService;


    @Override
    public ResponseEntity<SellerEntity> save(String mail, String password) throws Exception {

        SellerEntity sellerEntity = new SellerEntity();
        sellerEntity.setEmail(mail);
        sellerEntity.setPassword(passwordEncoder.encode(password));

        // Veritabanına kaydetme
        SellerEntity savedSeller = sellerRepository.save(sellerEntity);

        // Eğer bu adımda veri kaydedilemiyorsa, bir exception fırlayabilir
        if (savedSeller == null) {
            throw new RuntimeException("Seller not saved to the database");
        }

        return ResponseEntity.ok(savedSeller);

    }


    @Override
    public ResponseEntity<Map<String, String>> get() throws Exception {
        return null;
    }

    @Override
    public ResponseEntity<?> update(Long id) throws Exception {
        return null;
    }
}
