package co.edu.uniquindio.icaja.mapping.mappers;

import co.edu.uniquindio.icaja.mapping.dto.CuentaBancariaDto;
import co.edu.uniquindio.icaja.model.Cuenta;


public class CuentaBancariaMapper {

    public static CuentaBancariaDto toDto(Cuenta cuenta){
        return new CuentaBancariaDto(
                cuenta.getEntidad(),
                cuenta.getNumeroCuenta(),
                cuenta.getTipo(),
                cuenta.getSaldo().toString(),
                cuenta.getPropietario()
        );
    }

    public static Cuenta toCuentaBancaria(CuentaBancariaDto cuentaBancariaDto){
        return new Cuenta(
                cuentaBancariaDto.entidad(),
                cuentaBancariaDto.numeroCuenta(),
                cuentaBancariaDto.tipo(),
                cuentaBancariaDto.saldo(),
                cuentaBancariaDto.propietario()
        );
    }

}