package co.edu.uniquindio.icaja.mapping.mappers;

import co.edu.uniquindio.icaja.mapping.dto.CuentaBancariaDto;
import co.edu.uniquindio.icaja.mapping.dto.TransaccionDto;
import co.edu.uniquindio.icaja.model.CuentaBancaria;
import co.edu.uniquindio.icaja.model.Transaccion;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

public interface TransaccionMapper {
    TransaccionMapper INSTANCE = Mappers.getMapper(TransaccionMapper.class);
    // Mapeo de Transaccion a TransaccionDTO
    @Named("TransaccionToTransaccionDto")
    TransaccionDto TransaccionToTransaccionDto(Transaccion transaccion);

    // Mapeo de TransaccionDto a Transaccion
    @Named("TransaccionDtoToTransaccion")
    Transaccion TransaccionDtoToTransaccion(TransaccionDto transaccionDto);
}
