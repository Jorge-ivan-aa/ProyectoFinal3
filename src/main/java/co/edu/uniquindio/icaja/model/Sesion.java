package co.edu.uniquindio.icaja.model;

import co.edu.uniquindio.icaja.exception.CredencialesNoCoinciden;
import co.edu.uniquindio.icaja.exception.UsuarioNoExiste;
import co.edu.uniquindio.icaja.factory.ModelFactory;
import co.edu.uniquindio.icaja.model.enums.TipoUsuario;
import co.edu.uniquindio.icaja.model.services.Login;
import co.edu.uniquindio.icaja.utils.Seguimiento;

import java.util.ArrayList;

public class Sesion implements Login {
    private final Usuario usuario;
    private final String clave;

    public Sesion(String cedula, String clave) {
        this.usuario = this.buscarUsuario(cedula);
        this.cedula = cedula;
        this.clave = clave;
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
    public TipoUsuario ingresar() throws UsuarioNoExiste, CredencialesNoCoinciden{
        if (this.usuario == null) {
            Seguimiento.registrarLog(2, "Usuario no existe");
            throw new UsuarioNoExiste("Usuario no encontrado, revisa la cedula ingresada.");
        } else if (!usuario.getClave().equals(clave)) {
            throw new CredencialesNoCoinciden("Contraseña incorrecta, intenta nuevamente.");
        }

        return this.usuario.ingresar();
    }

    public Usuario getUsuario() {
        return usuario;
    }
}
