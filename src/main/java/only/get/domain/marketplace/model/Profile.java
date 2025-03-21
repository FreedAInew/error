package only.get.domain.marketplace.model;


/**
 * Representa un Perfil dentro del sistema.
 * Puede contener datos adicionales como dirección o preferencias.
 */
public record Profile(
    String address,
    String phoneNumber
) {}
