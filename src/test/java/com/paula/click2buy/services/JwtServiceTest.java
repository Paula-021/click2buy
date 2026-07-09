package com.paula.click2buy.services;


import com.paula.click2buy.auth.jwt.JwtService;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class JwtServiceTest {

    private JwtService jwtService = new JwtService("suGvdtEoWJVhrJEV4U7wIOkU45vrsmvATkmYcAtxcnV");

    @Test
    void shouldGenerateValidToken(){

        //Arrange

        UserDetails userDetails = User.withUsername("paula@gmail.com")
                .password("password123")
                .roles("USER")
                .build();

        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

        //Act

        String token = jwtService.generateToken(authentication);

        boolean isValid = jwtService.isTokenValid(token, userDetails);

        //Assert
        assertNotNull(token);
        assertTrue(isValid);

    }

}
