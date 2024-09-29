package co.edu.uniquindio.icaja.model;

import java.util.ArrayList;

public abstract class Transaccion {
    private int id;
    private String fecha;
    private double monto;
    private Categoria[] listacategoria;
    private CuentaBancaria cuenta;

    public Transaccion(int id, String fecha, double monto, Categoria[] listacategoria, CuentaBancaria cuenta) {
        this.id = id;
        this.fecha = fecha;
        this.monto = monto;
        this.listacategoria = listacategoria;
        this.cuenta = cuenta;
    }

    public abstract void realizarMovimiento();

    public abstract String factura();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }
}
