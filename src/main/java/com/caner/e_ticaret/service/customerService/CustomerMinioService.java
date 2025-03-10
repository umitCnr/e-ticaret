package com.caner.e_ticaret.service.customerService;

import com.caner.e_ticaret.dtos.ImgDto;
import com.caner.e_ticaret.entities.musteri.CustomerEntity;
import com.caner.e_ticaret.entities.musteri.CustomerInformationEntity;
import com.caner.e_ticaret.repository.ICustomerRepository;
import com.caner.e_ticaret.service.MinioService;
import com.caner.e_ticaret.utils.ACMinio;
import com.caner.e_ticaret.utils.Base64Img;
import com.caner.e_ticaret.utils.JwtService;
import lombok.RequiredArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Base64;
import java.util.Optional;
import java.util.logging.Logger;


@Service
public class CustomerMinioService extends ACMinio {


    public CustomerMinioService(JwtService jwtService, MinioService minioService, ICustomerRepository customerRepository) {
        super(jwtService, minioService, customerRepository);
    }

    @Override
    public ImgDto saveImage(MultipartFile file, String token) {

        CustomerEntity customerEntity = findUsernameByToken(token);
        CustomerInformationEntity customerInformationEntity = findInformationToCustomer(customerEntity);

        ImgDto imgDto = new ImgDto();

        try {

            String path = customerEntity.getName();
            MultipartFile uploadedFile = minioService.saveFile(file, path);
            imgDto.setImg(path + "/" + uploadedFile.getOriginalFilename());
            customerInformationEntity.setImgUrl(imgDto.getImg());

        } catch (Exception e) {

            throw new RuntimeException("Dosya yüklenirken hata oluştu :", e);
        }

        customerRepository.save(customerEntity);

        return imgDto;
    }

    @Override
    public ImgDto getImage(String token) {

        CustomerEntity customerEntity = findUsernameByToken(token);
        CustomerInformationEntity customerInformationEntity = findInformationToCustomer(customerEntity);
        ImgDto imgDto = new ImgDto();

        try {

            String url = customerInformationEntity.getImgUrl();

            if (url != null){

                byte[] image = minioService.getFile(url);
                String base64Image = Base64.getEncoder().encodeToString(image);
                imgDto.setImg(base64Image);
            }else {

                return null;
            }

        } catch (Exception e) {
            throw new RuntimeException("Dosya getirilirken hata oluştu :.", e);

        }
        return imgDto;
    }


    @Override
    public String deleteImage(String token) {
        CustomerEntity customerEntity = findUsernameByToken(token);
        CustomerInformationEntity customerInformationEntity = findInformationToCustomer(customerEntity);

        try {
            String url = customerInformationEntity.getImgUrl();

            if (url != null) {
                minioService.deleteFile(url);
                customerInformationEntity.setImgUrl(null);

                customerRepository.save(customerEntity);

                return "Resim silindi.";
            } else {
                return "Resim zaten mevcut değil.";
            }

        } catch (Exception e) {
            throw new RuntimeException("Dosya silinirken hata oluştu: " + e.getMessage(), e);
        }
    }

}
