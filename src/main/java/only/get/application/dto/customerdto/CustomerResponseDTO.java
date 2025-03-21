package only.get.application.dto.customerdto;


import java.time.LocalDate;

public record CustomerResponseDTO(Long id, String fullName, String address, LocalDate dateOfBirth) { }
