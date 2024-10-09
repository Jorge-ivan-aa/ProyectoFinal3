package co.edu.uniquindio.icaja.model.factories;

import co.edu.uniquindio.icaja.model.Categoria;
import co.edu.uniquindio.icaja.model.CuentaBancaria;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Transferencia extends Transaccion {
    private boolean esInterna;
    private CuentaBancaria cuentaDestino;

    public Transferencia(int id, String fecha, double monto, Categoria[] listacategoria, CuentaBancaria cuenta, boolean esInterna, CuentaBancaria cuentaDestino) {
        super(id, fecha, monto, listacategoria, cuenta);
        this.esInterna = esInterna;
        this.cuentaDestino = cuentaDestino;
    }

    @Override
    public String factura() {
        return "";
    }

    @Override
    public void realizarMovimiento() {

    }
}