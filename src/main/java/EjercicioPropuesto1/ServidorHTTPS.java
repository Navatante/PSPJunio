package EjercicioPropuesto1;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;
import java.security.KeyStore;

/**
 * Servidor HTTPS para el sistema de ventas de FarmaciaViva.
 *
 * Rutas disponibles:
 * - / : Página principal con formulario de venta y visualización de stock
 * - Cualquier otra ruta: Error 404
 */
public class ServidorHTTPS {

    // Puerto HTTPS estándar
    private static final int PUERTO = 8443;

    // Configuración del KeyStore
    private static final String KEYSTORE_FILE = "AlmacenSSL";
    private static final String KEYSTORE_PASSWORD = "123456";

    /**
     * Metodo principal que inicia el servidor HTTPS.
     */
    public static void main(String[] args) {
        System.out.println("=== Iniciando Servidor FarmaciaViva ===");

        // Probar el sistema de logs
        GestorLogs.probarLog();

        // Inicializar el archivo de stock
        GestorStock.inicializarStock();

        try {
            // Configurar el contexto SSL
            SSLServerSocket serverSocket = configurarSSL();

            System.out.println("Servidor HTTPS de FarmaciaViva iniciado en el puerto " + PUERTO);
            System.out.println("Visita https://localhost:" + PUERTO);

            // Registrar inicio en logs
            GestorLogs.registrarInicioSistema();

            // Bucle infinito para aceptar conexiones
            while (true) {
                SSLSocket clienteSocket = (SSLSocket) serverSocket.accept();
                Thread hiloServidor = new HiloServidorFarmacia(clienteSocket);
                hiloServidor.start();
            }

        } catch (Exception e) {
            System.err.println("Error al iniciar el servidor: " + e.getMessage());
            e.printStackTrace();
            GestorLogs.registrarError("SERVIDOR", "Error al iniciar servidor", "N/A", e.getMessage());
        }
    }

    /**
     * Configura el contexto SSL para el servidor.
     *
     * @return SSLServerSocket configurado
     * @throws Exception Si ocurre un error en la configuración
     */
    private static SSLServerSocket configurarSSL() throws Exception {
        // Cargar el KeyStore
        KeyStore keyStore = KeyStore.getInstance("JKS");
        try (FileInputStream keyStoreFile = new FileInputStream(KEYSTORE_FILE)) {
            keyStore.load(keyStoreFile, KEYSTORE_PASSWORD.toCharArray());
        }

        // Configurar el gestor de claves
        KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance("SunX509");
        keyManagerFactory.init(keyStore, KEYSTORE_PASSWORD.toCharArray());

        // Crear e inicializar el contexto SSL
        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(keyManagerFactory.getKeyManagers(), null, null);

        // Crear la fábrica de sockets SSL
        SSLServerSocketFactory factory = sslContext.getServerSocketFactory();

        // Crear y devolver el socket del servidor SSL
        return (SSLServerSocket) factory.createServerSocket(PUERTO);
    }

    /**
     * Clase interna que maneja cada conexión de cliente en un hilo separado.
     */
    private static class HiloServidorFarmacia extends Thread {
        private final SSLSocket cliente;

        public HiloServidorFarmacia(SSLSocket cliente) {
            this.cliente = cliente;
        }

        @Override
        public void run() {
            try (
                    BufferedReader entrada = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
                    PrintWriter salida = new PrintWriter(cliente.getOutputStream(), true)
            ) {
                // Leer la primera línea de la petición HTTP
                String peticion = entrada.readLine();
                if (peticion == null || (!peticion.startsWith("GET") && !peticion.startsWith("POST"))) {
                    return; // Ignorar peticiones que no sean GET o POST
                }

                System.out.println("Petición recibida: " + peticion);
                String[] partesPeticion = peticion.split(" ");
                String metodo = partesPeticion[0];
                String ruta = partesPeticion[1];

                // Obtener Content-Length para peticiones POST
                int contentLength = obtenerContentLength(entrada);

                // Leer el cuerpo si es un POST
                String cuerpo = "";
                if ("POST".equals(metodo) && contentLength > 0) {
                    char[] buffer = new char[contentLength];
                    entrada.read(buffer, 0, contentLength);
                    cuerpo = new String(buffer);
                    System.out.println("Cuerpo POST recibido: " + cuerpo);
                }

                // Procesar la petición según la ruta
                String respuesta;
                if ("/".equals(ruta)) {
                    respuesta = manejarPaginaPrincipal(metodo, cuerpo);
                } else {
                    // Cualquier otra ruta devuelve 404
                    respuesta = construirRespuesta(404, PaginasFarmacia.html_404);
                    System.out.println("Ruta no encontrada: " + ruta);
                }

                // Enviar la respuesta al cliente
                salida.println(respuesta);

            } catch (Exception e) {
                System.err.println("Error al procesar la petición: " + e.getMessage());
                e.printStackTrace();
                GestorLogs.registrarError("PETICION", "Error al procesar petición", "N/A", e.getMessage());
            } finally {
                try {
                    cliente.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        /**
         * Obtiene el Content-Length de los headers HTTP.
         *
         * @param entrada BufferedReader con la entrada del cliente
         * @return Tamaño del contenido o 0 si no se especifica
         * @throws IOException Si ocurre un error de lectura
         */
        private int obtenerContentLength(BufferedReader entrada) throws IOException {
            String linea;
            int contentLength = 0;

            while (!(linea = entrada.readLine()).isBlank()) {
                System.out.println("Header: " + linea);
                if (linea.startsWith("Content-Length: ")) {
                    contentLength = Integer.parseInt(linea.substring(16));
                }
            }

            return contentLength;
        }

        /**
         * Maneja la página principal de la farmacia.
         *
         * @param metodo Metodo HTTP (GET o POST)
         * @param cuerpo Cuerpo de la petición (si es POST)
         * @return Respuesta HTTP completa
         */
        private String manejarPaginaPrincipal(String metodo, String cuerpo) {
            try {
                String mensaje = "";

                // Si es POST, procesar la venta
                if ("POST".equals(metodo) && !cuerpo.isEmpty()) {
                    mensaje = procesarVenta(cuerpo);
                }

                // Leer el stock actual y generar la página
                Map<String, Integer> stock = GestorStock.leerStock();
                String stockHTML = PaginasFarmacia.generarStockHTML(
                        stock.getOrDefault("Paracetamol", 0),
                        stock.getOrDefault("Ibuprofeno", 0),
                        stock.getOrDefault("Vitamina C", 0)
                );

                String paginaCompleta = PaginasFarmacia.generarPaginaPrincipal(mensaje, stockHTML);
                return construirRespuesta(200, paginaCompleta);

            } catch (Exception e) {
                System.err.println("Error al manejar página principal: " + e.getMessage());
                e.printStackTrace();
                GestorLogs.registrarError("PAGINA_PRINCIPAL", "Error al generar página", "N/A", e.getMessage());
                return construirRespuesta(500, PaginasFarmacia.html_500);
            }
        }

        /**
         * Procesa una venta desde el formulario.
         *
         * @param cuerpo Cuerpo de la petición POST
         * @return Mensaje de resultado de la venta
         */
        private String procesarVenta(String cuerpo) {
            try {
                Map<String, String> parametros = parsearParametros(cuerpo);
                String producto = parametros.get("producto");
                String cantidadStr = parametros.get("cantidad");

                System.out.println("Procesando venta - Producto: " + producto + ", Cantidad: " + cantidadStr);

                // Validar parámetros
                if (producto == null || producto.trim().isEmpty()) {
                    GestorLogs.registrarError("VENTA", "Producto no especificado", "null", cuerpo);
                    return "Error: Debe seleccionar un producto.";
                }

                if (cantidadStr == null || cantidadStr.trim().isEmpty()) {
                    GestorLogs.registrarError("VENTA", "Cantidad no especificada", producto, cuerpo);
                    return "Error: Debe especificar una cantidad.";
                }

                // Validar que el producto sea válido
                if (!GestorStock.esProductoValido(producto)) {
                    GestorLogs.registrarError("VENTA", "Producto no válido", producto, cantidadStr);
                    return "Error: El producto seleccionado no es válido.";
                }

                // Validar cantidad
                int cantidad;
                try {
                    cantidad = Integer.parseInt(cantidadStr);
                    if (cantidad <= 0) {
                        throw new NumberFormatException("Cantidad debe ser mayor a 0");
                    }
                    if (cantidad > 50) {
                        throw new NumberFormatException("Cantidad no puede ser mayor a 50");
                    }
                } catch (NumberFormatException e) {
                    GestorLogs.registrarError("VENTA", "Cantidad inválida", producto, cantidadStr);
                    return "Error: La cantidad debe ser un número entero entre 1 y 50.";
                }

                // Intentar procesar la venta
                boolean ventaExitosa = GestorStock.procesarVenta(producto, cantidad);

                if (ventaExitosa) {
                    // Obtener stock restante para el log
                    int stockRestante = GestorStock.obtenerStockProducto(producto);
                    GestorLogs.registrarVentaExitosa(producto, cantidad, stockRestante);

                    return String.format("✅ Venta procesada exitosamente: %d unidades de %s.", cantidad, producto);
                } else {
                    // Obtener stock actual para mostrar en el error
                    int stockActual = GestorStock.obtenerStockProducto(producto);
                    GestorLogs.registrarStockInsuficiente(producto, cantidad, stockActual);

                    return String.format("❌ Stock insuficiente para el producto %s. Stock actual: %d unidades.", producto, stockActual);
                }

            } catch (Exception e) {
                System.err.println("Error al procesar venta: " + e.getMessage());
                e.printStackTrace();
                GestorLogs.registrarError("VENTA", "Error inesperado al procesar venta", "N/A", e.getMessage());
                return "Error: Ocurrió un problema al procesar la venta. Intente nuevamente.";
            }
        }

        /**
         * Parsea los parámetros de un cuerpo de petición POST.
         *
         * @param cuerpo Cuerpo de la petición
         * @return Mapa con los parámetros
         */
        private Map<String, String> parsearParametros(String cuerpo) {
            Map<String, String> parametros = new HashMap<>();
            if (cuerpo != null && !cuerpo.isEmpty()) {
                String[] pares = cuerpo.split("&");
                for (String par : pares) {
                    String[] partes = par.split("=");
                    if (partes.length == 2) {
                        try {
                            String clave = java.net.URLDecoder.decode(partes[0], StandardCharsets.UTF_8);
                            String valor = java.net.URLDecoder.decode(partes[1], StandardCharsets.UTF_8);
                            parametros.put(clave, valor);
                        } catch (Exception e) {
                            System.err.println("Error decodificando parámetros: " + e.getMessage());
                            GestorLogs.registrarError("PARSEO", "Error al decodificar parámetros", "N/A", par);
                        }
                    }
                }
            }
            return parametros;
        }

        /**
         * Construye una respuesta HTTP con un código de estado y contenido.
         *
         * @param codigo Código de estado HTTP
         * @param contenido Contenido del cuerpo de la respuesta
         * @return Respuesta HTTP completa
         */
        private String construirRespuesta(int codigo, String contenido) {
            String estadoHttp;
            switch (codigo) {
                case 200: estadoHttp = "200 OK"; break;
                case 404: estadoHttp = "404 Not Found"; break;
                case 500: estadoHttp = "500 Internal Server Error"; break;
                default: estadoHttp = "200 OK";
            }

            StringBuilder respuesta = new StringBuilder();
            respuesta.append("HTTP/1.1 ").append(estadoHttp).append("\r\n");
            respuesta.append("Content-Type: text/html; charset=UTF-8\r\n");
            respuesta.append("Content-Length: ").append(contenido.getBytes(StandardCharsets.UTF_8).length).append("\r\n");
            respuesta.append("Cache-Control: no-cache\r\n"); // Evitar caché para mostrar stock actualizado
            respuesta.append("Connection: close\r\n");
            respuesta.append("\r\n");
            respuesta.append(contenido);

            return respuesta.toString();
        }
    }
}
