package co.edu.uniquindio.icaja.model;


import co.edu.uniquindio.icaja.utils.tools.NumTool;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class Presupuesto implements Serializable {
    private String idPresupuesto;
    private String nombre;
    private BigDecimal montoAsignado;
    private BigDecimal montoGastado;
    private String[] categorias;
    //private Categoria categoriaPresupuesto;
    public static final long serialVersionID = 9L;

    public Presupuesto(String idPresupuesto, String nombre, String [] categorias, BigDecimal montoAsignado) {
        this.idPresupuesto = idPresupuesto;
        this.nombre = nombre;
        this.montoAsignado = NumTool.parseToDinero(String.valueOf(montoAsignado), "No se pudo asignar el presupuesto, monto ingresado no valido ");
        this.categorias = categorias;
    }
}
