package only.get.shared.validation;


import only.get.application.dto.customerdto.CustomerRequestDTO;

import org.springframework.stereotype.Component;

@Component
public class CustomerValidator implements Validator<CustomerRequestDTO> {
    private final StringValidator stringValidator;

    public CustomerValidator(StringValidator stringValidator) {
        this.stringValidator = stringValidator;
    }

    @Override
    public void validate(CustomerRequestDTO dto) {
        // Se usa el StringValidator para validar la longitud
        stringValidator.validateLength(dto.fullName(), 3);
    }
}
