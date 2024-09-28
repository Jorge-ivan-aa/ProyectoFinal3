package co.edu.uniquindio.proyectofinal3.model;

public class Usuario {
    private String nombre;
    private String Id;
    private String correo;
    private String telefono;
    private String direccion;
    private double saldoDisponible;

    public Usuario(String nombre, String id, String correo, String telefono, String direccion, double saldoDisponible) {
        this.nombre = nombre;
        Id = id;
        this.correo = correo;
        this.telefono = telefono;
        this.direccion = direccion;
        this.saldoDisponible = saldoDisponible;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
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

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public double getSaldoDisponible() {
        return saldoDisponible;
    }

    public void setSaldoDisponible(double saldoDisponible) {
        this.saldoDisponible = saldoDisponible;
    }
}
