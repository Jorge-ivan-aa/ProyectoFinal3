package co.edu.uniquindio.icaja.model;

import java.io.Serializable;

import co.edu.uniquindio.icaja.mapping.dto.DepositoDto;
import co.edu.uniquindio.icaja.mapping.dto.RetiroDto;
import co.edu.uniquindio.icaja.mapping.dto.TransferenciaDto;
import co.edu.uniquindio.icaja.mapping.mappers.TransaccionMapper;
import co.edu.uniquindio.icaja.model.factories.Transaccion;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.NoArgsConstructor;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class TransaccionFactory implements Serializable {

    public static final long serialVersionID = 4L;

    public <T> Transaccion crearTransaccion(T transaccionDto) {
        if (transaccionDto instanceof DepositoDto) {
            return TransaccionMapper.depositoDtoToTransaccion(transaccionDto);
        } else if (transaccionDto instanceof RetiroDto) {
            return TransaccionMapper.retiroDtoToTransaccion(transaccionDto);
        } else if (transaccionDto instanceof TransferenciaDto) {
            return TransaccionMapper.transferenciaDtoToTransaccion(transaccionDto);
        }
            return null;
    }

}
