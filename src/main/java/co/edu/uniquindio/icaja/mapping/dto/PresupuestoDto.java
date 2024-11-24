package co.edu.uniquindio.icaja.mapping.dto;

import co.edu.uniquindio.icaja.model.Categoria;


public record PresupuestoDto(
        String id,
        String nombre,
        String montoAsignado,
        String montoGastado,
        String[] categorias) {
}
