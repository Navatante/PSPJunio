package EjercicioPropuesto3;

public class Paginas {


    public static final String html_noEncontrado = "<html><head><title>Error 404</title><link rel=icon href=data:,/></head><body>"
            + "<h1>404 Página No Encontrada</h1>"
            + "<p>La página solicitada no existe.</p>"
            + "</body></html>";

    public static final String html_reservas = "<!DOCTYPE html>\n" +
            "<html lang=\"es\">\n" +
            "<head>\n" +
            "    <meta charset=\"UTF-8\">\n" +
            "    <link rel=icon href=data:,/>\n" +
            "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
            "    <title>Hotel – Reservas</title>\n" +
            "    <style>\n" +
            "        body {\n" +
            "            font-family: 'Arial', sans-serif;\n" +
            "            background-color: #eef2f3;\n" +
            "            margin: 0; padding: 0;\n" +
            "        }\n" +
            "        header {\n" +
            "            background-color: #4CAF50;\n" +
            "            color: white;\n" +
            "            text-align: center;\n" +
            "            padding: 20px 0;\n" +
            "        }\n" +
            "        .container {\n" +
            "            max-width: 450px;\n" +
            "            margin: 40px auto;\n" +
            "            background-color: white;\n" +
            "            padding: 25px;\n" +
            "            box-shadow: 0 0 12px rgba(0,0,0,0.1);\n" +
            "            border-radius: 8px;\n" +
            "        }\n" +
            "        label {\n" +
            "            display: block;\n" +
            "            font-weight: bold;\n" +
            "            margin-top: 10px;\n" +
            "        }\n" +
            "        select, input[type=\"number\"] {\n" +
            "            width: 100%;\n" +
            "            padding: 10px;\n" +
            "            margin-top: 5px;\n" +
            "            margin-bottom: 15px;\n" +
            "            border: 1px solid #ccc;\n" +
            "            border-radius: 4px;\n" +
            "            font-size: 15px;\n" +
            "        }\n" +
            "        button {\n" +
            "            width: 100%;\n" +
            "            padding: 12px;\n" +
            "            background-color: #4CAF50;\n" +
            "            color: white;\n" +
            "            font-size: 16px;\n" +
            "            border: none;\n" +
            "            border-radius: 4px;\n" +
            "            cursor: pointer;\n" +
            "            transition: background-color 0.3s ease;\n" +
            "        }\n" +
            "        button:hover {\n" +
            "            background-color: #45a049;\n" +
            "        }\n" +
            "    </style>\n" +
            "</head>\n" +
            "<body>\n" +
            "    <header>\n" +
            "        <h1>Hotel PelHilos a la mar</h1>\n" +
            "        <p>Registro de reservas por día</p>\n" +
            "    </header>\n" +
            "    <div class=\"container\">\n" +
            "        <form action=\"/\" method=\"POST\">\n" +
            "            <label for=\"dia\">Día de la semana:</label>\n" +
            "            <select id=\"dia\" name=\"dia\">\n" +
            "                <option value=\"lunes\">Lunes</option>\n" +
            "                <option value=\"martes\">Martes</option>\n" +
            "                <option value=\"miercoles\">Miércoles</option>\n" +
            "                <option value=\"jueves\">Jueves</option>\n" +
            "                <option value=\"viernes\">Viernes</option>\n" +
            "                <option value=\"sabado\">Sábado</option>\n" +
            "                <option value=\"domingo\">Domingo</option>\n" +
            "            </select>\n" +
            "            <label for=\"cantidad\">Número de habitaciones:</label>\n" +
            "            <input type=\"number\" id=\"cantidad\" name=\"cantidad\" min=\"1\" required autofocus>\n" +
            "            <button type=\"submit\">Reservar</button>\n" +
            "        </form>\n" +
            "    </div>\n" +
            "</body>\n" +
            "</html>";

}