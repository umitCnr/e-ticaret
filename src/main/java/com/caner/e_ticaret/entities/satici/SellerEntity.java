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
        ),
        @NamedEntityGraph(
                name = "seller-information",
                attributeNodes = {
                        @NamedAttributeNode("sellerInformationEntities")
                }
        )
})

public class SellerEntity extends MainEntitiy {

    @Column(name = "name")
    private String name;


    @Column(name = "password")
    private String password;

    @Column(name = "role")
    @Enumerated(value = EnumType.STRING)
    private Enums.ROLES roles;


    @OneToMany(mappedBy = "sellerEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    List<SellerProductEntity> sellerProductEntities;

    @OneToOne(mappedBy = "sellerEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private SellerInformationEntity sellerInformationEntities;


}
