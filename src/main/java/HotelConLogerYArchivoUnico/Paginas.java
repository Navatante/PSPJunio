package HotelConLogerYArchivoUnico;

public class Paginas {

    public static String html_reservas = """
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Hotel - Sistema de Reservas</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f0f2f5;
            margin: 0;
            padding: 20px;
        }
        .container {
            max-width: 500px;
            margin: 50px auto;
            background: white;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
        }
        .header {
            background: #4CAF50;
            color: white;
            text-align: center;
            padding: 20px;
            margin: -30px -30px 30px -30px;
            border-radius: 10px 10px 0 0;
        }
        .form-group {
            margin-bottom: 20px;
        }
        label {
            display: block;
            margin-bottom: 8px;
            font-weight: bold;
            color: #333;
        }
        select, input[type="number"] {
            width: 100%;
            padding: 12px;
            border: 2px solid #ddd;
            border-radius: 5px;
            font-size: 16px;
            box-sizing: border-box;
        }
        select:focus, input:focus {
            border-color: #4CAF50;
            outline: none;
        }
        .btn-reservar {
            width: 100%;
            background: #4CAF50;
            color: white;
            padding: 15px;
            border: none;
            border-radius: 5px;
            font-size: 18px;
            cursor: pointer;
            font-weight: bold;
        }
        .btn-reservar:hover {
            background: #45a049;
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="header">
            <h1>🏨 Hotel PelHilos</h1>
            <p>Sistema de Reservas</p>
        </div>
        
        <form method="POST" action="/">
            <div class="form-group">
                <label for="dia">Día de la semana:</label>
                <select name="dia" id="dia" required>
                    <option value="">-- Selecciona un día --</option>
                    <option value="lunes">Lunes</option>
                    <option value="martes">Martes</option>
                    <option value="miercoles">Miércoles</option>
                    <option value="jueves">Jueves</option>
                    <option value="viernes">Viernes</option>
                    <option value="sabado">Sábado</option>
                    <option value="domingo">Domingo</option>
                </select>
            </div>
            
            <div class="form-group">
                <label for="cantidad">Número de habitaciones:</label>
                <input type="number" name="cantidad" id="cantidad" min="1" max="50" required 
                       placeholder="Introduce el número de habitaciones">
            </div>
            
            <button type="submit" class="btn-reservar">
                ✓ Realizar Reserva
            </button>
        </form>
    </div>
</body>
</html>
""";

    public static String html_noEncontrado = """
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Página no encontrada - Hotel</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f8f9fa;
            margin: 0;
            padding: 20px;
            text-align: center;
        }
        .error-container {
            max-width: 600px;
            margin: 100px auto;
            background: white;
            padding: 40px;
            border-radius: 10px;
            box-shadow: 0 2px 15px rgba(0,0,0,0.1);
        }
        .error-code {
            font-size: 72px;
            font-weight: bold;
            color: #dc3545;
            margin-bottom: 20px;
        }
        .error-title {
            font-size: 24px;
            color: #333;
            margin-bottom: 15px;
        }
        .error-message {
            color: #666;
            font-size: 16px;
            margin-bottom: 30px;
            line-height: 1.5;
        }
        .btn-home {
            background: #007bff;
            color: white;
            padding: 12px 24px;
            text-decoration: none;
            border-radius: 5px;
            font-weight: bold;
            display: inline-block;
        }
        .btn-home:hover {
            background: #0056b3;
        }
        .hotel-icon {
            font-size: 48px;
            margin-bottom: 20px;
        }
    </style>
</head>
<body>
    <div class="error-container">
        <div class="hotel-icon">🏨</div>
        <div class="error-code">404</div>
        <div class="error-title">Página no encontrada</div>
        <div class="error-message">
            Lo sentimos, la página que buscas no existe en nuestro sistema de reservas.
            <br>
            Por favor, verifica la URL o regresa a la página principal.
        </div>
        <a href="/" class="btn-home">🏠 Volver al inicio</a>
    </div>
</body>
</html>
""";

    public static String html_exito = """
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Reserva Confirmada - Hotel</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f0f8f0;
            margin: 0;
            padding: 20px;
            text-align: center;
        }
        .success-container {
            max-width: 500px;
            margin: 100px auto;
            background: white;
            padding: 40px;
            border-radius: 10px;
            box-shadow: 0 2px 15px rgba(0,0,0,0.1);
            border-top: 5px solid #28a745;
        }
        .success-icon {
            font-size: 64px;
            color: #28a745;
            margin-bottom: 20px;
        }
        .success-title {
            font-size: 24px;
            color: #28a745;
            margin-bottom: 15px;
            font-weight: bold;
        }
        .success-message {
            color: #333;
            font-size: 16px;
            margin-bottom: 25px;
            line-height: 1.6;
        }
        .reservation-details {
            background: #f8f9fa;
            padding: 20px;
            border-radius: 5px;
            margin: 20px 0;
            border-left: 4px solid #28a745;
        }
        .btn-new {
            background: #28a745;
            color: white;
            padding: 12px 24px;
            text-decoration: none;
            border-radius: 5px;
            font-weight: bold;
            display: inline-block;
            margin-top: 15px;
        }
        .btn-new:hover {
            background: #218838;
        }
    </style>
</head>
<body>
    <div class="success-container">
        <div class="success-icon">✅</div>
        <div class="success-title">¡Reserva Confirmada!</div>
        <div class="success-message">
            Tu reserva ha sido procesada correctamente y registrada en nuestro sistema.
        </div>
        <div class="reservation-details">
            <strong>Detalles de la reserva:</strong><br>
            📅 Día: <strong>%s</strong><br>
            🏨 Habitaciones: <strong>%s</strong><br>
            📊 Total reservas para este día: <strong>%s</strong>
        </div>
        <a href="/" class="btn-new">📋 Nueva Reserva</a>
    </div>
</body>
</html>
""";
}