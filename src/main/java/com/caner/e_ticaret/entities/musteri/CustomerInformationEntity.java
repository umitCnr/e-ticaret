package com.caner.e_ticaret.entities.musteri;

import com.caner.e_ticaret.enums.Enums;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "customer_information")
public class CustomerInformationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "surname")
    private String surname;

    @Column(name = "e-posta")
    private String email;

    @Column(name = "adress")
    private String adress;

    @Column(name = "phone_number")
    private String phone_number;

    @Enumerated(EnumType.STRING)
    @Column(name = "cinsiyet")
    private Enums.gender gender;

    @Column(name = "age")
    private Integer age;

    @Column(name = "img_url")
    private String imgUrl;

    @JoinColumn(name = "customer_id")
    @OneToOne(fetch = FetchType.LAZY)
    private CustomerEntity customerEntity;
}
