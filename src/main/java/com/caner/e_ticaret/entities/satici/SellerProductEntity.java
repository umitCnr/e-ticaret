package com.caner.e_ticaret.entities.satici;

import com.caner.e_ticaret.entities.ProductEntity;
import com.caner.e_ticaret.enums.Enums;
import com.caner.e_ticaret.mainEntities.MainEntitiy;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "urunler")
@Getter
@Setter
public class SellerProductEntity extends MainEntitiy {

    @Column(name = "urun_adi")
    private String productName;

    @Column(name = "fiyat")
    private String productPrice;

    @Enumerated(EnumType.STRING)
    @Column(name = "kategori")
    private Enums.categories categories_Product;

    @Column(name = "miktar")
    private int ProductNumber;

    @Column(name = "satin_alan")
    private String SoldProductToCustomer;

    @Column(name = "imgUrl")
    private String imgUrl;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "satici_id")
    private SellerEntity sellerEntity;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductEntity> purchases;
}
