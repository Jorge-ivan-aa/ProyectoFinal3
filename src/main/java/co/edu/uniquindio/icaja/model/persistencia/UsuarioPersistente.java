package co.edu.uniquindio.icaja.model.persistencia;

import co.edu.uniquindio.icaja.model.Usuario;
import co.edu.uniquindio.icaja.model.enums.TipoUsuario;
import co.edu.uniquindio.icaja.model.services.Persistible;
import co.edu.uniquindio.icaja.utils.Persistencia;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UsuarioPersistente implements Persistible<Usuario> {


    @Override
    public void guardar(List<Usuario> usuarios) throws IOException {
        StringBuilder contenido = new StringBuilder();
        for(Usuario usuario:usuarios)
        {
            contenido.append(
                    usuario.getNombre()).append("@@")
                    .append(usuario.getCedula()).append("@@")
                    .append(usuario.getCorreo()).append("@@")
                    .append(usuario.getTelefono()).append("@@")
                    .append(usuario.getClave()).append("@@")
                    .append(usuario.getClaveTransaccional()).append("@@")
                    .append(usuario.getSaldoTotal()).append("@@")
                    .append(usuario.getIngresos()).append("@@")
                    .append(usuario.getGastos()).append("@@")
                    .append(usuario.getPresupuestoMensual()).append("@@")
                    .append(usuario.getTipoUsuario()).append("\n");
        }
        Persistencia.guardarArchivo("usuario.txt", contenido.toString(), false);
    }

    @Override
    public List<Usuario> leer(String ruta) throws IOException {
        ArrayList<Usuario> usuarios = new ArrayList<>();
        ArrayList<String> contenido = Persistencia.leerArchivo(ruta);
        String[] linea;
        for (String texto : contenido) {
            linea = texto.split("@@");
            Usuario usuario = new Usuario();
            usuario.setNombre(linea[0]);
            usuario.setCedula(linea[1]);
            usuario.setCorreo(linea[2]);
            usuario.setTelefono(linea[3]);
            usuario.setClave(linea[4]);
            usuario.setClaveTransaccional(linea[5]);
            usuario.setSaldoTotal(Double.parseDouble(linea[6]));
            usuario.setIngresos(Double.parseDouble(linea[7]));
            usuario.setGastos(Double.parseDouble(linea[8]));
            usuario.setTipoUsuario(TipoUsuario.valueOf(linea[10]));
            usuarios.add(usuario);
        }
        return usuarios;
    }
}
