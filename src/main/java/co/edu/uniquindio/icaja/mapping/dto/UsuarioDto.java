package co.edu.uniquindio.icaja.mapping.dto;

public record UsuarioDto(
        String id,
        String nombre,
        String cedula,
        String correo,
        String telefono,
        String clave,
        String claveTransaccional) {
}