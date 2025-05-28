package HotelComentarios;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class GestorFichero {

    private static final String CLAVE = "1234567890123456";
    private static final String ALGORITMO = "AES";
    private static final String NOMBRE_ARCHIVO = "comentarios_hotel.txt";
    private static final Logger logger = Logger.getLogger("logHotelComentarios");

    private static final Object ObjFichero = new Object();

    // Configurar el logger para escribir en archivo
    static {
        try {
            // Crear FileHandler que escribirá en "logHotelComentarios.log"
            FileHandler fileHandler = new FileHandler("logHotelComentarios.log", true); // true = append mode
            fileHandler.setFormatter(new SimpleFormatter()); // Formato simple y legible

            // Agregar el handler al logger
            logger.addHandler(fileHandler);
            logger.setLevel(Level.ALL); // Capturar todos los niveles de log

            // Opcional: Evitar que los mensajes aparezcan también en consola
            // logger.setUseParentHandlers(false);

        } catch (IOException e) {
            System.err.println("Error al configurar el archivo de log: " + e.getMessage());
        }
    }

    private static byte[] cifrar(String datos) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITMO);
        SecretKeySpec keySpec = new SecretKeySpec(CLAVE.getBytes(), ALGORITMO);
        cipher.init(Cipher.ENCRYPT_MODE, keySpec);
        return cipher.doFinal(datos.getBytes("UTF-8"));
    }

    private static String descifrar(byte[] datosCifrados) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITMO);
        SecretKeySpec keySpec = new SecretKeySpec(CLAVE.getBytes(), ALGORITMO);
        cipher.init(Cipher.DECRYPT_MODE, keySpec);
        byte[] bytesDescifrados = cipher.doFinal(datosCifrados);
        return new String(bytesDescifrados, "UTF-8");
    }

    public static void actualizarFichero(String tipo, String valoracion, String comentario) {
        synchronized (ObjFichero) {
            try {
                logger.info("Iniciando actualización del archivo de comentarios");

                Path path = Paths.get(NOMBRE_ARCHIVO);
                String contenidoActual = "";

                // Leer contenido existente si el archivo existe
                if (Files.exists(path) && Files.size(path) > 0) {
                    logger.info("Leyendo archivo existente");
                    byte[] contenidoCifrado = Files.readAllBytes(path);
                    contenidoActual = descifrar(contenidoCifrado);
                    logger.info("Archivo descifrado correctamente");
                } else {
                    logger.info("Archivo no existe, se creará uno nuevo");
                }

                // Generar timestamp
                LocalDateTime ahora = LocalDateTime.now();
                DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                String timestamp = ahora.format(formato);

                // Crear nueva línea con el formato especificado
                String nuevaLinea = String.format("%s -- %s -- %s/5 -- %s%n",
                        timestamp, tipo, valoracion, comentario);

                // Agregar la nueva línea al contenido existente
                contenidoActual += nuevaLinea;

                // Mostrar contenido completo descifrado
                System.out.println("=== CONTENIDO COMPLETO DEL ARCHIVO (DESCIFRADO) ===");
                System.out.println(contenidoActual);
                System.out.println("=== FIN DEL CONTENIDO ===");

                // Cifrar el contenido
                byte[] contenidoCifrado = cifrar(contenidoActual);
                logger.info("Contenido cifrado correctamente");

                // Escribir el archivo completo (no usar APPEND con cifrado)
                // StandardOpenOption.TRUNCATE_EXISTING:
                // Si el archivo ya existe, borra todo su contenido anterior antes de escribir el nuevo.
                Files.write(path, contenidoCifrado, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);

                logger.info("Nuevo comentario agregado correctamente: " + tipo + " - " + valoracion + "/5");
                logger.info("Acceso concurrente gestionado correctamente");

            } catch (Exception e) {
                logger.log(Level.SEVERE, "Error al actualizar el archivo: " + e.getMessage(), e);
                System.err.println("Error al actualizar fichero: " + e.getMessage());
            }
        }
    }
}