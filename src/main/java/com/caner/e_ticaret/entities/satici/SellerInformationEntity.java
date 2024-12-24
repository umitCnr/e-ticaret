package com.caner.e_ticaret.entities.satici;

import com.caner.e_ticaret.enums.Enums;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Lazy;

@Entity
@Getter
@Setter
@Table(name = "seller_information")
public class SellerInformationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "company_name")
    private String company_name;

    @Column(name = "surname")
    private String surname;


    @Column(name = "company_address")
    private String company_address;

    @Enumerated(EnumType.STRING)
    @Column(name = "cinsiyet")
    private Enums.gender gender;

    @Column(name = "phone_number")
    private String phone_number;

    @Column(name = "img_url")
    private String imgUrl;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "satici_id")
    private SellerEntity sellerEntity;


}
