package co.edu.uniquindio.icaja.controller.enums;

import co.edu.uniquindio.icaja.model.*;
import lombok.Getter;

import java.util.function.Function;

public enum TipoConsulta {
    // Para usuario
    ID_USUARIO(Usuario.class, usuario -> ((Usuario) usuario).getIdUsuario()),
    CEDULA(Usuario.class, usuario -> ((Usuario) usuario).getCedula()),
    CORREO(Usuario.class, usuario -> ((Usuario) usuario).getCorreo()),
    TELEFONO(Usuario.class, usuario -> ((Usuario) usuario).getTelefono()),

    // para categoria
    ID_CATEGORIA(Categoria.class, categoria -> ((Categoria) categoria).getIdCategoria()),

    // para cuenta
    ID_CUENTA(Cuenta.class, cuenta -> ((Cuenta) cuenta).getIdCuenta()),
    NUMERO_CUENTA(Cuenta.class, cuenta -> ((Cuenta) cuenta).getNumeroCuenta()),

    // para presupuesto
    ID_PRESUPUESTO(Presupuesto.class, presupuesto -> ((Presupuesto) presupuesto).getIdPresupuesto()),

    // para transaccion
    ID_TRANSACCION(Transaccion.class, transaccion -> ((Transaccion) transaccion).getIdTransaccion());


    @Getter
    private final Class<?> tipo;
    private final Function<Object, String> buscador;

    TipoConsulta(Class<?> tipo, Function<Object, String> buscador) {
        this.tipo = tipo;
        this.buscador = buscador;
    }

    public Function<Object, String> getBuscador() {
        return buscador;
    }
}
