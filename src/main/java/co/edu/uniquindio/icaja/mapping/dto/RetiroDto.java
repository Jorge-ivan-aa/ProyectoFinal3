package co.edu.uniquindio.icaja.mapping.dto;

import co.edu.uniquindio.icaja.mapping.services.ITransaccionDto;
import co.edu.uniquindio.icaja.model.Categoria;
import co.edu.uniquindio.icaja.model.CuentaBancaria;

import java.time.LocalDate;

public record RetiroDto(String id,
                        double monto,
                        Categoria[] listacategoria,
                        CuentaBancaria cuenta,
                        String motivo) implements ITransaccionDto {
    @Override
    public String tipo() {
        return "el retiro";
    }
}
