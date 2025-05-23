package EjemplosJunio;

import java.util.Random;

/**
 * Clase que contiene las páginas HTML del servidor.
 */
public class Paginas {

    /**
     * Página de inicio de sesión/registro
     */
    public static final String html_login = "<html><head><title>Inicio de Sesión</title><meta charset=UTF-8>"
            + "<link rel=icon href=data:,/>"
            + "<style>"
            + "body { font-family: Arial, sans-serif; margin: 0; padding: 20px; background-color: #f5f5f5; }"
            + ".container { max-width: 500px; margin: 0 auto; background-color: white; padding: 20px; border-radius: 5px; box-shadow: 0 0 10px rgba(0,0,0,0.1); }"
            + "h1 { text-align: center; color: #333; }"
            + "h2 { margin-top: 20px; color: #555; }"
            + "form { margin-bottom: 20px; }"
            + "label { display: block; margin-bottom: 5px; }"
            + "input[type=email], input[type=password] { width: 100%%; padding: 10px; margin-bottom: 10px; border: 1px solid #ddd; border-radius: 3px; }"
            + "button { background-color: #4CAF50; color: white; padding: 10px 15px; border: none; cursor: pointer; width: 100%%; border-radius: 3px; }"
            + "button:hover { background-color: #45a049; }"
            + ".error { color: red; margin-bottom: 10px; }"
            + "</style></head><body>"
            + "<div class=\"container\">"
            + "<h1>Servidor de Juegos</h1>"
            + "<h2>Iniciar Sesión</h2>"
            + "<form action=\"/login\" method=\"POST\">"
            + "<label for=\"email\">Email:</label>"
            + "<input type=\"email\" id=\"email\" name=\"email\" required pattern=\"[A-Za-z0-9+_.-]+@(.+)\" title=\"Ingrese un email válido\">"
            + "<label for=\"password\">Contraseña:</label>"
            + "<input type=\"password\" id=\"password\" name=\"password\" required pattern=\"[A-Za-z0-9]{6,}\" title=\"La contraseña debe tener al menos 6 caracteres alfanuméricos\">"
            + "<button type=\"submit\">Iniciar Sesión</button>"
            + "</form>"
            + "<h2>Registrarse</h2>"
            + "<form action=\"/registro\" method=\"POST\">"
            + "<label for=\"email_reg\">Email:</label>"
            + "<input type=\"email\" id=\"email_reg\" name=\"email\" required pattern=\"[A-Za-z0-9+_.-]+@(.+)\" title=\"Ingrese un email válido\">"
            + "<label for=\"password_reg\">Contraseña:</label>"
            + "<input type=\"password\" id=\"password_reg\" name=\"password\" required pattern=\"[A-Za-z0-9]{6,}\" title=\"La contraseña debe tener al menos 6 caracteres alfanuméricos\">"
            + "<button type=\"submit\">Registrarse</button>"
            + "</form>"
            + "%%s" // Para mensajes de error o éxito
            + "</div></body></html>";


    /**
     * Página de índice actualizada con botón de cierre de sesión
     */
    public static final String html_index = "<html><head><title>Inicio</title><meta charset=UTF-8>"
            + "<link rel=icon href=data:,/>"
            + "<style>"
            + "body { font-family: Arial, sans-serif; margin: 0; padding: 20px; background-color: #f5f5f5; }"
            + ".container { max-width: 800px; margin: 0 auto; background-color: white; padding: 20px; border-radius: 5px; box-shadow: 0 0 10px rgba(0,0,0,0.1); }"
            + "h1 { text-align: center; color: #333; }"
            + "ul { list-style-type: none; padding: 0; }"
            + "li { margin-bottom: 10px; }"
            + "a { display: block; padding: 10px; background-color: #4CAF50; color: white; text-decoration: none; border-radius: 3px; text-align: center; }"
            + "a:hover { background-color: #45a049; }"
            + ".logout { background-color: #f44336; margin-top: 20px; }"
            + ".logout:hover { background-color: #d32f2f; }"
            + ".user-info { text-align: right; margin-bottom: 20px; color: #666; }"
            + "</style></head><body>"
            + "<div class=\"container\">"
            + "<div class=\"user-info\">Usuario: %s</div>" // Email del usuario
            + "<h1>Bienvenido al Servidor de Juegos</h1>"
            + "<ul>"
            + "<li><a href='/adivina'>Adivina el Número</a></li>"
            + "<li><a href='/dados'>Lanza Dados</a></li>"
            + "<li><a href='/ppt'>Piedra, Papel o Tijera</a></li>"
            + "<li><a href='/logout' class=\"logout\">Cerrar Sesión</a></li>"
            + "</ul>"
            + "</div></body></html>";

    /**
     * Página de error 404 personalizada
     */
    public static final String html_noEncontrado = "<html><head><title>Error 404</title><link rel=icon href=data:,/>"
            + "<style>"
            + "body { font-family: Arial, sans-serif; text-align: center; padding: 50px; }"
            + "h1 { color: #d32f2f; }"
            + "p { margin-bottom: 20px; }"
            + "a { color: #4CAF50; text-decoration: none; }"
            + "a:hover { text-decoration: underline; }"
            + "</style></head><body>"
            + "<h1>404 Página No Encontrada</h1>"
            + "<p>La página solicitada no existe.</p>"
            + "<p><a href='/'>Volver al inicio</a></p>"
            + "</body></html>";

    /**
     * Página de error de autenticación
     */
    public static final String html_errorAutenticacion = "<html><head><title>Error de Autenticación</title><link rel=icon href=data:,/>"
            + "<style>"
            + "body { font-family: Arial, sans-serif; text-align: center; padding: 50px; }"
            + "h1 { color: #d32f2f; }"
            + "p { margin-bottom: 20px; }"
            + "a { color: #4CAF50; text-decoration: none; }"
            + "a:hover { text-decoration: underline; }"
            + "</style></head><body>"
            + "<h1>Error de Autenticación</h1>"
            + "<p>Debe iniciar sesión para acceder a esta página.</p>"
            + "<p><a href='/'>Ir a la página de inicio de sesión</a></p>"
            + "</body></html>";

    /**
     * Genera HTML para el juego "Adivina el Número" con botón de cerrar sesión
     *
     * @param resultado Resultado del juego
     * @return Página HTML completa
     */
    public static String generarHtmlAdivina(String resultado){
        return "<html><head><title>Adivina el Número</title><link rel=icon href=data:,/>"
                + "<style>"
                + "body { font-family: Arial, sans-serif; margin: 0; padding: 20px; background-color: #f5f5f5; }"
                + ".container { max-width: 800px; margin: 0 auto; background-color: white; padding: 20px; border-radius: 5px; box-shadow: 0 0 10px rgba(0,0,0,0.1); }"
                + "h1 { text-align: center; color: #333; }"
                + "form { margin-bottom: 20px; text-align: center; }"
                + "label { display: block; margin-bottom: 10px; }"
                + "input[type=number] { padding: 8px; border: 1px solid #ddd; border-radius: 3px; }"
                + "button { background-color: #4CAF50; color: white; padding: 8px 15px; border: none; cursor: pointer; border-radius: 3px; margin-left: 10px; }"
                + "button:hover { background-color: #45a049; }"
                + ".result { padding: 15px; background-color: #f9f9f9; border-radius: 3px; text-align: center; }"
                + ".nav { display: flex; justify-content: space-between; margin-top: 20px; }"
                + ".nav a { padding: 10px; color: white; text-decoration: none; border-radius: 3px; text-align: center; }"
                + ".home { background-color: #2196F3; }"
                + ".logout { background-color: #f44336; }"
                + ".home:hover { background-color: #0b7dda; }"
                + ".logout:hover { background-color: #d32f2f; }"
                + "</style></head><body>"
                + "<div class=\"container\">"
                + "<h1>¡Adivina el Número!</h1>"
                + "<form action='/adivina' method='POST'>"
                + "<label for='numero'>Introduce un número del 1 al 100:</label>"
                + "<input type='number' id='numero' name='numero' min='1' max='100' required></input>"
                + "<button type='submit'>Enviar</button>"
                + "</form>"
                + "<div class=\"result\">" + resultado + "</div>"
                + "<div class=\"nav\">"
                + "<a href='/' class=\"home\">Volver al Inicio</a>"
                + "<a href='/logout' class=\"logout\">Cerrar Sesión</a>"
                + "</div>"
                + "</div></body></html>";
    }

    /**
     * Genera HTML para el juego "Piedra, Papel o Tijera" con botón de cerrar sesión
     *
     * @param resultado Resultado del juego
     * @return Página HTML completa
     */
    public static String generarHtmlPpt(String resultado){
        return "<html><head><title>Piedra, Papel o Tijera</title><link rel=icon href=data:,/>"
                + "<style>"
                + "body { font-family: Arial, sans-serif; margin: 0; padding: 20px; background-color: #f5f5f5; }"
                + ".container { max-width: 800px; margin: 0 auto; background-color: white; padding: 20px; border-radius: 5px; box-shadow: 0 0 10px rgba(0,0,0,0.1); }"
                + "h1 { text-align: center; color: #333; }"
                + "form { display: flex; justify-content: center; gap: 10px; margin-bottom: 20px; }"
                + "button { background-color: #4CAF50; color: white; padding: 10px 20px; border: none; cursor: pointer; border-radius: 3px; font-size: 16px; }"
                + "button:hover { background-color: #45a049; }"
                + ".result { padding: 15px; background-color: #f9f9f9; border-radius: 3px; text-align: center; }"
                + ".nav { display: flex; justify-content: space-between; margin-top: 20px; }"
                + ".nav a { padding: 10px; color: white; text-decoration: none; border-radius: 3px; text-align: center; }"
                + ".home { background-color: #2196F3; }"
                + ".logout { background-color: #f44336; }"
                + ".home:hover { background-color: #0b7dda; }"
                + ".logout:hover { background-color: #d32f2f; }"
                + "</style></head><body>"
                + "<div class=\"container\">"
                + "<h1>¡Juega a Piedra, Papel o Tijera!</h1>"
                + "<form action='/ppt' method='POST'>"
                + "<button name='opcion' value='Piedra'>Piedra</button>"
                + "<button name='opcion' value='Papel'>Papel</button>"
                + "<button name='opcion' value='Tijeras'>Tijeras</button>"
                + "</form>"
                + "<div class=\"result\">" + resultado + "</div>"
                + "<div class=\"nav\">"
                + "<a href='/' class=\"home\">Volver al Inicio</a>"
                + "<a href='/logout' class=\"logout\">Cerrar Sesión</a>"
                + "</div>"
                + "</div></body></html>";
    }

    /**
     * Genera HTML para el juego "Lanza Dados" con botón de cerrar sesión
     *
     * @param resultado Resultado del juego
     * @return Página HTML completa
     */
    public static String generarHtmlDados(String resultado) {
        Random random = new Random();
        int dadoCliente = random.nextInt(6) + 1;
        return "<html><head><title>Lanza Dados</title><link rel=icon href=data:,/>"
                + "<style>"
                + "body { font-family: Arial, sans-serif; margin: 0; padding: 20px; background-color: #f5f5f5; }"
                + ".container { max-width: 800px; margin: 0 auto; background-color: white; padding: 20px; border-radius: 5px; box-shadow: 0 0 10px rgba(0,0,0,0.1); }"
                + "h1 { text-align: center; color: #333; }"
                + "form { text-align: center; margin-bottom: 20px; }"
                + "button { background-color: #4CAF50; color: white; padding: 10px 20px; border: none; cursor: pointer; border-radius: 3px; font-size: 16px; }"
                + "button:hover { background-color: #45a049; }"
                + ".result { padding: 15px; background-color: #f9f9f9; border-radius: 3px; text-align: center; }"
                + ".nav { display: flex; justify-content: space-between; margin-top: 20px; }"
                + ".nav a { padding: 10px; color: white; text-decoration: none; border-radius: 3px; text-align: center; }"
                + ".home { background-color: #2196F3; }"
                + ".logout { background-color: #f44336; }"
                + ".home:hover { background-color: #0b7dda; }"
                + ".logout:hover { background-color: #d32f2f; }"
                + "</style></head><body>"
                + "<div class=\"container\">"
                + "<h1>¡Lanza Dados!</h1>"
                + "<form action='/dados' method='POST'>"
                + "<button name='lanzar' value='" + dadoCliente + "'>Lanzar Dados</button>"
                + "</form>"
                + "<div class=\"result\">" + resultado + "</div>"
                + "<div class=\"nav\">"
                + "<a href='/' class=\"home\">Volver al Inicio</a>"
                + "<a href='/logout' class=\"logout\">Cerrar Sesión</a>"
                + "</div>"
                + "</div></body></html>";
    }
}