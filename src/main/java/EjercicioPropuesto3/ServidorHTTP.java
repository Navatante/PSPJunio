package EjercicioPropuesto3;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.KeyStore;

public class ServidorHTTP {

    // Puerto HTTPS estándar
    private static final int PUERTO = 8443;

    public static void main(String[] args) throws Exception {

        ServerSocket serverSocket = new ServerSocket(PUERTO);
        System.out.println("Servidor HTTP iniciado en el puerto " + PUERTO);

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
            this.cliente = cliente; // Asocia el socket del cliente al hilo
        }

        @Override
        public void run() {

            try (BufferedReader entrada = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
                    PrintWriter salida = new PrintWriter(cliente.getOutputStream(), true)) {

                // Leer la primera linea de la peticion HTTP
                String peticion = entrada.readLine();
                if (peticion != null && (!peticion.startsWith("GET") || !peticion.startsWith("POST"))) {
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

                        // Separo el contenido del cuerpo dia=xxx&cantidad=yyy
                        String dato1 = cuerpo.toString().split("&")[0];
                        String dato2 = cuerpo.toString().split("&")[1];
                    }
                }

            }
        }

    }
}


