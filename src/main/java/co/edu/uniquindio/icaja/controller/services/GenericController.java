package co.edu.uniquindio.icaja.controller.services;

import co.edu.uniquindio.icaja.exception.crud.ElementoNoExiste;
import co.edu.uniquindio.icaja.exception.crud.ElementoYaExiste;

/**
 * Interfaz que define los métodos generales para los controladores de los modelos.
 *
 * @param <DTO> el Data Transfer Object (DTO) que representa el modelo.
 * @param <ELEMENT> el modelo que maneja el controlador.
 */
public interface GenericController<DTO, ELEMENT> {

    /**
     * Crea un nuevo elemento del modelo y lo agrega a una lista de modelos.
     *
     * @param dto el DTO que representa el modelo a crear.
     * @throws ElementoYaExiste si el elemento ya existe en la lista de modelos.
     */
    void crear(DTO dto) throws ElementoYaExiste;

    /**
     * Consulta un elemento en la lista de modelos usando un identificador único.
     *
     * @param identificador el atributo único del modelo por el cual se realiza la consulta.
     * @return el modelo si existe, de lo contrario se lanza una excepción.
     * @throws ElementoNoExiste si el elemento consultado no existe en la lista.
     */
    ELEMENT consultar(String identificador) throws ElementoNoExiste;

    /**
     * Elimina un elemento de la lista de modelos usando un identificador único.
     *
     * @param identificador el atributo único del modelo que se desea eliminar.
     * @throws ElementoNoExiste si el elemento a eliminar no existe en la lista.
     */
    void eliminar(String identificador) throws ElementoNoExiste;

    /**
     * Actualiza los atributos de un elemento existente en la lista de modelos.
     *
     * @param dto el DTO que contiene los nuevos datos del modelo.
     * @throws ElementoNoExiste si el elemento a actualizar no existe en la lista.
     */
    void actualizar(DTO dto) throws ElementoNoExiste;

    /**
     * Sincroniza las listas internas y externas, y ejecuta los métodos de persistencia necesarios.
     */
    void sincronizarData();

    /**
     * Configura y ejecuta la lógica de persistencia para almacenar los datos del modelo.
     */
    void persistir();
}