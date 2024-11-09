package co.edu.uniquindio.icaja.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;
import java.util.Arrays;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.NoArgsConstructor;

@Getter
@Setter
@NoArgsConstructor


public abstract class Transaccion implements Serializable {
    private String id;
    private LocalDate fecha;
    private double monto;
    private Categoria[] listacategoria;
    private CuentaBancaria cuenta;
    private String motivo;
    public static final long serialVersionID = 9L;

    public Transaccion(double monto, Categoria[] listacategoria, CuentaBancaria cuenta, String motivo) {
        this.id = generarId();
        this.fecha = LocalDate.now();
        this.monto = monto;
        this.listacategoria = listacategoria;
        this.cuenta = cuenta;
        this.motivo = motivo;

        setvinculo(listacategoria);
    }

    private void setvinculo(Categoria[] lista) {
        for (Categoria categoria : listacategoria) {
            categoria.addTransaccion(this);
        }
    }

    private String generarId() {
        return UUID.randomUUID().toString();
    }

    public abstract void realizarMovimiento();

    public abstract String factura();

    public String getListacategoriatoString() {
        StringBuilder categorias = new StringBuilder();
        for (Categoria categoria : listacategoria) {
            categorias.append(categoria.getNombre()).append(", ");
        }

        return categorias.toString();
    }
}