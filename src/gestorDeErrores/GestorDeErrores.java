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
	private static final String ERROR_LEXICO = "Error lexico en la linea: ";
	private static final String ERROR_SINTACTICO = "Error sintactico en la linea: ";
	private static final String ERROR_SEMANTICO = "Error semantico en la linea: ";

	/**
	 * Metodo que se encarga de segun se recibe un error, gestionarlo y mostrar lo
	 * necesario en la consola
	 * 
	 * @param codigo
	 *            Codigo de error a gestionar
	 */
	public static void gestionarError(int codigo) {
		StringBuffer error = new StringBuffer();
		
		if (codigo < 2000) {
			error.append(ERROR_PROGRAMA);
			error.append(AnalizadorLexico.getNlinea());
			error.append(errorPrograma(codigo-2000));
		}else if (codigo < 3000) {
			error.append(ERROR_LEXICO);
			error.append(AnalizadorLexico.getNlinea());
			error.append(errorLexico(codigo-3000));
		}else if (codigo < 4000) {
			error.append(ERROR_SINTACTICO);
			error.append(AnalizadorLexico.getNlinea());
			error.append(errorSintactico(codigo-4000));
		}else if (codigo < 5000) {
			error.append(ERROR_SEMANTICO);
			error.append(AnalizadorLexico.getNlinea());
			error.append(errorSemantico(codigo-5000));
		}else {
			System.err.println("No se reconoce el error");
		}
		
		System.err.println(error);
		System.exit(1);
	}

	private static String errorPrograma(int codigo) {
		switch(codigo) {
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
	
	private static String errorLexico(int codigo) {
		switch(codigo) {
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
	
	private static String errorSintactico(int codigo) {
		switch(codigo) {
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
		switch(codigo) {
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
