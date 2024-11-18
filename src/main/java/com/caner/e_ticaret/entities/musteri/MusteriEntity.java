package com.caner.e_ticaret.entities.musteri;

import com.caner.e_ticaret.entities.UrunEntity;
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
        )
})
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

    @Enumerated(EnumType.STRING)
    @Column(name = "cinsiyet")
    private Enums.gender enums;

    @Column(name = "age")
    private int age;

    @OneToMany(mappedBy = "customer" , cascade = CascadeType.ALL,orphanRemoval = true)
    private List<UrunEntity> urunEntities;

}
