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
			error.append(errorSemantico(codigo - 4000, dato));
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
			return "El desplazamiento introducido no es un int " + dato;
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
			return "La sentencia no es correcta";
		case 2:
			return "La actualizacion del for es erronea";
		case 3:
			return "Se esperaba un token de incremento o decremento pero se ha recibido un " + dato;
		case 4:
			return "Se esperaba un token de asignacion pero se ha recibido un " + dato;
		case 5:
			return "Los unicos tipos aceptados son int, bool y chars";
		case 6:
			return "No se ha leido una expresion valida";
		case 7:
			return "No se ha leido una sentencia valida";
		case 9:
			return "Se ha encontrado un error en la declaracion de la funcion";
		case 10:
			return "No se esperaba el token " + dato;
		}

		return null;
	}

	private static String errorSemantico(int codigo, String dato) {
		switch (codigo) {
		case 1:
			return "Tipos no consistentes: " + dato;
		case 2:
			return "La sentencia break solo puede ir dentro de un switch";
		case 3:
			return "La sentencia return solo puede ir dentro del cuerpo de una funcion";

		}

		return null;
	}
}
