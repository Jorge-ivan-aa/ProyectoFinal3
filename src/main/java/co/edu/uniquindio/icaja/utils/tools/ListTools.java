package co.edu.uniquindio.icaja.utils.tools;

import co.edu.uniquindio.icaja.exception.almacenamiento.ElementoNoEncontrado;
import co.edu.uniquindio.icaja.exception.almacenamiento.TipoNoMapeado;

import java.util.List;
import java.util.function.Function;

public class ListTools {

    /**
     * Método recursivo que busca un elemento en una lista utilizando su identificador único (ID).
     * Si no encuentra el objeto en la lista, lanza una excepción {@link ElementoNoEncontrado}.
     *
     * @param lista La lista donde se busca el elemento.
     * @param mapper Función que mapea un objeto según la consulta (String).
     * @param consultado El identificador único del objeto a buscar.
     * @param index El índice actual en la lista para realizar la búsqueda recursiva.
     * @return El objeto encontrado si el ID coincide.
     * @throws ElementoNoEncontrado Si no se encuentra el objeto con el ID dado en la lista.
     */
    public static Object ConsultaAvanzada(List<?> lista, Function<Object, String> mapper, String consultado, int index) throws ElementoNoEncontrado {
        // Caso base: si el índice es mayor o igual al tamaño de la lista, devolvemos null
        if (index >= lista.size()) {
            throw new ElementoNoEncontrado("El elemento con el consultado " + consultado + "No se encontró en su lista correspondiente ");
        }

        Object elemento = lista.get(index);
        String resultado = mapper.apply(elemento);

        if (resultado != null && resultado.equals(consultado)) {
            return elemento;
        }

        // Llamada recursiva para el siguiente índice
        return ConsultaAvanzada(lista, mapper, consultado, index + 1);
    }

    /**
     * Actualzia los elementos de una lista en base una lista fuente.
     *
     * @param actualizable lista que se va a actualizar
     * @param listaFuente lista que se utiliza para actualizar otrs
     * @param <T> Tipo de lista.
     */
    public static  <T> void sincronizarLista(List<T> actualizable, List<T> listaFuente) {
        actualizable.clear();
        actualizable.addAll(listaFuente);
    }

}
