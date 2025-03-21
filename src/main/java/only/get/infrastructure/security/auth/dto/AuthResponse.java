// AuthResponse.java (DTO para devolver el token y otros datos en la respuesta del login)

package only.get.infrastructure.security.auth.dto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AuthResponse {
    private String accessToken;
    private String refreshToken;
}
