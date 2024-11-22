package co.edu.uniquindio.icaja.model;

import java.io.Serializable;

public class ChatBot implements Serializable {
    public static final long serialVersionID = 10L;
    //Logica interna para el chatBot
    public String[] procesarEntrada(String input, String contexto) {
        input = input.toLowerCase(); // Normaliza la entrada
        try {
            int opcion = Integer.parseInt(input);
            switch (contexto) {
                //Preguntas generales de la app, como crear presupuestos, como realizar depositos, etc
                case "inicial":  //contextos que llevan al listado de preguntas de cada una por separado, se debe crear un metodo por cada contexto
                    return inicial(opcion);
                    //Preguntas sobre el porque de crear presupuestos, pa que sirve una cuenta corriente, una de ahorro y esas
                case "estrategias":
                    return  estrategias(opcion);
                //Vuelve al contexto que estaba antes
                case"volver":
                    return volver(opcion);
                default:
                    return volver(opcion);
            }
        } catch (NumberFormatException e) {
            return new String[]{"Inicial","Selecciona una opción valida"};
        }
    }

    private String[] inicial(int opcion){
        switch (opcion) {
            //como realizar presupuestos?
            case 1:
                return new String[] {"opcion 1","Para la creación de un presupuesto deberás dirigirte al apartado de Cuentas Bancarias y buscar la opción que dice Ajustar presupuestos desde el menú de inicio."};
                //cómo generar mi reporte financiero?
            case 2:
                return new String[] {"opcion 2","Para la creación de un reporte financiero deberás dirigirte al apartado de Cuentas Bancarias, en donde buscarás la opción de Generar Reporte Financiero, luego de eso automaticamente podrás ver dicho reporte"};
                //cómo puedo cambiar mi contraseña de ingreso?
            case 3:
                return new String[] {"opcion 3","Para cambiar la contraseña de ingreso deberás al apartado de Perfil de Usuario y una vez allí, podrás diligenciar los cambios que requieras referente a tu perfil, como el Nombre, Telefono, Cédula e incluso el correo electrónico."};
                //Cómo puedo actualizar datos de mi perfil?
            case 4:
                return new String[] {"opcion 4","Para actualizar tus datos en la app, deberás dirigirte al apartado de perfil ubicada en la parte baja de la misma "};
            //Volver a inicial
            default:
                return new String[] {"volver","Resultado por defecto    "};

        }

    }
    private String[] volver(int opcion){
        switch (opcion) {
            case 1:  //contexto 1,
                return new String[] {"inicial","Digite 1: como realizar presupuestos?, Digite 2: como generar mi reporte financiero?, Digite 3: como puedo cambiar mi contraseña de ingreso?  "};
            case 2:
                return new String[] {"estrategias","Volviendo a estrategias "};
            default:
                return new String[] {"volver","volver al contexto anterior"};

        }
    }
    private String[] estrategias(int opcion){
        switch (opcion) {
            case 1:  //contexto 1,como puedo administrar mi salario de una forma ordenada
                return new String[] {"inicial","Como una medida de control podrías crear diferentes presupuestos para asi tener un mayor nivel de gestión con tus gastos y tu dinero"};
            case 2: // contexto 2, Para que me sirve tener presupuestos?
                return new String[] {"inicial","Tener presupuestos es una herramienta poderosa para gestionar tus finanzas de manera eficiente, ya que te ayudan a planificar, controlar tus gastos y alcanzar tus metas financieras. "};
            case 3: // contexto 3, que es una cuenta corriente?
                return new String[] {"inicial","Una cuenta corriente es un tipo de cuenta bancaria diseñada principalmente para gestionar el dinero de manera cómoda y realizar transacciones frecuentes. "};
            case 4: // contexto 4, que es una cuenta Ahorro?
                return new String[] {"inicial","Una cuenta de ahorro es un tipo de cuenta bancaria diseñada para que las personas puedan guardar su dinero de forma segura mientras generan intereses."};
            default: // contexto, volver a un llamado anterior
                return new String[] {"volver","volviendo"};

        }
    }


}
