package co.edu.uniquindio.proyectofinal3.model;

public class Presupuesto {
    private String nombre;
    private String idPresupuesto;
    private double montoAsignado;
    private double montoGastado;
    private String categoriaAsociada;

    public Presupuesto(String nombre, String idPresupuesto, double montoAsignado, double montoGastado, String categoriaAsociada) {
        this.nombre = nombre;
        this.idPresupuesto = idPresupuesto;
        this.montoAsignado = montoAsignado;
        this.montoGastado = montoGastado;
        this.categoriaAsociada= categoriaAsociada;
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

    public String getCategoriaAsociada() {
        return categoriaAsociada;
    }

    public void setCategoriaAsociada(String categoriaAsociada) {
        this.categoriaAsociada = categoriaAsociada;
    }
}
