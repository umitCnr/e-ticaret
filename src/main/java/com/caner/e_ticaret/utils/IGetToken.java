package com.caner.e_ticaret.utils;



public interface IGetToken {

    default String getToken(String token){

        JwtService jwtService = new JwtService();

        String jwt = token.substring(7);
        String jwt_control = jwtService.findUsername(jwt);

        return jwt_control;
    }
}
