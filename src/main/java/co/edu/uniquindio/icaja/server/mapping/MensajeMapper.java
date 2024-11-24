package co.edu.uniquindio.icaja.server.mapping;

import com.google.gson.Gson;

public class MensajeMapper {

    /**
     * Convierte un objeto de tipo MensajeDTO a su representación en formato JSON (String).
     * Utiliza la librería Gson para realizar la conversión.
     *
     * @param mensajeDTO El objeto de tipo MensajeDTO que se desea convertir a JSON.
     * @return Un String que representa el objeto MensajeDTO en formato JSON.
     */
    public static String toJson(MensajeDTO mensajeDTO) {
        Gson gson = new Gson();
        return gson.toJson(mensajeDTO);
    }

    /**
     * Convierte una cadena de texto en formato JSON a un objeto de tipo MensajeDTO.
     * Utiliza la librería Gson para realizar la conversión.
     *
     * @param json La cadena JSON que representa un objeto de tipo MensajeDTO.
     * @return Un objeto de tipo MensajeDTO representado por la cadena JSON.
     */
    public static MensajeDTO fromJson(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, MensajeDTO.class);
    }
}
