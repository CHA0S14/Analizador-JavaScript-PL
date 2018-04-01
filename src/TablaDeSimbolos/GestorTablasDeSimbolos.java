package TablaDeSimbolos;

/**
 * 
 * @author Ismael Ortega Sanchez
 * 
 *         Clase que se encarga de gestionar las tablas de simbolos activa y
 *         global
 */
public class GestorTablasDeSimbolos {
	private static TablaDeSimbolos tablaGlobal;
	private static TablaDeSimbolos tablaActiva;
	private static int idTabla = 2; // Se inicia a 2 porque la tabla general tiene el valor 1

	static {
		tablaGlobal = new TablaDeSimbolos(1);
		tablaActiva = tablaGlobal;
	}

	public static TablaDeSimbolos getTablaActiva() {
		return tablaActiva;
	}

	public static TablaDeSimbolos getTablaGlobal() {
		return tablaGlobal;
	}

	/**
	 * Metodo que crea una nueva tabla activa
	 */
	public static void nuevaTablaActiva(String nombre) {
		tablaActiva = new TablaDeSimbolos(idTabla, nombre);
		idTabla++;
	}

	/**
	 * Metodo que elimina la tabla de simbolos activa y la sustituye por la global
	 */
	public static void eliminarTablaActiva() {
		tablaActiva = tablaGlobal;
		System.gc(); // Ejecuto el colector de basura para eliminar la tabla que ya no tiene punteros
						// de la memoria
	}

	/**
	 * Metodo que se encarga de insertar un nuevo identificador en la tabla de
	 * simbolos activa
	 * 
	 * @param id
	 *            identificador a insertar en la tabla de simbolos
	 * @return indice del identificador, positivo si es la tabla global, negativo si
	 *         es la tabla activa
	 */
	public static int insertarId(String id) {
		// Insertamos el identificador en la tabla de simbolos activa
		int indice = tablaActiva.insertarId(id);
		// Si la tabla de simbolos y la activa no son la misma, se ha de devolver el
		// indice en negativo
		return tablaActiva.equals(tablaGlobal) ? indice : indice * -1;
	}

	/**
	 * Obtiene el indice del identificador en la tabla de simbolos, si es negativo,
	 * es en la tabla activa, si es positivo es en la tabla global, si no esta el
	 * identificador se insertara en la tabla global y se devolvera su indice
	 * 
	 * @param id
	 *            Identificador del cual se quiere obtener el indice
	 * @return indice del indentificador, positivo si es de la tabla global,
	 *         negativo si es de la activa
	 */
	public static int obtenerIndiceId(String id) {
		// Obtenemos el indice de la tabla de de simbolos activa
		int indice = tablaActiva.obtenerIndiceId(id);
		// Si no se encuentra el elemento en la tabla de simbolos activa y la tabla de
		// simbolos activa NO es la global, obtenemos el indice del elemento en la tabla
		// de simbolos global
		// Si se ha encontrado el elemento (indice != 0) y la tabla activa no es la
		// global hay que multiplicar por -1 el indice
		if (indice == 0 && !tablaActiva.equals(tablaGlobal)) {
			indice = tablaGlobal.obtenerIndiceId(id);
		} else if (indice != 0 && !tablaActiva.equals(tablaGlobal)) {
			indice = indice * -1;
		}

		// Si no se encuentra un indice en ninguna tabla de simbolos se tiene
		// que insertar el identificador en la tabla global
		if (indice == 0) {
			indice = tablaGlobal.insertarId(id);
		}
		return indice;
	}
}
