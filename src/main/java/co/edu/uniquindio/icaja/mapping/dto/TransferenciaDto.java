package co.edu.uniquindio.icaja.mapping.dto;

import co.edu.uniquindio.icaja.mapping.services.ITransaccionDto;
import co.edu.uniquindio.icaja.model.Categoria;
import co.edu.uniquindio.icaja.model.CuentaBancaria;

public record TransferenciaDto(String id,
                               String fecha,
                               double monto,
                               Categoria[] listacategoria,
                               CuentaBancaria cuenta,
                               String motivo,
                               boolean esInterna,
                               CuentaBancaria cuentaDestino) implements ITransaccionDto {
    @Override
    public String tipo() {
        return "la transferencia";
    }
}
