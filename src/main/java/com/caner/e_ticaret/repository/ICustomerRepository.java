package com.caner.e_ticaret.repository;

import com.caner.e_ticaret.entities.musteri.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ICustomerRepository extends JpaRepository<CustomerEntity, Long> {

    Optional<CustomerEntity> findByName(String name);
}
