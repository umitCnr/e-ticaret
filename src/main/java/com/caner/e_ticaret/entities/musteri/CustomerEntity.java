package com.caner.e_ticaret.entities.musteri;

import com.caner.e_ticaret.entities.ProductEntity;
import com.caner.e_ticaret.entities.satici.SellerInformationEntity;
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
public class CustomerEntity extends MainEntitiy implements UserDetails {

    @Column(name = "name")
    private String name;

    @Column(name = "password")
    private String password;

    @Column(name = "role")
    @Enumerated(value = EnumType.STRING)
    private Enums.ROLES roles;


    @OneToMany(mappedBy = "customer" , cascade = CascadeType.ALL,orphanRemoval = true)
    private List<ProductEntity> urunEntities;

    @OneToOne(mappedBy = "customerEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private CustomerInformationEntity customerInformationEntity;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(roles.name()));
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
