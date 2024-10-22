package co.edu.uniquindio.icaja.model.persistencia;

import co.edu.uniquindio.icaja.model.CuentaBancaria;
import co.edu.uniquindio.icaja.model.enums.TipoCuenta;
import co.edu.uniquindio.icaja.model.services.Persistible;
import co.edu.uniquindio.icaja.utils.respaldo.Persistencia;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CuentaBancariaPersistente implements Persistible<CuentaBancaria> {
    @Override
    public void guardar(List<CuentaBancaria> cuentaBancarias) throws IOException {
        StringBuilder contenido = new StringBuilder();
        for(CuentaBancaria cuentaBancaria:cuentaBancarias)
        {
            contenido.append(
                    cuentaBancaria.getNumeroCuenta()).append("@@")
                    .append(cuentaBancaria.getTipoCuenta()).append("@@")
                    .append(cuentaBancaria.getSaldo()).append("\n");
        }
        Persistencia.guardarArchivo("cuentaBancaria.txt", contenido.toString(), false);

    }

    @Override
    public List<CuentaBancaria> leer(String ruta) throws IOException {
        ArrayList<CuentaBancaria> cuentaBancarias = new ArrayList<>();
        ArrayList<String> contenido = Persistencia.leerArchivo(ruta);
        String[] linea;
        for (String texto : contenido) {
            linea = texto.split("@@");
            CuentaBancaria cuentaBancaria = new CuentaBancaria();
            cuentaBancaria.setNumeroCuenta(linea[0]);
            cuentaBancaria.setTipoCuenta(TipoCuenta.valueOf(linea[1]));
            cuentaBancaria.setSaldo(Double.parseDouble(linea[2]));
            cuentaBancarias.add(cuentaBancaria);
        }
        return cuentaBancarias;
    }
}
