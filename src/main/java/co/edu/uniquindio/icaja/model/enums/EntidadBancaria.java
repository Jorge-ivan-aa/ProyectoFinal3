package co.edu.uniquindio.icaja.model.enums;

import lombok.Getter;

@Getter
public enum EntidadBancaria {
    BANCOLOMBIA("Bancolombia"),
    DAVIVIENDA("Davivienda"),
    BANCO_DE_BOGOTA("Banco de Bogotá"),
    BANCO_DE_OCCIDENTE("Banco de Occidente"),
    BANCO_POPULAR("Banco Popular"),
    BANCO_AV_VILLAS("Banco AV Villas"),
    BBVA("BBVA"),
    SCOTIABANK_COLPATRIA("Scotiabank Colpatria"),
    ITAU("Itaú"),
    BANCO_GNB_SUDAMERIS("Banco GNB Sudameris"),
    CITI_BANK("Citi Bank"),
    BANCO_AGRARIO("Banco Agrario");

    private final String nombre;
    EntidadBancaria(String nombre) {
        this.nombre = nombre;
    }
}