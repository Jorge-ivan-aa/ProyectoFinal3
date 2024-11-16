package co.edu.uniquindio.icaja.mapping.dto;

import co.edu.uniquindio.icaja.mapping.services.ITransaccionDto;
import co.edu.uniquindio.icaja.model.Categoria;
import co.edu.uniquindio.icaja.model.Cuenta;
import co.edu.uniquindio.icaja.model.enums.TipoTransaccion;

public record TransferenciaDto(
        String id,
        TipoTransaccion tipo,
        String monto,
        String motivo,
        Cuenta cuentaOrigen,
        Cuenta cuentaDestino,
        Categoria categoria) implements ITransaccionDto {
}