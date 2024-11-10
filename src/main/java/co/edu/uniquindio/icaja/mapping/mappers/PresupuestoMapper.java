package co.edu.uniquindio.icaja.mapping.mappers;

import co.edu.uniquindio.icaja.mapping.dto.PresupuestoDto;
import co.edu.uniquindio.icaja.model.Presupuesto;

public class PresupuestoMapper {

    public static PresupuestoDto toDto(Presupuesto presupuesto) {
        return new PresupuestoDto(
                presupuesto.getIdPresupuesto(),
                presupuesto.getNombre(),
                presupuesto.getMontoAsignado(),
                presupuesto.getMontoGastado(),
                presupuesto.getCategorias()
        );
    }

    public static Presupuesto toPresupuesto(PresupuestoDto presupuestoDto) {
        return new Presupuesto(
                presupuestoDto.idPresupuesto(),
                presupuestoDto.nombre(),
                presupuestoDto.categorias(),
                presupuestoDto.montoAsignado()
        );
    }
}
