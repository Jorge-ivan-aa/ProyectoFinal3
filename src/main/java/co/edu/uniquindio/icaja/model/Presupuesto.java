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
    private BigDecimal montoAsignado = new BigDecimal(BigInteger.ZERO);
    private BigDecimal montoGastado = new BigDecimal(BigInteger.ZERO);
    private String[] idCategorias;
    public static final long serialVersionID = 9L;

    public Presupuesto(String nombre, BigDecimal montoAsignado, String... categoria) {
        this.idPresupuesto = generarId();
        this.nombre = nombre;
        this.montoAsignado = NumTool.parseToDinero(String.valueOf(montoAsignado), "No se pudo asignar el presupuesto, monto ingresado no valido ");
        this.idCategorias = categoria;
    }

    private String generarId() {
        return UUID.randomUUID().toString();
    }

}
