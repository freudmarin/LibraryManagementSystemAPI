package com.marin.librarymanagementsystemapi.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;

@Component
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        response.setContentType("application/json");

        PrintWriter writer = response.getWriter();
        writer.write("{\"status\": 401, \"error\": \"Unauthorized\", \"message\": \"Unauthorized request\", " +
                "\"reason\": \"" + authException.getMessage() + "\"," + "\"path\": \"" + request.getRequestURI() + "\"}");
        writer.flush();
        writer.close();
    }
}
