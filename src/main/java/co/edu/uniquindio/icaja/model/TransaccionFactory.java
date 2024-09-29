package co.edu.uniquindio.icaja.model;

public class TransaccionFactory {

    public Transaccion crearTransaccion(String tipo) {
        if (tipo.equalsIgnoreCase("deposito")) {

        } else if (tipo.equalsIgnoreCase("retiro")) {

        } else if (tipo.equalsIgnoreCase("transferencia")) {

        }
            return null;
    }

}
