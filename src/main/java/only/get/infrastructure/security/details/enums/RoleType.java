// ✅ Se usa en `RoleEntity` para definir roles de usuario como ADMIN o USER.


package only.get.infrastructure.security.details.enums;


public enum RoleType {
    ADMIN, // 👈 Sin prefijo (se almacenará como "ADMIN" en la BD)
    USER,
    MODERATOR;
}

