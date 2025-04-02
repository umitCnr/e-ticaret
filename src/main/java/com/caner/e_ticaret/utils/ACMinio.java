package com.caner.e_ticaret.utils;

import com.caner.e_ticaret.dtos.ImgDto;
import com.caner.e_ticaret.entities.musteri.CustomerEntity;
import com.caner.e_ticaret.entities.musteri.CustomerInformationEntity;
import com.caner.e_ticaret.repository.ICustomerRepository;
import com.caner.e_ticaret.service.MinioService;
import com.caner.e_ticaret.service.customerService.CustomerInformationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
public abstract class ACMinio {

    protected  final JwtService jwtService;
    protected  final MinioService minioService;
    protected  final ICustomerRepository customerRepository;

    public String getToken(String token){

        if (token.startsWith("Bearer ")){
            return jwtService.findUsername(token.substring(7));
        }else  throw new RuntimeException("Token formatı yanlış.");

    }

    protected  CustomerEntity findUsernameByToken(String token){

        String username = getToken(token);

        return customerRepository.findByName(username)
                .orElseThrow(()->new RuntimeException("kullanıcı bulunamadı -> customer"));
    }

    protected  CustomerInformationEntity findInformationToCustomer(CustomerEntity customerEntity){

        CustomerInformationEntity customerInformationEntity = customerEntity.getCustomerInformationEntity();

        if (customerInformationEntity == null) {
            customerInformationEntity = new CustomerInformationEntity();
            customerInformationEntity.setCustomerEntity(customerEntity);
            customerEntity.setCustomerInformationEntity(customerInformationEntity);
        }

        return customerInformationEntity;
    }

    public abstract ImgDto saveImage(MultipartFile file, String token);

    public abstract ImgDto getImage(String token);

    public abstract String deleteImage(String token);


}
