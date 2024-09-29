package co.edu.uniquindio.icaja.model;

import co.edu.uniquindio.icaja.exception.CredencialesNoCoinciden;
import co.edu.uniquindio.icaja.exception.UsuarioNoExiste;
import co.edu.uniquindio.icaja.factory.ModelFactory;
import co.edu.uniquindio.icaja.model.services.Login;

import java.util.ArrayList;

public class UsuarioProxy implements Login {
    private final Usuario usuario;
    private final String cedula;
    private final String clave;

    public UsuarioProxy(String cedula, String clave) {
        this.usuario = this.buscarUsuario();
        this.cedula = cedula;
        this.clave = clave;
    }

    public Usuario buscarUsuario() {
        ICaja icaja = ModelFactory.getInstance().getIcaja();
        ArrayList<Usuario> listaUsuarios = icaja.getListaUsuarios();

        for(Usuario usuario : listaUsuarios) {
            if(usuario.getCedula().equals(this.cedula)) {
                return usuario;
            }
        }

        return null;
    }

    @Override
    public boolean ingresar() throws UsuarioNoExiste, CredencialesNoCoinciden{
        if (this.usuario == null) {
            throw new UsuarioNoExiste("Usuario no encontrado, revisa la cedula ingresada.");
        } else if (!usuario.getClave().equals(clave)) {
            throw new CredencialesNoCoinciden("Contraseña incorrecta, intenta nuevamente.");
        }

        return this.usuario.ingresar();
    }
}
