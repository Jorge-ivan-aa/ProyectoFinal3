package co.edu.uniquindio.icaja.factory;


import co.edu.uniquindio.icaja.model.ICaja;
import co.edu.uniquindio.icaja.model.Usuario;

public class ModelFactory {
    private static ModelFactory instance;

    private final ICaja icaja;

    private ModelFactory() {
        icaja = new ICaja();
        generarAdmin();
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

    public void generarAdmin() {
        Usuario admin = new Usuario("admin", "1004827105", "jorgity345@gmail.com", "3175083079", "123", "12345", 0.0);
        Usuario admin2 = new Usuario ("admin", "1004871460", "hernandezluis1185@gmail.com", "3217998752", "2325", "2325", 0.0);
        Usuario admin3 = new Usuario ("admin", "1034777786", "carloss.coloradoa@uqvirtual.edu.co", "3014828719", "123", "12345", 0.0);

        admin.setAdministrador();
        admin2.setAdministrador();
        admin3.setAdministrador();
        icaja.addUsuario(admin);
        icaja.addUsuario(admin2);
        icaja.addUsuario(admin3);
    }

}
