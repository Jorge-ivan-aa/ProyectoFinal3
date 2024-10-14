package co.edu.uniquindio.icaja.controller.services;

import co.edu.uniquindio.icaja.exception.crud.ElementoNoExiste;
import co.edu.uniquindio.icaja.exception.crud.ElementoYaExiste;

public interface GenericController<DTO, ELEMENT> {

    void crear(DTO dto) throws ElementoYaExiste;
    ELEMENT consultar(String identificador) throws ElementoNoExiste;
    void eliminar(String identificador) throws ElementoNoExiste;
    void actualizar(DTO dto) throws ElementoNoExiste;

    void sincronizarData();
    void persistir();
}