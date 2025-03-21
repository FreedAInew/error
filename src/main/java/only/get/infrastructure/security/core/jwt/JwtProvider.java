//✅ PASO 3.3.2 - Crear JwtProvider


//✅ JwtProvider interactúa con Spring Security y usa TokenService como dependencia.
//JwtProvider usa TokenService como dependencia, pero también trabaja con UserDetails (Spring Security)


package only.get.infrastructure.security.core.jwt;

import io.jsonwebtoken.Claims;

import only.get.infrastructure.security.details.CustomUserDetails;
import only.get.infrastructure.security.service.RefreshTokenService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class JwtProvider {

    private final TokenService tokenService;
    private final RefreshTokenService refreshTokenService; // Nuevo servicio

    public JwtProvider(TokenService tokenService, RefreshTokenService refreshTokenService) {
        this.tokenService = tokenService;
        this.refreshTokenService = refreshTokenService;
    }

    // ✅ Genera los tokens (Access + Refresh)
    public JwtTokens generarTokens(UserDetails user) {


        if (!(user instanceof CustomUserDetails customUser)) {
            throw new IllegalArgumentException("Tipo de usuario inválido");
        }
        String accessToken = tokenService.generateToken(user.getUsername(), customUser.getId());
        String refreshToken = refreshTokenService.generateRefreshToken(customUser.getId());
        return new JwtTokens(accessToken, refreshToken);
    }

    // ✅ Valida el token asegurando que pertenece al usuario correcto
    public boolean validarToken(String token, UserDetails user) {
        Claims claims = tokenService.parseClaims(token);
        return claims.getSubject().equals(user.getUsername()) && tokenService.validateToken(token);
    }

    // ✅ Extrae el usuario desde el token
    public String getUsuarioDesdeToken(String token) {
        return tokenService.getUsernameFromToken(token);
    }

    // ✅ Refresca el Access Token usando un Refresh Token válido
    public String refrescarToken(String refreshToken, UserDetails user) {
        System.out.println("Intentando refrescar con token: " + refreshToken);
        System.out.println("UserDetails recibido: " + user.getUsername());
        System.out.println("User ID esperado: " + ((CustomUserDetails) user).getId());
    
        if (refreshTokenService.validateRefreshToken(refreshToken, ((CustomUserDetails) user).getId())) {
            return generarTokens(user).accessToken();
        }
        throw new IllegalArgumentException("Refresh Token inválido o expirado");
    }







    //for chat conexion whith auth  (websoquets 
    public boolean validarToken(String token) {
        return tokenService.validateToken(token); // Delegamos la validación a TokenService
    }
    






   























    
}
