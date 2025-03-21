package only.get.infrastructure.persistence.entity;


import jakarta.persistence.*;
import java.time.Instant;



import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

@Entity
public class RefreshTokenEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String token;

    @Column(nullable = false)
    private Instant expiryDate;

    @ManyToOne(fetch = FetchType.LAZY) // ✅ Relación correcta con UserEntity
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    public RefreshTokenEntity() {}

    public RefreshTokenEntity(String token, UserEntity user, Instant expiryDate) {
        this.token = token;
        this.user = user;
        this.expiryDate = expiryDate;
    }

    public Long getId() {
        return id;
    }

    public String getToken() {
        return token;
    }

    public UserEntity getUser() {
        return user;
    }

    public Instant getExpiryDate() {
        return expiryDate;
    }
}
