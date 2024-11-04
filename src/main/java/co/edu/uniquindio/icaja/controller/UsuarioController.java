package co.edu.uniquindio.icaja.controller;

import co.edu.uniquindio.icaja.controller.services.GenericController;
import co.edu.uniquindio.icaja.exception.crud.ElementoNoExiste;
import co.edu.uniquindio.icaja.exception.crud.ElementoYaExiste;
import co.edu.uniquindio.icaja.factory.ModelFactory;
import co.edu.uniquindio.icaja.mapping.dto.UsuarioDto;
import co.edu.uniquindio.icaja.mapping.mappers.UsuarioMapper;
import co.edu.uniquindio.icaja.model.Usuario;
import static co.edu.uniquindio.icaja.utils.loggin.Seguimiento.registrarLog;
import co.edu.uniquindio.icaja.utils.loggin.Seguimiento;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.Getter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Getter
public class UsuarioController implements GenericController<UsuarioDto, Usuario> {

    private ModelFactory factory;
    private final ObservableList<Usuario> listaUsuarioObservable;

    public UsuarioController() {
        factory = ModelFactory.getInstance();
        this.listaUsuarioObservable = FXCollections.observableArrayList();
        this.sincronizarData();
    }

@Override
    public void sincronizarData() {
        listaUsuarioObservable.clear();
        listaUsuarioObservable.addAll(factory.getIcaja().getListaUsuarios());
        factory.getIcaja().excluirAdmin(listaUsuarioObservable);
        persistir();
        factory.guardarRespaldo();
        registrarLog(1,"Se sincronizaron los usuarios.");
    }

    @Override
    public void crear(UsuarioDto usuarioDto) throws ElementoYaExiste {
        
        try {
            consultar(usuarioDto.cedula());
            registrarLog(2,"No se puede crear el elemento, el usuario ya existe");
            throw new ElementoYaExiste("No se puede crear el elemento, el usuario ya existe");
            
        } catch (ElementoNoExiste ignored) {
            Usuario nuevoUsuario = UsuarioMapper.toUsuario(usuarioDto);
            factory.getIcaja().addUsuario(nuevoUsuario);
            listaUsuarioObservable.add(nuevoUsuario);
            sincronizarData();
            registrarLog(1,"Se ha creado el usuario " + usuarioDto.nombre());
            
        }
    }

    @Override
    public Usuario consultar(String cedula) throws ElementoNoExiste {
        registrarLog(1,"Se consultó el usuario");

        ArrayList<Usuario> Usuarios = factory.getIcaja().getListaUsuarios();
        for (Usuario usuario : Usuarios) {
            if (usuario.getCedula().equals(cedula)) {
                return usuario;
            }
        }
        
        throw new ElementoNoExiste("El usuario no existe.");
    }    

    @Override
    public void eliminar(String cedula) throws ElementoNoExiste {
        try {
            Usuario eliminable = consultar(cedula);
            factory.getIcaja().removeUsuario(eliminable);
            sincronizarData();
            registrarLog(1,"Se eliminó el usuario de cedula " + cedula + ".");

        } catch (ElementoNoExiste e) {
            registrarLog(2,"No se pudo eliminar el elemento, " + e.getMessage());
            throw new ElementoNoExiste("No se pudo eliminar el elemento, " + e.getMessage());
        }
    }

    @Override
    public void actualizar(UsuarioDto usuarioDto) throws ElementoNoExiste {
        try {
            Usuario actualizable = consultar(usuarioDto.cedula());
            actualizable.setNombre(usuarioDto.nombre());
            actualizable.setTelefono(usuarioDto.telefono());
            actualizable.setPresupuestoMensual(usuarioDto.presupuestoMensual());

            if (!usuarioDto.clave().isEmpty()) {
                actualizable.setHashclave(usuarioDto.clave());
            }

            if (!usuarioDto.claveTransaccional().isEmpty()) {
                actualizable.setHashclaveTransaccional(usuarioDto.claveTransaccional());
            }

            actualizable.setCorreo(usuarioDto.correo());
            sincronizarData();
            registrarLog(1,"Se actualizó el usuario de cedula " + actualizable.getCedula() + " correctamente.");

        } catch (ElementoNoExiste e) {
            registrarLog(2,"No se pudo actualizar el elemento, " + e.getMessage());
            throw new ElementoNoExiste("No se pudo actualizar el elemento, " + e.getMessage());
        }
    }

    @Override
    public void persistir() {
        List<Usuario> usuarios = new ArrayList<>(factory.getIcaja().getListaUsuarios());
        factory.getIcaja().excluirAdmin(usuarios);
        try {
            factory.getUsuarioPersistente().guardar(usuarios);
        } catch (IOException e) {
            registrarLog(3, "Error, no se pudo guardar la información de usuario: " + e.getMessage());
        }
    }

    public void cerrarSesion() {
        Seguimiento.registrarLog(1, "Se cerró la sesion correctamente");
        factory.getIcaja().setSesion(null);
        factory.guardarRespaldo();
    }

}