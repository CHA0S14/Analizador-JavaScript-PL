package analizadorLexico.tablas;

/**
 *  
 * @author Ismael Ortega Sanchez
 *
 */
public class TablaPalabrasReservadas {
	private final String[] palabrasReservadas = { "true", "false", "var", "int", "bool", "chars", "write", "promp",
			"return", "if", "else", "while", "do", "for", "switch", "case", "break", "default", "funtion" };

	public int getCodigoPalabraReservada(String palabraReservada) {
		int index = -1;
		int i = 0;
		while (index == -1 && i < palabrasReservadas.length) {
			if(palabrasReservadas[i].equals(palabraReservada))
				index = i;
			i++;
		}
		return index != -1 ? 27 + index : index;
	}
}
