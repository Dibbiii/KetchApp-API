package com.alessandra_alessandro.ketchapp.jwt;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        // Questo metodo viene chiamato quando un utente non autenticato tenta di accedere a una risorsa protetta
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage());
    }
}