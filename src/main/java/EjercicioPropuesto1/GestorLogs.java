package EjercicioPropuesto1;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.logging.Level;

/**
 * Clase que gestiona el registro de eventos y errores de la farmacia.
 * No requiere sincronización manual ya que Logger maneja la concurrencia internamente.
 */
public class GestorLogs {

    private static final Logger logger = Logger.getLogger("logFarmacia");
    private static final String FORMATO_FECHA = "yyyy/MM/dd HH:mm:ss";
    private static FileHandler fileHandler = null;
    private static boolean inicializado = false;

    /**
     * Inicializa el gestor de logs
     */
    private static void inicializar() {
        if (inicializado) {
            return;
        }

        try {
            System.out.println("Inicializando GestorLogsFarmacia...");
            System.out.println("Directorio actual: " + System.getProperty("user.dir"));

            // Crear el FileHandler con append=true para añadir al archivo existente
            fileHandler = new FileHandler("logFarmacia.log", true);

            // Configurar el formatter personalizado
            fileHandler.setFormatter(new Formatter() {
                @Override
                public String format(LogRecord record) {
                    String fechaHora = LocalDateTime.now().format(DateTimeFormatter.ofPattern(FORMATO_FECHA));
                    return String.format("%s - %s%n", fechaHora, record.getMessage());
                }
            });

            // Configurar el logger
            logger.addHandler(fileHandler);
            logger.setUseParentHandlers(false); // Evitar salida duplicada en consola
            logger.setLevel(Level.ALL);

            // Añadir un shutdown hook para cerrar el fileHandler al finalizar la aplicación
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                if (fileHandler != null) {
                    fileHandler.close();
                }
            }));

            inicializado = true;
            System.out.println("GestorLogsFarmacia inicializado correctamente");

            // Escribir mensaje de inicio
            logger.log(Level.INFO, "Sistema de logs de farmacia inicializado correctamente");

        } catch (IOException e) {
            System.err.println("Error al inicializar GestorLogsFarmacia: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Error inesperado al inicializar GestorLogsFarmacia: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Registra un error en la operación de venta.
     * No requiere sincronización manual - Logger la maneja internamente.
     *
     * @param tipoOperacion Tipo de operación (VENTA, LECTURA_STOCK, etc.)
     * @param mensajeError Mensaje descriptivo del error
     * @param producto Producto involucrado en el error
     * @param valorRecibido Valor que causó el error
     */
    public static void registrarError(String tipoOperacion, String mensajeError, String producto, String valorRecibido) {
        // Asegurar que el gestor está inicializado
        if (!inicializado) {
            inicializar();
        }

        // Si la inicialización falló, intentar escribir en consola como fallback
        if (!inicializado || fileHandler == null) {
            System.err.println("ADVERTENCIA: No se pudo inicializar el sistema de logs. Error en consola:");
            String fechaHora = LocalDateTime.now().format(DateTimeFormatter.ofPattern(FORMATO_FECHA));
            String mensaje = String.format("%s - Error en %s: %s. Producto: %s, Valor recibido: %s",
                    fechaHora, tipoOperacion, mensajeError, producto, valorRecibido);
            System.err.println(mensaje);
            return;
        }

        try {
            String mensaje = String.format("Error en %s: %s. Producto: %s, Valor recibido: %s",
                    tipoOperacion, mensajeError, producto, valorRecibido);

            // Registrar el error en el logger (Logger maneja la sincronización)
            logger.log(Level.SEVERE, mensaje);

            // Forzar el flush para asegurar que se escriba inmediatamente
            fileHandler.flush();

            System.out.println("Error registrado en log: " + mensaje);

        } catch (Exception e) {
            System.err.println("Error al escribir en el log: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Registra una venta exitosa.
     *
     * @param producto Producto vendido
     * @param cantidad Cantidad vendida
     * @param stockRestante Stock restante después de la venta
     */
    public static void registrarVentaExitosa(String producto, int cantidad, int stockRestante) {
        if (!inicializado) {
            inicializar();
        }

        if (inicializado && fileHandler != null) {
            try {
                String mensaje = String.format("Venta exitosa: %s - Cantidad: %d - Stock restante: %d",
                        producto, cantidad, stockRestante);

                logger.log(Level.INFO, mensaje);
                fileHandler.flush();

                System.out.println("Venta registrada en log: " + mensaje);

            } catch (Exception e) {
                System.err.println("Error al registrar venta en log: " + e.getMessage());
            }
        }
    }

    /**
     * Registra un intento de venta con stock insuficiente.
     *
     * @param producto Producto solicitado
     * @param cantidadSolicitada Cantidad solicitada
     * @param stockDisponible Stock disponible
     */
    public static void registrarStockInsuficiente(String producto, int cantidadSolicitada, int stockDisponible) {
        if (!inicializado) {
            inicializar();
        }

        if (inicializado && fileHandler != null) {
            try {
                String mensaje = String.format("Stock insuficiente: %s - Solicitado: %d - Disponible: %d",
                        producto, cantidadSolicitada, stockDisponible);

                logger.log(Level.WARNING, mensaje);
                fileHandler.flush();

                System.out.println("Stock insuficiente registrado en log: " + mensaje);

            } catch (Exception e) {
                System.err.println("Error al registrar stock insuficiente en log: " + e.getMessage());
            }
        }
    }

    /**
     * Registra el inicio del sistema.
     */
    public static void registrarInicioSistema() {
        if (!inicializado) {
            inicializar();
        }

        if (inicializado && fileHandler != null) {
            try {
                String mensaje = "Sistema de farmacia iniciado - Puerto 8443";
                logger.log(Level.INFO, mensaje);
                fileHandler.flush();
                System.out.println("Inicio del sistema registrado en log");
            } catch (Exception e) {
                System.err.println("Error al registrar inicio del sistema: " + e.getMessage());
            }
        }
    }

    /**
     * Metodo para probar si el sistema de logs funciona correctamente
     */
    public static void probarLog() {
        registrarError("TEST", "Prueba del sistema de logs de farmacia", "Paracetamol", "valor_prueba");
    }
}