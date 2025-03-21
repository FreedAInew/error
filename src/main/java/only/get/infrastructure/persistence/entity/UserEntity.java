//📌 PASO 1: Definir la entidad 

// ✅ Sirve para modelar los usuarios en la base de datos usando JPA.
//UserEntity separado de Spring Security (solo maneja datos de la BD).


package only.get.infrastructure.persistence.entity;



import jakarta.persistence.JoinColumn;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;



@Entity
@Getter
@Setter
@Table(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

   @ManyToOne(fetch = FetchType.LAZY) 
   @JoinColumn(name = "role_id", nullable = false)
   private RoleEntity role;

}