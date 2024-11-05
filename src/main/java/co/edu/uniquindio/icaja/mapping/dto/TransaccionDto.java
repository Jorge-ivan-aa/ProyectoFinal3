package co.edu.uniquindio.icaja.mapping.dto;

import co.edu.uniquindio.icaja.model.Categoria;
import co.edu.uniquindio.icaja.model.CuentaBancaria;

public record TransaccionDto(double monto,
                             CuentaBancaria cuenta,
                             String motivo) {
}