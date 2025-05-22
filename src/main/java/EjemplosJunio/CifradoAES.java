package EjemplosJunio;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

public class CifradoAES {

    public static void main(String[] args) throws Exception {


        Cipher cipher = Cipher.getInstance("AES"); // Algoritmo de cifrado AES
        SecretKey clave = new SecretKeySpec("mi_clave_secreta".getBytes(), "AES"); // Clave de 16 bytes

        /////////////////////////////
        ///////// CIFRAMOS /////////
        ////////////////////////////

        cipher.init(Cipher.ENCRYPT_MODE, clave);

        // Leemos el fichero
        String datosDescifrados = Files.readString(Paths.get("log.log"));
        System.out.println("Datos descifrados: " + datosDescifrados);
        byte[] datosCifrados = cipher.doFinal(datosDescifrados.getBytes());

        System.out.println("Datos cifrados: " + new String(datosCifrados));

        // Escribimos el fichero
        try (FileOutputStream fos = new FileOutputStream("FicheroCifrado.txt")) {
            fos.write(datosCifrados);
        }


        /////////////////////////////
        //////// DESCIFRAMOS ////////
        ////////////////////////////

        cipher.init(Cipher.DECRYPT_MODE, clave);

        byte[] datosCifrados2 = Files.readAllBytes(Paths.get("FicheroCifrado.txt"));
        byte[] datosDescifrados2 = cipher.doFinal(datosCifrados2);

        System.out.println("Datos descifrados: " + new String(datosDescifrados2));

        try (FileOutputStream fos = new FileOutputStream("FicheroDescifrado.txt")) {
            fos.write(datosDescifrados2);
        }
        // Los hilos lo que tendran que hacer es cifrar y descifrar ficheros, trabaja con el fichero descifrado y cuando termine lo cifra.
    }
}
