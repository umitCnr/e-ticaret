package com.caner.e_ticaret.service.sellerService;

import com.caner.e_ticaret.dtos.InformationDto;
import com.caner.e_ticaret.entities.satici.SellerEntity;
import com.caner.e_ticaret.entities.satici.SellerInformationEntity;
import com.caner.e_ticaret.repository.ISellerRepository;
import com.caner.e_ticaret.utils.ASToken;
import com.caner.e_ticaret.utils.InformationFactory;
import com.caner.e_ticaret.utils.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class SellerInformationService extends ASToken implements InformationFactory  {


    public SellerInformationService(JwtService jwtService, ISellerRepository sellerRepository) {
        super(jwtService, sellerRepository);
    }


    @Override
    public String tokens(String token) {
        return null;
    }

    @Override
    public  SellerEntity saveInformation(String token, InformationDto informationDto)  {

        SellerEntity sellerEntity = findUsernameByToken(token);
        SellerInformationEntity sellerInformationEntity = findInformationOfSeller(sellerEntity);

        try {

            sellerInformationEntity.setCompany_name(informationDto.getCompanyName());
            sellerInformationEntity.setSurname(informationDto.getSurname());
            sellerInformationEntity.setCompany_address(informationDto.getCompanyAddress());
            sellerInformationEntity.setEmail(informationDto.getEmail());
            sellerInformationEntity.setGender(informationDto.getGender());
            sellerInformationEntity.setPhone_number(informationDto.getPhoneNumber());

        }catch (Exception e){
            System.out.println("kullanıcı bilgileri yüklenemedi" + e);
            return null;
        }

        sellerRepository.save(sellerEntity);

        return sellerEntity;
    }

    @Override
    public InformationDto getInformation(String token) {

        SellerEntity sellerEntity = findUsernameByToken(token);
        SellerInformationEntity sellerInformationEntity = findInformationOfSeller(sellerEntity);

        InformationDto informationDto = new InformationDto();

        try {

            informationDto.setCompanyAddress(sellerInformationEntity.getCompany_address());
            informationDto.setEmail(sellerInformationEntity.getEmail());
            informationDto.setPhoneNumber(sellerInformationEntity.getPhone_number());
            informationDto.setSurname(sellerInformationEntity.getSurname());
            informationDto.setCompanyName(sellerInformationEntity.getCompany_name());
            informationDto.setGender(sellerInformationEntity.getGender());

        }catch (Exception e){
            System.out.println("kullanıcı bilgileri alınamadı" + e);
            return null;
        }

        return informationDto;
    }

    @Override
    public <T> T UpdateInformation(String token, InformationDto dto) throws IOException {
        return null;
    }
}
