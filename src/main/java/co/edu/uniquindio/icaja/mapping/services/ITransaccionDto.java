package co.edu.uniquindio.icaja.mapping.services;

import co.edu.uniquindio.icaja.model.enums.TipoTransaccion;

public interface ITransaccionDto {
    String id();
    TipoTransaccion tipo();
}
