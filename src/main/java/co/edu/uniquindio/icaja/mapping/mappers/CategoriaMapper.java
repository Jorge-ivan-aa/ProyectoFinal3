package co.edu.uniquindio.icaja.mapping.mappers;

import co.edu.uniquindio.icaja.mapping.dto.CategoriaDto;
import co.edu.uniquindio.icaja.mapping.dto.UsuarioDto;
import co.edu.uniquindio.icaja.model.Categoria;
import co.edu.uniquindio.icaja.model.Usuario;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

public interface CategoriaMapper {
    CategoriaMapper INSTANCE = Mappers.getMapper(CategoriaMapper.class);
    // Mapeo de Categoria a CategoriaDTO
    @Named("categoriaToCategoriaDto")
    CategoriaDto categoriaToCategoriaDto(Categoria categoria);

    // Mapeo de CategoriaDto a Categoria
    @Named("categoriaDtoToCategoria")
    Categoria categoriaDtoToCategoria(CategoriaDto categoriaDto);
}
