package co.edu.uniquindio.icaja.factory;

import co.edu.uniquindio.icaja.model.ICaja;
import co.edu.uniquindio.icaja.model.Usuario;
import co.edu.uniquindio.icaja.model.persistencia.UsuarioPersistente;
import co.edu.uniquindio.icaja.utils.respaldo.ICajaRespaldo;
import lombok.Getter;
import java.io.IOException;
import java.util.List;

@Getter
public class ModelFactory {
    private static ModelFactory instance;
    private ICaja icaja;

    // PERSISTENCIA
    private final UsuarioPersistente usuarioPersistente;

    private ModelFactory() {
        icaja = cargaRespaldo();
        usuarioPersistente = new UsuarioPersistente();

        if (icaja == null) {
            icaja = new ICaja();
            loadData();
        }

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

        guardarRespaldo();
    }

    public ICaja cargaRespaldo() {
        return ICajaRespaldo.cargarRecursoICajaXML();
    }

    public void guardarRespaldo() {
        ICajaRespaldo.guardarRecursoICajaBinario(icaja);
        ICajaRespaldo.guardarRecursoICajaXML(icaja);
    }
}
