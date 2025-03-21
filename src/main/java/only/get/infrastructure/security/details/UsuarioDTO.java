package only.get.infrastructure.security.details;

import lombok.Getter;

//package only.get.api.dto;


@Getter

public class UsuarioDTO {
    private String username;

    public UsuarioDTO(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}

