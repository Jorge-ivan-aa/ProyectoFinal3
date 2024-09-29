package co.edu.uniquindio.icaja.model;

import co.edu.uniquindio.icaja.model.enums.TipoCuenta;

public class CuentaBancaria {
    private String entidad;
    private String numeroCuenta;
    private TipoCuenta tipoCuenta;
    private double saldo;
    private double limite;

    public CuentaBancaria(String entidad, String numeroCuenta, TipoCuenta tipoCuenta, double saldo, double limite) {
        this.entidad = entidad;
        this.numeroCuenta = numeroCuenta;
        this.tipoCuenta = tipoCuenta;
        this.saldo = saldo;
        this.limite = this.definirLimite(tipoCuenta, limite);
    }

    public double definirLimite(TipoCuenta tipoCuenta, double limite) {
        if (tipoCuenta == TipoCuenta.CREDITO) {
            return limite;
        }
        return this.saldo;
    }

    public String getEntidad() {
        return entidad;
    }

    public void setEntidad(String entidad) {
        this.entidad = entidad;
    }

    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    public void setNumeroCuenta(String numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

    public TipoCuenta getTipoCuenta() {
        return tipoCuenta;
    }

    public void setTipoCuenta(TipoCuenta tipoCuenta) {
        this.tipoCuenta = tipoCuenta;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public double getLimite() {
        return limite;
    }

    public void setLimite(double limite) {
        this.limite = limite;
    }
}
