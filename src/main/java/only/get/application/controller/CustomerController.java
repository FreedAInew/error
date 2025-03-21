package only.get.application.controller;


import only.get.application.dto.customerdto.CustomerRequestDTO;
import only.get.application.dto.customerdto.CustomerResponseDTO;
import only.get.application.service.CustomerService;
import jakarta.validation.Valid;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/api/customers")
@SecurityRequirement(name = "bearer-key") // ⬅ Mismo nombre definido en OpenAPI

public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    // 🔹 Crear o actualizar un Customer
    @PostMapping
    public ResponseEntity<CustomerResponseDTO> createOrUpdateCustomer(@Valid @RequestBody CustomerRequestDTO dto) {
        CustomerResponseDTO response = customerService.createOrUpdateCustomer(dto); //✅ Usa el nombre correcto de CustomerService

        return ResponseEntity.ok(response);
    }



    // ✅ Obtener cualquier Customer por ID (Solo ADMIN)
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/{id}")
    
    public ResponseEntity<CustomerResponseDTO> getCustomerByAdmin(@PathVariable Long id) {
        CustomerResponseDTO response = customerService.getCustomer(id);
        return ResponseEntity.ok(response);
    }


    // ✅ Obtener todos los Customers (Solo ADMIN)
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping()
    public ResponseEntity<List<CustomerResponseDTO>> getAllCustomers() {
        return ResponseEntity.ok(customerService.getAllCustomers());
    }





    //@GetMapping("/me")

    // Obtener el Customer del usuario autenticado
@GetMapping("/me")
public ResponseEntity<CustomerResponseDTO> getMyCustomer() {
    return ResponseEntity.ok(customerService.getMyCustomer());
}

// Obtener un Customer por ID (pero solo si pertenece al usuario autenticado)
@GetMapping("/{id}")
public ResponseEntity<CustomerResponseDTO> getCustomerByUser(@PathVariable Long id) {
    return ResponseEntity.ok(customerService.getCustomerIfOwnedByUser(id));
}



    



    // Actualizar un Customer (PUT para actualización completa)
    @PutMapping("/{id}")
    public ResponseEntity<CustomerResponseDTO> updateCustomer(@PathVariable Long id,
                                                              @RequestBody @Valid CustomerRequestDTO dto) {
        CustomerResponseDTO response = customerService.updateCustomer(id, dto);
        return ResponseEntity.ok(response);
    }
    
    // También podrías tener un PATCH para actualizaciones parciales
    @PatchMapping("/{id}")
    public ResponseEntity<CustomerResponseDTO> patchCustomer(@PathVariable Long id,
                                                             @RequestBody @Valid CustomerRequestDTO dto) {
        CustomerResponseDTO response = customerService.updateCustomer(id, dto);
        return ResponseEntity.ok(response);
    }

    // Eliminar un Customer (DELETE)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }
}
