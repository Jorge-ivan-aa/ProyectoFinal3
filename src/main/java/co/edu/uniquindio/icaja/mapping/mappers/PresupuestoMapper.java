package co.edu.uniquindio.icaja.mapping.mappers;

import co.edu.uniquindio.icaja.mapping.dto.PresupuestoDto;
import co.edu.uniquindio.icaja.model.Presupuesto;
import co.edu.uniquindio.icaja.utils.tools.NumTool;

public class PresupuestoMapper {

    public static PresupuestoDto toDto(Presupuesto presupuesto) {
        return new PresupuestoDto(
                presupuesto.getIdPresupuesto(),
                presupuesto.getNombre(),
                presupuesto.getMontoAsignado().toString(),
                presupuesto.getMontoGastado().toString(),
                presupuesto.getIdCategorias()
        );
    }

    public static Presupuesto toPresupuesto(PresupuestoDto presupuestoDto) {
        return new Presupuesto(
                presupuestoDto.nombre(),
                NumTool.parseToDinero(presupuestoDto.montoAsignado()),
                presupuestoDto.categorias()
        );
    }
}
