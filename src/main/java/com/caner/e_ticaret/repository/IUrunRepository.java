package com.caner.e_ticaret.repository;

import com.caner.e_ticaret.entities.UrunEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUrunRepository extends JpaRepository<UrunEntity,Long> {
}
