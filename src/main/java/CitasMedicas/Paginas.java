package CitasMedicas;

public class Paginas {

    public static final String html_noEncontrado = "<html><head><title>Error 404</title><link rel=icon href=data:,/></head><body>" +
            "<h1>404 Página No Encontrada</h1>" +
            "<p>La página solicitada no existe en el sistema ClinicaCare.</p>" +
            "<a href=\"/\">Volver al inicio</a>" +
            "</body></html>";

    public static final String html_citas = "<!DOCTYPE html>\n" +
            "<html lang=\"es\">\n" +
            "<head>\n" +
            "    <meta charset=\"UTF-8\">\n" +
            "    <link rel=\"icon\" href=\"data:,/\">\n" +
            "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
            "    <title>ClinicaCare – Sistema de Citas</title>\n" +
            "    <style>\n" +
            "        body {\n" +
            "            font-family: 'Arial', sans-serif;\n" +
            "            background: linear-gradient(135deg, #74b9ff 0%, #0984e3 100%);\n" +
            "            margin: 0; padding: 0;\n" +
            "            min-height: 100vh;\n" +
            "        }\n" +
            "        header {\n" +
            "            background: rgba(255,255,255,0.1);\n" +
            "            color: white;\n" +
            "            text-align: center;\n" +
            "            padding: 30px 0;\n" +
            "            box-shadow: 0 2px 10px rgba(0,0,0,0.1);\n" +
            "        }\n" +
            "        header h1 {\n" +
            "            margin: 0;\n" +
            "            font-size: 2.5rem;\n" +
            "            text-shadow: 2px 2px 4px rgba(0,0,0,0.3);\n" +
            "        }\n" +
            "        header p {\n" +
            "            margin: 10px 0 0 0;\n" +
            "            font-size: 1.2rem;\n" +
            "            opacity: 0.9;\n" +
            "        }\n" +
            "        .container {\n" +
            "            max-width: 500px;\n" +
            "            margin: 40px auto;\n" +
            "            background-color: white;\n" +
            "            padding: 30px;\n" +
            "            box-shadow: 0 10px 30px rgba(0,0,0,0.2);\n" +
            "            border-radius: 15px;\n" +
            "        }\n" +
            "        h2 {\n" +
            "            text-align: center;\n" +
            "            color: #2d3436;\n" +
            "            margin-bottom: 25px;\n" +
            "            font-size: 1.5rem;\n" +
            "        }\n" +
            "        label {\n" +
            "            display: block;\n" +
            "            font-weight: bold;\n" +
            "            margin-top: 15px;\n" +
            "            margin-bottom: 5px;\n" +
            "            color: #2d3436;\n" +
            "            font-size: 1.1rem;\n" +
            "        }\n" +
            "        select, input[type=\"number\"] {\n" +
            "            width: 100%;\n" +
            "            padding: 12px;\n" +
            "            margin-bottom: 20px;\n" +
            "            border: 2px solid #ddd;\n" +
            "            border-radius: 8px;\n" +
            "            font-size: 16px;\n" +
            "            transition: border-color 0.3s ease;\n" +
            "            box-sizing: border-box;\n" +
            "        }\n" +
            "        select:focus, input[type=\"number\"]:focus {\n" +
            "            outline: none;\n" +
            "            border-color: #74b9ff;\n" +
            "            box-shadow: 0 0 0 3px rgba(116, 185, 255, 0.2);\n" +
            "        }\n" +
            "        button {\n" +
            "            width: 100%;\n" +
            "            padding: 15px;\n" +
            "            background: linear-gradient(135deg, #74b9ff 0%, #0984e3 100%);\n" +
            "            color: white;\n" +
            "            font-size: 18px;\n" +
            "            font-weight: bold;\n" +
            "            border: none;\n" +
            "            border-radius: 8px;\n" +
            "            cursor: pointer;\n" +
            "            transition: all 0.3s ease;\n" +
            "        }\n" +
            "        button:hover {\n" +
            "            transform: translateY(-2px);\n" +
            "            box-shadow: 0 5px 15px rgba(116, 185, 255, 0.4);\n" +
            "        }\n" +
            "        .specialty-icon {\n" +
            "            margin-right: 8px;\n" +
            "        }\n" +
            "        .form-group {\n" +
            "            margin-bottom: 20px;\n" +
            "        }\n" +
            "    </style>\n" +
            "</head>\n" +
            "<body>\n" +
            "    <header>\n" +
            "        <h1>🏥 ClinicaCare</h1>\n" +
            "        <p>Sistema de Gestión de Citas Médicas</p>\n" +
            "    </header>\n" +
            "    \n" +
            "    <div class=\"container\">\n" +
            "        <h2>📅 Registrar Nueva Cita</h2>\n" +
            "        \n" +
            "        <form action=\"/\" method=\"POST\">\n" +
            "            <div class=\"form-group\">\n" +
            "                <label for=\"especialidad\">\n" +
            "                    <span class=\"specialty-icon\">👨‍⚕️</span>\n" +
            "                    Especialidad Médica:\n" +
            "                </label>\n" +
            "                <select id=\"especialidad\" name=\"especialidad\" required>\n" +
            "                    <option value=\"\">-- Seleccionar especialidad --</option>\n" +
            "                    <option value=\"medicina_general\">🩺 Medicina General</option>\n" +
            "                    <option value=\"cardiologia\">❤️ Cardiología</option>\n" +
            "                    <option value=\"dermatologia\">🧴 Dermatología</option>\n" +
            "                    <option value=\"pediatria\">👶 Pediatría</option>\n" +
            "                    <option value=\"traumatologia\">🦴 Traumatología</option>\n" +
            "                </select>\n" +
            "            </div>\n" +
            "            \n" +
            "            <div class=\"form-group\">\n" +
            "                <label for=\"cantidad\">\n" +
            "                    <span class=\"specialty-icon\">🔢</span>\n" +
            "                    Número de Citas:\n" +
            "                </label>\n" +
            "                <input type=\"number\" id=\"cantidad\" name=\"cantidad\" min=\"1\" max=\"50\" required autofocus>\n" +
            "            </div>\n" +
            "            \n" +
            "            <button type=\"submit\">\n" +
            "                📝 Registrar Citas\n" +
            "            </button>\n" +
            "        </form>\n" +
            "        \n" +
            "        <div style=\"text-align: center; margin-top: 20px; color: #636e72; font-size: 0.9rem;\">\n" +
            "            💡 Las citas se acumulan por especialidad médica\n" +
            "        </div>\n" +
            "    </div>\n" +
            "</body>\n" +
            "</html>";
}