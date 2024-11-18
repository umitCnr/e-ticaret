package com.caner.e_ticaret.entities.satici;

import com.caner.e_ticaret.enums.Enums;
import com.caner.e_ticaret.mainEntities.MainEntitiy;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "satici")
@NamedEntityGraphs({
        @NamedEntityGraph(
                name = "seller-product",
                attributeNodes = {
                        @NamedAttributeNode("sellerProductEntities"),
                }
        )
})
public class SellerEntity extends MainEntitiy {

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "sirket_adi")
    private String company_name;

    @Column(name = "sirket_adresi")
    private String company_address;

    @Enumerated(EnumType.STRING)
    @Column(name = "cinsiyet")
    private Enums.gender gender;

    @Column(name = "eposta")
    private String email;

    @Column(name = "sirket_telefon")
    private String phone_number;

    @OneToMany(mappedBy = "sellerEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    List<SellerProductEntity> sellerProductEntities;
}
