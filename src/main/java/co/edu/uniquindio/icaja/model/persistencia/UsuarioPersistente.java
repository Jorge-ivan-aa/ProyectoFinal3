package co.edu.uniquindio.icaja.model.persistencia;

import co.edu.uniquindio.icaja.model.*;
import co.edu.uniquindio.icaja.model.services.Persistible;
import co.edu.uniquindio.icaja.utils.loggin.Seguimiento;
import co.edu.uniquindio.icaja.utils.respaldo.Persistencia;
import co.edu.uniquindio.icaja.utils.tools.NumTool;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UsuarioPersistente implements Persistible<Usuario> {

    public UsuarioPersistente() {
    }

    @Override
    public void guardar(List<Usuario> usuarios) throws IOException {
        StringBuilder contenido = new StringBuilder();
        for (Usuario usuario : usuarios) {
            contenido.append(usuario.getIdUsuario()).append("@@")
                    .append(usuario.getNombre()).append("@@")
                    .append(usuario.getCedula()).append("@@")
                    .append(usuario.getCorreo()).append("@@")
                    .append(usuario.getTelefono()).append("@@")
                    .append(usuario.getClave()).append("@@")
                    .append(usuario.getClaveTransaccional()).append("@@")
                    .append(usuario.getSaldoTotal().toString()).append("@@")
                    .append(usuario.getIngresos().toString()).append("@@")
                    .append(usuario.getGastos().toString()).append("@@");

            contenido.append("<<<@@");
            System.out.println(usuario.getIdCuentas());
            usuario.getIdCuentas().forEach(idCuenta -> contenido.append(idCuenta).append("@@"));
            contenido.append("<<<@@");
            usuario.getIdPresupuestos().forEach(idPresupuesto -> contenido.append(idPresupuesto).append("@@"));
            contenido.append("<<<@@");
            usuario.getIdCategorias().forEach(idCategoria -> contenido.append(idCategoria).append("@@"));
            contenido.append("<<<@@");
            usuario.getIdTransacciones().forEach(idTransaccion -> contenido.append(idTransaccion).append("@@"));
            contenido.append("\n");
        }


        Persistencia.guardarArchivo("usuario.txt", contenido.toString(), false);
    }

    @Override
    public List<Usuario> leer(String ruta) throws IOException {
        ArrayList<Usuario> usuarios = new ArrayList<>();
        ArrayList<String> contenido = Persistencia.leerArchivo(ruta);
        String[] linea;

        try {
            for (String texto : contenido) {
                linea = texto.split("@@");
                Usuario usuario = getUsuario(linea);
                usuarios.add(usuario);
            }
        } catch (Exception e) {
            Seguimiento.registrarLog(3, "Se generó un error inesperado al intentar cargar los usuarios:" +
                    e.getMessage() +
                    "\n Clase: " + e.getStackTrace()[0].getClassName() +
                    " | Método: " + e.getStackTrace()[0].getMethodName() +
                    " | Archivo: " + e.getStackTrace()[0].getFileName() +
                    " | Línea: " + e.getStackTrace()[0].getLineNumber());
        }

        return usuarios;
    }

    private Usuario getUsuario(String[] linea) {
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(linea[0]);
        usuario.setNombre(linea[1]);
        usuario.setCedula(linea[2]);
        usuario.setCorreo(linea[3]);
        usuario.setTelefono(linea[4]);
        usuario.setClave(linea[5]);
        usuario.setClaveTransaccional(linea[6]);
        usuario.setSaldoTotal(NumTool.parseToDinero(linea[7]));
        usuario.setIngresos(NumTool.parseToDinero(linea[8]));
        usuario.setGastos(NumTool.parseToDinero(linea[9]));

        int idx = 10+1;
//         Restauramos las listas de Cuentas, Presupuestos, Categorias y Transacciones usando el método genérico
        usuario.setIdCuentas(restaurarLista(linea, idx));
        idx += usuario.getIdCuentas().size() + 1;

        System.out.println("aqui estamos leyendo: " + usuario.getIdCuentas());

        usuario.setIdPresupuestos(restaurarLista(linea, idx));
        idx += usuario.getIdPresupuestos().size() + 1;

        usuario.setIdCategorias((restaurarLista(linea, idx)));
        idx += usuario.getIdCategorias().size() + 1;

        usuario.setTransacciones(restaurarLista(linea, idx));

        return usuario;
    }


    private ArrayList<String> restaurarLista(String[] datosUsuario, int indice) {
        ArrayList<String> lista = new ArrayList<>();
        if (indice < datosUsuario.length) {
            while (!datosUsuario[indice].equals("<<<")) {
                lista.add(datosUsuario[indice]);
                indice++;
            }
        }

        return lista;
    }
}


