package com.Fabrica.TelcoNova.controller;

import java.io.IOException;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @GetMapping("/google")
    public void redirectToGoogle(HttpServletResponse response) throws IOException {
        response.sendRedirect("/oauth2/authorization/google");
    }

    // Nuevo endpoint para que el usuario pueda obtener su token
    @GetMapping("/success")
    public String showToken(@RequestParam("token") String token) {
        return "Login exitoso. Tu token JWT es:\n " + token;
    }
}