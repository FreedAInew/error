// 📌 PASO 2: Repositorio para acceder a los roles en la base de datos

package only.get.infrastructure.persistence.repository;

import only.get.infrastructure.persistence.entity.RoleEntity;
import only.get.infrastructure.security.details.enums.RoleType;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
    Optional<RoleEntity> findByName(RoleType name); // 🔍 Buscar rol por nombre
    boolean existsByName(RoleType name); // 👈 Usa RoleType, no String


}

