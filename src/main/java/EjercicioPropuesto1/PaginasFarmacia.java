package EjercicioPropuesto1;

/**
 * Clase que contiene las páginas HTML del servidor de farmacia.
 */
public class PaginasFarmacia {

    /**
     * Página principal de la farmacia con formulario de venta
     * @param mensaje Mensaje a mostrar (éxito, error, o vacío)
     * @param stockActual String con el stock actual para mostrar
     * @return Página HTML completa
     */
    public static String generarPaginaPrincipal(String mensaje, String stockActual) {
        return "<html><head><title>FarmaciaViva - Sistema de Ventas</title><meta charset=UTF-8>"
                + "<link rel=icon href=data:,/>"
                + "<style>"
                + "body { font-family: Arial, sans-serif; margin: 0; padding: 20px; background-color: #f0f8ff; }"
                + ".container { max-width: 800px; margin: 0 auto; background-color: white; padding: 30px; border-radius: 10px; box-shadow: 0 0 15px rgba(0,0,0,0.1); }"
                + "h1 { text-align: center; color: #2c5aa0; margin-bottom: 30px; }"
                + "h2 { color: #4a6fa5; border-bottom: 2px solid #e1ecf4; padding-bottom: 10px; }"
                + ".stock-section { background-color: #f8f9fa; padding: 20px; border-radius: 8px; margin-bottom: 30px; }"
                + ".stock-item { display: flex; justify-content: space-between; align-items: center; padding: 10px 0; border-bottom: 1px solid #dee2e6; }"
                + ".stock-item:last-child { border-bottom: none; }"
                + ".producto-nombre { font-weight: bold; color: #495057; }"
                + ".producto-stock { background-color: #28a745; color: white; padding: 5px 10px; border-radius: 15px; font-size: 14px; }"
                + ".producto-stock.bajo { background-color: #dc3545; }"
                + ".producto-stock.medio { background-color: #ffc107; color: #212529; }"
                + ".form-section { background-color: #ffffff; padding: 20px; border: 2px solid #e1ecf4; border-radius: 8px; }"
                + "form { margin-bottom: 20px; }"
                + "label { display: block; margin-bottom: 8px; font-weight: bold; color: #495057; }"
                + "select, input[type=number] { width: 100%; padding: 12px; margin-bottom: 15px; border: 2px solid #ced4da; border-radius: 5px; font-size: 16px; }"
                + "select:focus, input[type=number]:focus { outline: none; border-color: #4a6fa5; }"
                + "button { background-color: #28a745; color: white; padding: 12px 20px; border: none; cursor: pointer; width: 100%; border-radius: 5px; font-size: 16px; font-weight: bold; }"
                + "button:hover { background-color: #218838; }"
                + ".mensaje { padding: 15px; margin-bottom: 20px; border-radius: 5px; font-weight: bold; text-align: center; }"
                + ".success { background-color: #d4edda; color: #155724; border: 1px solid #c3e6cb; }"
                + ".error { background-color: #f8d7da; color: #721c24; border: 1px solid #f5c6cb; }"
                + ".footer { text-align: center; margin-top: 30px; color: #6c757d; font-size: 14px; }"
                + "</style></head><body>"
                + "<div class=\"container\">"
                + "<h1>🏥 FarmaciaViva - Sistema de Ventas</h1>"

                // Sección de stock actual
                + "<div class=\"stock-section\">"
                + "<h2>📦 Stock Actual</h2>"
                + stockActual
                + "</div>"

                // Mensaje de resultado (si existe)
                + (mensaje.isEmpty() ? "" : "<div class=\"mensaje " + (mensaje.contains("Error") || mensaje.contains("insuficiente") ? "error" : "success") + "\">" + mensaje + "</div>")

                // Formulario de venta
                + "<div class=\"form-section\">"
                + "<h2>💊 Realizar Venta</h2>"
                + "<form action=\"/\" method=\"POST\">"
                + "<label for=\"producto\">Seleccionar Producto:</label>"
                + "<select id=\"producto\" name=\"producto\" required>"
                + "<option value=\"\">-- Seleccione un producto --</option>"
                + "<option value=\"Paracetamol\">Paracetamol</option>"
                + "<option value=\"Ibuprofeno\">Ibuprofeno</option>"
                + "<option value=\"Vitamina C\">Vitamina C</option>"
                + "</select>"
                + "<label for=\"cantidad\">Cantidad:</label>"
                + "<input type=\"number\" id=\"cantidad\" name=\"cantidad\" min=\"1\" max=\"50\" required placeholder=\"Ingrese la cantidad\">"
                + "<button type=\"submit\">🛒 Procesar Venta</button>"
                + "</form>"
                + "</div>"

                + "<div class=\"footer\">"
                + "<p>© 2025 FarmaciaViva - Sistema de Gestión de Stock</p>"
                + "</div>"
                + "</div></body></html>";
    }

    /**
     * Genera el HTML para mostrar el stock actual
     * @param paracetamol Stock de Paracetamol
     * @param ibuprofeno Stock de Ibuprofeno
     * @param vitaminaC Stock de Vitamina C
     * @return HTML del stock formateado
     */
    public static String generarStockHTML(int paracetamol, int ibuprofeno, int vitaminaC) {
        StringBuilder stock = new StringBuilder();

        stock.append(generarItemStock("Paracetamol", paracetamol));
        stock.append(generarItemStock("Ibuprofeno", ibuprofeno));
        stock.append(generarItemStock("Vitamina C", vitaminaC));

        return stock.toString();
    }

    /**
     * Genera un item individual del stock
     * @param producto Nombre del producto
     * @param cantidad Cantidad disponible
     * @return HTML del item
     */
    private static String generarItemStock(String producto, int cantidad) {
        String claseStock = "producto-stock";
        if (cantidad <= 3) {
            claseStock += " bajo";
        } else if (cantidad <= 7) {
            claseStock += " medio";
        }

        return "<div class=\"stock-item\">"
                + "<span class=\"producto-nombre\">" + producto + "</span>"
                + "<span class=\"" + claseStock + "\">" + cantidad + " unidades</span>"
                + "</div>";
    }

    /**
     * Página de error 404 personalizada para la farmacia
     */
    public static final String html_404 = "<html><head><title>Error 404 - FarmaciaViva</title><meta charset=UTF-8>"
            + "<link rel=icon href=data:,/>"
            + "<style>"
            + "body { font-family: Arial, sans-serif; text-align: center; padding: 50px; background-color: #f0f8ff; }"
            + ".container { max-width: 500px; margin: 0 auto; background-color: white; padding: 40px; border-radius: 10px; box-shadow: 0 0 15px rgba(0,0,0,0.1); }"
            + "h1 { color: #dc3545; font-size: 72px; margin: 0; }"
            + "h2 { color: #495057; margin-bottom: 20px; }"
            + "p { color: #6c757d; margin-bottom: 20px; line-height: 1.5; }"
            + "a { display: inline-block; background-color: #28a745; color: white; padding: 12px 24px; text-decoration: none; border-radius: 5px; font-weight: bold; }"
            + "a:hover { background-color: #218838; }"
            + ".icon { font-size: 48px; margin-bottom: 20px; }"
            + "</style></head><body>"
            + "<div class=\"container\">"
            + "<div class=\"icon\">🏥</div>"
            + "<h1>404</h1>"
            + "<h2>Página No Encontrada</h2>"
            + "<p>Lo sentimos, la página que busca no existe en el sistema de FarmaciaViva.</p>"
            + "<p>Por favor, verifique la URL o regrese a la página principal.</p>"
            + "<a href=\"/\">🏠 Volver al Inicio</a>"
            + "</div></body></html>";

    /**
     * Página de error interno del servidor
     */
    public static final String html_500 = "<html><head><title>Error 500 - FarmaciaViva</title><meta charset=UTF-8>"
            + "<link rel=icon href=data:,/>"
            + "<style>"
            + "body { font-family: Arial, sans-serif; text-align: center; padding: 50px; background-color: #f0f8ff; }"
            + ".container { max-width: 500px; margin: 0 auto; background-color: white; padding: 40px; border-radius: 10px; box-shadow: 0 0 15px rgba(0,0,0,0.1); }"
            + "h1 { color: #dc3545; font-size: 72px; margin: 0; }"
            + "h2 { color: #495057; margin-bottom: 20px; }"
            + "p { color: #6c757d; margin-bottom: 20px; line-height: 1.5; }"
            + "a { display: inline-block; background-color: #28a745; color: white; padding: 12px 24px; text-decoration: none; border-radius: 5px; font-weight: bold; }"
            + "a:hover { background-color: #218838; }"
            + ".icon { font-size: 48px; margin-bottom: 20px; }"
            + "</style></head><body>"
            + "<div class=\"container\">"
            + "<div class=\"icon\">⚠️</div>"
            + "<h1>500</h1>"
            + "<h2>Error Interno del Servidor</h2>"
            + "<p>Ha ocurrido un error interno en el sistema de FarmaciaViva.</p>"
            + "<p>Por favor, intente nuevamente en unos momentos.</p>"
            + "<a href=\"/\">🏠 Volver al Inicio</a>"
            + "</div></body></html>";
}