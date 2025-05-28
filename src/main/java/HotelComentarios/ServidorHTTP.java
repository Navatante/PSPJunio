package HotelComentarios;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServidorHTTP {

    // Puerto HTTP estándar
    private static final int PUERTO = 8443;

    public static void main(String[] args) throws Exception {

        ServerSocket serverSocket = new ServerSocket(PUERTO);
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
            try (BufferedReader entrada = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
                 PrintWriter salida = new PrintWriter(cliente.getOutputStream(), true)) {

                // Leer la primera linea de la peticion HTTP
                String peticion = entrada.readLine();
                if(peticion != null && (peticion.startsWith("GET") || peticion.startsWith("POST"))) {
                    String ruta = peticion.split(" ")[1]; // Extraer la ruta de la peticion
                    StringBuilder cuerpo = new StringBuilder(); // Para almacenar el cuerpo de la peticion
                    String linea;

                    // Leer encabezados HTTP y determinar el tamano del cuerpo
                    int contentLength = 0;
                    while (!(linea = entrada.readLine()).isBlank()) {
                        if (linea.startsWith("Content-Length: ")) {
                            contentLength = Integer.parseInt(linea.substring(16));
                        }
                    }

                    // Leer el cuerpo de la peticion si es un POST
                    if (peticion.startsWith("POST") && contentLength > 0) {
                        char[] buffer = new char[contentLength];
                        entrada.read(buffer, 0, contentLength);
                        cuerpo.append(buffer);

                        // Contenido del cuerpo
                        System.out.println("Contenido del cuerpo: " + cuerpo.toString());

                        // Parsear los datos del formulario: tipo=x&valoracion=y&comentario=z
                        String[] parametros = cuerpo.toString().split("&");
                        String tipo = "";
                        String valoracion = "";
                        String comentario = "";

                        // Extraer cada parámetro
                        for (String parametro : parametros) {
                            String[] partes = parametro.split("=", 2); // Limitamos a 2 partes por si el comentario tiene "="
                            if (partes.length == 2) {
                                String clave = partes[0];
                                String valor = java.net.URLDecoder.decode(partes[1], "UTF-8"); // Decodificar URL

                                switch (clave) {
                                    case "tipo":
                                        tipo = valor;
                                        break;
                                    case "valoracion":
                                        valoracion = valor;
                                        break;
                                    case "comentario":
                                        comentario = valor;
                                        break;
                                }
                            }
                        }

                        System.out.println("Nuevo comentario - Tipo: " + tipo + ", Valoración: " + valoracion + "/5, Comentario: " + comentario);

                        // Llamar al metodo corregido con los 3 parámetros
                        GestorFichero.actualizarFichero(tipo, valoracion, comentario);
                    }

                    // Si la peticion no es un POST, sera un GET y veimos aqui:
                    String respuesta;
                    if (ruta.equals("/")) {
                        // Si la ruta es la raíz, devuelve el formulario HTML
                        respuesta = construirRespuesta(200, Paginas.html_comentarios);
                    } else {
                        respuesta = construirRespuesta(404, Paginas.html_noEncontrado);
                    }

                    salida.println(respuesta); // Envia la respuesta al cliente

                    cliente.close(); // Cierra la conexion

                }

            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception ex) {
                Logger.getLogger(HiloServidor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        private String construirRespuesta(int codigo, String html) {
            String lineaInicial = codigo == 200 ? "HTTP/1.1 200 OK" : "HTTP/1.1 404 Not Found";
            return lineaInicial + "\n" +
                    "Content-Type: text/html; charset=UTF-8" +
                    "\nContent-Length: " + html.length() +
                    "\n\n" +
                    html;
        }
    }
}
