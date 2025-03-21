package only.get.shared.exception;


// ❌ NO usar @Component aquí, no es un Bean de Spring
public class InvalidCustomerException extends RuntimeException {
    public InvalidCustomerException(String message) {
        super(message);
    }
}
