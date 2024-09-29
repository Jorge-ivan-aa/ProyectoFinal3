package co.edu.uniquindio.icaja.model;

import java.util.ArrayList;

public class Usuario {
    private String nombre;
    private String cedula;
    private String correo;
    private String telefono;
    private String clave;
    private String claveTransaccional;
    private double saldoTotal;
    private double ingresos;
    private double gastos;
    private double presupuestoMensual;
    private ArrayList<CuentaBancaria> listaCuentas;

    public Usuario(String nombre, String cedula, String correo, String telefono, String clave, String claveTransaccional, double saldoTotal, double presupuestoMensual) {
        this.nombre = nombre;
        this.cedula = cedula;
        this.correo = correo;
        this.telefono = telefono;
        this.clave = clave;
        this.claveTransaccional = claveTransaccional;
        this.saldoTotal = saldoTotal;
        this.ingresos = 0;
        this.gastos = 0;
        this.presupuestoMensual = presupuestoMensual;
        this.listaCuentas = new ArrayList<>();
    }

    public ArrayList<CuentaBancaria> getListaCuentas() {
        return listaCuentas;
    }

    public void addCuenta(CuentaBancaria cuenta) {
        this.listaCuentas.add(cuenta);
    }

    public void removeCuenta(CuentaBancaria cuenta) {
        this.listaCuentas.remove(cuenta);
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getClaveTransaccional() {
        return claveTransaccional;
    }

    public void setClaveTransaccional(String claveTransaccional) {
        this.claveTransaccional = claveTransaccional;
    }

    public double getSaldoTotal() {
        return saldoTotal;
    }

    public void setSaldoTotal(double saldoTotal) {
        this.saldoTotal = saldoTotal;
    }

    public double getIngresos() {
        return ingresos;
    }

    public void setIngresos(double ingresos) {
        this.ingresos = ingresos;
    }

    public double getGastos() {
        return gastos;
    }

    public void setGastos(double gastos) {
        this.gastos = gastos;
    }

    public double getPresupuestoMensual() {
        return presupuestoMensual;
    }

    public void setPresupuestoMensual(double presupuestoMensual) {
        this.presupuestoMensual = presupuestoMensual;
    }
}
