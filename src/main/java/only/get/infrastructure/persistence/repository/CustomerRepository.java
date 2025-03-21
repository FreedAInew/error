package only.get.infrastructure.persistence.repository;


import only.get.infrastructure.persistence.entity.CustomerEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {
    // 🔍 Busca un CustomerEntity por el ID del UserEntity asociado
    Optional<CustomerEntity> findByUserId(Long userId); 


    // ✅ Método más explícito que verifica si un usuario ya tiene un CustomerEntity
    boolean existsByUserId(Long userId);



}

