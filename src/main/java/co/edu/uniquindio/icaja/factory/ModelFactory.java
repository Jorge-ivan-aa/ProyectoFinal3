package co.edu.uniquindio.icaja.factory;

import co.edu.uniquindio.icaja.model.ICaja;
import co.edu.uniquindio.icaja.model.Usuario;

public class ModelFactory {
    private static ModelFactory instance;

    private final ICaja icaja;

    private ModelFactory() {
        icaja = new ICaja();
        generarAdmin();
        generarUsuario();
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
        Usuario admin = new Usuario("AdminJorge", "1004827105", "jorgity345@gmail.com", "3175083079", "123", "12345", 0.0);
        Usuario admin2 = new Usuario ("AdminJack", "1004871460", "hernandezluis1185@gmail.com", "3217998752", "2325", "2325", 0.0);
        Usuario admin3 = new Usuario ("AdminCarlitos", "1034777786", "carloss.coloradoa@uqvirtual.edu.co", "3014828719", "123", "12345", 0.0);

        admin.setAdministrador();
        admin2.setAdministrador();
        admin3.setAdministrador();
        icaja.addUsuario(admin);
        icaja.addUsuario(admin2);
        icaja.addUsuario(admin3);
    }

    public void generarUsuario() {
        Usuario usuario = new Usuario("Miguel", "7894561", "miguel@correo.com", "3108749632", "54321", "54321", 0.0);
        Usuario usuario1 = new Usuario("Sofía", "1122334", "sofia@gmail.com", "3225748596", "67890", "67890", 0.0);
        Usuario usuario2 = new Usuario("Andrés", "4455667", "andres@yahoo.com", "3156478923", "98765", "98765", 0.0);
        Usuario usuario3 = new Usuario("Valentina", "9988776", "valentina@hotmail.com", "3196584751", "24680", "24680", 0.0);
        Usuario usuario4 = new Usuario("Esteban", "3355779", "esteban@outlook.com", "3138745963", "13579", "13579", 0.0);

        icaja.addUsuario(usuario);
        icaja.addUsuario(usuario1);
        icaja.addUsuario(usuario2);
        icaja.addUsuario(usuario3);
        icaja.addUsuario(usuario4);
    }


}
