package co.edu.uniquindio.icaja.mapping.mappers;

import co.edu.uniquindio.icaja.mapping.dto.CategoriaDto;
import co.edu.uniquindio.icaja.mapping.dto.UsuarioDto;
import co.edu.uniquindio.icaja.model.Categoria;
import co.edu.uniquindio.icaja.model.Usuario;


public class CategoriaMapper {

    public static CategoriaDto categoriaToCategoriaDto(Categoria categoria){
        return new CategoriaDto(
                categoria.getNombre(),
                categoria.getDescripcion(),
                categoria.getTipoCategoria(),
                categoria.getTransacciones()

        );
    }
    public static Categoria categoriaDtoToCategoria(CategoriaDto categoriaDto){
        return new Categoria(
                categoriaDto.nombre(),
                categoriaDto.descripcion(),
                categoriaDto.tipoCategoria(),
                categoriaDto.transacciones()
        );
    }

}