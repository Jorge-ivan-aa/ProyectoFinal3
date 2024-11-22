package co.edu.uniquindio.icaja.server.config;

import lombok.Getter;

@Getter
public enum Cola {
    COLA_SYNC("cola_sync");

    final String cola;
    Cola(String cola) {
        this.cola = cola;
    }
}
