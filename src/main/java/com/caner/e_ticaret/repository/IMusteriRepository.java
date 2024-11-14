package com.caner.e_ticaret.repository;

import com.caner.e_ticaret.entities.MusteriEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IMusteriRepository extends JpaRepository<MusteriEntity,Long> {
}