package co.edu.uniquindio.icaja.mapping.dto;

import co.edu.uniquindio.icaja.mapping.services.ITransaccionDto;
import co.edu.uniquindio.icaja.model.Categoria;
import co.edu.uniquindio.icaja.model.CuentaBancaria;

public record DepositoDto(String id,
                          String fecha,
                          double monto,
                          Categoria[] listacategoria,
                          CuentaBancaria cuenta,
                          String motivo) implements ITransaccionDto {

    @Override
    public String tipo() {
        return "el deposito";
    }
}
