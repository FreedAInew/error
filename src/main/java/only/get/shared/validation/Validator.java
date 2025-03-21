package only.get.shared.validation;


@FunctionalInterface
public interface Validator<T> {
    void validate(T t);
}

