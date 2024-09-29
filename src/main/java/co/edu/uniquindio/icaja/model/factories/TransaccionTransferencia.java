package co.edu.uniquindio.icaja.model.factories;

import co.edu.uniquindio.icaja.model.Categoria;
import co.edu.uniquindio.icaja.model.CuentaBancaria;
import co.edu.uniquindio.icaja.model.Transaccion;

public class TransaccionTransferencia extends Transaccion {
    public TransaccionTransferencia(int id, String fecha, double monto, Categoria[] listacategoria, CuentaBancaria cuenta) {
        super(id, fecha, monto, listacategoria, cuenta);
    }

    @Override
    public String factura() {
        return "";
    }

    @Override
    public void realizarMovimiento() {

    }
}
