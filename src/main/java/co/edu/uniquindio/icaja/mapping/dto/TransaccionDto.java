package co.edu.uniquindio.icaja.mapping.dto;

import co.edu.uniquindio.icaja.model.Categoria;
import co.edu.uniquindio.icaja.model.CuentaBancaria;
import co.edu.uniquindio.icaja.model.factories.Transaccion;

public record TransaccionDto(
         int id,
         String fecha,
         double monto,
         Categoria[] listacategoria,
         CuentaBancaria cuenta,
         boolean esInterna,
         CuentaBancaria cuentaDestino) {
}
