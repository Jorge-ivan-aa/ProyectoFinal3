package co.edu.uniquindio.icaja.mapping.mappers;

import co.edu.uniquindio.icaja.mapping.dto.CategoriaDto;
import co.edu.uniquindio.icaja.mapping.dto.CuentaBancariaDto;
import co.edu.uniquindio.icaja.model.Categoria;
import co.edu.uniquindio.icaja.model.CuentaBancaria;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

public interface CuentaBancariaMapper {
    CuentaBancariaMapper INSTANCE = Mappers.getMapper(CuentaBancariaMapper.class);
    // Mapeo de cuentaBancaria a cuentaBancariaDTO
    @Named("cuentaBancariaTocuentaBancariaDto")
    CuentaBancariaDto CuentaBancariaToCuentaBancariaDto(CuentaBancaria cuentaBancaria);

    // Mapeo de CuentaBancariaDto a CuentaBancaria
    @Named("CuentaBancariaDtoToCuentaBancaria")
    CuentaBancaria CuentaBancariaDtoToCuentaBancaria(CuentaBancariaDto cuentaBancariaDto);
}
