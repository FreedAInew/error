// AuthController.java (Controlador que expone los endpoints de autenticación)
package only.get.infrastructure.security.auth;

import only.get.infrastructure.security.auth.dto.AuthRequest;
import only.get.infrastructure.security.auth.dto.AuthResponse;
import only.get.infrastructure.security.auth.dto.RefreshRequest;
import only.get.infrastructure.security.auth.dto.RegisterRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*") // Permitir solicitudes desde otros dominios
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }


    // Endpoint para registro público
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        authService.register(request);
        return ResponseEntity.ok("Usuario registrado exitosamente");
    }






    /**
     * ✅ Autenticación de usuario y generación de Access y Refresh Token.
     */
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody AuthRequest authRequest) {
        AuthResponse authResponse = authService.authenticate(authRequest.getUsername(), authRequest.getPassword());
        return ResponseEntity.ok(authResponse);
    }

    /**
     * ✅ Genera un nuevo Access Token con el Refresh Token válido.
     */


    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refreshToken(@Valid @RequestBody RefreshRequest refreshRequest) {
        String newAccessToken = authService.refreshAccessToken(refreshRequest.getRefreshToken(), refreshRequest.getUserId());
        return ResponseEntity.ok(new AuthResponse(newAccessToken, refreshRequest.getRefreshToken()));
    }

    /**
     * ✅ Logout → Invalida el Refresh Token.
     */
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@Valid @RequestBody RefreshRequest refreshRequest) {
        authService.logout(refreshRequest.getUserId()); // Elimina todos los Refresh Tokens
        return ResponseEntity.noContent().build();
    }









//union de microservicio??
    @GetMapping("/validate")
public ResponseEntity<?> validateToken(@RequestParam String token) {
    boolean isValid = authService.validateToken(token);
    return isValid ? ResponseEntity.ok().body("Valid") 
                   : ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid");
}
 







}