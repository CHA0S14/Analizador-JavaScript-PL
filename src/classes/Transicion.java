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
	// Estado destino del automata
	private int estado;

	public Transicion(int estado) {
		this.estado = estado;
	}

	public int getEstado() {
		return estado;
	}
}
