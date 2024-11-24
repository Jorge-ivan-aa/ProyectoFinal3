package co.edu.uniquindio.icaja.mapping.mappers;

import co.edu.uniquindio.icaja.mapping.dto.CategoriaDto;
import co.edu.uniquindio.icaja.model.Categoria;


public class CategoriaMapper {

    public static CategoriaDto toDto(Categoria categoria){
        return new CategoriaDto(
                categoria.getIdCategoria(),
                categoria.getNombre(),
                categoria.getDescripcion()
        );
    }
    public static Categoria toCategoria(CategoriaDto categoriaDto){
        return new Categoria(
                categoriaDto.nombre(),
                categoriaDto.descripcion()
        );
    }

}