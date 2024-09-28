package co.edu.uniquindio.proyectofinal3.model;

public class Presupuesto {
    private String nombre;
    private String idPresupuesto;
    private double montoAsignado;
    private double montoGastado;

    public Presupuesto(String nombre, String idPresupuesto, double montoAsignado, double montoGastado) {
        this.nombre = nombre;
        this.idPresupuesto = idPresupuesto;
        this.montoAsignado = montoAsignado;
        this.montoGastado = montoGastado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getIdPresupuesto() {
        return idPresupuesto;
    }

    public void setIdPresupuesto(String idPresupuesto) {
        this.idPresupuesto = idPresupuesto;
    }

    public double getMontoAsignado() {
        return montoAsignado;
    }

    public void setMontoAsignado(double montoAsignado) {
        this.montoAsignado = montoAsignado;
    }

    public double getMontoGastado() {
        return montoGastado;
    }

    public void setMontoGastado(double montoGastado) {
        this.montoGastado = montoGastado;
    }
}
