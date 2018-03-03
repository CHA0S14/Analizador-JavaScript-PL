package analizadorLexico;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import classes.Transicion;

/**
 * 
 * @author Ismael Ortega Sanchez
 *
 *         Esta clase representa la tabla de transiciones correspondiente al
 *         automata del analizador lexico
 */
public class TablaDeTransiciones {
	// Tabla de transiciones del automata
	private List<List<TransicionLexico>> tabla = new ArrayList<List<TransicionLexico>>();

	public TablaDeTransiciones() {
		// Creo las listas de transicion por cada estado
		TransicionLexico[] estado0 = {};
		
		// Añado las listas a la tabla
		tabla.add(Arrays.asList(estado0));
	}
	
	public TransicionLexico getTransicion(int estado, char caracter) {
		int indice = this.tabla.get(estado).indexOf(new Transicion(caracter));
		return this.tabla.get(estado).get(indice);
	}
}
