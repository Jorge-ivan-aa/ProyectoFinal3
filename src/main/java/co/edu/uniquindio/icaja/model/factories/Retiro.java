package co.edu.uniquindio.icaja.model.factories;

import co.edu.uniquindio.icaja.model.Categoria;
import co.edu.uniquindio.icaja.model.CuentaBancaria;
import lombok.Getter;

import java.util.Random;

@Getter
public class Retiro extends Transaccion {
    private final String claveRetiro;

    public Retiro(int id, String fecha, double monto, Categoria[] listacategoria, CuentaBancaria cuenta) {
        super(id, fecha, monto, listacategoria, cuenta);
        claveRetiro = generarClaveRetiro();
    }

    public String generarClaveRetiro() {
        Random random = new Random();
        StringBuilder clave = new StringBuilder();

        for (int i = 0; i < 5; i++) {
            int numero = random.nextInt(10); // Genera un número entre 0 y 9
            clave.append(numero);
        }

        return clave.toString();
    }

    @Override
    public String factura() {
        return "";
    }

    @Override
    public void realizarMovimiento() {

    }
}
