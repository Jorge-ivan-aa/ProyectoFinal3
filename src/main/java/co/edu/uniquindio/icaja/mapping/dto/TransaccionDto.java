package co.edu.uniquindio.icaja.mapping.dto;

import co.edu.uniquindio.icaja.model.Categoria;
import co.edu.uniquindio.icaja.model.Cuenta;
import co.edu.uniquindio.icaja.model.enums.TipoTransaccion;

public record TransaccionDto(TipoTransaccion tipo, String monto, String motivo, Cuenta cuenta, Categoria... listaCategoria) {
}