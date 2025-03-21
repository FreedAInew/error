package only.get.domain.marketplace.model;

import java.math.BigDecimal;

import java.util.Objects;

public class Product {
    private final String name;
    private final String description;
    private final BigDecimal price;

    public Product(String name, String description, BigDecimal price) {
        this.name = Objects.requireNonNull(name, "El nombre no puede ser nulo");
        if (name.trim().isEmpty()) throw new IllegalArgumentException("El nombre no puede estar vacío");

        this.description = (description == null || description.trim().isEmpty()) ? "Sin descripción" : description;
        
        this.price = Objects.requireNonNull(price, "El precio no puede ser nulo");
        if (price.compareTo(BigDecimal.ZERO) <= 0) throw new IllegalArgumentException("El precio debe ser mayor a 0");
    }

    // Getters
    public String getName() { return name; }
    public String getDescription() { return description; }
    public BigDecimal getPrice() { return price; }
}
