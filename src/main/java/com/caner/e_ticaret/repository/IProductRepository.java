package com.caner.e_ticaret.repository;

import com.caner.e_ticaret.entities.ProductEntity;
import com.caner.e_ticaret.entities.satici.SellerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IProductRepository extends JpaRepository<ProductEntity,Long> {

}
