package only.get.infrastructure.security.auth.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RefreshRequest {
    private String refreshToken;
    private Long userId;

}
