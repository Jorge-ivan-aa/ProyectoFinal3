package co.edu.uniquindio.icaja.model.services;

import co.edu.uniquindio.icaja.exception.CredencialesNoCoinciden;
import co.edu.uniquindio.icaja.exception.UsuarioNoExiste;
import co.edu.uniquindio.icaja.model.enums.TipoUsuario;

public interface Login {
    public TipoUsuario ingresar() throws UsuarioNoExiste, CredencialesNoCoinciden;
}
