package co.edu.uniquindio.icaja.factory;

import co.edu.uniquindio.icaja.model.Categoria;
import co.edu.uniquindio.icaja.model.Cuenta;
import co.edu.uniquindio.icaja.model.ICaja;
import co.edu.uniquindio.icaja.model.Usuario;
import co.edu.uniquindio.icaja.model.persistencia.CategoriaPersistente;
import co.edu.uniquindio.icaja.model.persistencia.CuentaBancariaPersistente;
import co.edu.uniquindio.icaja.model.persistencia.PresupuestoPersistente;
import co.edu.uniquindio.icaja.model.persistencia.UsuarioPersistente;
import co.edu.uniquindio.icaja.utils.loggin.Seguimiento;
import co.edu.uniquindio.icaja.utils.respaldo.ICajaRespaldo;
import co.edu.uniquindio.icaja.utils.respaldo.Persistencia;
import lombok.Getter;
import java.io.IOException;
import java.util.List;

@Getter
public class ModelFactory {
    private static ModelFactory instance;
    private ICaja icaja;

    // PERSISTENCIA
    private final UsuarioPersistente usuarioPersistente;
    private final CuentaBancariaPersistente cuentaBancariaPersistente;
    private final CategoriaPersistente categoriaPersistente;
    private final PresupuestoPersistente presupuestoPersistente;

    private ModelFactory() {
        icaja = cargaRespaldo();
        usuarioPersistente = new UsuarioPersistente();
        cuentaBancariaPersistente = new CuentaBancariaPersistente();
        categoriaPersistente = new CategoriaPersistente();
        presupuestoPersistente= new PresupuestoPersistente();

        if (icaja == null) {
            icaja = new ICaja();
            loadData();
        }

    loadConfig();
    }


    public static ModelFactory getInstance() {
        if (instance == null) {
            instance = new ModelFactory();
        }
        return instance;
    }

    public void loadData() {
        List<Usuario> usuarios = null;
        List<Cuenta> cuentasBancarias = null;
        List<Categoria> categorias = null;
        try {
            usuarios = usuarioPersistente.leer("usuario.txt");
            cuentasBancarias = cuentaBancariaPersistente.leer("cuenta.txt");
            categorias = categoriaPersistente.leer("categoria.txt");
            
        } catch (IOException e) {
            Seguimiento.registrarLog(3, "No se han podido cargar los archivos de persistencia: " + e.getMessage());
        }

        agregarElementos(usuarios);
        agregarElementos(cuentasBancarias);
        agregarElementos(categorias);

        guardarRespaldo();
    }
    
    private <T> void agregarElementos(List<T> listaElementos) {
        if (listaElementos != null) {
            for (T elemento :listaElementos) {
                if (elemento instanceof Usuario) {
                    icaja.addUsuario((Usuario) elemento);
                } else if (elemento instanceof Cuenta) {
                    icaja.addCuentaBancaria((Cuenta) elemento);
                } else if (elemento instanceof  Categoria) {
                    icaja.addCategoria((Categoria) elemento);
                }
            }
        }
    }
    

    public void loadConfig() {
        String cedula = Persistencia.cargarConfiguracion("admin");
        String contrasena = Persistencia.cargarConfiguracion("contrasena");

        icaja.excluirAdmin(icaja.getListaUsuarios());

        Usuario admin = new Usuario();
        admin.setNombre("Administrador");
        admin.setCedula(cedula);
        admin.setHashclave(contrasena);
        admin.setAdministrador();

        icaja.addUsuario(admin);
        Seguimiento.registrarLog(1,"Se cargó la configuración de las credenciales de administrador");
    }

    public ICaja cargaRespaldo() {
        return ICajaRespaldo.cargarRecursoICajaXML();
    }

    public void guardarRespaldo() {
        icaja.setSesion(null);
        ICajaRespaldo.guardarRecursoICajaBinario(icaja);
        ICajaRespaldo.guardarRecursoICajaXML(icaja);
    }
}
