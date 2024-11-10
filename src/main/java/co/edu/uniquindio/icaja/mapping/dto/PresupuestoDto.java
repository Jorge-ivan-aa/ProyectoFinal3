package co.edu.uniquindio.icaja.mapping.dto;

import java.math.BigDecimal;

public record PresupuestoDto(String idPresupuesto,
                             String nombre,
                             BigDecimal montoAsignado,
                             BigDecimal montoGastado,
                             String [] categorias) {
}
