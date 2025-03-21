package only.get.infrastructure.security.service;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import only.get.infrastructure.persistence.entity.RefreshTokenEntity;
import only.get.infrastructure.persistence.entity.UserEntity;
import only.get.infrastructure.persistence.repository.RefreshTokenRepository;
import only.get.infrastructure.persistence.repository.UserRepository;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;







@Service
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;

    public RefreshTokenService(RefreshTokenRepository refreshTokenRepository, UserRepository userRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.userRepository = userRepository;
    }

    // ✅ Genera y almacena un Refresh Token para un usuario
    public String generateRefreshToken(Long userId) {
        Optional<UserEntity> userOpt = userRepository.findById(userId);

        if (userOpt.isEmpty()) {
            throw new IllegalArgumentException("Usuario no encontrado");
        }

        UserEntity user = userOpt.get();

        // 🔹 1. Eliminar tokens anteriores antes de generar uno nuevo
    refreshTokenRepository.deleteByUserId(userId); 

        // 🔹 2. Generar nuevo token y guardarlo

        String token = UUID.randomUUID().toString();
        Instant expiryDate = Instant.now().plusSeconds(86400); // Expira en 1 día

        RefreshTokenEntity refreshToken = new RefreshTokenEntity(token, user, expiryDate);

        refreshTokenRepository.save(refreshToken);
        return token;
    }

    // ✅ Valida si el Refresh Token es válido, pertenece al usuario y NO está expirado
    public boolean validateRefreshToken(String token, Long userId) {
        Optional<RefreshTokenEntity> refreshTokenOpt = refreshTokenRepository.findByToken(token);

        if (refreshTokenOpt.isEmpty()) {
            return false; // Token no encontrado
        }

        RefreshTokenEntity refreshToken = refreshTokenOpt.get();

        // 🔥 Si el token está expirado, se elimina y devuelve falso
        if (refreshToken.getExpiryDate().isBefore(Instant.now())) {
            refreshTokenRepository.delete(refreshToken);
            return false;
        }

        return refreshToken.getUser().getId().equals(userId); // 🔥 Ahora usamos refreshToken.getUser()
    }

     // ✅ Revoca un Refresh Token específico
    public void revokeRefreshToken(String token) {
        refreshTokenRepository.deleteByToken(token);
    }

    // ✅ Revoca todos los Refresh Tokens de un usuario al hacer logout
    @Transactional
    public void revokeAllTokensByUserId(Long userId) {
        refreshTokenRepository.deleteByUserId(userId);
    }
}