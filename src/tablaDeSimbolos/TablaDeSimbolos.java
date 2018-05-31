package tablaDeSimbolos;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import gestorDeErrores.GestorDeErrores;

/**
 * 
 * @author Ismael Ortega Sanchez
 * 
 *         Clase que se encarga de gestionar las tablas de simbolos activa y
 *         global
 */
public class TablaDeSimbolos {
	private static final String FICHERO_TABLA_DE_SIMBOLOS = "./Resultados/tablaDeSimbolos",
			SEPARADOR = "--------------------------------------------";

	private static PrintWriter tablaDeSimbolos;

	private static DatosDeLaTablaDeSimbolos tablaGlobal;
	private static DatosDeLaTablaDeSimbolos tablaActiva; // Esta tabla puede ser la tabla local o la global dependiendo
															// del punto del codigo
	private static int idTabla = 2; // Se inicia a 2 porque la tabla general tiene el valor 1

	/**
	 * insertar atributo consultar atributo
	 * 
	 * 1 funcion distinta para insertar o buscar cada atributo
	 * 
	 * imprimir tabla reciba el nombre del fichero
	 */

	static {
		resetTablaDeSimbolos();
	}

	/**
	 * Metodo que resetea las tablas de simbolos para el testing y para llamar desde
	 * static
	 */
	public static void resetTablaDeSimbolos() {
		tablaGlobal = new DatosDeLaTablaDeSimbolos(1);
		tablaActiva = tablaGlobal;

		try {
			tablaDeSimbolos = new PrintWriter(new FileWriter(FICHERO_TABLA_DE_SIMBOLOS));
		} catch (IOException e) {
			// Envio al gestor de errore el codigo 1002 reservado a error de compilador
			// cuando un fichero no se puede abrir
			GestorDeErrores.gestionarError(1002, FICHERO_TABLA_DE_SIMBOLOS);
		}
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
		escribirTablaActivaEnFichero();
		if (tablaActiva.equals(tablaGlobal))
			tablaGlobal = null;
		tablaActiva = tablaGlobal;
		System.gc(); // Ejecuto el colector de basura para eliminar la tabla que ya no tiene punteros
						// de la memoria
	}

	/**
	 * Metodo que se encarga de escribir la tabla activa en un fichero
	 */
	private static void escribirTablaActivaEnFichero() {
		tablaDeSimbolos.println(tablaActiva.toString());

		// Si no es la tabla global escribimos un separador en el archivo
		if (!tablaActiva.equals(tablaGlobal)) {
			tablaDeSimbolos.println();
			tablaDeSimbolos.println(SEPARADOR);
			tablaDeSimbolos.println();
		}

		tablaDeSimbolos.flush();
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

	public static Entrada obtenerOperando(int indice) {
		return tablaActiva.obtenerOperando(indice);
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
	 *            	TablaDeSimbolos.TIPO = tipo del identificador
	 *            	TablaDeSimbolos.DESP = desplazamiento del identificador
	 *            	TablaDeSimbolos.PARAM = parametro de la funcion
	 *            	TablaDeSimbolos.RET = tipo de retorno de la funcion
	 *            	TablaDeSimbolos.TAG = etiqueta de la funcion
	 *            </pre>
	 * 
	 * @param valor
	 *            valor del atributo a modificar
	 * 
	 * @param numParametro
	 *            numero del parametro a modificar
	 */
	public static void insetarAtributo(int indice, String atributo, String valor, boolean modo) {
		switch (atributo) {
		case Entrada.TIPO:
			insertarAtributoTipo(indice, valor);
			break;
		case Entrada.DESP:
			insertarAtributoDesplazamiento(indice, valor);
			break;
		case Entrada.PARAM:
			insertarAtributoParametro(indice, valor, modo);
			break;
		case Entrada.RET:
			insertarAtributoRetorno(indice, valor);
			break;
		case Entrada.TAG:
			insertarAtributoEtiqueta(indice, valor);
			break;
		default:
			GestorDeErrores.gestionarError(1005, atributo);
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
	 *            Valor del tipo a elegir entre:
	 * 
	 *            <pre>
	 * 			  	TablaDeSimbolos.CHARS
	 * 				TablaDeSimbolos.INT
	 * 				TablaDeSimbolos.BOOL
	 * 				TablaDeSimbolos.FUNC
	 *            </pre>
	 */
	public static void insertarAtributoTipo(int indice, String valor) {
		obtenerTablaSegunIndice(indice).obtenerOperando(indice).setTipo(valor);
	}

	/**
	 * Metodo que se encarga de aniadir el atributo desplazamiento en la fila de un
	 * identificador que no sea funcion
	 * 
	 * @param indice
	 *            Indice del identificador a modificar
	 * @param valor
	 *            Valor del desplazamiento
	 */
	public static void insertarAtributoDesplazamiento(int indice, String valor) {
		obtenerTablaSegunIndice(indice).obtenerOperando(indice).setDespl(tablaActiva.getDesp());
		try {
			tablaActiva.incrementarDesp(Integer.parseInt(valor));
		} catch (NumberFormatException e) {
			GestorDeErrores.gestionarError(1009, valor);
		}
	}

	/**
	 * Metodo que se encarga de aniadir un parametro a la funcion
	 * 
	 * @param indice
	 *            Indice del identificador a modificar
	 * @param valor
	 *            Valor del parametro el cual solo puede ser uno de los siguientes:
	 * 
	 *            <pre>
	 * 			  	TablaDeSimbolos.CHARS
	 * 				TablaDeSimbolos.INT
	 * 				TablaDeSimbolos.BOOL
	 *            </pre>
	 * 
	 * @param numParametro
	 *            Numero del parametro a modificar
	 */
	public static void insertarAtributoParametro(int indice, String valor, boolean modo) {
		Entrada operando = obtenerTablaSegunIndice(indice).obtenerOperando(indice);

		operando.addTipoParam(valor);
		operando.addModoParam(modo);
	}

	/**
	 * Metodo que se encarga de insertar el atributo de retorno para un
	 * identificador de funcion
	 * 
	 * @param indice
	 *            Indice de la fila de la tabla de simbolos
	 * @param valor
	 *            Valor de retorno siendo un tipo de los siguientes:
	 * 
	 *            <pre>
	 * 			  	TablaDeSimbolos.CHARS
	 * 				TablaDeSimbolos.INT
	 * 				TablaDeSimbolos.BOOL
	 *            </pre>
	 */
	public static void insertarAtributoRetorno(int indice, String valor) {
		obtenerTablaSegunIndice(indice).obtenerOperando(indice).setTipoRetorno(valor);
	}

	/**
	 * Metodo que se encarga de insertar un atributo etiqueta en un identificador de
	 * tipo funcion
	 * 
	 * @param indice
	 *            Indice de la linea de la tabla de simbolos
	 * @param valor
	 *            Valor de la etiqueta
	 */
	public static void insertarAtributoEtiqueta(int indice, String valor) {
		obtenerTablaSegunIndice(indice).obtenerOperando(indice).setEtiqFuncion(valor);
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
			GestorDeErrores.gestionarError(1004, "El indice buscado es " + indice + " y la tabla activa es "
					+ ((!tablaActiva.equals(tablaGlobal)) ? "Una tabla local" : "La tabla global"));
		}

		return tabla;
	}
}
