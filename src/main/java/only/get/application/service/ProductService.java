package only.get.application.service;


import only.get.application.dto.productdto.ProductRequestDTO;
import only.get.application.dto.productdto.ProductResponseDTO;
import only.get.domain.marketplace.model.Product;
import only.get.infrastructure.persistence.entity.ProductEntity;
import only.get.infrastructure.persistence.repository.ProductRepository;
import only.get.mapper.ProductMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // 🔹 Crear producto
    public ProductResponseDTO createProduct(ProductRequestDTO requestDTO) {
        Product product = ProductMapper.toDomain(requestDTO); // Convierte DTO a dominio
        ProductEntity entity = ProductMapper.toEntity(product); // Convierte dominio a Entity
        ProductEntity savedEntity = productRepository.save(entity); // Guarda en la BD
        return ProductMapper.toDTO(savedEntity); // Convierte Entity a ResponseDTO
    }

    // 🔹 Obtener todos los productos
    public List<ProductResponseDTO> getAllProducts() {
        List<ProductEntity> products = productRepository.findAll();
        return products.stream().map(ProductMapper::toDTO).collect(Collectors.toList());
    }

    // 🔹 Obtener un producto por ID
    public Optional<ProductResponseDTO> getProductById(Long id) {
        return productRepository.findById(id).map(ProductMapper::toDTO);
    }

    // 🔹 Eliminar un producto por ID
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}
