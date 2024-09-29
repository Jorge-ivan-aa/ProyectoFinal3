package co.edu.uniquindio.icaja.model.services;

import co.edu.uniquindio.icaja.exception.CredencialesNoCoinciden;
import co.edu.uniquindio.icaja.exception.UsuarioNoExiste;

public interface Login {
    public boolean ingresar() throws UsuarioNoExiste, CorreoNoRegistrado, CredencialesNoCoinciden;
}
