package com.paula.click2buy.shipment.endpoints;

import com.paula.click2buy.shipment.dtos.TokenResponseDTO;
import com.paula.click2buy.shipment.services.MelhorEnvioOAuthService;
import com.paula.click2buy.shipment.services.MelhorEnvioTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/oauth")
public class OAuthCallbackController {

    @Autowired
    private MelhorEnvioOAuthService melhorEnvioOAuthService;
    @Autowired
    private MelhorEnvioTokenService melhorEnvioTokenService;

    @GetMapping("/callback")
    public ResponseEntity<?> callback(@RequestParam String code){
        // Lógica para processar o código de autorização recebido
        // e trocar por um token de acesso

        TokenResponseDTO tokenResponseDTO = melhorEnvioOAuthService.exchangeCodeForToken(code);

        melhorEnvioTokenService.saveToken(tokenResponseDTO.getAccessToken(), tokenResponseDTO.getRefreshToken(), tokenResponseDTO.getExpiresIn()) ;


        return ResponseEntity.ok("Código de autorização recebido: Access Token" + tokenResponseDTO.getAccessToken() + "\n Token Type: " + tokenResponseDTO.getTokenType() + "\n Expires In: " + tokenResponseDTO.getExpiresIn() + "\n Refresh Token: " + tokenResponseDTO.getRefreshToken());
    }
}
