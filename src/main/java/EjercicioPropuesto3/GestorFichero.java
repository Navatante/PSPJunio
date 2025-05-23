package EjercicioPropuesto3;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class GestorFichero {

    private static final String CLAVE = "1234567890123456";
    private static final String ALGORITMO = "AES";

    private static final Object ObjLunes = new Object();
    private static final Object ObjMartes = new Object();
    private static final Object ObjMiercoles = new Object();
    private static final Object ObjJueves = new Object();
    private static final Object ObjViernes = new Object();
    private static final Object ObjSabado = new Object();
    private static final Object ObjDomingo = new Object();

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

    public static void actualizarFichero(String dia, int habitaciones) {
        switch(dia) {
            case "lunes":
                actualizarLunes(habitaciones);
                break;
            case "martes":
                actualizarMartes(habitaciones);
                break;
            case "miercoles":
                actualizarMiercoles(habitaciones);
                break;
            case "jueves":
                actualizarJueves(habitaciones);
                break;
            case "viernes":
                actualizarViernes(habitaciones);
                break;
            case "sabado":
                actualizarSabado(habitaciones);
                break;
            case "domingo":
                actualizarDomingo(habitaciones);
                break;
        }
    }

    private static void actualizarLunes(int habitaciones) {
        try {
            synchronized (ObjLunes) {
                Path path = Paths.get("lunes.txt");
                int reservas = 0;

                // Verificamos si el archivo existe y tiene contenido
                if (Files.exists(path) && Files.size(path) > 0) {
                    byte[] contenidoCifrado = Files.readAllBytes(path);
                    reservas = Integer.parseInt(descifrar(contenidoCifrado));
                }

                // Agregamos el nuevo valor
                reservas += habitaciones;

                if(reservas > 0) {
                    System.out.println("El nuevo dato en lunes.txt es: " + reservas);
                }

                // Cifrar el contenido antes de guardarlo
                byte[] contenidoCifrado = cifrar(Integer.toString(reservas));

                // Escribe el contenido cifrado en el archivo
                // Si el archivo no existe, lo crea (StandardOpenOption.CREATE)
                // Si el archivo ya existe, agrega el contenido sin borrar lo anterior (StandardOpenOption.APPEND)

                Files.write(path, contenidoCifrado, StandardOpenOption.CREATE);
            }

        } catch (Exception e) {
            System.err.println("Error al actualizar fichero de lunes: " + e.getMessage());
        }
    }

    private static void actualizarMartes(int habitaciones) {
        try {
            synchronized (ObjMartes) {
                Path path = Paths.get("martes.txt");
                int reservas = 0;

                // Verificamos si el archivo existe y tiene contenido
                if (Files.exists(path) && Files.size(path) > 0) {
                    byte[] contenidoCifrado = Files.readAllBytes(path);
                    reservas = Integer.parseInt(descifrar(contenidoCifrado));
                }

                // Agregamos el nuevo valor
                reservas += habitaciones;

                if(reservas > 0) {
                    System.out.println("El nuevo dato en martes.txt es: " + reservas);
                }

                // Cifrar el contenido antes de guardarlo
                byte[] contenidoCifrado = cifrar(Integer.toString(reservas));

                // Escribe el contenido cifrado en el archivo
                // Si el archivo no existe, lo crea (StandardOpenOption.CREATE)
                // Si el archivo ya existe, agrega el contenido sin borrar lo anterior (StandardOpenOption.APPEND)
                Files.write(path, contenidoCifrado, StandardOpenOption.CREATE);
            }

        } catch (Exception e) {
            System.err.println("Error al actualizar fichero de martes: " + e.getMessage());
        }
    }

    private static void actualizarMiercoles(int habitaciones) {
        try {
            synchronized (ObjMiercoles) {
                Path path = Paths.get("miercoles.txt");
                int reservas = 0;

                // Verificamos si el archivo existe y tiene contenido
                if (Files.exists(path) && Files.size(path) > 0) {
                    byte[] contenidoCifrado = Files.readAllBytes(path);
                    reservas = Integer.parseInt(descifrar(contenidoCifrado));
                }

                // Agregamos el nuevo valor
                reservas += habitaciones;

                if(reservas > 0) {
                    System.out.println("El nuevo dato en miercoles.txt es: " + reservas);
                }

                // Cifrar el contenido antes de guardarlo
                byte[] contenidoCifrado = cifrar(Integer.toString(reservas));

                // Escribe el contenido cifrado en el archivo
                Files.write(path, contenidoCifrado, StandardOpenOption.CREATE);
            }

        } catch (Exception e) {
            System.err.println("Error al actualizar fichero de miercoles: " + e.getMessage());
        }
    }

    private static void actualizarJueves(int habitaciones) {
        try {
            synchronized (ObjJueves) {
                Path path = Paths.get("jueves.txt");
                int reservas = 0;

                // Verificamos si el archivo existe y tiene contenido
                if (Files.exists(path) && Files.size(path) > 0) {
                    byte[] contenidoCifrado = Files.readAllBytes(path);
                    reservas = Integer.parseInt(descifrar(contenidoCifrado));
                }

                // Agregamos el nuevo valor
                reservas += habitaciones;

                if(reservas > 0) {
                    System.out.println("El nuevo dato en jueves.txt es: " + reservas);
                }

                // Cifrar el contenido antes de guardarlo
                byte[] contenidoCifrado = cifrar(Integer.toString(reservas));

                // Escribe el contenido cifrado en el archivo
                Files.write(path, contenidoCifrado, StandardOpenOption.CREATE);
            }

        } catch (Exception e) {
            System.err.println("Error al actualizar fichero de jueves: " + e.getMessage());
        }
    }

    private static void actualizarViernes(int habitaciones) {
        try {
            synchronized (ObjViernes) {
                Path path = Paths.get("viernes.txt");
                int reservas = 0;

                // Verificamos si el archivo existe y tiene contenido
                if (Files.exists(path) && Files.size(path) > 0) {
                    byte[] contenidoCifrado = Files.readAllBytes(path);
                    reservas = Integer.parseInt(descifrar(contenidoCifrado));
                }

                // Agregamos el nuevo valor
                reservas += habitaciones;

                if(reservas > 0) {
                    System.out.println("El nuevo dato en viernes.txt es: " + reservas);
                }

                // Cifrar el contenido antes de guardarlo
                byte[] contenidoCifrado = cifrar(Integer.toString(reservas));

                // Escribe el contenido cifrado en el archivo
                Files.write(path, contenidoCifrado, StandardOpenOption.CREATE);
            }

        } catch (Exception e) {
            System.err.println("Error al actualizar fichero de viernes: " + e.getMessage());
        }
    }

    private static void actualizarSabado(int habitaciones) {
        try {
            synchronized (ObjSabado) {
                Path path = Paths.get("sabado.txt");
                int reservas = 0;

                // Verificamos si el archivo existe y tiene contenido
                if (Files.exists(path) && Files.size(path) > 0) {
                    byte[] contenidoCifrado = Files.readAllBytes(path);
                    reservas = Integer.parseInt(descifrar(contenidoCifrado));
                }

                // Agregamos el nuevo valor
                reservas += habitaciones;

                if(reservas > 0) {
                    System.out.println("El nuevo dato en sabado.txt es: " + reservas);
                }

                // Cifrar el contenido antes de guardarlo
                byte[] contenidoCifrado = cifrar(Integer.toString(reservas));

                // Escribe el contenido cifrado en el archivo
                Files.write(path, contenidoCifrado, StandardOpenOption.CREATE);
            }

        } catch (Exception e) {
            System.err.println("Error al actualizar fichero de sabado: " + e.getMessage());
        }
    }

    private static void actualizarDomingo(int habitaciones) {
        try {
            synchronized (ObjDomingo) {
                Path path = Paths.get("domingo.txt");
                int reservas = 0;

                // Verificamos si el archivo existe y tiene contenido
                if (Files.exists(path) && Files.size(path) > 0) {
                    byte[] contenidoCifrado = Files.readAllBytes(path);
                    reservas = Integer.parseInt(descifrar(contenidoCifrado));
                }

                // Agregamos el nuevo valor
                reservas += habitaciones;

                if(reservas > 0) {
                    System.out.println("El nuevo dato en domingo.txt es: " + reservas);
                }

                // Cifrar el contenido antes de guardarlo
                byte[] contenidoCifrado = cifrar(Integer.toString(reservas));

                // Escribe el contenido cifrado en el archivo
                Files.write(path, contenidoCifrado, StandardOpenOption.CREATE);
            }

        } catch (Exception e) {
            System.err.println("Error al actualizar fichero de domingo: " + e.getMessage());
        }
    }
}
