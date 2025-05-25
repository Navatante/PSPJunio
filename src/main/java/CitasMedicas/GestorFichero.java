package CitasMedicas;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class GestorFichero {

    private static final String CLAVE = "1234567890123456";
    private static final String ALGORITMO = "AES";

    private static final Object MedGeneral = new Object();
    private static final Object Cardiologia = new Object();
    private static final Object Dermatologia = new Object();
    private static final Object Pediatria = new Object();
    private static final Object Traumatologia = new Object();

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

    public static void actualizarFichero(String especialidad, int cantidad) {
        switch(especialidad) {
            case "medicina_general":
                actualizarMedicinaGeneral(cantidad);
                break;
            case "cardiologia":
                actualizarCardiologia(cantidad);
                break;
            case "dermatologia":
                actualizarDermatologia(cantidad);
                break;
            case "pediatria":
                actualizarPediatria(cantidad);
                break;
            case "traumatologia":
                actualizarTraumatologia(cantidad);
                break;
        }
    }

    private static void actualizarMedicinaGeneral(int cantidad) {
        try {
            synchronized (MedGeneral) {
                Path path = Paths.get("medicina_general.txt");
                int citas = 0;

                // Verificamos si el archivo existe y tiene contenido
                if(Files.exists(path) && Files.size(path) > 0) {
                    byte[] contenidoCifrado = Files.readAllBytes(path);
                    citas = Integer.parseInt(descifrar(contenidoCifrado));
                }

                // Agregamos el nuevo valor
                citas += cantidad;

                if(citas > 0) {
                    System.out.println("El nuevo dato en medicina_general.txt es: " + citas);
                }

                // Cifrar el contenido antes de guardarlo
                byte[] contenidoCifrado = cifrar(Integer.toString(citas));

                // Escribe el contenido cifrado en el archivo
                // Si el archivo no existe, lo crea (StandardOpenOption.CREATE)
                // Si el archivo ya existe, agrega el contenido sin borrar lo anterior (StandardOpenOption.APPEND)

                Files.write(path, contenidoCifrado, StandardOpenOption.CREATE);
            }
        } catch (Exception e) {
            System.err.println("Error al actualizar fichero de medicina_general: " + e.getMessage());
        }
    }

    private static void actualizarCardiologia(int cantidad) {
        try {
            synchronized (Cardiologia) {
                Path path = Paths.get("cardiologia.txt");
                int citas = 0;

                // Verificamos si el archivo existe y tiene contenido
                if(Files.exists(path) && Files.size(path) > 0) {
                    byte[] contenidoCifrado = Files.readAllBytes(path);
                    citas = Integer.parseInt(descifrar(contenidoCifrado));
                }

                // Agregamos el nuevo valor
                citas += cantidad;

                if(citas > 0) {
                    System.out.println("El nuevo dato en cardiologia.txt es: " + citas);
                }

                // Cifrar el contenido antes de guardarlo
                byte[] contenidoCifrado = cifrar(Integer.toString(citas));

                // Escribe el contenido cifrado en el archivo
                // Si el archivo no existe, lo crea (StandardOpenOption.CREATE)
                // Si el archivo ya existe, agrega el contenido sin borrar lo anterior (StandardOpenOption.APPEND)

                Files.write(path, contenidoCifrado, StandardOpenOption.CREATE);
            }
        } catch (Exception e) {
            System.err.println("Error al actualizar fichero de cardiologia: " + e.getMessage());
        }
    }

    private static void actualizarDermatologia(int cantidad) {
        try {
            synchronized (Dermatologia) {
                Path path = Paths.get("dermatologia.txt");
                int citas = 0;

                // Verificamos si el archivo existe y tiene contenido
                if(Files.exists(path) && Files.size(path) > 0) {
                    byte[] contenidoCifrado = Files.readAllBytes(path);
                    citas = Integer.parseInt(descifrar(contenidoCifrado));
                }

                // Agregamos el nuevo valor
                citas += cantidad;

                if(citas > 0) {
                    System.out.println("El nuevo dato en dermatologia.txt es: " + citas);
                }

                // Cifrar el contenido antes de guardarlo
                byte[] contenidoCifrado = cifrar(Integer.toString(citas));

                // Escribe el contenido cifrado en el archivo
                // Si el archivo no existe, lo crea (StandardOpenOption.CREATE)
                // Si el archivo ya existe, agrega el contenido sin borrar lo anterior (StandardOpenOption.APPEND)

                Files.write(path, contenidoCifrado, StandardOpenOption.CREATE);
            }
        } catch (Exception e) {
            System.err.println("Error al actualizar fichero de dermatologia: " + e.getMessage());
        }
    }

    private static void actualizarPediatria(int cantidad) {
        try {
            synchronized (Pediatria) {
                Path path = Paths.get("pediatria.txt");
                int citas = 0;

                // Verificamos si el archivo existe y tiene contenido
                if(Files.exists(path) && Files.size(path) > 0) {
                    byte[] contenidoCifrado = Files.readAllBytes(path);
                    citas = Integer.parseInt(descifrar(contenidoCifrado));
                }

                // Agregamos el nuevo valor
                citas += cantidad;

                if(citas > 0) {
                    System.out.println("El nuevo dato en pediatria.txt es: " + citas);
                }

                // Cifrar el contenido antes de guardarlo
                byte[] contenidoCifrado = cifrar(Integer.toString(citas));

                // Escribe el contenido cifrado en el archivo
                // Si el archivo no existe, lo crea (StandardOpenOption.CREATE)
                // Si el archivo ya existe, agrega el contenido sin borrar lo anterior (StandardOpenOption.APPEND)

                Files.write(path, contenidoCifrado, StandardOpenOption.CREATE);
            }
        } catch (Exception e) {
            System.err.println("Error al actualizar fichero de pediatria: " + e.getMessage());
        }
    }

    private static void actualizarTraumatologia(int cantidad) {
        try {
            synchronized (Traumatologia) {
                Path path = Paths.get("traumatologia.txt");
                int citas = 0;

                // Verificamos si el archivo existe y tiene contenido
                if(Files.exists(path) && Files.size(path) > 0) {
                    byte[] contenidoCifrado = Files.readAllBytes(path);
                    citas = Integer.parseInt(descifrar(contenidoCifrado));
                }

                // Agregamos el nuevo valor
                citas += cantidad;

                if(citas > 0) {
                    System.out.println("El nuevo dato en traumatologia.txt es: " + citas);
                }

                // Cifrar el contenido antes de guardarlo
                byte[] contenidoCifrado = cifrar(Integer.toString(citas));

                // Escribe el contenido cifrado en el archivo
                // Si el archivo no existe, lo crea (StandardOpenOption.CREATE)
                // Si el archivo ya existe, agrega el contenido sin borrar lo anterior (StandardOpenOption.APPEND)

                Files.write(path, contenidoCifrado, StandardOpenOption.CREATE);
            }
        } catch (Exception e) {
            System.err.println("Error al actualizar fichero de traumatologia: " + e.getMessage());
        }
    }


}
