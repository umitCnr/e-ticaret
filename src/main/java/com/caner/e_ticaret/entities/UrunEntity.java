package com.caner.e_ticaret.entities;

import com.caner.e_ticaret.entities.musteri.MusteriEntity;
import com.caner.e_ticaret.entities.satici.SellerProductEntity;
import com.caner.e_ticaret.mainEntities.MainEntitiy;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "alinan_urun")
@Getter
@Setter
public class UrunEntity extends MainEntitiy {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "musteri_id", nullable = false)
    private MusteriEntity customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "urun_id", nullable = false)
    private SellerProductEntity product;

    @Column(name = "miktar", nullable = false)
    private int numberProduct;
}
