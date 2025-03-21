package only.get.shared.validation;
import only.get.shared.exception.InvalidCustomerException;

import org.springframework.stereotype.Component;

@Component
public class StringValidator {
    public void validateLength(String value, int minLength) {
        if (value == null || value.length() < minLength) {
            throw new InvalidCustomerException("El valor debe tener al menos " + minLength + " caracteres.");
        }
    }
}
