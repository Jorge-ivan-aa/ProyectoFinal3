package co.edu.uniquindio.proyectofinal3.model;

public class Transaccion {
    String idTransaccion;
    String fecha;
    String tipoTransaccion;
    String monto;
    String cuentaOrigen;
    String cuentaDestino;
    String descripción;


    public Transaccion(String idTransaccion, String fecha, String tipoTransaccion, String monto,String cuentaOrigen, String cuentaDestino, String descripción) {
        this.idTransaccion = idTransaccion;
        this.fecha = fecha;
        this.tipoTransaccion = tipoTransaccion;
        this.monto= monto;
        this.cuentaOrigen = cuentaOrigen;
        this.cuentaDestino = cuentaDestino;
        this.descripción= descripción;
    }

    public String getIdTransaccion() {
        return idTransaccion;
    }

    public void setIdTransaccion(String idTransaccion) {
        this.idTransaccion = idTransaccion;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getTipoTransaccion() {
        return tipoTransaccion;
    }

    public void setTipoTransaccion(String tipoTransaccion) {
        this.tipoTransaccion = tipoTransaccion;
    }

    public String getMonto() {
        return monto;
    }

    public void setMonto(String monto) {
        this.monto = monto;
    }

    public String getCuentaOrigen() {
        return cuentaOrigen;
    }

    public void setCuentaOrigen(String cuentaOrigen) {
        this.cuentaOrigen = cuentaOrigen;
    }

    public String getCuentaDestino() {
        return cuentaDestino;
    }

    public void setCuentaDestino(String cuentaDestino) {
        this.cuentaDestino = cuentaDestino;
    }

    public String getDescripción() {
        return descripción;
    }

    public void setDescripción(String descripción) {
        this.descripción = descripción;
    }
}
