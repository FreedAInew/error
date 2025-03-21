// 3.5 - AuthService.java
package only.get.infrastructure.security.auth;

import only.get.infrastructure.persistence.entity.CustomerEntity;
import only.get.infrastructure.persistence.entity.RoleEntity;
import only.get.infrastructure.persistence.entity.UserEntity;
import only.get.infrastructure.persistence.repository.CustomerRepository;
import only.get.infrastructure.persistence.repository.RoleRepository;
import only.get.infrastructure.persistence.repository.UserRepository;
import only.get.infrastructure.security.auth.dto.AuthResponse;
import only.get.infrastructure.security.auth.dto.RegisterRequest;
import only.get.infrastructure.security.core.jwt.JwtProvider;
import only.get.infrastructure.security.core.jwt.JwtTokens;
import only.get.infrastructure.security.details.CustomUserDetails;
import only.get.infrastructure.security.details.enums.RoleType;
import only.get.infrastructure.security.service.RefreshTokenService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final RefreshTokenService refreshTokenService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final CustomerRepository customerRepository;

    // ✅ Inyección de dependencias
    public AuthService(AuthenticationManager authenticationManager,
                      JwtProvider jwtProvider,
                      RefreshTokenService refreshTokenService,
                      UserRepository userRepository,
                      PasswordEncoder passwordEncoder,
                      RoleRepository roleRepository,
                      CustomerRepository customerRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtProvider = jwtProvider;
        this.refreshTokenService = refreshTokenService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.customerRepository = customerRepository;
    }

    // ✅ Registro de nuevo usuario con perfil de cliente
    public void register(RegisterRequest request) {
        // Validación de usuario existente
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new IllegalArgumentException("El usuario ya existe");
        }

        

        // 🔹 Creación del usuario principal
        UserEntity user = new UserEntity();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        
        
        // Asignar rol por defecto
        RoleEntity defaultRole = roleRepository.findByName(RoleType.USER)
                .orElseThrow(() -> new RuntimeException("Rol por defecto no configurado"));
                user.setRole((defaultRole));

                user = userRepository.save(user); // ✅ Guardamos el usuario
        
        

        // 🔹 Creación del perfil de cliente asociado
        CustomerEntity customer = new CustomerEntity();
        customer.setUser(user);  // Establecer relación bidireccional
        //customer.setFullName(request.getFullName());

        customerRepository.save(customer);
    }

    // ✅ Autenticación y generación de tokens
    public AuthResponse authenticate(String username, String password) {
        // Autenticación con Spring Security
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(username, password)
        );

        // Conversión a UserDetails personalizado
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        // Generación de tokens JWT
        JwtTokens tokens = jwtProvider.generarTokens(userDetails);

        return new AuthResponse(
            tokens.accessToken(),
            tokens.refreshToken()  // ✅ Token generado por JwtProvider
        );
    }

    // ✅ Gestión de logout
    public void logout(String refreshToken) {
        refreshTokenService.revokeRefreshToken(refreshToken);
    }

    public void logout(Long userId) {
        refreshTokenService.revokeAllTokensByUserId(userId);
    }

    // ✅ Renovación de Access Token
    public String refreshAccessToken(String refreshToken, Long userId) {
        if (!refreshTokenService.validateRefreshToken(refreshToken, userId)) {
            throw new IllegalArgumentException("Refresh Token inválido o expirado");
        }

        UserDetails user = loadUserById(userId);
        return jwtProvider.refrescarToken(refreshToken, user);
    }

    // ✅ Carga de usuario por ID
    public UserDetails loadUserById(Long userId) {
        UserEntity userEntity = userRepository.findById(userId)
            .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con ID: " + userId));
        return CustomUserDetails.build(userEntity);
    }










    public void registerAdmin(String username, String password) {
        if (userRepository.existsByRole_Name(RoleType.ADMIN)) {
            throw new IllegalStateException("Ya existe un administrador registrado.");
        }
    
        UserEntity admin = new UserEntity();
        admin.setUsername(username);
        admin.setPassword(passwordEncoder.encode(password));
        admin.setRole(roleRepository.findByName(RoleType.ADMIN)
                    .orElseThrow(() -> new IllegalStateException("Rol ADMIN no encontrado")));
    
        userRepository.save(admin);
    }










    public boolean validateToken(String token) {
        return jwtProvider.validarToken(token); // Llamada al método que valida el token en JwtProvider
    } 

    
    













}