package co.edu.uniquindio.icaja.mapping.mappers;


import co.edu.uniquindio.icaja.mapping.dto.DepositoDto;
import co.edu.uniquindio.icaja.mapping.dto.RetiroDto;
import co.edu.uniquindio.icaja.mapping.dto.TransferenciaDto;
import co.edu.uniquindio.icaja.model.factories.Deposito;
import co.edu.uniquindio.icaja.model.factories.Retiro;
import co.edu.uniquindio.icaja.model.Transaccion;
import co.edu.uniquindio.icaja.model.factories.Transferencia;

public class TransaccionMapper {
    public static Transaccion retiroDtoToTransaccion(RetiroDto retiroDto){
        return new Retiro(
                retiroDto.fecha(),
                retiroDto.monto(),
                retiroDto.listacategoria(),
                retiroDto.cuenta(),
                retiroDto.motivo()
        );

    }
    public static Transaccion depositoDtoToTransaccion (DepositoDto depositoDto){
        return new Deposito(
                depositoDto.fecha(),
                depositoDto.monto(),
                depositoDto.listacategoria(),
                depositoDto.cuenta(),
                depositoDto.motivo()
        );
    }
    public static Transaccion transferenciaDtoToTransaccion (TransferenciaDto transferenciaDto){
      return new Transferencia(
              transferenciaDto.fecha(),
              transferenciaDto.monto(),
              transferenciaDto.listacategoria(),
              transferenciaDto.cuenta(),
              transferenciaDto.motivo(),
              transferenciaDto.esInterna(),
              transferenciaDto.cuentaDestino()
      ) ;
    }
}