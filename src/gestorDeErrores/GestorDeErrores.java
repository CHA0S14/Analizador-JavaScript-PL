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
			error.append(errorSintactico(codigo - 3000));
			error.append(" -> linea " + AnalizadorLexico.getNlinea());
		} else if (codigo < 5000) {
			error.append(ERROR_SEMANTICO);
			error.append(errorSemantico(codigo - 4000));
			error.append(" -> linea " + AnalizadorLexico.getNlinea());
		} else {
			System.err.println("No se reconoce el error");
		}

		System.err.println(error);
		System.exit(1);
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
			return "Se ha leido un caracter no imprimible";
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

	private static String errorSintactico(int codigo) {
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
