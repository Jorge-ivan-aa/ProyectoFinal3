package co.edu.uniquindio.icaja.model;

import co.edu.uniquindio.icaja.exception.login.UsuarioNoExiste;
import co.edu.uniquindio.icaja.factory.ModelFactory;
import co.edu.uniquindio.icaja.model.enums.TipoUsuario;
import co.edu.uniquindio.icaja.model.services.Login;
import co.edu.uniquindio.icaja.utils.loggin.Seguimiento;
import lombok.Getter;

import java.util.ArrayList;

@Getter
public class Sesion implements Login {
    private final Usuario usuario;


    public Sesion(String cedula) {
        this.usuario = this.buscarUsuario(cedula);
    }

    public Usuario buscarUsuario(String cedula) {
        ICaja icaja = ModelFactory.getInstance().getIcaja();
        ArrayList<Usuario> listaUsuarios = icaja.getListaUsuarios();

        for(Usuario usuario : listaUsuarios) {
            if(usuario.getCedula().equals(cedula)) {
                return usuario;
            }
        }

        return null;
    }

    @Override
    public TipoUsuario ingresar(String clave) throws UsuarioNoExiste {
        if (this.usuario == null) {
            Seguimiento.registrarLog(2, "Usuario no existe");
            throw new UsuarioNoExiste("Usuario no encontrado, revisa la cedula ingresada.");
        }

        return this.usuario.ingresar(clave);

    }
}
