package SistemaInventario;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServidorHTTP {

    private static final int PUERTO = 8443;
    private static final Logger logger = Logger.getLogger("InventarioLog");

    public static void main(String[] args) throws Exception {

        // Inicializar el archivo de inventario si no existe
        GestorFichero.inicializarInventario();

        ServerSocket serverSocket = new ServerSocket(PUERTO);
        logger.info("Servidor HTTP iniciado en el puerto " + PUERTO);
        System.out.println("Servidor HTTP iniciado en el puerto " + PUERTO);
        System.out.println("Visita http://localhost:" + PUERTO);

        while (true) {
            // Esperar a que un cliente se conecte
            Socket clientSocket = serverSocket.accept();
            // Crear un nuevo hilo para manejar la solicitud del cliente
            new Thread(new HiloServidor(clientSocket)).start();
        }
    }

    private static class HiloServidor extends Thread {
        private final Socket cliente;

        public HiloServidor(Socket cliente) {
            this.cliente = cliente;
        }

        @Override
        public void run() {
            logger.fine("Nuevo hilo iniciado para cliente: " + cliente.getRemoteSocketAddress());

            try (BufferedReader entrada = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
                 PrintWriter salida = new PrintWriter(cliente.getOutputStream(), true)) {

                // Leer la primera línea de la petición HTTP
                String peticion = entrada.readLine();

                if (peticion != null && (peticion.startsWith("GET") || peticion.startsWith("POST"))) {
                    String[] partespeticion = peticion.split(" ");
                    String metodo = partespeticion[0]; // Metodo HTTP (GET o POST)
                    String ruta = partespeticion[1]; // Ruta (/, /venta, /stock)

                    logger.info("Petición recibida:\n" + "\tMetodo: " + metodo + "\n\tRuta: " + ruta);

                    StringBuilder cuerpo = new StringBuilder();
                    String linea;

                    // Leer encabezados HTTP y determinar el tamaño del cuerpo
                    int contentLength = 0;
                    while (!(linea = entrada.readLine()).isBlank()) {
                        if (linea.startsWith("Content-Length: ")) {
                            contentLength = Integer.parseInt(linea.substring(16));
                        }
                    }

                    String respuesta;

                    // Procesar según la ruta y metodo
                    switch (ruta) {
                        case "/":
                            if (metodo.equals("GET")) {
                                // Página principal con formulario
                                respuesta = construirRespuesta(200, Paginas.html_principal);
                                logger.fine("Devolviendo página principal");
                            } else {
                                // Metodo no permitido para la ruta raíz
                                respuesta = construirRespuesta(405, Paginas.html_error.replace("<!-- ERROR_MESSAGE_PLACEHOLDER -->",
                                        "Método no permitido. Use GET para acceder a esta página."));
                            }
                            break;

                        case "/venta":
                            if (metodo.equals("POST")) {
                                // Procesar venta
                                respuesta = procesarVenta(entrada, contentLength);
                            } else {
                                // Metodo no permitido
                                respuesta = construirRespuesta(405, Paginas.html_error.replace("<!-- ERROR_MESSAGE_PLACEHOLDER -->",
                                        "Método no permitido. Use POST para procesar ventas."));
                            }
                            break;

                        case "/stock":
                            if (metodo.equals("GET")) {
                                // Página de consulta de inventario
                                respuesta = mostrarInventario();
                            } else {
                                // Metodo no permitido
                                respuesta = construirRespuesta(405, Paginas.html_error.replace("<!-- ERROR_MESSAGE_PLACEHOLDER -->",
                                        "Método no permitido. Use GET para consultar el inventario."));
                            }
                            break;

                        default:
                            // Página no encontrada
                            respuesta = construirRespuesta(404, Paginas.html_noEncontrado);
                            logger.warning("Ruta no encontrada: " + ruta);
                            break;
                    }

                    salida.println(respuesta);
                    cliente.close();
                }

            } catch (IOException e) {
                logger.log(Level.SEVERE, "Error de E/O en hilo servidor", e);
                e.printStackTrace();
            } catch (Exception ex) {
                logger.log(Level.SEVERE, "Error general en hilo servidor", ex);
                ex.printStackTrace();
            }
        }

        private String procesarVenta(BufferedReader entrada, int contentLength) throws IOException {
            if (contentLength > 0) {
                char[] buffer = new char[contentLength];
                entrada.read(buffer, 0, contentLength);
                String cuerpo = new String(buffer);

                logger.info("Contenido del cuerpo de venta: " + cuerpo);
                System.out.println("Procesando venta - Datos recibidos: " + cuerpo);

                try {
                    // Parsear datos del formulario: producto=xxx&cantidad=yyy
                    String[] partes = cuerpo.split("&");
                    String producto = null;
                    int cantidad = 0;

                    for (String parte : partes) {
                        String[] campo = parte.split("=");
                        if (campo.length == 2) {
                            if (campo[0].equals("producto")) {
                                producto = java.net.URLDecoder.decode(campo[1], "UTF-8");
                            } else if (campo[0].equals("cantidad")) {
                                cantidad = Integer.parseInt(campo[1]);
                            }
                        }
                    }

                    if (producto == null || cantidad <= 0) {
                        logger.warning("Datos de venta inválidos: producto=" + producto + ", cantidad=" + cantidad);
                        return construirRespuesta(400, Paginas.html_error.replace("<!-- ERROR_MESSAGE_PLACEHOLDER -->",
                                "Datos de venta inválidos. Verifique el producto y la cantidad."));
                    }

                    // Procesar la venta
                    boolean ventaExitosa = GestorFichero.procesarVenta(producto, cantidad);

                    if (ventaExitosa) {
                        logger.info("Venta exitosa: " + producto + " x" + cantidad);
                        String mensajeExito = String.format(
                                "Venta registrada exitosamente:<br><br>" +
                                        "<strong>Producto:</strong> %s<br>" +
                                        "<strong>Cantidad:</strong> %d unidades<br>" +
                                        "<strong>Stock restante:</strong> %d unidades",
                                producto, cantidad, GestorFichero.obtenerStock(producto)
                        );
                        return construirRespuesta(200, Paginas.html_exito.replace("<!-- SUCCESS_MESSAGE_PLACEHOLDER -->", mensajeExito));
                    } else {
                        logger.warning("Venta fallida por stock insuficiente: " + producto + " x" + cantidad);
                        int stockActual = GestorFichero.obtenerStock(producto);
                        String mensajeError = String.format(
                                "❌ <strong>Stock insuficiente</strong><br><br>" +
                                        "Producto: %s<br>" +
                                        "Cantidad solicitada: %d<br>" +
                                        "Stock disponible: %d<br><br>" +
                                        "Por favor, ajuste la cantidad o seleccione otro producto.",
                                producto, cantidad, stockActual >= 0 ? stockActual : 0
                        );
                        return construirRespuesta(400, Paginas.html_error.replace("<!-- ERROR_MESSAGE_PLACEHOLDER -->", mensajeError));
                    }

                } catch (Exception e) {
                    logger.log(Level.SEVERE, "Error al procesar venta", e);
                    return construirRespuesta(500, Paginas.html_error.replace("<!-- ERROR_MESSAGE_PLACEHOLDER -->",
                            "Error interno del servidor al procesar la venta. Inténtelo de nuevo."));
                }
            } else {
                return construirRespuesta(400, Paginas.html_error.replace("<!-- ERROR_MESSAGE_PLACEHOLDER -->",
                        "No se recibieron datos de venta."));
            }
        }

        private String mostrarInventario() {
            try {
                Map<String, Integer> inventario = GestorFichero.obtenerInventario();

                if (inventario == null) {
                    logger.severe("Error al obtener inventario");
                    return construirRespuesta(500, Paginas.html_error.replace("<!-- ERROR_MESSAGE_PLACEHOLDER -->",
                            "Error al consultar el inventario. Inténtelo de nuevo."));
                }

                // Construir tabla de inventario
                StringBuilder tablaStock = new StringBuilder();
                String[] iconos = {"📱", "💻", "📲", "🎧", "🔌"};
                String[] productos = {"Smartphones", "Laptops", "Tablets", "Auriculares", "Accesorios"};

                for (int i = 0; i < productos.length; i++) {
                    String producto = productos[i];
                    int stock = inventario.getOrDefault(producto, 0);
                    String claseStock;
                    String estadoTexto;

                    if (stock <= 10) {
                        claseStock = "stock-low";
                        estadoTexto = "⚠️ Crítico";
                    } else if (stock <= 30) {
                        claseStock = "stock-medium";
                        estadoTexto = "⚡ Medio";
                    } else {
                        claseStock = "stock-high";
                        estadoTexto = "✅ Bueno";
                    }

                    tablaStock.append(String.format(
                            "<tr>" +
                                    "<td class=\"stock-item\">%s %s</td>" +
                                    "<td class=\"stock-count %s\">%d</td>" +
                                    "<td class=\"%s\">%s</td>" +
                                    "</tr>",
                            iconos[i], producto, claseStock, stock, claseStock, estadoTexto
                    ));
                }

                String paginaStock = Paginas.html_stock.replace("<!-- STOCK_DATA_PLACEHOLDER -->", tablaStock.toString());
                logger.fine("Mostrando página de inventario");
                return construirRespuesta(200, paginaStock);

            } catch (Exception e) {
                logger.log(Level.SEVERE, "Error al mostrar inventario", e);
                return construirRespuesta(500, Paginas.html_error.replace("<!-- ERROR_MESSAGE_PLACEHOLDER -->",
                        "Error al mostrar el inventario. Inténtelo de nuevo."));
            }
        }

        private String construirRespuesta(int codigo, String html) {
            String lineaInicial;
            switch (codigo) {
                case 200:
                    lineaInicial = "HTTP/1.1 200 OK";
                    break;
                case 400:
                    lineaInicial = "HTTP/1.1 400 Bad Request";
                    break;
                case 404:
                    lineaInicial = "HTTP/1.1 404 Not Found";
                    break;
                case 405:
                    lineaInicial = "HTTP/1.1 405 Method Not Allowed";
                    break;
                case 500:
                    lineaInicial = "HTTP/1.1 500 Internal Server Error";
                    break;
                default:
                    lineaInicial = "HTTP/1.1 " + codigo + " Unknown";
            }

            return lineaInicial + "\n" +
                    "Content-Type: text/html; charset=UTF-8\n" +
                    "Content-Length: " + html.length() + "\n" +
                    "Connection: close\n" +
                    "\n" +
                    html;
        }
    }
}