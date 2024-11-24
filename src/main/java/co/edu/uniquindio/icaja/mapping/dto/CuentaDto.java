package co.edu.uniquindio.icaja.mapping.dto;

import co.edu.uniquindio.icaja.model.Usuario;
import co.edu.uniquindio.icaja.model.enums.EntidadBancaria;
import co.edu.uniquindio.icaja.model.enums.TipoCuenta;

public record CuentaDto(
        String id,
        EntidadBancaria entidad,
        String numeroCuenta,
        TipoCuenta tipo,
        String saldo,
        String propietario) {
}
