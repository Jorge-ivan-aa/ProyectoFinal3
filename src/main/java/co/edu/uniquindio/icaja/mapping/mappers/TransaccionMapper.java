package co.edu.uniquindio.icaja.mapping.mappers;

import co.edu.uniquindio.icaja.mapping.dto.RetiroODepostoDto;
import co.edu.uniquindio.icaja.mapping.dto.TransferenciaDto;
import co.edu.uniquindio.icaja.model.Transaccion;

public class TransaccionMapper {

    public static Transaccion toTransaccion(TransferenciaDto transaccionDto){
        return new Transaccion(
                transaccionDto.tipo(),
                transaccionDto.monto(),
                transaccionDto.motivo(),
                transaccionDto.cuentaOrigen(),
                transaccionDto.cuentaDestino(),
                transaccionDto.categoria()
        );
    }

    public static Transaccion toTransaccion(RetiroODepostoDto transaccionDto) {
        return new Transaccion(
                transaccionDto.tipo(),
                transaccionDto.monto(),
                transaccionDto.motivo(),
                transaccionDto.cuenta(),
                transaccionDto.categoria()
        );
    }

}