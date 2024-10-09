package co.edu.uniquindio.icaja.model.factories;

import java.io.Serializable;

import co.edu.uniquindio.icaja.model.Categoria;
import co.edu.uniquindio.icaja.model.CuentaBancaria;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.NoArgsConstructor;

@Getter
@Setter
@ToString
@NoArgsConstructor

public abstract class Transaccion implements Serializable {
    private int id;
    private String fecha;
    private double monto;
    private Categoria[] listacategoria;
    private CuentaBancaria cuenta;
    public static final long serialVersionID = 3L;

    public Transaccion(int id, String fecha, double monto, Categoria[] listacategoria, CuentaBancaria cuenta) {
        this.id = id;
        this.fecha = fecha;
        this.monto = monto;
        this.listacategoria = listacategoria;
        this.cuenta = cuenta;
    }

    public abstract void realizarMovimiento();

    public abstract String factura();

}
