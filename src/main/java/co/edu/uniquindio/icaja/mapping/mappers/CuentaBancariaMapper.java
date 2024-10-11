package co.edu.uniquindio.icaja.mapping.mappers;

import co.edu.uniquindio.icaja.mapping.dto.CategoriaDto;
import co.edu.uniquindio.icaja.mapping.dto.CuentaBancariaDto;
import co.edu.uniquindio.icaja.model.Categoria;
import co.edu.uniquindio.icaja.model.CuentaBancaria;


public class CuentaBancariaMapper {
    public static CuentaBancariaDto cuentaBancariaToCuentaBancariaDto(CuentaBancaria cuentaBancaria){
        return new CuentaBancariaDto(
                cuentaBancaria.getEntidad(),
                cuentaBancaria.getNumeroCuenta(),
                cuentaBancaria.getTipoCuenta(),
                cuentaBancaria.getSaldo(),
                cuentaBancaria.getLimite(),
                cuentaBancaria.getPropietario()
        );
    }

    public static CuentaBancaria cuentaBancariaDtoToCuentaBancaria(CuentaBancariaDto cuentaBancariaDto){
        return new CuentaBancaria(
                cuentaBancariaDto.entidad(),
                cuentaBancariaDto.numeroCuenta(),
                cuentaBancariaDto.tipoCuenta(),
                cuentaBancariaDto.saldo(),
                cuentaBancariaDto.limite(),
                cuentaBancariaDto.propietario()
        );
    }

}
