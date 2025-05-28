package HotelComentarios;

public class Paginas {

    public static final String html_noEncontrado = "<html><head><title>Error 404</title><link rel=icon href=data:,/></head><body>"
            + "<h1>404 Página No Encontrada</h1>"
            + "<p>La página solicitada no existe.</p>"
            + "</body></html>";

    public static final String html_comentarios = """
            <!DOCTYPE html>
            <html lang="es">
            <head>
                <meta charset="UTF-8">
                <link rel=icon href=data:,/>
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>Hotel PelHilos – Sistema de Comentarios</title>
                <style>
                    body {
                        font-family: 'Arial', sans-serif;
                        background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
                        margin: 0; padding: 0;
                        min-height: 100vh;
                    }
                    header {
                        background-color: rgba(76, 175, 80, 0.9);
                        color: white;
                        text-align: center;
                        padding: 25px 0;
                        box-shadow: 0 2px 10px rgba(0,0,0,0.1);
                    }
                    .container {
                        max-width: 550px;
                        margin: 30px auto;
                        background-color: white;
                        padding: 35px;
                        box-shadow: 0 8px 25px rgba(0,0,0,0.15);
                        border-radius: 12px;
                    }
                    .form-title {
                        text-align: center;
                        color: #333;
                        margin-bottom: 25px;
                        font-size: 24px;
                    }
                    label {
                        display: block;
                        font-weight: bold;
                        margin-top: 15px;
                        color: #555;
                    }
                    select, textarea {
                        width: 100%;
                        padding: 12px;
                        margin-top: 8px;
                        margin-bottom: 18px;
                        border: 2px solid #ddd;
                        border-radius: 6px;
                        font-size: 15px;
                        transition: border-color 0.3s ease;
                    }
                    select:focus, textarea:focus {
                        outline: none;
                        border-color: #4CAF50;
                    }
                    textarea {
                        resize: vertical;
                        min-height: 100px;
                        font-family: 'Arial', sans-serif;
                    }
                    .rating-container {
                        margin: 15px 0;
                    }
                    .rating {
                        display: flex;
                        gap: 5px;
                        margin-top: 8px;
                    }
                    .rating input[type="radio"] {
                        display: none;
                    }
                    .rating label {
                        font-size: 25px;
                        color: #ddd;
                        cursor: pointer;
                        transition: color 0.2s ease;
                        margin: 0;
                    }
                    .rating label:hover,
                    .rating label:hover ~ label,
                    .rating input[type="radio"]:checked ~ label {
                        color: #ffd700;
                    }
                    button {
                        width: 100%;
                        padding: 15px;
                        background: linear-gradient(45deg, #4CAF50, #45a049);
                        color: white;
                        font-size: 18px;
                        font-weight: bold;
                        border: none;
                        border-radius: 8px;
                        cursor: pointer;
                        transition: all 0.3s ease;
                        margin-top: 10px;
                    }
                    button:hover {
                        background: linear-gradient(45deg, #45a049, #3d8b40);
                        transform: translateY(-2px);
                        box-shadow: 0 4px 12px rgba(0,0,0,0.2);
                    }
                    .info-text {
                        font-size: 14px;
                        color: #666;
                        text-align: center;
                        margin-bottom: 20px;
                    }
                </style>
            </head>
            <body>
                <header>
                    <h1>🏨 Hotel PelHilos a la Mar</h1>
                    <p>Comparte tu experiencia con nosotros</p>
                </header>
                <div class="container">
                    <h2 class="form-title">💬 Sistema de Comentarios</h2>
                    <p class="info-text">Tu opinión es muy importante para nosotros. Por favor, comparte tu experiencia.</p>
                    <form action="/" method="POST">
                        <label for="tipo">📋 Tipo de comentario:</label>
                        <select id="tipo" name="tipo" required>
                            <option value="">-- Selecciona una categoría --</option>
                            <option value="servicio_habitacion">🛎️ Servicio de habitación</option>
                            <option value="restaurante">🍽️ Restaurante</option>
                            <option value="recepcion">🏨 Recepción</option>
                            <option value="limpieza">🧹 Limpieza</option>
                            <option value="instalaciones">🏊 Instalaciones</option>
                            <option value="sugerencias">💡 Sugerencias generales</option>
                        </select>
                        
                        <div class="rating-container">
                            <label>⭐ Valoración:</label>
                            <div class="rating">
                                <input type="radio" id="star5" name="valoracion" value="5" required>
                                <label for="star5">★</label>
                                <input type="radio" id="star4" name="valoracion" value="4">
                                <label for="star4">★</label>
                                <input type="radio" id="star3" name="valoracion" value="3">
                                <label for="star3">★</label>
                                <input type="radio" id="star2" name="valoracion" value="2">
                                <label for="star2">★</label>
                                <input type="radio" id="star1" name="valoracion" value="1">
                                <label for="star1">★</label>
                            </div>
                        </div>
                        
                        <label for="comentario">✍️ Tu comentario:</label>
                        <textarea id="comentario" name="comentario" placeholder="Cuéntanos sobre tu experiencia..." required autofocus></textarea>
                        
                        <button type="submit">📤 Enviar Comentario</button>
                    </form>
                </div>
                
                <script>
                    // Mejorar la interacción de las estrellas
                    const ratings = document.querySelectorAll('.rating input');
                    const labels = document.querySelectorAll('.rating label');
                    
                    labels.forEach((label, index) => {
                        label.addEventListener('mouseover', () => {
                            labels.forEach((l, i) => {
                                if (i >= index) {
                                    l.style.color = '#ffd700';
                                } else {
                                    l.style.color = '#ddd';
                                }
                            });
                        });
                    });
                    
                    document.querySelector('.rating').addEventListener('mouseleave', () => {
                        const checked = document.querySelector('.rating input:checked');
                        labels.forEach((label, index) => {
                            if (checked && index >= Array.from(ratings).indexOf(checked)) {
                                label.style.color = '#ffd700';
                            } else {
                                label.style.color = '#ddd';
                            }
                        });
                    });
                </script>
            </body>
            </html>
            """;

}