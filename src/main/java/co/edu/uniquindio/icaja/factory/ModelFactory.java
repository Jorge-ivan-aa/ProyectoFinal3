package co.edu.uniquindio.icaja.factory;

import co.edu.uniquindio.icaja.model.ICaja;
import co.edu.uniquindio.icaja.model.Usuario;
import co.edu.uniquindio.icaja.model.persistencia.UsuarioPersistente;
import lombok.Getter;
import java.io.IOException;
import java.util.List;

@Getter
public class ModelFactory {
    private static ModelFactory instance;
    private final ICaja icaja;

    // PERSISTENCIA
    private final UsuarioPersistente usuarioPersistente;

    private ModelFactory() {
        icaja = new ICaja();
        usuarioPersistente = new UsuarioPersistente();
        loadData();
    }


    public static ModelFactory getInstance() {
        if (instance == null) {
            instance = new ModelFactory();
        }
        return instance;
    }

    public void loadData() {
        List<Usuario> usuarios = null;
        try {
            usuarios = usuarioPersistente.leer("usuario.txt");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        if (usuarios != null) {
            for (Usuario usuario : usuarios) {
                icaja.addUsuario(usuario);
            }
        }
    }
}
