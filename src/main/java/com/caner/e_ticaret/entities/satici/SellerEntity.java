package com.caner.e_ticaret.entities.satici;

import com.caner.e_ticaret.enums.Enums;
import com.caner.e_ticaret.mainEntities.MainEntitiy;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
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

public class SellerEntity extends MainEntitiy implements UserDetails {

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


    //KULLANICININ ROLE DEĞERLERİNİ DÖNDÜRÜYOR
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(roles.name()));
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() { // KULLANICI HESABI SÜRESİ DOLUP DOLMADIĞINI BERLİRTEN METOT
        return true;
    }

    @Override
    public boolean isAccountNonLocked() { // HESAP KİTLİ Mİ DEĞİL Mİ ?
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() { //Kullanıcı aktif mi pasif mi onu öğreniyoruz
        return true;
    }
}
