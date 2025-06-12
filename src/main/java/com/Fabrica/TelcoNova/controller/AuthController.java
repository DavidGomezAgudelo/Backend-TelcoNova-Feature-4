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

@GetMapping("/success")
public String showTokenPage(@RequestParam("token") String token) {
    String htmlTemplate = """
        <!DOCTYPE html>
        <html lang="es">
        <head>
            <meta charset="UTF-8">
            <title>Token Obtenido</title>
            <style>
                body { font-family: sans-serif; display: flex; justify-content: center; align-items: center; height: 100vh; background-color: #f0f2f5; margin: 0; }
                .container { background: white; padding: 40px; border-radius: 8px; box-shadow: 0 4px 12px rgba(0,0,0,0.1); text-align: center; max-width: 90%; }
                h1 { color: #1c1e21; }
                #token { word-break: break-all; background: #f5f6f7; border: 1px solid #dddfe2; padding: 10px; margin: 20px 0; border-radius: 4px; }
                button { background-color: #1877f2; color: white; border: none; padding: 12px 20px; border-radius: 6px; font-size: 16px; cursor: pointer; transition: background-color 0.2s; }
                button:hover { background-color: #166fe5; }
                #copy-feedback { color: #4b8a33; font-weight: bold; opacity: 0; transition: opacity 0.3s; }
            </style>
        </head>
        <body>
            <div class="container">
                <h1>¡Login Exitoso!</h1>
                <p>Aquí tienes tu token JWT. Cópialo y pégalo en la herramienta de testing.</p>
                <pre id="token">__TOKEN_PLACEHOLDER__</pre> <!-- Usamos un marcador de posición único -->
                <button onclick="copyToken()">Copiar Token</button>
                <p id="copy-feedback">¡Copiado!</p>
            </div>
            <script>
                function copyToken() {
                    const tokenElement = document.getElementById('token');
                    navigator.clipboard.writeText(tokenElement.textContent).then(() => {
                        const feedback = document.getElementById('copy-feedback');
                        feedback.style.opacity = 1;
                        setTimeout(() => { feedback.style.opacity = 0; }, 2000);
                    }).catch(err => {
                        console.error('Error al copiar el token: ', err);
                        alert('No se pudo copiar el token. Por favor, hazlo manualmente.');
                    });
                }
            </script>
        </body>
        </html>
    """;
    
    // Reemplazamos el marcador de posición con el token real
    return htmlTemplate.replace("__TOKEN_PLACEHOLDER__", token);
}
}