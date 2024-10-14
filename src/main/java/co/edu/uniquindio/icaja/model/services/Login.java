package co.edu.uniquindio.icaja.model.services;

import co.edu.uniquindio.icaja.exception.login.CredencialesNoCoinciden;
import co.edu.uniquindio.icaja.exception.login.UsuarioNoExiste;
import co.edu.uniquindio.icaja.model.enums.TipoUsuario;

public interface Login {
    public TipoUsuario ingresar(String clave) throws UsuarioNoExiste, CredencialesNoCoinciden;
}
