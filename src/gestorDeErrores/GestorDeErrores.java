package gestorDeErrores;

import analizadorLexico.AnalizadorLexico;

/**
 * Clase que simboliza el gestor de errores del procesador de lenguajes
 * 
 * @author Ismael Ortega Sanchez
 *
 */
public class GestorDeErrores {
	private static final String ERROR_PROGRAMA = "Error de programa: ";
	private static final String ERROR_LEXICO = "Error lexico: ";
	private static final String ERROR_SINTACTICO = "Error sintactico: ";
	private static final String ERROR_SEMANTICO = "Error semantico: ";

	/**
	 * Metodo que se encarga de segun se recibe un error, gestionarlo y mostrar lo
	 * necesario en la consola
	 * 
	 * @param codigo
	 *            Codigo de error a gestionar
	 */
	public static void gestionarError(int codigo, String dato) {
		StringBuffer error = new StringBuffer();

		if (codigo < 2000) {
			error.append(ERROR_PROGRAMA);
			error.append(errorCompilador(codigo - 1000, dato));
		} else if (codigo < 3000) {
			error.append(ERROR_LEXICO);
			error.append(errorLexico(codigo - 2000, dato));
			error.append(" -> linea " + AnalizadorLexico.getNlinea());
		} else if (codigo < 4000) {
			error.append(ERROR_SINTACTICO);
			error.append(errorSintactico(codigo - 3000, dato));
			error.append(" -> linea " + AnalizadorLexico.getNlinea());
		} else if (codigo < 5000) {
			error.append(ERROR_SEMANTICO);
			error.append(errorSemantico(codigo - 4000));
			error.append(" -> linea " + AnalizadorLexico.getNlinea());
		} else {
			System.err.println("No se reconoce el error");
		}

		System.err.println(error);
		System.exit(codigo);
	}

	private static String errorCompilador(int codigo, String dato) {
		switch (codigo) {
		case 1:
			return "No se han recibido suficientes argumentos para el programa,"
					+ " se necesita la ruta del fichero a analizar";
		case 2:
			return "No se ha podido abrir el fichero " + dato;
		case 3:
			return "Ha ocurrido un error al leer o escribir el fichero " + dato;
		case 4:
			return "Ha ocurrido algun error con el indice de la tabla de simbolos " + dato;
		case 5:
			return "Se ha insertado un nombre de atributo invalido " + dato;
		case 6:
			return "Se ha insertado un tipo de atributo invalido " + dato;
		case 7:
			return "Se esta intentando añadir desplazamiento a una funcion";
		case 8:
			return "Se esta intentando aniadir un atributo " + dato + " que es de funcion a una variable";
		case 9:
			return "Se esta intentando aniadir un modo de paso de parametro " + dato + " que es invalido";
		case 10:
			return "Se ha introducido un modo de paso de variable incorrecto " + dato;
		case 11:
			return "Se ha insertado un modo de parametro invalido " + dato;
		}

		return null;
	}

	private static String errorLexico(int codigo, String dato) {
		switch (codigo) {
		case 1:
			return "Se ha excedido el valor numerico maximo permitido de 32767, se ha escrito un valor de " + dato;
		case 2:
			return "Se ha intentado declarar una variable nueva con el nombre \"" + dato
					+ "\" que ya esta siendo usado";
		case 3:
			return "El caracter " + dato + " no pertenece al conjunto de caracteres permitidos";
		case 4:
			return "No se esperaba el caracter '" + dato + "'";
		case 5:
			return "Se ha leido un caracter no imprimible";
		case 6:
			return "Se esperaba la inicializacion de un numero hexadecimal pero se ha recibido el caracter '" + dato
					+ "'";
		case 7:
			return "Se esperaba una n o una t para formar \\n o \\t pero se ha recibido el caracter '" + dato + "'";
		case 8:
			return "Se esperaba un & o un = para formar && o &= pero se ha recibido el caracter '" + dato + "'";
		case 9:
			return "Se esperaba un | o un = para formar || o |= pero se ha recibido el caracter '" + dato + "'";
		}

		return null;
	}

	private static String errorSintactico(int codigo, String dato) {
		switch (codigo) {
		case 1:
			return "Se esperaba la creacion de una variable, su incremento o decremento, "
					+ "declaracion de una funcion o un bloque de codigo";
		case 2:
			return "En el primer parametro de un for se espera la asignacion de una variable";
		case 3:
			return "En el ultimo parametro de un for se espera la modificacion de una variable o nada";
		case 4:
			return "En un switch se espera por lo menos un case";
		case 5:
			return "Los unicos tipos aceptados son int, bool y chars";
		case 6:
			return "Las expresiones solo pueden comenzar por !, (, una operacion de incremento o decremento,"
					+ " identificador, tipo cadena, true, false o entero";
		case 7:
			return "Se esperaba un valor ya sea por identificador, entero, cadena, true, false, "
					+ "una expresion entre parentesis o el autoincremento o decremento de una variable";
		case 8:
			return "Se esperaba una sentencia ya sea una llamada a un identificador, un write, "
					+ "prompt, return, autoincremento o decremento o un break";
		case 9:
			return "Tras una sentencia con un identificador se espera una asignacion, los parametros de"
					+ " una funcion o un postdecremento o incremento";
		case 10:
			return "Se esperaba la generacion de una funcion con su palabra resevada 'function'";
		case 11:
			return "No se esperaba el token " + dato;
		}

		return null;
	}

	private static String errorSemantico(int codigo) {
		switch (codigo) {
		case 1:
			break;
		case 2:
			break;
		case 3:
			break;
		case 4:
			break;
		case 5:
			break;
		case 6:
			break;
		case 7:
			break;
		case 8:
			break;
		case 9:
			break;
		case 10:
			break;
		case 11:
			break;
		case 12:
			break;

		}

		return null;
	}
}
