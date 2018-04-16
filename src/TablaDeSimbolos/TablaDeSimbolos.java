package TablaDeSimbolos;

import gestorDeErrores.GestorDeErrores;

/**
 * 
 * @author Ismael Ortega Sanchez
 * 
 *         Clase que se encarga de gestionar las tablas de simbolos activa y
 *         global
 */
public class TablaDeSimbolos {
	public static final String TIPO = "tipo";
	public static final String DESP = "desplazamiento";
	public static final String PARAM = "parametro";
	public static final String RET = "retorno";
	public static final String TAG = "etiqueta";
	public static final String CHARS = "chars";
	public static final String BOOL = "bool";
	public static final String INT = "int";
	public static final String FUNC = "funcion";
	private static DatosDeLaTablaDeSimbolos tablaGlobal;
	private static DatosDeLaTablaDeSimbolos tablaActiva; // Esta tabla puede ser la tabla local o la global dependiendo
															// del punto
	// del codigo
	private static int idTabla = 2; // Se inicia a 2 porque la tabla general tiene el valor 1

	/**
	 * insertar atributo consultar atributo
	 * 
	 * 1 funcion distinta para insertar o buscar cada atributo
	 * 
	 * imprimir tabla reciba el nombre del fichero
	 */

	static {
		tablaGlobal = new DatosDeLaTablaDeSimbolos(1);
		tablaActiva = tablaGlobal;
	}

	/**
	 * Metodo que crea una nueva tabla activa
	 */
	public static void nuevaTablaActiva(String nombre) {
		tablaActiva = new DatosDeLaTablaDeSimbolos(idTabla, nombre);
		idTabla++;
	}

	/**
	 * Metodo que elimina la tabla de simbolos activa y la sustituye por la global
	 */
	public static void eliminarTablaActiva() {
		tablaActiva = tablaGlobal;
		if (tablaActiva.equals(tablaGlobal))
			tablaGlobal = null;
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
	public static int buscarId(String id) {
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

	/**
	 * Metodo que se encarga de insertar un valor de un atrubito en el indice de la
	 * tabla de simbolos correspondiente
	 * 
	 * @param indice
	 *            indice de la tabla de simbolos
	 * @param atributo
	 *            Atributo que se quiere modificar pueden ser:
	 * 
	 *            <pre>
	 *            TablaDeSimbolos.TIPO = tipo del identificador
	 *            TablaDeSimbolos.DESP = desplazamiento del identificador
	 *            TablaDeSimbolos.PARAM = parametro de la funcion
	 *            TablaDeSimbolos.RET = tipo de retorno de la funcion
	 *            TablaDeSimbolos.TAG = etiqueta de la funcion
	 *            </pre>
	 * 
	 * @param valor
	 *            valor del atributo a modificar
	 */
	public static void insetarAtributoId(int indice, String atributo, String valor, int numParametro) {
		switch (atributo) {
		case TIPO:
			insertarAtributoTipo(indice, valor);
			break;
		case DESP:
			break;
		case PARAM:
			break;
		case RET:
			break;
		case TAG:
			break;
		}
	}

	/**
	 * Metodo que se encarga de insertar el atributo tipo en la fila de un
	 * identificador
	 * 
	 * @param indice
	 *            Indice del identificador a modificar
	 * @param valor
	 *            Valor del indice a modificar a elegir entre:
	 * 
	 *            <pre>
	 * 			  	TablaDeSimbolos.CHARS
	 * 				TablaDeSimbolos.INT
	 * 				TablaDeSimbolos.BOOL
	 * 				TablaDeSimbolos.FUNC
	 *            </pre>
	 */
	public static void insertarAtributoTipo(int indice, String valor) {
		switch (valor) {
		case CHARS:
		case INT:
		case BOOL:
		case FUNC:
			obtenerTablaSegunIndice(indice).obtenerIdentificador(indice)[2] = valor;
			break;
		default:
			// TODO No se a introducido un tipo valido
			break;
		}
	}

	/**
	 * Metodo que se encarga de segun se recibe un indice de una tabla de simbolos,
	 * devuelve la tabla de simbolos correspondiente
	 * 
	 * @param indice
	 *            indice del elemento de la tabla de simbolos
	 * @return tabla de simbolos a la que corresponde el indice
	 */
	private static DatosDeLaTablaDeSimbolos obtenerTablaSegunIndice(int indice) {
		DatosDeLaTablaDeSimbolos tabla = null;

		if (indice > 0) {
			tabla = tablaGlobal;
		} else if (indice < 0 && !tablaActiva.equals(tablaGlobal)) {
			tabla = tablaActiva;
		} else {
			// TODO Gestor de errores, algo raro ha sucedido con el indice
			GestorDeErrores.gestionarError(1004, "El indice buscado es " + indice + " y la tabla activa es "
					+ ((!tablaActiva.equals(tablaGlobal)) ? "Una tabla local" : "La tabla global"));
		}

		return tabla;
	}
}
