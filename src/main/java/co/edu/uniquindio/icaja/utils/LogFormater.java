package co.edu.uniquindio.icaja.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public class LogFormater extends Formatter {
        @Override
        public String format(LogRecord record) {
            // Obtener la fecha y la hora formateadas
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String fecha = now.format(dateFormatter);

            // Formato del log deseado
            return String.format("%s [%s]: %s%n",
                    record.getLevel(),
                    fecha,
                    record.getMessage());
        }
}
