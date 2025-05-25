package HotelComentarios;

public class Paginas {

    public static final String html_noEncontrado = "<html><head><title>Error 404</title><link rel=icon href=data:,/></head><body>"
            + "<h1>404 Página No Encontrada</h1>"
            + "<p>La página solicitada no existe.</p>"
            + "</body></html>";

    public static final String html_comentarios = "<!DOCTYPE html>\\n" +
            "<html lang=\\\"es\\\">\\n" +
            "<head>\\n" +
            "    <meta charset=\\\"UTF-8\\\">\\n" +
            "    <link rel=icon href=data:,/>\\n" +
            "    <meta name=\\\"viewport\\\" content=\\\"width=device-width, initial-scale=1.0\\\">\\n" +
            "    <title>Hotel PelHilos – Sistema de Comentarios</title>\\n" +
            "    <style>\\n" +
            "        body {\\n" +
            "            font-family: 'Arial', sans-serif;\\n" +
            "            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);\\n" +
            "            margin: 0; padding: 0;\\n" +
            "            min-height: 100vh;\\n" +
            "        }\\n" +
            "        header {\\n" +
            "            background-color: rgba(76, 175, 80, 0.9);\\n" +
            "            color: white;\\n" +
            "            text-align: center;\\n" +
            "            padding: 25px 0;\\n" +
            "            box-shadow: 0 2px 10px rgba(0,0,0,0.1);\\n" +
            "        }\\n" +
            "        .container {\\n" +
            "            max-width: 550px;\\n" +
            "            margin: 30px auto;\\n" +
            "            background-color: white;\\n" +
            "            padding: 35px;\\n" +
            "            box-shadow: 0 8px 25px rgba(0,0,0,0.15);\\n" +
            "            border-radius: 12px;\\n" +
            "        }\\n" +
            "        .form-title {\\n" +
            "            text-align: center;\\n" +
            "            color: #333;\\n" +
            "            margin-bottom: 25px;\\n" +
            "            font-size: 24px;\\n" +
            "        }\\n" +
            "        label {\\n" +
            "            display: block;\\n" +
            "            font-weight: bold;\\n" +
            "            margin-top: 15px;\\n" +
            "            color: #555;\\n" +
            "        }\\n" +
            "        select, textarea {\\n" +
            "            width: 100%;\\n" +
            "            padding: 12px;\\n" +
            "            margin-top: 8px;\\n" +
            "            margin-bottom: 18px;\\n" +
            "            border: 2px solid #ddd;\\n" +
            "            border-radius: 6px;\\n" +
            "            font-size: 15px;\\n" +
            "            transition: border-color 0.3s ease;\\n" +
            "        }\\n" +
            "        select:focus, textarea:focus {\\n" +
            "            outline: none;\\n" +
            "            border-color: #4CAF50;\\n" +
            "        }\\n" +
            "        textarea {\\n" +
            "            resize: vertical;\\n" +
            "            min-height: 100px;\\n" +
            "            font-family: 'Arial', sans-serif;\\n" +
            "        }\\n" +
            "        .rating-container {\\n" +
            "            margin: 15px 0;\\n" +
            "        }\\n" +
            "        .rating {\\n" +
            "            display: flex;\\n" +
            "            gap: 5px;\\n" +
            "            margin-top: 8px;\\n" +
            "        }\\n" +
            "        .rating input[type=\\\"radio\\\"] {\\n" +
            "            display: none;\\n" +
            "        }\\n" +
            "        .rating label {\\n" +
            "            font-size: 25px;\\n" +
            "            color: #ddd;\\n" +
            "            cursor: pointer;\\n" +
            "            transition: color 0.2s ease;\\n" +
            "            margin: 0;\\n" +
            "        }\\n" +
            "        .rating label:hover,\\n" +
            "        .rating label:hover ~ label,\\n" +
            "        .rating input[type=\\\"radio\\\"]:checked ~ label {\\n" +
            "            color: #ffd700;\\n" +
            "        }\\n" +
            "        button {\\n" +
            "            width: 100%;\\n" +
            "            padding: 15px;\\n" +
            "            background: linear-gradient(45deg, #4CAF50, #45a049);\\n" +
            "            color: white;\\n" +
            "            font-size: 18px;\\n" +
            "            font-weight: bold;\\n" +
            "            border: none;\\n" +
            "            border-radius: 8px;\\n" +
            "            cursor: pointer;\\n" +
            "            transition: all 0.3s ease;\\n" +
            "            margin-top: 10px;\\n" +
            "        }\\n" +
            "        button:hover {\\n" +
            "            background: linear-gradient(45deg, #45a049, #3d8b40);\\n" +
            "            transform: translateY(-2px);\\n" +
            "            box-shadow: 0 4px 12px rgba(0,0,0,0.2);\\n" +
            "        }\\n" +
            "        .info-text {\\n" +
            "            font-size: 14px;\\n" +
            "            color: #666;\\n" +
            "            text-align: center;\\n" +
            "            margin-bottom: 20px;\\n" +
            "        }\\n" +
            "    </style>\\n" +
            "</head>\\n" +
            "<body>\\n" +
            "    <header>\\n" +
            "        <h1>🏨 Hotel PelHilos a la Mar</h1>\\n" +
            "        <p>Comparte tu experiencia con nosotros</p>\\n" +
            "    </header>\\n" +
            "    <div class=\\\"container\\\">\\n" +
            "        <h2 class=\\\"form-title\\\">💬 Sistema de Comentarios</h2>\\n" +
            "        <p class=\\\"info-text\\\">Tu opinión es muy importante para nosotros. Por favor, comparte tu experiencia.</p>\\n" +
            "        <form action=\\\"/\\\" method=\\\"POST\\\">\\n" +
            "            <label for=\\\"tipo\\\">📋 Tipo de comentario:</label>\\n" +
            "            <select id=\\\"tipo\\\" name=\\\"tipo\\\" required>\\n" +
            "                <option value=\\\"\\\">-- Selecciona una categoría --</option>\\n" +
            "                <option value=\\\"servicio_habitacion\\\">🛎️ Servicio de habitación</option>\\n" +
            "                <option value=\\\"restaurante\\\">🍽️ Restaurante</option>\\n" +
            "                <option value=\\\"recepcion\\\">🏨 Recepción</option>\\n" +
            "                <option value=\\\"limpieza\\\">🧹 Limpieza</option>\\n" +
            "                <option value=\\\"instalaciones\\\">🏊 Instalaciones</option>\\n" +
            "                <option value=\\\"sugerencias\\\">💡 Sugerencias generales</option>\\n" +
            "            </select>\\n" +
            "            \\n" +
            "            <div class=\\\"rating-container\\\">\\n" +
            "                <label>⭐ Valoración:</label>\\n" +
            "                <div class=\\\"rating\\\">\\n" +
            "                    <input type=\\\"radio\\\" id=\\\"star5\\\" name=\\\"valoracion\\\" value=\\\"5\\\" required>\\n" +
            "                    <label for=\\\"star5\\\">★</label>\\n" +
            "                    <input type=\\\"radio\\\" id=\\\"star4\\\" name=\\\"valoracion\\\" value=\\\"4\\\">\\n" +
            "                    <label for=\\\"star4\\\">★</label>\\n" +
            "                    <input type=\\\"radio\\\" id=\\\"star3\\\" name=\\\"valoracion\\\" value=\\\"3\\\">\\n" +
            "                    <label for=\\\"star3\\\">★</label>\\n" +
            "                    <input type=\\\"radio\\\" id=\\\"star2\\\" name=\\\"valoracion\\\" value=\\\"2\\\">\\n" +
            "                    <label for=\\\"star2\\\">★</label>\\n" +
            "                    <input type=\\\"radio\\\" id=\\\"star1\\\" name=\\\"valoracion\\\" value=\\\"1\\\">\\n" +
            "                    <label for=\\\"star1\\\">★</label>\\n" +
            "                </div>\\n" +
            "            </div>\\n" +
            "            \\n" +
            "            <label for=\\\"comentario\\\">✍️ Tu comentario:</label>\\n" +
            "            <textarea id=\\\"comentario\\\" name=\\\"comentario\\\" placeholder=\\\"Cuéntanos sobre tu experiencia...\\\" required autofocus></textarea>\\n" +
            "            \\n" +
            "            <button type=\\\"submit\\\">📤 Enviar Comentario</button>\\n" +
            "        </form>\\n" +
            "    </div>\\n" +
            "    \\n" +
            "    <script>\\n" +
            "        // Mejorar la interacción de las estrellas\\n" +
            "        const ratings = document.querySelectorAll('.rating input');\\n" +
            "        const labels = document.querySelectorAll('.rating label');\\n" +
            "        \\n" +
            "        labels.forEach((label, index) => {\\n" +
            "            label.addEventListener('mouseover', () => {\\n" +
            "                labels.forEach((l, i) => {\\n" +
            "                    if (i >= index) {\\n" +
            "                        l.style.color = '#ffd700';\\n" +
            "                    } else {\\n" +
            "                        l.style.color = '#ddd';\\n" +
            "                    }\\n" +
            "                });\\n" +
            "            });\\n" +
            "        });\\n" +
            "        \\n" +
            "        document.querySelector('.rating').addEventListener('mouseleave', () => {\\n" +
            "            const checked = document.querySelector('.rating input:checked');\\n" +
            "            labels.forEach((label, index) => {\\n" +
            "                if (checked && index >= Array.from(ratings).indexOf(checked)) {\\n" +
            "                    label.style.color = '#ffd700';\\n" +
            "                } else {\\n" +
            "                    label.style.color = '#ddd';\\n" +
            "                }\\n" +
            "            });\\n" +
            "        });\\n" +
            "    </script>\\n" +
            "</body>\\n" +
            "</html>\";";

}
