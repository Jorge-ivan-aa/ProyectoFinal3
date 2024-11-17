package co.edu.uniquindio.icaja.model;

import java.io.Serializable;

public class ChatBot implements Serializable {
    public static final long serialVersionID = 10L;
    //Logica interna para el chatBot
    public String procesarEntrada(String input) {
        input = input.toLowerCase(); // Normaliza la entrada
        if (input.contains("hola")) {
            return "¡Hola! ¿Cómo estás?";
        } else if (input.contains("ayuda")) {
            return "Estoy aquí para ayudarte. ¿Qué necesitas?";
        } else if (input.contains("adiós")||input.contains("adios")) {
            return "¡Adiós! Espero haberte ayudado.";
            //preguntas generales con estrategias de ahorro
        } else if (input.contains("como puedo administrar mi salario de una forma ordenada?")||input.contains("¿como puedo administrar mi salario de una forma ordenada?")){
            return "Podrías como una medida de control crear diferentes presupuestos para asi tener un mayor nivel de gestión con tus gastos y tu dinero.";
        } else if(input.contains("donde puedo crear presupuestos?")||input.contains("¿En donde puedo crear presupuestos?")||input.contains("¿En donde puedo crear algún presupuesto?")) {
            return "Para la creación de un presupuesto deberás dirigirte al apartado de Cuentas Bancarias y buscar la opción que dice"+ "´Ajustar presupuestos´"  +" desde el menú de inicio";
        } else if(input.contains("cómo generar mi reporte financiero?")||input.contains("¿como puedo generar mi reporte financiero?")||input.contains("¿como puedo generar un resporte financiero?")) {
            return " ";
        } else if(input.contains("cómo puedo cambiar mi contraseña de ingreso?")||input.contains("¿Cómo puedo cambiar mi contraseña de ingreso?")||input.contains("¿como puedo cambiar mi contraseña?")) {
            return " ";
        } else if(input.contains("Cómo puedo actualizar datos de mi perfil?")||input.contains("como puedo actualizar mi perfil?")||input.contains("¿en donde puedo actualizar mi perfil?")) {
            return " ";
        } else if(input.contains("Para que me sirve tener presupuestos?")||input.contains("¿Para que sirve crear presupuestos?")) {
            return " ";
        } else if(input.contains("Cuántas cuentas bancarias puedo tener?")||input.contains("¿cuantas cuentas bancarias puedo tener?")||input.contains("¿cuentas cuentas bancarias se pueden tener?")) {
            return " ";
        } else if(input.contains("como puedo crear una nueva cuenta bancaria?")||input.contains("¿como se crea una cuenta bancaria?")) {
            return " ";
        } else if(input.contains("que entidades hay actualmente en la aplicación?")||input.contains("¿cuantas entidades hay en la aplicación?")) {
            return " ";
        } else if(input.contains("Cuales son los tipos de cuentas que usa la aplicación?")) {
            return " ";
        } else if(input.contains("que es una cuenta corriente?")) {
            return " ";
        } else if(input.contains("que es una cuenta de ahorro?")) {
            return " ";
        } else if(input.contains("para que me sirve una cuenta corriente?")) {
            return " ";
        } else if(input.contains("para que me sirve una cuenta de ahorro?")) {
            return " ";


        } else {
            return "Lo siento, no entiendo tu pregunta.";
        }
    }
}
