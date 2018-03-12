package analizadorLexico.tablas;

/**
 * Clase que contiene la tabla de palabras reservadas del lenguaje
 * 
 * @author Ismael Ortega Sanchez
 *
 */
public class TablaPalabrasReservadas {
	private final String[] palabrasReservadas = { "true", "false", "var", "int", "bool", "chars", "write", "promp",
			"return", "if", "else", "while", "do", "for", "switch", "case", "break", "default", "funtion" };

	/**
	 * Metodo que devuelve la codificacion del token para la palabra reservada o -1
	 * si no es una palbra reservada
	 * 
	 * @param palabraReservada
	 *            Cadena que se desea comprobar si es una palabra reservada
	 * @return Codigo del token para la palabra reservada o -1 si no existe
	 */
	public int getCodigoPalabraReservada(String palabraReservada) {
		int index = -1;
		int i = 0;
		// Recorremos las tabla en busca de la palabra reservada
		while (index == -1 && i < palabrasReservadas.length) {
			if (palabrasReservadas[i].equals(palabraReservada))
				index = i;
			i++;
		}
		// Si se ha encontrado algo se le suma 27 al indice para codificar el token
		// La primera paralabra reservada, true es el token 27 el indice es 0
		return index != -1 ? 27 + index : index;
	}
}
