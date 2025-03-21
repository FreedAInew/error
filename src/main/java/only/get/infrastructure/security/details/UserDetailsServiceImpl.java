//✅ PASO 3.2 - Crear UserDetailsServiceImpl

// Servicio de autenticación que carga los datos del usuario desde la base de datos
//Carga usuarios desde la base de datos para que Spring Security los pueda autenticar.


package only.get.infrastructure.security.details;

import only.get.infrastructure.persistence.repository.UserRepository;
import only.get.infrastructure.persistence.entity.UserEntity;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
@Transactional  // Asegura que los roles se carguen bien
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        System.out.println("Usuario encontrado: " + user.getUsername());
        System.out.println("Roles: " + user.getRole().getName().name());

        // ✅ CORRECCIÓN: Usar `CustomUserDetails.build(user)`
        return CustomUserDetails.build(user);
    }
}
