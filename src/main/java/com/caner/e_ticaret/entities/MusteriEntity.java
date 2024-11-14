package com.caner.e_ticaret.entities;

import com.caner.e_ticaret.enums.Enums;
import com.caner.e_ticaret.mainEntities.MainEntitiy;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

@Entity
@Table(name = "musteri")
@Getter
@Setter
public class MusteriEntity extends MainEntitiy {

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "adress")
    private String adress;

    @Column(name = "phone_number")
    private String phone_number;

    @Column(name = "e-posta")
    private String email;

    @Column(name = "cinsiyet")
    private Enums.gender enums;

    @Column(name = "age")
    private int age;

    @OneToMany(mappedBy = "musteri" , cascade = CascadeType.ALL,orphanRemoval = true)
    private List<UrunEntity> urunEntities;

}
