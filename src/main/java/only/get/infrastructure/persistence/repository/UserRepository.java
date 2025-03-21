// 📌 PASO 2: Repositorio para acceder a los usuarios en la base de datos


package only.get.infrastructure.persistence.repository;


import only.get.infrastructure.persistence.entity.UserEntity;
import only.get.infrastructure.security.details.enums.RoleType;

import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
   Optional<UserEntity> findByUsername(String username); // 👈 Debe existir


boolean existsByUsername(String username);

// ✅ Método para verificar si existe al menos un usuario con un rol específico
boolean existsByRole_Name(RoleType name);

//@Query("SELECT u FROM UserEntity u WHERE u.username = :username")
//UserEntity findByUsername(@Param("username") String username);

}

