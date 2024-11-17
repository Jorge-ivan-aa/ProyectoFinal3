package co.edu.uniquindio.icaja.mapping.mappers;

import co.edu.uniquindio.icaja.mapping.dto.CuentaDto;
import co.edu.uniquindio.icaja.model.Cuenta;


public class CuentaBancariaMapper {

    public static CuentaDto toDto(Cuenta cuenta){
        return new CuentaDto (
                cuenta.getIdCuenta(),
                cuenta.getEntidad(),
                cuenta.getNumeroCuenta(),
                cuenta.getTipo(),
                cuenta.getSaldo().toString(),
                cuenta.getIdpropietario()
        );
    }

    public static Cuenta toCuentaBancaria(CuentaDto cuentaDto){
        return new Cuenta(
                cuentaDto.entidad(),
                cuentaDto.numeroCuenta(),
                cuentaDto.tipo(),
                cuentaDto.saldo(),
                cuentaDto.propietario()
        );
    }

}