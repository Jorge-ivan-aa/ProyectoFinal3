package co.edu.uniquindio.icaja.mapping.dto;

import co.edu.uniquindio.icaja.model.Categoria;
import co.edu.uniquindio.icaja.model.CuentaBancaria;

public record DepositoDto(int id,
                          String fecha,
                          double monto,
                          Categoria[] listacategoria,
                          CuentaBancaria cuenta) {
}
