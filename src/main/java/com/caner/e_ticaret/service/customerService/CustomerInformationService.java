package com.caner.e_ticaret.service.customerService;

import com.caner.e_ticaret.dtos.InformationDto;
import com.caner.e_ticaret.entities.musteri.CustomerEntity;
import com.caner.e_ticaret.entities.musteri.CustomerInformationEntity;
import com.caner.e_ticaret.enums.Enums;
import com.caner.e_ticaret.repository.ICustomerRepository;
import com.caner.e_ticaret.service.MinioService;
import com.caner.e_ticaret.utils.Base64Img;
import com.caner.e_ticaret.utils.IGetToken;
import com.caner.e_ticaret.utils.InformationFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerInformationService implements InformationFactory, IGetToken {


    private final MinioService minioService;
    private final ICustomerRepository customerRepository;
    private final Base64Img base64Img;


    @Override
    public CustomerEntity saveInformation(String token, InformationDto informationDto, MultipartFile file) {


        Optional<CustomerEntity> customerEntity = customerRepository.findByName(getToken(token));
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

        try {
            String path = customerEntity1.getName();
            MultipartFile uploadedFile = minioService.saveFile(file, path);
            informationDto.setImgUrl(path + "/" + uploadedFile.getOriginalFilename());
        } catch (Exception e) {
            throw new RuntimeException("Dosya yüklenirken bir hata oluştu.", e);
        }

        customerInformationEntity.setSurname(informationDto.getSurname());
        customerInformationEntity.setAdress(informationDto.getAddress());
        customerInformationEntity.setAge(informationDto.getAge());
        customerInformationEntity.setEmail(informationDto.getEmail());
        customerInformationEntity.setPhone_number(informationDto.getPhoneNumber());
        customerInformationEntity.setImgUrl(informationDto.getImgUrl());
        customerInformationEntity.setId(informationDto.getId());
        customerInformationEntity.setGender(informationDto.getGender());

        customerRepository.save(customerEntity1);

        return customerEntity1;
    }

    @Override
    public InformationDto getInformation(String token) {

        InformationDto informationDto = new InformationDto();

        Optional<CustomerEntity> customerEntity = customerRepository.findByName(getToken(token));
        if (customerEntity.isEmpty()) {
            throw new RuntimeException("Kullanıcı bulunamadı: ");
        }
        CustomerEntity customerEntity1 = customerEntity.get();
        CustomerInformationEntity customerInformationEntity = customerEntity1.getCustomerInformationEntity();

        String base64 = base64Img.base64ToImg(customerInformationEntity.getImgUrl());

        informationDto.setImgUrl(base64);
        informationDto.setAddress(customerInformationEntity.getAdress());
        informationDto.setAge(customerInformationEntity.getAge());
        informationDto.setSurname(customerInformationEntity.getSurname());
        informationDto.setId(customerInformationEntity.getId());
        informationDto.setPhoneNumber(customerInformationEntity.getPhone_number());
        informationDto.setGender(customerInformationEntity.getGender());

        return informationDto;
    }


    @Override
    public InformationDto UpdateInformation(String token, InformationDto dto, MultipartFile file) throws IOException {

        Optional<CustomerEntity> customerEntity = customerRepository.findByName(getToken(token));
        if (customerEntity.isEmpty()) {
            throw new RuntimeException("Kullanıcı bulunamadı: ");
        }

        CustomerEntity customerEntity1 = customerEntity.get();
        CustomerInformationEntity customerInformationEntity = customerEntity1.getCustomerInformationEntity();

        if (file != null && !file.isEmpty()) {
            try {

                if (customerInformationEntity.getImgUrl() != null) {
                    minioService.deleteFile(customerInformationEntity.getImgUrl());
                }
                String path = customerEntity1.getName();
                MultipartFile uploadedFile = minioService.saveFile(file, path);
                String newImageUrl = path + "/" + uploadedFile.getOriginalFilename();

                customerInformationEntity.setImgUrl(newImageUrl);
                dto.setImgUrl(newImageUrl);
            } catch (Exception e) {
                throw new RuntimeException("Resim güncellenemedi: " + e.getMessage());
            }
        }

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


    @Override
    public CustomerEntity deleteInformation(String token, MultipartFile file) {

        Optional<CustomerEntity> customerEntity = customerRepository.findByName(getToken(token));
        if (customerEntity.isEmpty()) {
            throw new RuntimeException("Kullanıcı bulunamadı: ");
        }

        CustomerEntity customerEntity1 = customerEntity.get();
        CustomerInformationEntity customerInformationEntity = customerEntity1.getCustomerInformationEntity();

        if (file != null && !file.isEmpty()) {
            try {
                if (customerInformationEntity.getImgUrl() != null) {
                    minioService.deleteFile(customerInformationEntity.getImgUrl());

                    customerInformationEntity.setImgUrl(null);
                }
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("Resim silinirken bir hata oluştu: " + e.getMessage());
            }
        }

        return customerRepository.save(customerEntity1);
    }
}
