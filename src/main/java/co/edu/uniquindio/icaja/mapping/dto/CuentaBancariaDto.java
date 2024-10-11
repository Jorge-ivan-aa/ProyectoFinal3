package co.edu.uniquindio.icaja.mapping.dto;

import co.edu.uniquindio.icaja.model.Usuario;
import co.edu.uniquindio.icaja.model.enums.TipoCuenta;

public record CuentaBancariaDto(
        String entidad,
        String numeroCuenta,
        TipoCuenta tipoCuenta,
        double saldo,
        double limite,
        Usuario propietario) {
}
