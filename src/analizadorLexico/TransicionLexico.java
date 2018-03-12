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

	public TransicionLexico(String regex, int estado, String[] accionesSemanticas) {
		super(regex, estado);
		this.accionesSemanticas = accionesSemanticas;
	}

	public TransicionLexico(String regex, int estado) {
		super(regex, estado);
	}

	public TransicionLexico(String regex) {
		super(regex);
	}

	public String[] getAccionesSemanticas() {
		return accionesSemanticas;
	}
}
