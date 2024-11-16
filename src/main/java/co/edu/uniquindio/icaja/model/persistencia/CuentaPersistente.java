package co.edu.uniquindio.icaja.model.persistencia;

import co.edu.uniquindio.icaja.exception.almacenamiento.ElementoNoEncontrado;
import co.edu.uniquindio.icaja.exception.almacenamiento.TipoNoMapeado;
import co.edu.uniquindio.icaja.factory.ModelFactory;
import co.edu.uniquindio.icaja.model.Cuenta;
import co.edu.uniquindio.icaja.model.Usuario;
import co.edu.uniquindio.icaja.model.enums.EntidadBancaria;
import co.edu.uniquindio.icaja.model.enums.TipoCuenta;
import co.edu.uniquindio.icaja.model.services.Persistible;
import co.edu.uniquindio.icaja.utils.loggin.Seguimiento;
import co.edu.uniquindio.icaja.utils.respaldo.Persistencia;
import co.edu.uniquindio.icaja.utils.tools.NumTool;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CuentaPersistente implements Persistible<Cuenta> {

    private final ModelFactory singleton;

    public CuentaPersistente(ModelFactory singleton) {
        this.singleton = singleton;
    }

    @Override
    public void guardar(List<Cuenta> cuentas) throws IOException {
        StringBuilder contenido = new StringBuilder();
        for (Cuenta cuenta : cuentas) {
            contenido.append(cuenta.getIdCuenta()).append("@@")
                    .append(cuenta.getEntidad().toString()).append("@@")
                    .append(cuenta.getNumeroCuenta()).append("@@")
                    .append(cuenta.getTipo().toString()).append("@@")
                    .append(cuenta.getSaldo().toString()).append("@@")
                    .append(cuenta.getPropietario().getIdUsuario()).append("\n");

        }

        Persistencia.guardarArchivo("cuenta.txt", contenido.toString(), false);

    }

    @Override
    public List<Cuenta> leer(String ruta) throws IOException {
        ArrayList<Cuenta> cuentas = new ArrayList<>();

        ArrayList<String> contenido = Persistencia.leerArchivo(ruta);
        String[] linea;

        try {
            for (String texto : contenido) {
                linea = texto.split("@@");
                Cuenta cuenta = new Cuenta(linea[4]);

                cuenta.setIdCuenta(linea[0]);
                cuenta.setEntidad(EntidadBancaria.valueOf(linea[1]));
                cuenta.setNumeroCuenta(linea[2]);
                cuenta.setTipo(TipoCuenta.valueOf(linea[3]));
                cuenta.setIdpropietario(linea[5]);

                cuentas.add(cuenta);
            }

        } catch (Exception e) {
            Seguimiento.registrarLog(3, "Se generó un error inesperado al intentar cargar las cuentas:" +
                    e.getMessage() +
                    "\n Clase: " + e.getStackTrace()[0].getClassName() +
                    " | Método: " + e.getStackTrace()[0].getMethodName() +
                    " | Archivo: " + e.getStackTrace()[0].getFileName() +
                    " | Línea: " + e.getStackTrace()[0].getLineNumber());
        }

        return cuentas;
    }
}
