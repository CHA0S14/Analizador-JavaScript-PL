package analizadorSemantico;

import java.util.List;

import javafx.util.Pair;
import tablaDeSimbolos.Entrada;
import tablaDeSimbolos.TablaDeSimbolos;

public class AnalizadorSemantico {
	public static final String TIPO_OK = "TIPO_OK";
	public static final String TIPO_VACIO = "TIPO_VACIO";
	public static final int DESP_INT = 8;
	public static final int DESP_CHARS = 8;
	public static final int DESP_BOOL = 1;

	private static boolean zonaDeDeclaracion;

	static {
		zonaDeDeclaracion = false;
	}

	/**
	 * Metodo que desactiva la zona de declaracion
	 */
	public static void desactivarZonaDeDeclaracion() {
		zonaDeDeclaracion = false;
	}

	/**
	 * Metodo que activa la zona de declaracion
	 */
	public static void activarZonaDeDeclaracion() {
		zonaDeDeclaracion = true;
	}

	/**
	 * Metodo que devuelve el estado de la zona de declaracion
	 * 
	 * @return True si activa, false si inactiva
	 */
	public static boolean isZonaDeDeclaracion() {
		return zonaDeDeclaracion;
	}

	/**
	 * Metodo que se encarga de aniadir datos de un identificador en la tabla de
	 * simbolos
	 * 
	 * @param indice
	 *            indice de la tabla de simbolos donde esta el identificador, ademas
	 *            si es negativo indica que es la tabla local, si es positivo indica
	 *            la tabla global
	 * @param tipo
	 *            Tipo del identificador
	 * @param ancho
	 *            Ancho a desplazar
	 */
	public static void aniadirTS(int indice, String tipo, int ancho) {
		TablaDeSimbolos.insertarAtributoTipo(indice, tipo);
		TablaDeSimbolos.insertarAtributoDesplazamiento(indice, ancho);
	}

	/**
	 * Metodo que devuelve el tipo almacenado en una tabla de simbolos para un
	 * identificador
	 * 
	 * @param indice
	 *            indice de la tabla de simbolo >0 si tabla global o <0 si local
	 * @return Tipo del identificador
	 */
	public static String tipoID(int indice) {
		return TablaDeSimbolos.getTipoIndice(indice);
	}

	/**
	 * Metodo que comprueba que los parametros que recibe una funcion coinciden con
	 * los que se pusieron al declararla
	 * 
	 * @param indice
	 *            indice de la tabla de simbolo >0 si tabla global o <0 si local
	 * @param param
	 *            Lista de parametros con los que comparar
	 * @return true si coinciden, false si no
	 */
	public static boolean validarParam(int indice, List<String> param) {
		List<String> paramTS = TablaDeSimbolos.getParamID(indice);

		return param.equals(paramTS); // Equals en un arraylist devuelve ttrue si tiene los mismo elementos en el
										// mismo orden
	}

	/**
	 * Metodo que devuelve el lexema almacenado en una tabla de simbolos para un
	 * identificador
	 * 
	 * @param indice
	 *            indice de la tabla de simbolo >0 si tabla global o <0 si local
	 * @return String lexema del identificador
	 */
	public static String lexemaID(int indice) {
		return TablaDeSimbolos.getLexemaIndice(indice);
	}

	/**
	 * Metodo que se encarga de crear una tabla de simbolos local dado el indice del
	 * identificador de la tabla global
	 * 
	 * @param lexema
	 */
	public static void crearTablaDeSimbolos(String lexema) {
		TablaDeSimbolos.nuevaTablaActiva(lexema);
	}

	/**
	 * Metodo que se encarga de cerrar la tabla de simbolos activa
	 */
	public static void cerrarTablaDeSimbolos() {
		TablaDeSimbolos.eliminarTablaActiva();
	}

	/**
	 * Metodo que se encarga de actualizar los atributos del identificador de una
	 * funcion
	 * 
	 * @param indice
	 *            Indice de la tabla de simbolos del identificador a editar
	 * @param tipoRetorno
	 *            Tipo de retorno
	 * @param params
	 *            Parametros y modo de paso de la funcion
	 */
	public static void actualizarIndiceFuncion(int indice, String tipoRetorno, List<Pair<String, Boolean>> params) {
		TablaDeSimbolos.insertarAtributoTipo(indice, Entrada.FUNC);
		TablaDeSimbolos.insertarAtributoRetorno(indice, tipoRetorno);

		for (Pair<String, Boolean> param : params) {
			TablaDeSimbolos.insertarAtributoParametro(indice, param.getKey(), param.getValue());
		}

	}

	/**
	 * Metodo que se encarga de actualizar los atributos de un identificador que es
	 * un parametro en la tabla de simbolos local a la funcion
	 * 
	 * @param indice
	 *            indice de la tabla de simbolos
	 * @param tipo
	 *            tipo del parametro
	 * @param ancho
	 *            Ancho que hay que mover el desplazamiento
	 */
	public static void aniadirParamTS(int indice, String tipo, int ancho) {
		aniadirTS(indice, tipo, ancho);
		TablaDeSimbolos.insertarAtributoIsParam(indice, true);
	}
}
