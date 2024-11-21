package co.edu.uniquindio.icaja.model;


import co.edu.uniquindio.icaja.utils.tools.NumTool;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class Presupuesto implements Serializable {
    private String idPresupuesto;
    private String nombre;
    private String montoAsignado;
    private String montoGastado="0";
    private String[] idCategorias;
    public static final long serialVersionID = 9L;

    public Presupuesto(String nombre, BigDecimal montoAsignado, String... categoria) {
        this.idPresupuesto = generarId();
        this.nombre = nombre;
        this.montoAsignado = NumTool.parseToDinero(String.valueOf(montoAsignado), "No se pudo asignar el presupuesto, monto ingresado no valido ").toString();
        this.idCategorias = categoria;
    }

    private String generarId() {
        return UUID.randomUUID().toString();
    }

    public void sumarGastos(BigDecimal monto, String idCategoria) {
        for (String id: idCategorias) {
            if (idCategoria.equals(id)) {
                montoGastado = NumTool.parseToDinero(montoGastado).add(monto).toString();
            }
        }
    }

}
