package co.edu.uniquindio.icaja.factory;

import co.edu.uniquindio.icaja.model.ICaja;
import co.edu.uniquindio.icaja.model.Usuario;
import lombok.Getter;

import javax.imageio.IIOException;
import java.io.IOException;
import java.util.List;

@Getter
public class ModelFactory {
    private static ModelFactory instance;

    private final ICaja icaja;

    private ModelFactory() {
        icaja = new ICaja();
        loadData();
    }


    public static ModelFactory getInstance() {
        if (instance == null) {
            instance = new ModelFactory();
        }
        return instance;
    }

    public ICaja getIcaja() {
        return icaja;
    }

    public void loadData() {
        List<Usuario> usuarios = null;
        try {
            usuarios = new Usuario().leer("usuario.txt");
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
