package co.edu.uniquindio.proyectofinal3.model;

public class Cuenta {
    private String nombreBanco;
    private String idCuenta;
    private String numeroCuenta;
    private String tipoCuenta;

    public Cuenta(String nombreBanco, String idCuenta, String numeroCuenta, String tipoCuenta) {
        this.nombreBanco = nombreBanco;
        this.idCuenta = idCuenta;
        this.numeroCuenta = numeroCuenta;
        this.tipoCuenta = tipoCuenta;
    }

    public String getNombreBanco() {
        return nombreBanco;
    }

    public void setNombreBanco(String nombreBanco) {
        this.nombreBanco = nombreBanco;
    }

    public String getIdCuenta() {
        return idCuenta;
    }

    public void setIdCuenta(String idCuenta) {
        this.idCuenta = idCuenta;
    }

    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    public void setNumeroCuenta(String numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

    public String getTipoCuenta() {
        return tipoCuenta;
    }

    public void setTipoCuenta(String tipoCuenta) {
        this.tipoCuenta = tipoCuenta;
    }
}
