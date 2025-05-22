package EjemplosJunio;

import org.mindrot.jbcrypt.BCrypt;

public class CifradoBCrypt {

    public static void main(String[] args) {

        String password = "mi_contraseña_secreta";
        System.out.println("Password: " + password);

        String hash = BCrypt.hashpw(password, BCrypt.gensalt(10));
        System.out.println("Hash: " + hash);

        boolean coincide = BCrypt.checkpw(password, hash);

        System.out.println("¿Coincide? " + coincide);
    }
}
