package TablaDeSimbolos;

public class GestorTablasDeSimbolos {
	private static TablaDeSimbolos tablaGlobal;
	private static TablaDeSimbolos tablaActiva;
	
	static {
		tablaGlobal = new TablaDeSimbolos();
		tablaActiva = tablaGlobal;
	}
	
	public static TablaDeSimbolos getTablaActiva() {
		return tablaActiva;
	}
	
	public static TablaDeSimbolos nuevaTablaActiva() {
		tablaActiva = new TablaDeSimbolos();
		return tablaActiva;
	}
	
	public static void eliminarTablaActiva() {
		tablaActiva = null;
	}
}
