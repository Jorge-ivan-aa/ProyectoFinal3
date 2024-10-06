package co.edu.uniquindio.icaja.mapping.dto;

public record UsuarioDto(
     String nombre,
     String cedula,
     String correo,
     String telefono,
     String clave,
     String claveTransaccional,
     double saldoTotal,
     double ingresos,
     double gastos,
     double presupuestoMensual) {
}