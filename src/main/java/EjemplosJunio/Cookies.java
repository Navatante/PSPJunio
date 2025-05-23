package EjemplosJunio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class Cookies {

    private static final ConcurrentHashMap<String, String> sesiones = new ConcurrentHashMap<>();

    public static void main(String[] args) throws IOException {

        // Crear un servidor que escuche en el puerto 8066
        ServerSocket serverSocket = new ServerSocket(8066);
        System.out.println("Servidor HTTP iniciado en el puerto 8066");
        System.out.println("Visite http://localhost:8066");

        // Bucle infinito para aceptar conexiones de clientes
        while (true) {

            Socket cliente = serverSocket.accept(); // Espera y acepta una conexion entrante
            Thread hiloServidor = new HiloServidor(cliente); // Crear un hilo para manejar la conexión del cliente
            hiloServidor.start();

        }


    }

    private static class HiloServidor extends Thread {
        private final Socket cliente;

        public HiloServidor(Socket cliente) {
            this.cliente = cliente;
        }

        @Override
        public void run() {
            try (
                    BufferedReader entrada = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
                    PrintWriter salida = new PrintWriter(cliente.getOutputStream(), true, StandardCharsets.UTF_8);
            ) {
                // Lee la primera línea de la petición HTTP.
                String peticion = entrada.readLine();
                if (peticion == null || (!peticion.startsWith("GET") && !peticion.startsWith("POST"))) {
                    return; // Ignora la petición si no es GET o POST.
                }
                System.out.println("peticion: " + peticion);
                String ruta = peticion.split(" ")[1]; // Extrae la ruta solicitada.

                // Leer encabezados HTTP. Determina la sesiónID y el tamaño del cuerpo.
                String linea;
                String sessionId = null;
                int contentLength = 0;

                while (!(linea = entrada.readLine()).isBlank()) {
                    System.out.println("Metadato: " + linea);
                    if (linea.startsWith("Content-Length: ")) {
                        contentLength = Integer.parseInt(linea.substring(16));
                    } else if (linea.startsWith("Cookie: ")) {
                        String[] cookies = linea.substring(8).split("; ");
                        for (String cookie : cookies) {
                            if (cookie.startsWith("sessionId=")) {
                                sessionId = cookie.substring(10);
                            }
                        }
                    }
                }
                if (sessionId == null) {
                    sessionId = UUID.randomUUID().toString();
                    System.out.println("***COOKIE creada: "+ sessionId);
                }

                String email = "";
                String sesion = sesiones.putIfAbsent(sessionId, email);


                System.out.println("linea: vacia");

// Leer el cuerpo si es un POST.
                StringBuilder cuerpo = new StringBuilder(); // Para almacenar el cuerpo de la solicitud.
                if (peticion.startsWith("POST") && contentLength > 0) {
                    char[] buffer = new char[contentLength];
                    entrada.read(buffer, 0, contentLength);
                    cuerpo.append(buffer);
                }

                String respuesta; // Contendrá la respuesta generada por el servidor.

////                if (ruta.equals("/")) {
////                    respuesta = construirRespuesta(200, Paginas.html_index, sessionId);
////                } else if (ruta.startsWith("/adivina")) {
////                    respuesta = construirRespuesta(200, Paginas.html_adivina, sessionId);
////                } else if (ruta.startsWith("/dados")) {
////                    respuesta = construirRespuesta(200, Paginas.html_dados, sessionId);
////                } else if (ruta.startsWith("/ppt")) {
////                    respuesta = construirRespuesta(200, Paginas.html_ppt, sessionId);
////                } else {
////                    respuesta = construirRespuesta(404, Paginas.html_noencontrado, sessionId);
////                }
//
//                salida.println(respuesta); // Envía la respuesta al cliente.

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static String construirRespuesta(int codigo, String contenido, String sessionId) {
        return (codigo == 200 ? "HTTP/1.1 200 OK" : "HTTP/1.1 400 Bad Request") + "\n"    // Línea inicial
                + "Content-Type: text/html; charset=UTF-8"+ "\n"                          // Metadatos
                + "Content-Length: " + contenido.length() + "\n"
                + "Set-Cookie: sessionId=" + sessionId + "; Path=/;\n"
                + "Content-Type: text/html; charset=UTF-8\n"
                + "\n"                                                                    // Línea vacía
                + contenido;                                                              // Cuerpo
    }
}

