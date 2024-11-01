package br.ucsal.docshare.security;

import java.util.Date;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;

@Component
public class JwtUtil {

    private String secretKey = "VkFJIERBUiBCUkFTSUlJSUlJTExMTExWQUkgREFSIEJSQVNJSUlJSUlMTExMTFZBSSBEQVIgQlJBU0lJSUlJSUxMTExMVkFJIERBUiBCUkFTSUlJSUlJTExMTExWQUkgREFSIEJSQVNJSUlJSUlMTExMTFZBSSBEQVIgQlJBU0lJSUlJSUxMTExMVkFJIERBUiBCUkFTSUlJSUlJTExMTExWQUkgREFSIEJSQVNJSUlJSUlMTExMTFZBSSBEQVIgQlJBU0lJSUlJSUxMTExMVkFJIERBUiBCUkFTSUlJSUlJTExMTEw=";

    public String generateToken(String username) {
        return Jwts.builder()
            .setSubject(username)
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + 10000 * 60 * 60))
            .signWith(SignatureAlgorithm.HS256, secretKey)
            .compact();
    }

    public Claims extractClaims(String token) {
        try {
            return Jwts.parser()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
        } catch (SignatureException e) {
            // Tratar exceção quando a assinatura não for válida
            throw new RuntimeException("Invalid JWT signature: " + e.getMessage());
        }
    }

    public boolean isTokenValid(String token, String username) {
        return username.equals(extractClaims(token).getSubject()) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractClaims(token).getExpiration().before(new Date());
    }
}
