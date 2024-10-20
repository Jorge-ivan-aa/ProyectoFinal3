package co.edu.uniquindio.icaja.model;

import java.io.Serializable;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.NoArgsConstructor;

@Getter
@Setter
@ToString
@NoArgsConstructor

public abstract class Transaccion implements Serializable {
    private String id;
    private String fecha;
    private double monto;
    private Categoria[] listacategoria;
    private CuentaBancaria cuenta;
    private String motivo;
    public static final long serialVersionID = 3L;

    public Transaccion(String fecha, double monto, Categoria[] listacategoria, CuentaBancaria cuenta, String motivo) {
        this.id = generarId();
        this.fecha = fecha;
        this.monto = monto;
        this.listacategoria = listacategoria;
        this.cuenta = cuenta;
        this.motivo = motivo;
    }


    private String generarId() {
        return UUID.randomUUID().toString();
    }

    public abstract void realizarMovimiento();

    public abstract String factura();

}
