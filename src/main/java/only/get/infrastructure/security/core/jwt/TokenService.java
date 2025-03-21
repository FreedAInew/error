//✅ PASO 3.3.1 - Crear JwtProvider

//✅ TokenService maneja únicamente los tokens, sin depender de Spring Security.
//Se encarga de firmar, validar y extraer información del token.

package only.get.infrastructure.security.core.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class TokenService {

    @Value("${security.jwt.secret}") 
    private String jwtSecret;

    @Value("${security.jwt.expiration}") 
    private int jwtExpiration;

    // ✅ Cambio 1: Usar SecretKey en lugar de Key
    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes());
    }

    // ✅ Genera token (método actualizado pero misma firma)
    public String generateToken(String username, Long userId) {
        return Jwts.builder()
                .subject(username)              // .subject() en lugar de setSubject()
                .claim("id", userId)
                .issuedAt(new Date())           // .issuedAt()
                .expiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .signWith(getSigningKey())       // Sin algoritmo (se infiere automáticamente)
                .compact();
    }

    // ✅ Extrae claims (mismo retorno Claims)
    public Claims parseClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())     // .verifyWith() en lugar de setSigningKey()
                .build()
                .parseSignedClaims(token)       // parseSignedClaims()
                .getPayload();                  // .getPayload() en lugar de getBody()
    }

    // ✅ Métodos sin cambios (mismas firmas)
    public String getUsernameFromToken(String token) {
        return parseClaims(token).getSubject();
    }

    public boolean validateToken(String token) {
        try {
            parseClaims(token);
            return true;
        } catch (JwtException e) {
            System.out.println("Error en token: " + e.getMessage());
            return false;
        }
    }
}