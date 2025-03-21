package only.get.mapper;

import only.get.application.dto.productdto.ProductRequestDTO;
import only.get.application.dto.productdto.ProductResponseDTO;
import only.get.domain.marketplace.model.Product;
import only.get.infrastructure.persistence.entity.ProductEntity;

import java.math.BigDecimal;

public class ProductMapper {

    // 🔹 Convierte ProductRequestDTO → Product (Para aplicar reglas de negocio)
    public static Product toDomain(ProductRequestDTO dto) {
        if (dto == null) return null;
        return new Product(dto.name(), dto.description(), BigDecimal.valueOf(dto.price()));
    }

    // 🔹 Convierte Product → ProductEntity (Para guardar en la BD)
    public static ProductEntity toEntity(Product product) {
        if (product == null) return null;
        return new ProductEntity( product.getName(), product.getDescription(), product.getPrice());
    }

    // 🔹 Convierte ProductEntity → ProductResponseDTO (Para enviar al usuario)
    public static ProductResponseDTO toDTO(ProductEntity entity) {
        if (entity == null) return null;
        return new ProductResponseDTO(entity.getId(), entity.getName(), entity.getDescription(), entity.getPrice());
    }
}
