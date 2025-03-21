package only.get.application.service;

import only.get.application.dto.customerdto.CustomerRequestDTO;
import only.get.application.dto.customerdto.CustomerResponseDTO;
import only.get.infrastructure.persistence.entity.CustomerEntity;
import only.get.infrastructure.persistence.entity.UserEntity;
import only.get.infrastructure.persistence.repository.CustomerRepository;
import only.get.infrastructure.persistence.repository.UserRepository;
import only.get.mapper.CustomerMapper;
import only.get.shared.validation.CustomerValidator;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;


@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;
    private final CustomerValidator customerValidator;
        private final UserRepository userRepository; // 👈 Añadir


    public CustomerService(CustomerRepository customerRepository,
                           CustomerMapper customerMapper,
                           CustomerValidator customerValidator,  UserRepository userRepository      ) {// 👈 Nuevo parámetro 
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
        this.customerValidator = customerValidator;
        this.userRepository = userRepository;

    }

    @Transactional // 👈 Asegúrate de que el método sea transaccional

    public CustomerResponseDTO createOrUpdateCustomer(CustomerRequestDTO dto) {
    customerValidator.validate(dto);

    // Obtener el usuario autenticado
    String username = SecurityContextHolder.getContext().getAuthentication().getName();
    UserEntity user = userRepository.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("Usuario no autenticado"));

    // Buscar si ya existe el CustomerEntity del usuario
    CustomerEntity existingCustomer = customerRepository.findById(user.getId()).orElse(null);

    if (existingCustomer != null) {
        // 🚀 Si ya tiene datos, no permitimos cambios
        if (existingCustomer.getFullName() != null && !existingCustomer.getFullName().isBlank()) {
            throw new RuntimeException("El usuario ya tiene un perfil de Customer.");
        }

        // 🔹 Si el Customer existe pero está vacío, lo actualizamos
        customerMapper.updateEntityFromDTO(dto, existingCustomer);
        existingCustomer.setUser(user);
        CustomerEntity updatedCustomer = customerRepository.save(existingCustomer);
        return customerMapper.toResponseDTO(updatedCustomer);
    }

    // 🔥 Si no existe, creamos uno nuevo
    CustomerEntity newCustomer = customerMapper.toEntity(customerMapper.toModel(dto));
    newCustomer.setUser(user);
    newCustomer.setId(user.getId());  // Mantener el mismo ID que el UserEntity

    CustomerEntity savedCustomer = customerRepository.save(newCustomer);
    return customerMapper.toResponseDTO(savedCustomer);
}









    

    public CustomerResponseDTO updateCustomer(Long id, CustomerRequestDTO dto) {
        customerValidator.validate(dto);
        CustomerEntity existingEntity = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found"));


        // Aquí puedes actualizar manualmente los campos o usar otro método de mapeo
        existingEntity.setFullName(dto.fullName());
        existingEntity.setAddress(dto.address());
        existingEntity.setDateOfBirth(dto.dateOfBirth());

        CustomerEntity updatedEntity = customerRepository.save(existingEntity);
        return customerMapper.toResponseDTO(updatedEntity);
    }

    public CustomerResponseDTO getCustomer(Long id) {
        CustomerEntity entity = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        return customerMapper.toResponseDTO(entity);
    }

    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }



   // get  all customerss 
    public List<CustomerResponseDTO> getAllCustomers() {
        List<CustomerEntity> customers = customerRepository.findAll();
        return customers.stream()
                .map(customerMapper::toResponseDTO)
                .collect(Collectors.toList());
    }






//adiccion 

    // Obtener el perfil del usuario autenticado
public CustomerResponseDTO getMyCustomer() {
    String username = SecurityContextHolder.getContext().getAuthentication().getName();
    UserEntity user = userRepository.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("Usuario no autenticado"));

    CustomerEntity customer = customerRepository.findByUserId(user.getId())
            .orElseThrow(() -> new RuntimeException("No se encontró perfil de Customer para este usuario"));

    return customerMapper.toResponseDTO(customer);
}

// Obtener un Customer por ID, pero solo si pertenece al usuario autenticado
public CustomerResponseDTO getCustomerIfOwnedByUser(Long id) {
    String username = SecurityContextHolder.getContext().getAuthentication().getName();
    UserEntity user = userRepository.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("Usuario no autenticado"));

    CustomerEntity customer = customerRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Customer no encontrado"));

    if (!customer.getUser().getId().equals(user.getId())) {
        throw new RuntimeException("Acceso denegado: No puedes ver este perfil.");
    }

    return customerMapper.toResponseDTO(customer);
}

    
















}
