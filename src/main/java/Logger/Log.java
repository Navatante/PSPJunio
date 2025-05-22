package Logger;

import java.io.IOException;
import java.util.logging.*;

public class Log {

    private static final Logger logger = Logger.getLogger("Mylog");

    public static void main(String[] args) throws IOException {
        /////////////////////////////
        /// CONFIGURACION LOGGER ///
        ////////////////////////////

        // Crear y configurar el manejador de archivos
        FileHandler fileHandler = new FileHandler("log.txt", true);
        fileHandler.setFormatter(new SimpleFormatter());
        logger.addHandler(fileHandler);
    }
}
