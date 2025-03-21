// 📌 PASO 3.1 - Adapta UserEntity para Spring Security
//CustomUserDetails solamente se encarga de representar un usuario en Spring Security.

package only.get.infrastructure.security.details;


import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

import only.get.infrastructure.persistence.entity.RoleEntity;
import only.get.infrastructure.persistence.entity.UserEntity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Getter;


@Getter  // ✅ Evita escribir getters manualmente
public class CustomUserDetails implements UserDetails {
    private Long id;  // 🔥 Agregar ID del usuario(opcional)
    private String username;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;


    // ✅ Constructor necesario para poder instanciar `CustomUserDetails`
    public CustomUserDetails(Long id ,String username, String password, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.authorities = authorities;
    }

    // ✅ Método para convertir UserEntity a CustomUserDetails


    public static CustomUserDetails build(UserEntity user) {
    RoleEntity role = Optional.ofNullable(user.getRole())
        .orElseThrow(() -> new SecurityException("Usuario sin rol: " + user.getUsername()));

    // ✅ Agrega "ROLE_" manualmente aquí
    GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + role.getName().name());

    return new CustomUserDetails(
        user.getId(),
        user.getUsername(),
        user.getPassword(),
        Collections.singletonList(authority)
    );
}

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}










 // ✅ Método para convertir UserEntity a CustomUserDetailssin securityutilys 

   /* public static CustomUserDetails build(UserEntity user) {
            // ✅ Manejo seguro de roles
    RoleEntity role = Optional.ofNullable(user.getRole())
        .orElseThrow(() -> new SecurityException("Usuario sin rol: " + user.getUsername()));

    // ✅ Convertir rol único a Authority
   GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + role.getName().name());*/
    
    // Uso en CustomUserDetails:
        // ✅ Convertir rol único a Authority






/*
    public static CustomUserDetails build(UserEntity user) {
        // ✅ Manejo seguro de roles
        GrantedAuthority authority = SecurityUtils.convertRoleToAuthority(
            Optional.ofNullable(user.getRole())
                .orElseThrow(() -> new SecurityException("Usuario sin rol: " + user.getUsername()))
        );
    
    
        return new CustomUserDetails(
                user.getId(),    // 🔥 Agregar ID
                user.getUsername(),
                user.getPassword(),
                Collections.singletonList(authority) // Lista con 1 elemento

        );
    }/* */