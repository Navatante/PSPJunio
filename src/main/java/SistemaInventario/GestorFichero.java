package SistemaInventario;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
import java.util.logging.Level;

public class GestorFichero {

    private static final String CLAVE = "1234567890123456";
    private static final String ALGORITMO = "AES";
    private static final String ARCHIVO_INVENTARIO = "inventario.txt";

    // Logger para registrar operaciones
    private static final Logger logger = Logger.getLogger(GestorFichero.class.getName());

    // Objeto para sincronización del archivo único
    private static final Object lockInventario = new Object();

    // Inventario inicial por defecto
    private static final String INVENTARIO_INICIAL = "Smartphones:50,Laptops:25,Tablets:30,Auriculares:100,Accesorios:200";

    private static byte[] cifrar(String datos) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITMO);
        SecretKeySpec keySpec = new SecretKeySpec(CLAVE.getBytes(), ALGORITMO);
        cipher.init(Cipher.ENCRYPT_MODE, keySpec);
        return cipher.doFinal(datos.getBytes());
    }

    private static String descifrar(byte[] datosCifrados) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITMO);
        SecretKeySpec keySpec = new SecretKeySpec(CLAVE.getBytes(), ALGORITMO);
        cipher.init(Cipher.DECRYPT_MODE, keySpec);
        byte[] bytesDescifrados = cipher.doFinal(datosCifrados);
        return new String(bytesDescifrados);
    }

    /**
     * Inicializa el archivo de inventario con valores por defecto si no existe
     */
    public static void inicializarInventario() {
        try {
            Path path = Paths.get(ARCHIVO_INVENTARIO);
            if (!Files.exists(path)) {
                logger.info("Inicializando archivo de inventario con valores por defecto");
                byte[] contenidoCifrado = cifrar(INVENTARIO_INICIAL);
                Files.write(path, contenidoCifrado, StandardOpenOption.CREATE);
                System.out.println("Inventario inicializado: " + INVENTARIO_INICIAL);
            }
        } catch (Exception e) {
            logger.severe("Error al inicializar inventario: " + e.getMessage());
            System.err.println("Error al inicializar inventario: " + e.getMessage());
        }
    }

    /**
     * Parsea el contenido del inventario desde formato CSV a Map
     */
    private static Map<String, Integer> parsearInventario(String contenido) {
        Map<String, Integer> inventario = new HashMap<>();
        if (contenido != null && !contenido.trim().isEmpty()) {
            String[] productos = contenido.split(",");
            for (String producto : productos) {
                String[] partes = producto.split(":");
                if (partes.length == 2) {
                    inventario.put(partes[0].trim(), Integer.parseInt(partes[1].trim()));
                }
            }
        }
        return inventario;
    }

    /**
     * Convierte el Map de inventario a formato CSV
     */
    private static String inventarioAString(Map<String, Integer> inventario) {
        StringBuilder sb = new StringBuilder();
        boolean primero = true;
        for (Map.Entry<String, Integer> entry : inventario.entrySet()) {
            if (!primero) {
                sb.append(",");
            }
            sb.append(entry.getKey()).append(":").append(entry.getValue());
            primero = false;
        }
        return sb.toString();
    }

    /**
     * Procesa una venta y actualiza el inventario
     * @param producto Tipo de producto vendido
     * @param cantidad Cantidad vendida
     * @return true si la venta fue exitosa, false si no hay suficiente stock
     */
    public static boolean procesarVenta(String producto, int cantidad) {
        try {
            synchronized (lockInventario) {
                logger.info("Iniciando procesamiento de venta: " + producto + " x" + cantidad);

                Path path = Paths.get(ARCHIVO_INVENTARIO);
                Map<String, Integer> inventario;

                // Leer inventario actual
                if (Files.exists(path) && Files.size(path) > 0) {
                    byte[] contenidoCifrado = Files.readAllBytes(path);
                    String contenidoDescifrado = descifrar(contenidoCifrado);
                    inventario = parsearInventario(contenidoDescifrado);
                    logger.fine("Inventario leído: " + contenidoDescifrado);
                } else {
                    logger.warning("Archivo de inventario no encontrado, usando valores por defecto");
                    inventario = parsearInventario(INVENTARIO_INICIAL);
                }

                // Verificar si el producto existe y hay suficiente stock
                if (!inventario.containsKey(producto)) {
                    logger.warning("Producto no encontrado: " + producto);
                    return false;
                }

                int stockActual = inventario.get(producto);
                if (stockActual < cantidad) {
                    logger.warning("Stock insuficiente para " + producto + ". Stock actual: " + stockActual + ", solicitado: " + cantidad);
                    return false;
                }

                // Actualizar inventario
                int nuevoStock = stockActual - cantidad;
                inventario.put(producto, nuevoStock);

                // Guardar inventario actualizado
                String nuevoContenido = inventarioAString(inventario);
                byte[] contenidoCifrado = cifrar(nuevoContenido);
                Files.write(path, contenidoCifrado, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);

                // Mostrar por consola para verificación
                System.out.println("Venta procesada - Inventario actualizado: " + nuevoContenido);
                logger.info("Venta exitosa: " + producto + " x" + cantidad + ". Nuevo stock: " + nuevoStock);

                return true;
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error al procesar venta", e);
            System.err.println("Error al procesar venta: " + e.getMessage());
            return false;
        }
    }

    /**
     * Obtiene el inventario actual para mostrar en la página de consulta
     * @return Map con el inventario actual o null si hay error
     */
    public static Map<String, Integer> obtenerInventario() {
        try {
            synchronized (lockInventario) {
                logger.fine("Consultando inventario actual");

                Path path = Paths.get(ARCHIVO_INVENTARIO);

                if (Files.exists(path) && Files.size(path) > 0) {
                    byte[] contenidoCifrado = Files.readAllBytes(path);
                    String contenidoDescifrado = descifrar(contenidoCifrado);
                    Map<String, Integer> inventario = parsearInventario(contenidoDescifrado);

                    logger.fine("Inventario consultado: " + contenidoDescifrado);
                    System.out.println("Consulta de inventario: " + contenidoDescifrado);

                    return inventario;
                } else {
                    logger.warning("Archivo de inventario no encontrado para consulta");
                    return parsearInventario(INVENTARIO_INICIAL);
                }
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error al consultar inventario", e);
            System.err.println("Error al consultar inventario: " + e.getMessage());
            return null;
        }
    }

    /**
     * Obtiene el stock de un producto específico
     * @param producto Nombre del producto
     * @return Stock actual del producto, -1 si no existe o hay error
     */
    public static int obtenerStock(String producto) {
        Map<String, Integer> inventario = obtenerInventario();
        if (inventario != null && inventario.containsKey(producto)) {
            return inventario.get(producto);
        }
        return -1;
    }
}
