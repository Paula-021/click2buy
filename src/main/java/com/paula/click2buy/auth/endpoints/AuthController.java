package com.paula.click2buy.auth.endpoints;

import com.paula.click2buy.auth.endpoints.dtos.LoginRequestDTO;
import com.paula.click2buy.auth.endpoints.dtos.LoginResponseDTO;
import com.paula.click2buy.auth.jwt.JwtService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginRequestDTO, HttpServletResponse response) {

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequestDTO.getEmail(), loginRequestDTO.getPassword()));

        // Aqui você pode gerar um token JWT ou outra forma de autenticação

        String token = jwtService.generateToken(authentication);
        System.out.println(token);

        ResponseCookie cookie = ResponseCookie.from("accessToken", token)
                .httpOnly(true) //impede que o cookie seja acessado por JavaScript, aumentando a segurança contra ataques de Cross-Site Scripting (XSS).
                .secure(false) //somente em modo desenvolvimento, em produção deve ser true para garantir que o cookie seja enviado apenas em conexões HTTPS.
                .path("/") // disponivel em toda a aplicação
                .maxAge(60 * 60) // 1 hora
                .sameSite("Strict") // impede que o cookie seja enviado em requisições cross-site, aumentando a segurança contra ataques de Cross-Site Request Forgery (CSRF).
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString()); //adicionando o cookie dentro do header da resposta



        return ResponseEntity.ok().build();


    }


}
