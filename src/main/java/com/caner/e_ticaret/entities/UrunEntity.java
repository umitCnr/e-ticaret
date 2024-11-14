package com.caner.e_ticaret.entities;

import com.caner.e_ticaret.mainEntities.MainEntitiy;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "alinan_urun")
@Getter
@Setter
public class UrunEntity extends MainEntitiy {


    @Column(name = "company_name")
    private String company_name;

    @Column(name = "company_address_name")
    private String company_address_name;

    @Column(name = "company_phone_number")
    private String company_phone_number;

    @Column(name = "company_e_posta")
    private String company_e_posta;

    @Column(name = "price")
    private int price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "musteri_id")
    private MusteriEntity musteri;
}
