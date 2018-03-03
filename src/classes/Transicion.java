package classes;

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
	private char caracter;
	// Estado destino del automata
	private int estado;

	public Transicion(char caracter, int estado) {
		this.caracter = caracter;
		this.estado = estado;
	}

	public Transicion(char caracter) {
		this.caracter = caracter;
	}

	public char getCaracter() {
		return caracter;
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
		if (arg0 instanceof Transicion) {
			// Compara los caracteres y se devuelve el resultado
			return this.caracter == ((Transicion) arg0).getCaracter();
		}
		return false;
	}
}
