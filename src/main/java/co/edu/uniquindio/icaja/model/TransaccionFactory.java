package co.edu.uniquindio.icaja.model;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.NoArgsConstructor;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class TransaccionFactory implements Serializable {
    public static final long serialVersionID = 4L;

    public Transaccion crearTransaccion(String tipo) {
        if (tipo.equalsIgnoreCase("deposito")) {

        } else if (tipo.equalsIgnoreCase("retiro")) {

        } else if (tipo.equalsIgnoreCase("transferencia")) {

        }
            return null;
    }

}
