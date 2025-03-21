package only.get.infrastructure.persistence.repository;




import only.get.infrastructure.persistence.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    // Métodos adicionales si los necesitas (ej: buscar por nombre)
}
