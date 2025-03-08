package com.caner.e_ticaret.service.customerService;

import com.caner.e_ticaret.dtos.InformationDto;
import com.caner.e_ticaret.entities.musteri.CustomerEntity;
import com.caner.e_ticaret.entities.musteri.CustomerInformationEntity;
import com.caner.e_ticaret.repository.ICustomerRepository;
import com.caner.e_ticaret.service.MinioService;
import com.caner.e_ticaret.utils.Base64Img;
import com.caner.e_ticaret.utils.InformationFactory;
import com.caner.e_ticaret.utils.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerInformationService implements InformationFactory {


    private final MinioService minioService;
    private final ICustomerRepository customerRepository;
    private final Base64Img base64Img;
    private final JwtService jwtService;


    @Override
    public String tokens(String token) {
        String jwt = token;
        if (token.startsWith("Bearer ")) {
            jwt = token.substring(7);
        } else {
            throw new RuntimeException("Token formatı yanlış.");
        }

        String username = jwtService.findUsername(jwt);

        return username;
    }


    @Override
    public CustomerEntity saveInformation(String token, InformationDto informationDto) {

        Optional<CustomerEntity> customerEntity = customerRepository.findByName(tokens(token));
        if (customerEntity.isEmpty()) {
            throw new RuntimeException("Kullanıcı bulunamadı: ");
        }

        CustomerEntity customerEntity1 = customerEntity.get();

        CustomerInformationEntity customerInformationEntity = customerEntity1.getCustomerInformationEntity();
        if (customerInformationEntity == null) {
            customerInformationEntity = new CustomerInformationEntity();
            customerInformationEntity.setCustomerEntity(customerEntity1);
            customerEntity1.setCustomerInformationEntity(customerInformationEntity);
        }

        customerInformationEntity.setSurname(informationDto.getSurname());
        customerInformationEntity.setAdress(informationDto.getAddress());
        customerInformationEntity.setAge(informationDto.getAge());
        customerInformationEntity.setEmail(informationDto.getEmail());
        customerInformationEntity.setPhone_number(informationDto.getPhoneNumber());
        customerInformationEntity.setImgUrl(informationDto.getImgUrl());
        customerInformationEntity.setGender(informationDto.getGender());

        customerRepository.save(customerEntity1);

        return customerEntity1;
    }

    @Override
    public InformationDto getInformation(String token) {

        InformationDto informationDto = new InformationDto();

        Optional<CustomerEntity> customerEntity = customerRepository.findByName(tokens(token));
        if (customerEntity.isEmpty()) {
            throw new RuntimeException("Kullanıcı bulunamadı: ");
        }
        CustomerEntity customerEntity1 = customerEntity.get();
        CustomerInformationEntity customerInformationEntity = customerEntity1.getCustomerInformationEntity();

        informationDto.setAddress(customerInformationEntity.getAdress());
        informationDto.setAge(customerInformationEntity.getAge());
        informationDto.setSurname(customerInformationEntity.getSurname());
        informationDto.setId(customerInformationEntity.getId());
        informationDto.setPhoneNumber(customerInformationEntity.getPhone_number());
        informationDto.setGender(customerInformationEntity.getGender());
        informationDto.setEmail(customerInformationEntity.getEmail());

        return informationDto;
    }


    @Override
    public InformationDto UpdateInformation(String token, InformationDto dto) throws IOException {


        Optional<CustomerEntity> customerEntity = customerRepository.findByName(tokens(token));
        if (customerEntity.isEmpty()) {
            throw new RuntimeException("Kullanıcı bulunamadı: ");
        }

        CustomerEntity customerEntity1 = customerEntity.get();
        CustomerInformationEntity customerInformationEntity = customerEntity1.getCustomerInformationEntity();



        if (dto.getAddress() != null) {
            customerInformationEntity.setAdress(dto.getAddress());
        }
        if (dto.getEmail() != null) {
            customerInformationEntity.setEmail(dto.getEmail());
        }
        if (dto.getPhoneNumber() != null) {
            customerInformationEntity.setPhone_number(dto.getPhoneNumber());
        }
        if (dto.getAge() != null) {
            customerInformationEntity.setAge(dto.getAge());
        }
        if (dto.getGender() != null) {
            customerInformationEntity.setGender(dto.getGender());
        }
        if (dto.getSurname() != null) {
            customerInformationEntity.setSurname(dto.getSurname());
        }

        customerRepository.save(customerEntity1);

        return dto;
    }

}
