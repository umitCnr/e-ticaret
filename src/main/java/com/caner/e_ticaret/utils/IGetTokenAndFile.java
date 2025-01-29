package com.caner.e_ticaret.utils;

import lombok.Data;
import lombok.RequiredArgsConstructor;


public interface IGetTokenAndFile {

    default String getToken(String token){

        JwtService jwtService = new JwtService();

        String jwt = token.substring(7);
        String jwt_control = jwtService.findUsername(jwt);

        return jwt_control;
    }
}
