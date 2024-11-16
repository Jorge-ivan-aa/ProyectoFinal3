package co.edu.uniquindio.icaja.model.persistencia;

import co.edu.uniquindio.icaja.exception.almacenamiento.SinPersistencia;
import co.edu.uniquindio.icaja.factory.ModelFactory;
import co.edu.uniquindio.icaja.model.*;
import co.edu.uniquindio.icaja.model.services.Persistible;
import co.edu.uniquindio.icaja.utils.loggin.Seguimiento;
import co.edu.uniquindio.icaja.utils.respaldo.Persistencia;
import co.edu.uniquindio.icaja.utils.tools.NumTool;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UsuarioPersistente implements Persistible<Usuario> {

    private final ModelFactory singleton;

    public UsuarioPersistente(ModelFactory singleton) {
        this.singleton = singleton;
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
                    .append(usuario.getIngresos().toString()).append("@@")
                    .append(usuario.getGastos().toString()).append("@@");

            contenido.append("<<<@@");
            usuario.getIdCuentas().forEach(idCuenta -> contenido.append(idCuenta).append("@@"));
            contenido.append("<<<@@");
            usuario.getPresupuestos().forEach(presupuesto -> contenido.append(presupuesto.getIdPresupuesto()).append("@@"));
            contenido.append("<<<@@");
            usuario.getCategorias().forEach(categoria -> contenido.append(categoria.getIdCategoria()).append("@@"));
            contenido.append("<<<@@");
            usuario.getTransacciones().forEach(transaccion -> contenido.append(transaccion.getIdTransaccion()).append("@@"));
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
        usuario.setIngresos(NumTool.parseToDinero(linea[7]));
        usuario.setGastos(NumTool.parseToDinero(linea[8]));

        int idx = 9;

        // Restauramos las listas de Cuentas, Presupuestos, Categorias y Transacciones usando el método genérico
        usuario.setCuentas((ArrayList<Cuenta>) singleton.restaurarLista(linea, idx, "<<<", Cuenta.class));
        idx += usuario.getCuentas().size() + 1;

        usuario.setPresupuestos((ArrayList<Presupuesto>) singleton.restaurarLista(linea, idx, "<<<", Presupuesto.class));
        idx += usuario.getPresupuestos().size() + 1;

        usuario.setCategorias((ArrayList<Categoria>) singleton.restaurarLista(linea, idx, "<<<", Categoria.class));
        idx += usuario.getCategorias().size() + 1;

        usuario.setTransacciones(singleton.restaurarLista(linea, idx, "<<<", Transaccion.class));

        return usuario;
    }

}


