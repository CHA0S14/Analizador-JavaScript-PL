package analizadorLexico;

import classes.Transicion;

/**
 * 
 * @author Ismael Ortega Sanchez
 *
 *         El analizador lexico es diferente al resto de automatas porque sus
 *         transiciones tienen ademas unas acciones semanticas asociadas, esta
 *         clase hereda de Transicion, que sera la forma estandar de un
 *         automata, y aniade el valor de acciones semanticas
 */
public class TransicionLexico extends Transicion {
	// Acciones semanticas correspondientes a la transicion
	private String[] accionesSemanticas;

	public TransicionLexico(char caracter, int estado, String[] accionesSemanticas) {
		super(caracter, estado);
		this.accionesSemanticas = accionesSemanticas;
	}

	public TransicionLexico(char caracter, int estado) {
		super(caracter, estado);
	}

	public TransicionLexico(char caracter) {
		super(caracter);
	}

	public String[] getAccionesSemanticas() {
		return accionesSemanticas;
	}
}
