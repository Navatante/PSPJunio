package SistemaInventario;

public class Paginas {

    /**
     * Página principal con formulario de ventas
     */
    public static final String html_principal = """
        <!DOCTYPE html>
        <html lang="es">
        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <title>TechStock - Sistema de Inventario</title>
            <style>
                body {
                    font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
                    margin: 0;
                    padding: 0;
                    background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
                    min-height: 100vh;
                }

                .header {
                    background: rgba(255,255,255,0.1);
                    color: white;
                    text-align: center;
                    padding: 2rem;
                    margin-bottom: 2rem;
                }

                .header h1 {
                    margin: 0;
                    font-size: 2.5rem;
                    text-shadow: 2px 2px 4px rgba(0,0,0,0.3);
                }

                .header p {
                    margin: 0.5rem 0 0 0;
                    font-size: 1.1rem;
                    opacity: 0.9;
                }

                .container {
                    max-width: 600px;
                    margin: 0 auto;
                    padding: 0 1rem;
                }

                .form-card {
                    background: white;
                    border-radius: 15px;
                    padding: 2rem;
                    box-shadow: 0 10px 30px rgba(0,0,0,0.2);
                    margin-bottom: 2rem;
                }

                .form-group {
                    margin-bottom: 1.5rem;
                }

                label {
                    display: block;
                    margin-bottom: 0.5rem;
                    font-weight: 600;
                    color: #333;
                    font-size: 1.1rem;
                }

                select, input[type="number"] {
                    width: 100%;
                    padding: 1rem;
                    border: 2px solid #e1e5e9;
                    border-radius: 8px;
                    font-size: 1rem;
                    transition: border-color 0.3s ease;
                    box-sizing: border-box;
                }

                select:focus, input[type="number"]:focus {
                    outline: none;
                    border-color: #667eea;
                    box-shadow: 0 0 0 3px rgba(102, 126, 234, 0.1);
                }

                .btn-group {
                    display: flex;
                    gap: 1rem;
                    margin-top: 2rem;
                }

                .btn {
                    flex: 1;
                    padding: 1rem 2rem;
                    border: none;
                    border-radius: 8px;
                    font-size: 1.1rem;
                    font-weight: 600;
                    cursor: pointer;
                    transition: all 0.3s ease;
                    text-decoration: none;
                    text-align: center;
                    display: inline-block;
                }

                .btn-primary {
                    background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
                    color: white;
                }

                .btn-secondary {
                    background: #6c757d;
                    color: white;
                }

                .btn:hover {
                    transform: translateY(-2px);
                    box-shadow: 0 5px 15px rgba(0,0,0,0.2);
                }

                .product-icon {
                    display: inline-block;
                    margin-right: 0.5rem;
                }
            </style>
        </head>
        <body>
            <div class="header">
                <h1>📱 TechStock</h1>
                <p>Sistema de Gestión de Inventario</p>
            </div>

            <div class="container">
                <div class="form-card">
                    <h2 style="text-align: center; color: #333; margin-bottom: 2rem;">Registrar Venta</h2>

                    <form method="POST" action="/venta">
                        <div class="form-group">
                            <label for="producto">
                                <span class="product-icon">🛍️</span>
                                Tipo de Producto:
                            </label>
                            <select id="producto" name="producto" required>
                                <option value="">-- Seleccionar producto --</option>
                                <option value="Smartphones">📱 Smartphones</option>
                                <option value="Laptops">💻 Laptops</option>
                                <option value="Tablets">📲 Tablets</option>
                                <option value="Auriculares">🎧 Auriculares</option>
                                <option value="Accesorios">🔌 Accesorios</option>
                            </select>
                        </div>

                        <div class="form-group">
                            <label for="cantidad">
                                <span class="product-icon">🔢</span>
                                Cantidad Vendida:
                            </label>
                            <input type="number" id="cantidad" name="cantidad" min="1" max="999" required>
                        </div>

                        <div class="btn-group">
                            <button type="submit" class="btn btn-primary">
                                🛒 Registrar Venta
                            </button>
                            <a href="/stock" class="btn btn-secondary">
                                📊 Ver Inventario
                            </a>
                        </div>
                    </form>
                </div>
            </div>
        </body>
        </html>
        """;

    /**
     * Página de consulta de inventario
     */
    public static final String html_stock = """
        <!DOCTYPE html>
        <html lang="es">
        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <title>TechStock - Inventario Actual</title>
            <style>
                body {
                    font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
                    margin: 0;
                    padding: 0;
                    background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
                    min-height: 100vh;
                }

                .header {
                    background: rgba(255,255,255,0.1);
                    color: white;
                    text-align: center;
                    padding: 2rem;
                    margin-bottom: 2rem;
                }

                .header h1 {
                    margin: 0;
                    font-size: 2.5rem;
                    text-shadow: 2px 2px 4px rgba(0,0,0,0.3);
                }

                .container {
                    max-width: 800px;
                    margin: 0 auto;
                    padding: 0 1rem;
                }

                .stock-card {
                    background: white;
                    border-radius: 15px;
                    padding: 2rem;
                    box-shadow: 0 10px 30px rgba(0,0,0,0.2);
                    margin-bottom: 2rem;
                }

                .stock-table {
                    width: 100%;
                    border-collapse: collapse;
                    margin-bottom: 2rem;
                }

                .stock-table th, .stock-table td {
                    padding: 1rem;
                    text-align: left;
                    border-bottom: 1px solid #e1e5e9;
                }

                .stock-table th {
                    background: #f8f9fa;
                    font-weight: 600;
                    color: #333;
                }

                .stock-item {
                    font-size: 1.1rem;
                }

                .stock-count {
                    font-weight: 600;
                    font-size: 1.2rem;
                }

                .stock-low {
                    color: #dc3545;
                }

                .stock-medium {
                    color: #ffc107;
                }

                .stock-high {
                    color: #28a745;
                }

                .btn {
                    display: inline-block;
                    padding: 1rem 2rem;
                    background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
                    color: white;
                    text-decoration: none;
                    border-radius: 8px;
                    font-weight: 600;
                    transition: all 0.3s ease;
                    text-align: center;
                }

                .btn:hover {
                    transform: translateY(-2px);
                    box-shadow: 0 5px 15px rgba(0,0,0,0.2);
                }

                .refresh-info {
                    text-align: center;
                    color: #666;
                    font-style: italic;
                    margin-bottom: 1rem;
                }
            </style>
        </head>
        <body>
            <div class="header">
                <h1>📊 Inventario Actual</h1>
                <p>Estado del stock en tiempo real</p>
            </div>

            <div class="container">
                <div class="stock-card">
                    <div class="refresh-info">
                        Última actualización: <span id="timestamp"></span>
                    </div>

                    <table class="stock-table">
                        <thead>
                            <tr>
                                <th>Producto</th>
                                <th>Stock Disponible</th>
                                <th>Estado</th>
                            </tr>
                        </thead>
                        <tbody>
                            <!-- STOCK_DATA_PLACEHOLDER -->
                        </tbody>
                    </table>

                    <div style="text-align: center;">
                        <a href="/" class="btn">⬅️ Volver a Ventas</a>
                    </div>
                </div>
            </div>

            <script>
                document.getElementById('timestamp').textContent = new Date().toLocaleString('es-ES');
            </script>
        </body>
        </html>
        """;

    /**
     * Página de error (stock insuficiente, etc.)
     */
    public static final String html_error = """
        <!DOCTYPE html>
        <html lang="es">
        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <title>TechStock - Error</title>
            <style>
                body {
                    font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
                    margin: 0;
                    padding: 0;
                    background: linear-gradient(135deg, #ff6b6b 0%, #ee5a52 100%);
                    min-height: 100vh;
                    display: flex;
                    align-items: center;
                    justify-content: center;
                }

                .error-card {
                    background: white;
                    border-radius: 15px;
                    padding: 3rem;
                    box-shadow: 0 10px 30px rgba(0,0,0,0.2);
                    text-align: center;
                    max-width: 500px;
                    margin: 1rem;
                }

                .error-icon {
                    font-size: 4rem;
                    margin-bottom: 1rem;
                }

                .error-title {
                    color: #dc3545;
                    font-size: 2rem;
                    margin-bottom: 1rem;
                    font-weight: 600;
                }

                .error-message {
                    color: #666;
                    font-size: 1.2rem;
                    margin-bottom: 2rem;
                    line-height: 1.5;
                }

                .btn {
                    display: inline-block;
                    padding: 1rem 2rem;
                    background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
                    color: white;
                    text-decoration: none;
                    border-radius: 8px;
                    font-weight: 600;
                    transition: all 0.3s ease;
                    margin: 0.5rem;
                }

                .btn:hover {
                    transform: translateY(-2px);
                    box-shadow: 0 5px 15px rgba(0,0,0,0.2);
                }

                .btn-secondary {
                    background: #6c757d;
                }
            </style>
        </head>
        <body>
            <div class="error-card">
                <div class="error-icon">⚠️</div>
                <h1 class="error-title">Error en la Operación</h1>
                <p class="error-message">
                    <!-- ERROR_MESSAGE_PLACEHOLDER -->
                </p>
                <div>
                    <a href="/" class="btn">🏠 Volver al Inicio</a>
                    <a href="/stock" class="btn btn-secondary">📊 Ver Inventario</a>
                </div>
            </div>
        </body>
        </html>
        """;

    /**
     * Página 404 - No encontrado
     */
    public static final String html_noEncontrado = """
        <!DOCTYPE html>
        <html lang="es">
        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <title>TechStock - Página no encontrada</title>
            <style>
                body {
                    font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
                    margin: 0;
                    padding: 0;
                    background: linear-gradient(135deg, #343a40 0%, #495057 100%);
                    min-height: 100vh;
                    display: flex;
                    align-items: center;
                    justify-content: center;
                }

                .error-card {
                    background: white;
                    border-radius: 15px;
                    padding: 3rem;
                    box-shadow: 0 10px 30px rgba(0,0,0,0.2);
                    text-align: center;
                    max-width: 500px;
                    margin: 1rem;
                }

                .error-number {
                    font-size: 6rem;
                    font-weight: bold;
                    color: #6c757d;
                    margin: 0;
                    line-height: 1;
                }

                .error-title {
                    color: #333;
                    font-size: 2rem;
                    margin: 1rem 0;
                    font-weight: 600;
                }

                .error-message {
                    color: #666;
                    font-size: 1.1rem;
                    margin-bottom: 2rem;
                    line-height: 1.5;
                }

                .btn {
                    display: inline-block;
                    padding: 1rem 2rem;
                    background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
                    color: white;
                    text-decoration: none;
                    border-radius: 8px;
                    font-weight: 600;
                    transition: all 0.3s ease;
                }

                .btn:hover {
                    transform: translateY(-2px);
                    box-shadow: 0 5px 15px rgba(0,0,0,0.2);
                }

                .robot {
                    font-size: 3rem;
                    margin-bottom: 1rem;
                }
            </style>
        </head>
        <body>
            <div class="error-card">
                <div class="robot">🤖</div>
                <h1 class="error-number">404</h1>
                <h2 class="error-title">Página no encontrada</h2>
                <p class="error-message">
                    Lo sentimos, la página que buscas no existe en el sistema TechStock.
                    <br>Verifica la URL o regresa al inicio.
                </p>
                <a href="/" class="btn">🏠 Volver al Inicio</a>
            </div>
        </body>
        </html>
        """;

    /**
     * Página de éxito tras registrar una venta
     */
    public static final String html_exito = """
        <!DOCTYPE html>
        <html lang="es">
        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <title>TechStock - Venta Registrada</title>
            <style>
                body {
                    font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
                    margin: 0;
                    padding: 0;
                    background: linear-gradient(135deg, #28a745 0%, #20c997 100%);
                    min-height: 100vh;
                    display: flex;
                    align-items: center;
                    justify-content: center;
                }

                .success-card {
                    background: white;
                    border-radius: 15px;
                    padding: 3rem;
                    box-shadow: 0 10px 30px rgba(0,0,0,0.2);
                    text-align: center;
                    max-width: 500px;
                    margin: 1rem;
                }

                .success-icon {
                    font-size: 4rem;
                    margin-bottom: 1rem;
                }

                .success-title {
                    color: #28a745;
                    font-size: 2rem;
                    margin-bottom: 1rem;
                    font-weight: 600;
                }

                .success-message {
                    color: #666;
                    font-size: 1.2rem;
                    margin-bottom: 2rem;
                    line-height: 1.5;
                }

                .btn {
                    display: inline-block;
                    padding: 1rem 2rem;
                    background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
                    color: white;
                    text-decoration: none;
                    border-radius: 8px;
                    font-weight: 600;
                    transition: all 0.3s ease;
                    margin: 0.5rem;
                }

                .btn:hover {
                    transform: translateY(-2px);
                    box-shadow: 0 5px 15px rgba(0,0,0,0.2);
                }

                .btn-secondary {
                    background: #6c757d;
                }
            </style>
        </head>
        <body>
            <div class="success-card">
                <div class="success-icon">✅</div>
                <h1 class="success-title">¡Venta Registrada!</h1>
                <p class="success-message">
                    <!-- SUCCESS_MESSAGE_PLACEHOLDER -->
                </p>
                <div>
                    <a href="/" class="btn">🛒 Nueva Venta</a>
                    <a href="/stock" class="btn btn-secondary">📊 Ver Inventario</a>
                </div>
            </div>
        </body>
        </html>
        """;
}
