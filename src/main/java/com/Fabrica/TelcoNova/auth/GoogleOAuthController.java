package com.Fabrica.TelcoNova.auth;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

@RestController
@RequestMapping("/oauth/google")
public class GoogleOAuthController {

    @Value("${google.clientId}")
    private String clientId;

    @Value("${google.clientSecret}")
    private String clientSecret;

    @Value("${google.redirectUri}")
    private String redirectUri;

    @Value("${jwt.secret}")
    private String jwtSecret;

    /**
     * Este endpoint genera dinámicamente el enlace de login con Google
     */
    @GetMapping("/login-url")
    public ResponseEntity<String> getGoogleLoginUrl() {
        String authUrl = "https://accounts.google.com/o/oauth2/v2/auth?" +
                "client_id=" + clientId +
                "&redirect_uri=" + redirectUri +
                "&response_type=code" +
                "&scope=email%20profile" +
                "&access_type=offline";

        return ResponseEntity.ok(authUrl);
    }

    /**
     * Google redirige aquí con el código
     */
    @GetMapping("/callback")
    public ResponseEntity<?> handleCallback(@RequestParam("code") String code) {
        System.out.println("🔁 Código recibido: " + code);

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("code", code);
        params.add("client_id", clientId);
        params.add("client_secret", clientSecret);
        params.add("redirect_uri", redirectUri);
        params.add("grant_type", "authorization_code");

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);

        ResponseEntity<Map> response;
        try {
            response = restTemplate.postForEntity("https://oauth2.googleapis.com/token", request, Map.class);
        } catch (Exception e) {
            System.err.println("❌ Error al contactar con Google: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("Error al contactar con Google");
        }

        if (!response.getStatusCode().is2xxSuccessful() || response.getBody() == null) {
            System.err.println("❌ Error en respuesta de token: " + response);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al obtener token de Google");
        }

        String idToken = (String) response.getBody().get("id_token");
        if (idToken == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Token de ID no recibido");
        }

        System.out.println("✅ ID Token recibido: " + idToken);

        DecodedJWT googleJwt;
        try {
            googleJwt = JWT.decode(idToken);
        } catch (Exception e) {
            System.err.println("❌ Error al decodificar el token: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al decodificar token");
        }

        String email = googleJwt.getClaim("email").asString();
        String name = googleJwt.getClaim("name").asString();
        String picture = googleJwt.getClaim("picture").asString();

        System.out.println("📧 Email: " + email);
        System.out.println("👤 Nombre: " + name);
        System.out.println("🖼️ Foto: " + picture);

        String ourToken = JWT.create()
                .withSubject(email)
                .withClaim("name", name != null ? name : "")
                .withClaim("picture", picture != null ? picture : "")
                .withExpiresAt(new Date(System.currentTimeMillis() + 3600 * 1000)) // 1 hora
                .sign(Algorithm.HMAC256(jwtSecret));

        System.out.println("🔐 JWT propio generado: " + ourToken);

        return ResponseEntity.ok(Map.of(
                "token", ourToken,
                "email", email,
                "name", name,
                "picture", picture
        ));
    }
}
