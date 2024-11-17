package co.edu.uniquindio.icaja.controller;

import co.edu.uniquindio.icaja.controller.enums.TipoConsulta;
import co.edu.uniquindio.icaja.controller.services.GenericController;
import co.edu.uniquindio.icaja.exception.almacenamiento.ElementoNoEncontrado;
import co.edu.uniquindio.icaja.exception.crud.AtributoUtilizado;
import co.edu.uniquindio.icaja.exception.crud.ElementoNoExiste;
import co.edu.uniquindio.icaja.exception.crud.ElementoYaExiste;
import co.edu.uniquindio.icaja.factory.ModelFactory;
import co.edu.uniquindio.icaja.mapping.dto.UsuarioDto;
import co.edu.uniquindio.icaja.mapping.mappers.UsuarioMapper;
import co.edu.uniquindio.icaja.model.Usuario;
import static co.edu.uniquindio.icaja.controller.enums.TipoConsulta.*;
import static co.edu.uniquindio.icaja.utils.loggin.Seguimiento.registrarLog;
import static co.edu.uniquindio.icaja.utils.tools.ListTools.ConsultaAvanzada;
import co.edu.uniquindio.icaja.utils.loggin.Seguimiento;
import javafx.collections.ObservableList;
import lombok.Getter;


@Getter
public class UsuarioController implements GenericController<UsuarioDto, Usuario> {

    private final ModelFactory factory;
    private final ObservableList<Usuario> listaUsuarioObservable;

    public UsuarioController() {
        factory = ModelFactory.getInstance();
        this.listaUsuarioObservable = this.factory.getListaUsuarioObservable();
        this.sincronizarData();
    }

    public void sincronizarData() {
        factory.sincronizarData();
    }

    @Override
    public void crear(UsuarioDto usuarioDto) throws ElementoYaExiste, AtributoUtilizado {

        try {
            consultar(usuarioDto.id(), ID_USUARIO);
            registrarLog(2, "No se puede crear el elemento, el usuario ya existe");
            throw new ElementoYaExiste("No se puede crear el elemento, el usuario ya existe");

        } catch (ElementoNoExiste ignored) {
            verificarAtributoUtilizado(usuarioDto.cedula(), CEDULA, "No se puede crear el elemento, el usuario ya existe");
            verificarAtributoUtilizado(usuarioDto.telefono(), TELEFONO, "El telefono: " + usuarioDto.telefono() + " ya está siendo utilizado por otro usuario");
            verificarAtributoUtilizado(usuarioDto.correo(), CORREO, "El correo: " + usuarioDto.correo() + " ya está siendo utilizado por otro usuario");

            Usuario nuevoUsuario = UsuarioMapper.toUsuario(usuarioDto);
            factory.getIcaja().add(nuevoUsuario);
            listaUsuarioObservable.add(nuevoUsuario);
            sincronizarData();
            registrarLog(1, "Se ha creado el usuario " + usuarioDto.nombre());

        }
    }

    private void verificarAtributoUtilizado(String valor, TipoConsulta tipo, String mensaje) throws AtributoUtilizado {
        if (consultar(valor, tipo) != null) {
            registrarLog(2, mensaje);
            throw new AtributoUtilizado(mensaje);
        }
    }


    @Override
    public Usuario consultar(String consulta, TipoConsulta tipoConsulta) throws ElementoNoExiste {
        Seguimiento.registrarLog(1, "Se hace una consulta de tipo " + tipoConsulta + " con el criterio: " + consulta);
        try {
            return (Usuario) ConsultaAvanzada(factory.getIcaja().getListaUsuarios(),
                    tipoConsulta.getBuscador(),
                    consulta,
                    0);

        } catch (ElementoNoEncontrado e) {
            if (tipoConsulta.equals(ID_USUARIO)) {
                throw new ElementoNoExiste("No se encontró un usuario con el id: " + consulta);
            }

            Seguimiento.registrarLog(2, "No se encontró el elemento con el criterio especificado");
            return null;
        }
    }


    @Override
    public void eliminar(String id) throws ElementoNoExiste {
        try {
            Usuario eliminable = consultar(id, ID_USUARIO);
            factory.getIcaja().remove(eliminable);
            sincronizarData();
            registrarLog(1, "Se eliminó el usuario con id " + id + ".");

        } catch (ElementoNoExiste e) {
            registrarLog(2, "No se pudo eliminar el elemento, " + e.getMessage());
            throw new ElementoNoExiste("No se pudo eliminar el elemento, " + e.getMessage());
        }
    }

    @Override
    public void actualizar(UsuarioDto usuarioDto) throws ElementoNoExiste {
        try {
            Usuario actualizable = consultar(usuarioDto.id(), ID_USUARIO);

            if (actualizable.getCedula().equals(usuarioDto.cedula())) {
                actualizable.setNombre(usuarioDto.nombre());
                actualizable.setTelefono(usuarioDto.telefono());

                if (!usuarioDto.clave().isEmpty()) {
                    actualizable.setHashclave(usuarioDto.clave());
                }

                if (!usuarioDto.claveTransaccional().isEmpty()) {
                    actualizable.setHashclaveTransaccional(usuarioDto.claveTransaccional());
                }

                actualizable.setCorreo(usuarioDto.correo());
                sincronizarData();
                registrarLog(1, "Se actualizó el usuario de cedula " + actualizable.getCedula() + " correctamente.");
            } else {
                throw new ElementoNoExiste("No se puede modificar la cedula.");
            }


        } catch (ElementoNoExiste e) {
            registrarLog(2, "No se pudo actualizar el elemento, " + e.getMessage());
            throw new ElementoNoExiste("No se pudo actualizar el elemento, " + e.getMessage());
        }
    }


    public void cerrarSesion() {
        Seguimiento.registrarLog(1, "Se cerró la sesion correctamente");
        factory.getIcaja().setSesion(null);
        factory.guardarRespaldo();
    }

}