package com.caner.e_ticaret.utils;

import com.caner.e_ticaret.entities.satici.SellerEntity;
import com.caner.e_ticaret.entities.satici.SellerInformationEntity;
import com.caner.e_ticaret.repository.ISellerRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class ASToken {

    protected final JwtService jwtService;
    protected final ISellerRepository sellerRepository;


    public String getToken(String token) {
        if (token.startsWith("Bearer ")) {
            return jwtService.findUsername(token.substring(7));
        } else throw new RuntimeException("Token formatı yanlış");
    }

    protected SellerEntity findUsernameByToken(String token) {
        String username = getToken(token);
        return sellerRepository.findByName(username)
                .orElseThrow(() -> new RuntimeException("kullanıcı bulunamadı -> seller"));
    }

    protected SellerInformationEntity findInformationOfSeller(SellerEntity sellerEntity) {
        SellerInformationEntity sellerInformationEntity = sellerEntity.getSellerInformationEntities();

        if (sellerInformationEntity == null) {
            sellerInformationEntity = new SellerInformationEntity();
            sellerInformationEntity.setSellerEntity(sellerEntity);
            sellerEntity.setSellerInformationEntities(sellerInformationEntity);
        }
        return sellerInformationEntity;
    }

}
