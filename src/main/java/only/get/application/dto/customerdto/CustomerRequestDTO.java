
package only.get.application.dto.customerdto;

import java.time.LocalDate;

public record CustomerRequestDTO(String fullName, String address, LocalDate dateOfBirth) { }
