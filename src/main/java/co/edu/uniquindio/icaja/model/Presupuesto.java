package co.edu.uniquindio.icaja.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class Presupuesto implements Serializable {
    private String idPresupuesto;
    private String nombrePresupuesto;
    private String montoAsignado;
    private String montoGastado;
    private String categoriaPresupuesto;
    //private Categoria categoriaPresupuesto;
    public static final long serialVersionID = 9L;

    public Presupuesto(String idPresupuesto, String nombrePresupuesto, String montoAsignado, String montoGastado, String categoriaPresupuesto) {
        this.idPresupuesto = idPresupuesto;
        this.nombrePresupuesto = nombrePresupuesto;
        this.montoAsignado = montoAsignado;
        this.montoGastado = montoGastado;
        this.categoriaPresupuesto = categoriaPresupuesto;
    }
}
