package co.edu.uniquindio.icaja.mapping.mappers;


import co.edu.uniquindio.icaja.mapping.dto.DepositoDto;
import co.edu.uniquindio.icaja.mapping.dto.RetiroDto;
import co.edu.uniquindio.icaja.mapping.dto.TransferenciaDto;
import co.edu.uniquindio.icaja.model.factories.Deposito;
import co.edu.uniquindio.icaja.model.factories.Retiro;
import co.edu.uniquindio.icaja.model.Transaccion;
import co.edu.uniquindio.icaja.model.factories.Transferencia;

public class TransaccionMapper {

    public static Transaccion toTransaccion(RetiroDto retiroDto){
        return new Retiro(
                retiroDto.monto(),
                retiroDto.listacategoria(),
                retiroDto.cuenta(),
                retiroDto.motivo()
        );

    }
    public static Transaccion toTransaccion(DepositoDto depositoDto){
        return new Deposito(
                depositoDto.monto(),
                depositoDto.listacategoria(),
                depositoDto.cuenta(),
                depositoDto.motivo()
        );
    }
    public static Transaccion toTransaccion(TransferenciaDto transferenciaDto){
      return new Transferencia(
              transferenciaDto.monto(),
              transferenciaDto.listacategoria(),
              transferenciaDto.cuenta(),
              transferenciaDto.motivo(),
              transferenciaDto.esInterna(),
              transferenciaDto.cuentaDestino()
      ) ;
    }
}