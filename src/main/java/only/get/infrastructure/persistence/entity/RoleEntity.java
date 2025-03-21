// 📌 PASO 1: Definir la entidad 

// ✅ Representa los roles de usuario en la base de datos usando JPA.

package only.get.infrastructure.persistence.entity;



import java.util.List;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import only.get.infrastructure.security.details.enums.RoleType;


@Entity
@Getter
@Setter
@Table(name = "roles")
public class RoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true)
    private RoleType name;  // 🔥 Enum: `ADMIN`, `USER`, etc.

    @OneToMany(mappedBy = "role", fetch = FetchType.LAZY)
    private List<UserEntity> users; // ✅ Un rol puede estar en varios usuarios
}
