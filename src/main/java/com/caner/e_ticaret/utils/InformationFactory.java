package com.caner.e_ticaret.utils;

import com.caner.e_ticaret.dtos.InformationDto;
import com.caner.e_ticaret.entities.musteri.CustomerEntity;
import org.springframework.web.multipart.MultipartFile;

public interface InformationFactory {

    <T> CustomerEntity saveInformation(String token, InformationDto informationDto, MultipartFile file);

    <T> T getInformation(String token);

    <T> T UpdateInformation(String token, InformationFactory informationFactory);

    <T> T deleteInformation(String token,long id);

}
