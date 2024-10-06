package co.edu.uniquindio.icaja.model;

import java.io.Serializable;

public class TransaccionFactory implements Serializable {
    public static final long serialVersionID = 4L;

    public Transaccion crearTransaccion(String tipo) {
        if (tipo.equalsIgnoreCase("deposito")) {

        } else if (tipo.equalsIgnoreCase("retiro")) {

        } else if (tipo.equalsIgnoreCase("transferencia")) {

        }
            return null;
    }

    public TransaccionFactory() {
    }
}
