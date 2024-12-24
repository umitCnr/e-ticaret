package com.caner.e_ticaret.entities.musteri;

import com.caner.e_ticaret.entities.ProductEntity;
import com.caner.e_ticaret.entities.satici.SellerInformationEntity;
import com.caner.e_ticaret.enums.Enums;
import com.caner.e_ticaret.mainEntities.MainEntitiy;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "musteri")
@NamedEntityGraphs({
        @NamedEntityGraph(
                name = "customer-product",
                attributeNodes = {
                        @NamedAttributeNode("urunEntities"),
                }
        ),
        @NamedEntityGraph(
                name = "customer-information",
                attributeNodes = {
                        @NamedAttributeNode("customerInformationEntity"),
                }
        )
})

@Getter
@Setter
public class CustomerEntity extends MainEntitiy {

    @Column(name = "name")
    private String name;

    @Column(name = "password")
    private String password;

    @Column(name = "e-posta")
    private String email;


    @OneToMany(mappedBy = "customer" , cascade = CascadeType.ALL,orphanRemoval = true)
    private List<ProductEntity> urunEntities;

    @OneToOne(mappedBy = "customerEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private CustomerInformationEntity customerInformationEntity;

}
