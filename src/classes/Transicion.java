package classes;

import java.util.regex.Pattern;

/**
 * 
 * @author Ismael Ortega Sanchez
 * 
 *         Esta clase representa una transicion de un automata, segun un
 *         caracter de entrada el automata se desplaza a un estado
 *
 */
public class Transicion {
	// Caracter que lanza la transicion
	private String regex;
	// Estado destino del automata
	private int estado;

	public Transicion(String regex, int estado) {
		this.regex = regex;
		this.estado = estado;
	}

	public Transicion(String regex) {
		this.regex = regex;
	}

	public String getRegex() {
		return regex;
	}

	public int getEstado() {
		return estado;
	}

	@Override
	/**
	 * Metodo que se encarga de comparar los objetos de la clase transicion, dos
	 * objetos Transicion seran iguales si el caracter que la lanza es el mismo,
	 * esto es util porque en un mismo estado no puede haber dos transiciones con el
	 * mismo caracter lanzador, asi que si tenemos un Map de transiciones en un
	 * estado con un Map.get(Transicion(charLanzador)) obtendremos automaticamente
	 * la transicion deseada
	 */
	public boolean equals(Object arg0) {
		// Comprueba que el objeto recibido es de la clase Transicion
		if (arg0 instanceof String) {
			//Compila la expresion regular y compara con el string a ver si lo contiene
			return Pattern.matches(regex, (String)arg0);
		}
		return false;
	}
}
