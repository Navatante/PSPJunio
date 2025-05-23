package EjercicioPropuesto1;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Clase que gestiona el stock de productos de la farmacia.
 * Implementa sincronización básica con synchronized para evitar condiciones de carrera.
 * La lectura no se sincroniza para permitir múltiples lecturas concurrentes,
 * pero la escritura sí se sincroniza para evitar problemas de concurrencia.
 */
public class GestorStock {

    private static final String ARCHIVO_STOCK = "stock.txt";

    // Productos válidos de la farmacia
    private static final String[] PRODUCTOS_VALIDOS = {"Paracetamol", "Ibuprofeno", "Vitamina C"};

    /**
     * Inicializa el archivo de stock si no existe.
     */
    public static void inicializarStock() {
        File archivo = new File(ARCHIVO_STOCK);
        if (!archivo.exists()) {
            try {
                System.out.println("Creando archivo de stock inicial...");
                try (PrintWriter writer = new PrintWriter(new FileWriter(ARCHIVO_STOCK))) {
                    writer.println("Paracetamol:10");
                    writer.println("Ibuprofeno:8");
                    writer.println("Vitamina C:12");
                }
                System.out.println("Archivo de stock creado correctamente");
            } catch (IOException e) {
                System.err.println("Error al crear archivo de stock: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    /**
     * Lee el stock actual de todos los productos.
     * No se sincroniza para permitir múltiples lecturas concurrentes.
     *
     * @return Mapa con el stock de cada producto
     * @throws Exception Si ocurre un error en la lectura
     */
    public static Map<String, Integer> leerStock() throws Exception {
        ConcurrentHashMap<String, Integer> stock = new ConcurrentHashMap<>();

        if (!Files.exists(Paths.get(ARCHIVO_STOCK))) {
            // Si no existe, devolver stock inicial
            stock.put("Paracetamol", 10);
            stock.put("Ibuprofeno", 8);
            stock.put("Vitamina C", 12);
            return stock;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(ARCHIVO_STOCK))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                if (!linea.trim().isEmpty()) {
                    String[] partes = linea.split(":");
                    if (partes.length == 2) {
                        String producto = partes[0].trim();
                        int cantidad = Integer.parseInt(partes[1].trim());
                        stock.put(producto, cantidad);
                    }
                }
            }
        }

        System.out.println("Stock leído correctamente: " + stock);
        return stock;
    }

    /**
     * Procesa una venta de producto.
     * Se sincroniza todo el metodo para evitar condiciones de carrera durante la escritura.
     *
     * @param producto Nombre del producto
     * @param cantidad Cantidad a vender
     * @return true si la venta fue exitosa, false si no hay stock suficiente
     * @throws Exception Si ocurre un error en el proceso
     */
    public static synchronized boolean procesarVenta(String producto, int cantidad) throws Exception {
        System.out.println("Procesando venta: " + producto + " - Cantidad: " + cantidad);

        // Validar que el producto existe
        if (!esProductoValido(producto)) {
            System.err.println("Producto no válido: " + producto);
            return false;
        }

        // Leer stock actual
        Map<String, Integer> stock = leerStockSinImprimir();

        // Verificar si hay stock suficiente
        int stockActual = stock.getOrDefault(producto, 0);
        if (stockActual < cantidad) {
            System.out.println("Stock insuficiente para " + producto + ". Stock actual: " + stockActual + ", solicitado: " + cantidad);
            return false;
        }

        // Actualizar el stock
        stock.put(producto, stockActual - cantidad);

        // Escribir el archivo actualizado
        escribirStock(stock);

        System.out.println("Venta procesada exitosamente. Nuevo stock de " + producto + ": " + stock.get(producto));
        return true;
    }

    /**
     * Lee el stock sin imprimir mensajes (metodo auxiliar interno).
     */
    private static Map<String, Integer> leerStockSinImprimir() throws Exception {
        Map<String, Integer> stock = new HashMap<>();

        if (!Files.exists(Paths.get(ARCHIVO_STOCK))) {
            // Si no existe, devolver stock inicial
            stock.put("Paracetamol", 10);
            stock.put("Ibuprofeno", 8);
            stock.put("Vitamina C", 12);
            return stock;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(ARCHIVO_STOCK))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                if (!linea.trim().isEmpty()) {
                    String[] partes = linea.split(":");
                    if (partes.length == 2) {
                        String producto = partes[0].trim();
                        int cantidad = Integer.parseInt(partes[1].trim());
                        stock.put(producto, cantidad);
                    }
                }
            }
        }

        return stock;
    }

    /**
     * Escribe el stock al archivo (metodo auxiliar interno).
     */
    private static void escribirStock(Map<String, Integer> stock) throws Exception {
        try (PrintWriter writer = new PrintWriter(new FileWriter(ARCHIVO_STOCK))) {
            for (String producto : PRODUCTOS_VALIDOS) {
                int cantidad = stock.getOrDefault(producto, 0);
                writer.println(producto + ":" + cantidad);
            }
        }
    }

    /**
     * Actualiza el stock completo.
     * Se sincroniza para evitar escrituras concurrentes.
     *
     * @param nuevoStock Nuevo stock a establecer
     * @throws Exception Si ocurre un error en la escritura
     */
    public static synchronized void actualizarStock(Map<String, Integer> nuevoStock) throws Exception {
        escribirStock(nuevoStock);
        System.out.println("Stock actualizado: " + nuevoStock);
    }

    /**
     * Valida si un producto es válido.
     *
     * @param producto Nombre del producto a validar
     * @return true si el producto es válido
     */
    public static boolean esProductoValido(String producto) {
        if (producto == null || producto.trim().isEmpty()) {
            return false;
        }

        for (String productoValido : PRODUCTOS_VALIDOS) {
            if (productoValido.equals(producto.trim())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Obtiene el stock de un producto específico.
     *
     * @param producto Nombre del producto
     * @return Cantidad en stock, -1 si el producto no existe
     */
    public static int obtenerStockProducto(String producto) {
        if (!esProductoValido(producto)) {
            return -1;
        }

        try {
            Map<String, Integer> stock = leerStock();
            return stock.getOrDefault(producto, 0);
        } catch (Exception e) {
            System.err.println("Error al obtener stock del producto " + producto + ": " + e.getMessage());
            return -1;
        }
    }

    /**
     * Metodo para testing: permite establecer stock específico.
     * Solo debe usarse en pruebas.
     */
    public static void establecerStockTesting(Map<String, Integer> nuevoStock) {
        try {
            actualizarStock(nuevoStock);
            System.out.println("Stock establecido para testing: " + nuevoStock);
        } catch (Exception e) {
            System.err.println("Error al establecer stock para testing: " + e.getMessage());
        }
    }
}
