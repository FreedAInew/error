package only.get.infrastructure.persistence.entity;



import java.time.LocalDate;

import jakarta.persistence.Entity;

import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;



@Entity
@Getter
@Setter
@Table(name = "customers")
public class CustomerEntity {
    @Id
    private Long id;  // 👈 Usa el mismo ID de UserEntity

    @OneToOne
    @MapsId  // 👈 Vincula los ID, asegurando que cada Customer tenga su UserEntity
    @JoinColumn(name = "user_id")
    private UserEntity user;

    private String fullName;
    private String address;
    private LocalDate dateOfBirth;
}
