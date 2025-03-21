package only.get.domain.customers.model;


import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
public class Customer {
    private Long id;
    private String fullName;
    private String address;
    private LocalDate dateOfBirth;

    // 🔥 Reglas de negocio aquí
    public void validateCustomer() {
        if (fullName == null || fullName.isBlank()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío.");
        }
        if (dateOfBirth == null || dateOfBirth.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("La fecha de nacimiento no es válida.");
        }
    }
}
