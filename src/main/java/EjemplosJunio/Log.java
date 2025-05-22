package EjemplosJunio;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.*;

public class Log {

    private static final Logger logger = Logger.getLogger("Mylog");

    public static void main(String[] args) throws IOException {
        /////////////////////////////
        /// CONFIGURACION LOGGER ///
        ////////////////////////////

        // Crear y configurar el manejador de archivos
        FileHandler fileHandler = new FileHandler("log.txt", true);

        // Formato por defecto
        //fileHandler.setFormatter(new SimpleFormatter());

        // Formato personalizado
        fileHandler.setFormatter(new Formatter() {
            @Override
            public String format(LogRecord record) {
                String fechaHora = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
                return String.format("(%s): %s%n", fechaHora, record.getMessage());
            }
        });


        // Asignar el manejador al logger
        logger.addHandler(fileHandler);

        // Activar mensajes por consola
        logger.setUseParentHandlers(true);

        // Establecer el nivel de registro
        logger.setLevel(Level.ALL);

        /////////////////////////////
        ////// USO DEL LOGGER //////
        ////////////////////////////

        // Mostrar mensajes
        logger.log(Level.INFO, "Aplicación iniciada");

        try {
            int resultado = 10 / 0; // Esto generará una excepción
            logger.log(Level.INFO, "Resultado: " + resultado);
        } catch (ArithmeticException e) {
            logger.log(Level.SEVERE, "Error: División por cero", e);
        }

        // Tipos de mensajes
        logger.log(Level.FINE, "Mensaje de nivel FINE");
        logger.log(Level.FINER, "Mensaje de nivel FINER");
        logger.log(Level.FINEST, "Mensaje de nivel FINEST");
        logger.log(Level.CONFIG, "Mensaje de nivel CONFIG");
        logger.log(Level.WARNING, "Mensaje de nivel WARNING");
        logger.log(Level.SEVERE, "Mensaje de nivel SEVERE");
        logger.log(Level.INFO, "Mensaje de nivel INFO");
        logger.log(Level.ALL, "Mensaje de nivel ALL");
        logger.log(Level.OFF, "Mensaje de nivel OFF");

        logger.log(Level.INFO, "Aplicación finalizada");



        // Con BCrypt se cifra la contrasena y con AES se cifra el archivo
    }
}
