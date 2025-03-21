// 📌 PASO 2: Repositorio para acceder a los RefreshTokenRepository en la base de datos


package only.get.infrastructure.persistence.repository;


import only.get.infrastructure.persistence.entity.RefreshTokenEntity;
import only.get.infrastructure.persistence.entity.UserEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import jakarta.transaction.Transactional;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshTokenEntity, Long> {
    Optional<RefreshTokenEntity> findByToken(String token);
    void deleteByToken(String token);

    //opcional buscar por user 
    Optional<RefreshTokenEntity> findByUser(UserEntity user);

    
// Eliminar todos los tokens de un usuario
@Modifying
@Transactional
@Query("DELETE FROM RefreshTokenEntity r WHERE r.user.id = :userId")
void deleteByUserId(@Param("userId") Long userId);
}