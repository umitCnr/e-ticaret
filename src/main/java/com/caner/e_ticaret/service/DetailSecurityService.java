package com.caner.e_ticaret.service;

import com.caner.e_ticaret.entities.musteri.CustomerEntity;
import com.caner.e_ticaret.entities.satici.SellerEntity;
import com.caner.e_ticaret.repository.ICustomerRepository;
import com.caner.e_ticaret.repository.IProductRepository;
import com.caner.e_ticaret.repository.ISellerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DetailSecurityService implements UserDetailsService {

    private final ICustomerRepository customerRepository;
    private final ISellerRepository sellerRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<SellerEntity> sellerEntityOptional = sellerRepository.findByName(username);

        if (sellerEntityOptional.isPresent()) {

            SellerEntity sellerEntity = sellerEntityOptional.get();

            return User.withUsername(sellerEntity.getEmail())
                    .password(sellerEntity.getPassword())
                    .roles("SELLER")
                    .build();
        }

        Optional<CustomerEntity> optionalCustomerEntity = customerRepository.findByName(username);

        if (optionalCustomerEntity.isPresent()) {

            CustomerEntity customerEntity = optionalCustomerEntity.get();

            return User.withUsername(customerEntity.getEmail())
                    .password(customerEntity.getPassword())
                    .roles("CUSTOMER")
                    .build();
        }

        throw new UsernameNotFoundException("Kullanıcı bulunamadı: " + username);
    }
}
