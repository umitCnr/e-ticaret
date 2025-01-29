package com.caner.e_ticaret.dtos;

import com.caner.e_ticaret.enums.Enums;
import lombok.Data;

@Data
public class InformationDto {

    private String surname;
    private Long id;
    private Integer age;
    private String email;
    private String phoneNumber;
    private Enums.gender gender;
    private String address;
    private String companyName;
    private String companyAddress;
    private String imgUrl;


}
