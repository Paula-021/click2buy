package com.paula.click2buy.shipment.services;

import com.paula.click2buy.shipment.dtos.TokenResponseDTO;
import org.springframework.stereotype.Service;

@Service
public interface MelhorEnvioOAuthService {

    TokenResponseDTO exchangeCodeForToken(String code);

    TokenResponseDTO refreshToken(String refreshToken);
}
