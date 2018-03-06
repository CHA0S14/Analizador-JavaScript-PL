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
		TransicionLexico[] estado0 = { new TransicionLexico("/", 1, new char[] { 'L' }),
				new TransicionLexico("-", 5, new char[] { 'L' }),
				new TransicionLexico("[1-9]", 6, new char[] { 'N', 'L' }),
				new TransicionLexico("0", 7, new char[] { 'L' }),
				new TransicionLexico("\"", 10, new char[] { 'S', 'L' }),
				new TransicionLexico("'", 12, new char[] { 'S', 'L' }),
				new TransicionLexico("&", 18, new char[] { 'L' }), new TransicionLexico("|", 19, new char[] { 'L' }),
				new TransicionLexico("\\w", 20, new char[] { 'S', 'L' }),
				new TransicionLexico(",", -1, new char[] { 'T' }), new TransicionLexico(";", -1, new char[] { 'T' }),
				new TransicionLexico("\\?", -1, new char[] { 'T' }), new TransicionLexico(":", -1, new char[] { 'T' }),
				new TransicionLexico("!", -1, new char[] { 'T' }), new TransicionLexico("{", -1, new char[] { 'T' }),
				new TransicionLexico("}", -1, new char[] { 'T' }), new TransicionLexico("(", -1, new char[] { 'T' }),
				new TransicionLexico(")", -1, new char[] { 'T' }), new TransicionLexico("<", 22, new char[] { 'L' }),
				new TransicionLexico(">", 21, new char[] { 'L' }), new TransicionLexico("=", 17, new char[] { 'L' }),
				new TransicionLexico("%", 16, new char[] { 'L' }), new TransicionLexico("*", 15, new char[] { 'L' }),
				new TransicionLexico("+", 14, new char[] { 'L' }), new TransicionLexico("\n", -1, new char[] { 'T' }),
				new TransicionLexico("", -1, new char[] { 'T' }),
				new TransicionLexico("( |\r|\t)", 0, new char[] { 'L' }) };

		TransicionLexico[] estado1 = { new TransicionLexico("/", 2, new char[] { 'L' }),
				new TransicionLexico("*", 3, new char[] { 'L' }) };

		TransicionLexico[] estado2 = { new TransicionLexico("\n", 0, new char[] {}),
				new TransicionLexico("", 0, new char[] {}),
				new TransicionLexico("([^\n]|[^\\Z])", 2, new char[] { 'L' }) };

		TransicionLexico[] estado3 = { new TransicionLexico("\\*", 4, new char[] { 'L' }),
				new TransicionLexico("[^\\*]", 3, new char[] { 'L' }) };

		TransicionLexico[] estado4 = { new TransicionLexico("/", 0, new char[] { 'L' }),
				new TransicionLexico("[^/]", 3, new char[] { 'L' }) };
		
		TransicionLexico[] estado5 = {};
		TransicionLexico[] estado6 = {};
		TransicionLexico[] estado7 = {};
		TransicionLexico[] estado8 = {};
		TransicionLexico[] estado9 = {};
		TransicionLexico[] estado10 = {};
		TransicionLexico[] estado11 = {};
		TransicionLexico[] estado12 = {};
		TransicionLexico[] estado13 = {};
		TransicionLexico[] estado14 = {};
		TransicionLexico[] estado15 = {};
		TransicionLexico[] estado16 = {};
		TransicionLexico[] estado17 = {};
		TransicionLexico[] estado18 = {};
		TransicionLexico[] estado19 = {};
		TransicionLexico[] estado20 = {};
		TransicionLexico[] estado21 = {};
		TransicionLexico[] estado22 = {};
		TransicionLexico[] estado23 = {};

		// Añado las listas a la tabla
		tabla.add(Arrays.asList(estado0));
		tabla.add(Arrays.asList(estado1));
		tabla.add(Arrays.asList(estado2));
		tabla.add(Arrays.asList(estado3));
		tabla.add(Arrays.asList(estado4));
		tabla.add(Arrays.asList(estado5));
		tabla.add(Arrays.asList(estado6));
		tabla.add(Arrays.asList(estado7));
		tabla.add(Arrays.asList(estado8));
		tabla.add(Arrays.asList(estado9));
		tabla.add(Arrays.asList(estado10));
		tabla.add(Arrays.asList(estado11));
		tabla.add(Arrays.asList(estado12));
		tabla.add(Arrays.asList(estado13));
		tabla.add(Arrays.asList(estado14));
		tabla.add(Arrays.asList(estado15));
		tabla.add(Arrays.asList(estado16));
		tabla.add(Arrays.asList(estado17));
		tabla.add(Arrays.asList(estado18));
		tabla.add(Arrays.asList(estado19));
		tabla.add(Arrays.asList(estado20));
		tabla.add(Arrays.asList(estado21));
		tabla.add(Arrays.asList(estado22));
		tabla.add(Arrays.asList(estado23));

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
