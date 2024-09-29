package co.edu.uniquindio.proyectofinal3.factory;

import co.edu.uniquindio.proyectofinal3.model.ICaja;

public class ModelFactory {
    private static ModelFactory instance;

    private ICaja icaja;

    private ModelFactory() {
        icaja = new ICaja();
       

    }

    public static ModelFactory getInstance() {
        if (instance == null) {
            instance = new ModelFactory();
        }
        return instance;
    }

    public ICaja getIcaja() {
        return icaja;
    }

}
