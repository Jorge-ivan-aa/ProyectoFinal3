package co.edu.uniquindio.icaja.mapping.mappers;
import co.edu.uniquindio.icaja.mapping.dto.UsuarioDto;
import co.edu.uniquindio.icaja.model.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;


@Mapper
public interface UsuarioMapper {
    UsuarioMapper INSTANCE = Mappers.getMapper(UsuarioMapper.class);

    // MApeo de usuario a usuarioDTO
    @Named("usuarioToUsuarioDto")
    UsuarioDto usuarioToUsuarioDto(Usuario usuario);

    // MApeo de usuarioDto a usuario
    @Named("usuarioDtoToUsuario")
    Usuario usuarioDtoToUsuario(UsuarioDto usuarioDto);

}