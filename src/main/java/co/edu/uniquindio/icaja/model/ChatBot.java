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
            //preguntas generales
        } else if (input.contains("como puedo administrar mi salario de una forma ordenada?")||input.contains("¿como puedo administrar mi salario de una forma ordenada?")){
            return "Podrías como una medida\n de control crear diferentes\n presupuestos para asi tener\n un mayor nivel de gestión\n con tus gastos y tu dinero.";
        } else if(input.contains("en donde puedo crear presupuestos?")||input.contains("¿En donde puedo crear presupuestos?")||input.contains("¿En donde puedo crear algún presupuesto?")) {
            return "Para la creación de un presupuesto deberás dirigirte al apartado de Cuentas Bancarias y buscar la opción que dice"+ "´Ajustar presupuestos´"  +" desde el menú de inicio.";
        } else if(input.contains("cómo generar mi reporte financiero?")||input.contains("¿como puedo generar mi reporte financiero?")||input.contains("¿como puedo generar un resporte financiero?")) {
            return "Para la creación de un reporte\n financiero deberás dirigirte al\n apartado de Cuentas Bancarias,\n en donde buscarás la opción\n de Generar Reporte Financiero,\n luego de eso automaticamente\n podrás ver dicho reporte.";
        } else if(input.contains("cómo puedo cambiar mi contraseña de ingreso?")||input.contains("¿Cómo puedo cambiar mi contraseña de ingreso?")||input.contains("¿como puedo cambiar mi contraseña?")) {
            return "Para cambiar la contraseña\n de ingreso deberás al apartado\n de Perfil de Usuario y una\n vez allí, podrás diligenciar los cambios\n que requieras referente a\n tu perfil, como el Nombre,\n Telefono, Cédula e incluso el\n correo electrónico.  ";
        } else if(input.contains("Cómo puedo actualizar datos de mi perfil?")||input.contains("como puedo actualizar mi perfil?")||input.contains("¿en donde puedo actualizar mi perfil?")) {
            return " ";
        } else if(input.contains("Para que me sirve tener presupuestos?")||input.contains("¿Para que sirve crear presupuestos?")) {
            return "Tener presupuestos es una\n herramienta poderosa para\n gestionar tus finanzas de manera\n eficiente, ya que te ayudan\n a planificar, controlar tus gastos\n y alcanzar tus metas financieras. ";
        } else if(input.contains("Cuántas cuentas bancarias puedo tener?")||input.contains("¿cuantas cuentas bancarias puedo tener?")||input.contains("¿cuentas cuentas bancarias se pueden tener?")) {
            return " ";
        } else if(input.contains("como puedo crear una nueva cuenta bancaria?")||input.contains("¿como se crea una cuenta bancaria?")) {
            return "Para crear una cuenta\n bancaria inicialmente debes\n dirigirte al apartado de\n Transacciones y diligenciar los\n datos requeridos para dicha\n creación.";
        } else if(input.contains("que entidades hay actualmente en la aplicación?")||input.contains("¿cuantas entidades hay en la aplicación?")) {
            return " ";
        } else if(input.contains("Cuales son los tipos de cuentas que usa la aplicación?")) {
            return "En la aplicación podrás\n encontrar cuentas de\n tipo CORRIENTE como también\n cuentas de tipo AHORRO.";
        } else if(input.contains("que es una cuenta corriente?")) {
            return "Una cuenta corriente es\n un tipo de cuenta bancaria\n diseñada principalmente para\n gestionar el dinero de manera\n cómoda y realizar transacciones\n frecuentes.  ";
        } else if(input.contains("que es una cuenta de ahorro?")) {
            return "Una cuenta de ahorro es\n un tipo de cuenta bancaria\n diseñada para que las personas\n puedan guardar su dinero de\n forma segura mientras generan\n intereses. ";
        } else if(input.contains("para que me sirve una cuenta corriente?")) {
            return "Una cuenta corriente es\n útil para gestionar tu dinero\n de manera ágil y práctica\n en tu vida diaria, ya\n que está diseñada para facilitar\n el acceso y las transacciones frecuentes.";
        } else if(input.contains("para que me sirve una cuenta de ahorro?")) {
            return "Una cuenta de ahorro es\n ideal para guardar dinero de\n forma segura y hacerlo crecer\n con el tiempo mediante intereses.";


        } else {
            return "Lo siento, no entiendo tu pregunta.";
        }
    }
}
