package co.edu.uniquindio.icaja.mapping.mappers;
import co.edu.uniquindio.icaja.mapping.dto.UsuarioDto;
import co.edu.uniquindio.icaja.model.Usuario;


public class UsuarioMapper {

    public static UsuarioDto usuarioToUsuarioDTo(Usuario usuario) {
        return new UsuarioDto(
                usuario.getNombre(),
                usuario.getCedula(),
                usuario.getCorreo(),
                usuario.getTelefono(),
                usuario.getClave(),
                usuario.getClaveTransaccional(),
                usuario.getPresupuestoMensual()
        );
    }

    public static Usuario usuarioDtoToUsuario(UsuarioDto usuarioDto) {
        return new Usuario(
                usuarioDto.nombre(),
                usuarioDto.cedula(),
                usuarioDto.correo(),
                usuarioDto.telefono(),
                usuarioDto.clave(),
                usuarioDto.claveTransaccional(),
                usuarioDto.presupuestoMensual()
        );
    }
}