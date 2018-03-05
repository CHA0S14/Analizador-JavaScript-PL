package analizadorLexico;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 
 * @author Ismael Ortega Sanchez
 *
 *         Esta clase representa la tabla de transiciones correspondiente al
 *         automata del analizador lexico
 */
public class TablaDeTransiciones {
	// Tabla de transiciones del automata
	private static List<List<TransicionLexico>> tabla = new ArrayList<List<TransicionLexico>>();

	static {
		// Creo las listas de transicion por cada estado
		TransicionLexico[] estado0 = {};

		// Añado las listas a la tabla
		tabla.add(Arrays.asList(estado0));
	}

	/**
	 * Metodo que se encarga de devolver la transicion correspondiente dado un
	 * estado y el caracter
	 * 
	 * @param estado
	 *            Estado en el que se encuentra el automata
	 * @param caracter
	 *            Caracter leido del fichero que lanza la transicion
	 * @return Transicion a realizar
	 */
	public static TransicionLexico getTransicion(int estado, char caracter) {
		@SuppressWarnings("unlikely-arg-type") // Como el equals de transicion realmente trabaja con un string, nos vale
												// lo siguiente pero java te pone un warning para que tengas cuidad por
												// eso el supress warning
		int indice = tabla.get(estado).indexOf("" + caracter);
		return tabla.get(estado).get(indice);
	}
}
