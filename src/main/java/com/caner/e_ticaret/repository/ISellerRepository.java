package com.caner.e_ticaret.repository;

import com.caner.e_ticaret.entities.satici.SellerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ISellerRepository extends JpaRepository<SellerEntity, Long> {

    Optional<SellerEntity> findByName(String name);

}
