package only.get.infrastructure.security;


import only.get.infrastructure.persistence.entity.RoleEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public class SecurityUtils { // ✅ Debe ser una clase normal, no un método suelto

    public static GrantedAuthority convertRoleToAuthority(RoleEntity role) {
        if (role == null || role.getName() == null) {
            throw new IllegalArgumentException("Rol inválido o sin nombre");
        }
        return new SimpleGrantedAuthority("ROLE_" + role.getName().name());
    }
}

// Uso en CustomUserDetails:
//GrantedAuthority authority = SecurityUtils.convertRoleToAuthority(user.getRole());
