package co.edu.uniquindio.icaja.model.persistencia;

import co.edu.uniquindio.icaja.model.Cuenta;
import co.edu.uniquindio.icaja.model.enums.TipoCuenta;
import co.edu.uniquindio.icaja.model.services.Persistible;
import co.edu.uniquindio.icaja.utils.respaldo.Persistencia;
import co.edu.uniquindio.icaja.utils.tools.NumTool;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CuentaBancariaPersistente implements Persistible<Cuenta> {
    @Override
    public void guardar(List<Cuenta> cuentas) throws IOException {
        StringBuilder contenido = new StringBuilder();
        for(Cuenta cuenta : cuentas)
        {
            contenido.append(
                    cuenta.getNumeroCuenta()).append("@@")
                    .append(cuenta.getTipo()).append("@@")
                    .append(cuenta.getSaldo()).append("\n");
        }
        Persistencia.guardarArchivo("cuentaBancaria.txt", contenido.toString(), false);

    }

    @Override
    public List<Cuenta> leer(String ruta) throws IOException {
        ArrayList<Cuenta> cuentas = new ArrayList<>();
        ArrayList<String> contenido = Persistencia.leerArchivo(ruta);
        String[] linea;
        for (String texto : contenido) {
            linea = texto.split("@@");
            Cuenta cuenta = new Cuenta();
            cuenta.setNumeroCuenta(linea[0]);
            cuenta.setTipo(TipoCuenta.valueOf(linea[1]));
            cuenta.setSaldo(NumTool.parseToDinero(linea[2]));
            cuentas.add(cuenta);
        }
        return cuentas;
    }
}
