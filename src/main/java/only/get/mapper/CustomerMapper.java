package only.get.mapper;

import only.get.application.dto.customerdto.CustomerRequestDTO;
import only.get.application.dto.customerdto.CustomerResponseDTO;
import only.get.domain.customers.model.Customer;
import only.get.infrastructure.persistence.entity.CustomerEntity;
import only.get.infrastructure.persistence.entity.UserEntity;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
    CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);

    // DTO → Modelo
    @Mapping(target = "id", ignore = true)  // El ID lo genera la base de datos
    Customer toModel(CustomerRequestDTO dto);

    // Modelo → Entidad (Para la base de datos)
    @Mapping(target = "user", ignore = true)  // ❌ Evitamos que MapStruct maneje UserEntity
    CustomerEntity toEntity(Customer model);

    // Entidad → DTO de Respuesta
    CustomerResponseDTO toResponseDTO(CustomerEntity entity);

    // 🔹 Para actualizar un CustomerEntity existente sin sobrescribir el ID
    @Mapping(target = "id", ignore = true) // No tocar el ID
    @Mapping(target = "user", ignore = true) // No sobrescribir UserEntity
    void updateEntityFromDTO(CustomerRequestDTO dto, @MappingTarget CustomerEntity entity);

    // ✅ Método para crear una entidad con UserEntity asignado
    default CustomerEntity toEntityWithUser(Customer model, UserEntity user) {
        CustomerEntity entity = toEntity(model);
        entity.setUser(user);
        entity.setId(user.getId()); // Asegurar que usen el mismo ID
        return entity;
    }
}
