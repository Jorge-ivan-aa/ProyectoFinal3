package co.edu.uniquindio.icaja.mapping.mappers;


import co.edu.uniquindio.icaja.mapping.dto.DepositoDto;
import co.edu.uniquindio.icaja.mapping.dto.RetiroDto;
import co.edu.uniquindio.icaja.mapping.dto.TransaccionDto;
import co.edu.uniquindio.icaja.mapping.dto.TransferenciaDto;
import co.edu.uniquindio.icaja.model.factories.Deposito;
import co.edu.uniquindio.icaja.model.factories.Retiro;
import co.edu.uniquindio.icaja.model.factories.Transaccion;
import co.edu.uniquindio.icaja.model.factories.Transferencia;

public class TransaccionMapper {
//    public static TransaccionDto transaccionToTransaccionDto(Transaccion TransaccionDto) {
//        return new TransaccionDto(
//                TransaccionDto.getId(),
//                TransaccionDto.getFecha(),
//                TransaccionDto.getMonto(),
//                TransaccionDto.getListacategoria(),
//                TransaccionDto.getCuenta()
//        );
//    }

//    public static Transaccion transaccionDtoToTransaccion(TransaccionDto transaccionDto) {
//        return new Transaccion(
//                transaccionDto.id(),
//                transaccionDto.fecha(),
//                transaccionDto.monto(),
//                transaccionDto.listacategoria(),
//                transaccionDto.cuenta()
//        );
//    }

    public static Transaccion retiroDtoToTransaccion(RetiroDto retiroDto){
        return new Retiro(
                retiroDto.id(),
                retiroDto.fecha(),
                retiroDto.monto(),
                retiroDto.listacategoria(),
                retiroDto.cuenta());

    }
    public static Transaccion depositoDtoToTransaccion (DepositoDto depositoDto){
        return new Deposito(
                depositoDto.id(),
                depositoDto.fecha(),
                depositoDto.monto(),
                depositoDto.listacategoria(),
                depositoDto.cuenta()
        ) ;
    }
    public static Transaccion transferenciaDtoToTransaccion (TransferenciaDto transferenciaDto){
      return new Transferencia(
              transferenciaDto.id(),
              transferenciaDto.fecha(),
              transferenciaDto.monto(),
              transferenciaDto.listacategoria(),
              transferenciaDto.cuenta(),
              transferenciaDto.esInterna(),
              transferenciaDto.cuentaDestino()
      ) ;
    }
}
