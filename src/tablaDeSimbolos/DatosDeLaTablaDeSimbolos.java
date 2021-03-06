package tablaDeSimbolos;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Ismael Ortega Sanchez
 *
 *         Clase que simboliza una tabla de simbolos
 */
public class DatosDeLaTablaDeSimbolos {

	private int id, desp;
	private String nombreFuncion; // Solamente tiene valor si no es la tabla general
	private List<Entrada> operandos;

	public DatosDeLaTablaDeSimbolos(int id) {
		this.operandos = new ArrayList<>();
		this.id = id;
		desp = 0;
	}

	public DatosDeLaTablaDeSimbolos(int id, String nombreFuncion) {
		this.operandos = new ArrayList<>();
		this.id = id;
		this.nombreFuncion = nombreFuncion;
	}

	/**
	 * Metodo que sencarga de insertar una id en la tabla de simbolos
	 * 
	 * @param id
	 *            identificador a insertar en la tabla
	 * @return indice del identificador insertado
	 */
	public int insertarId(String id) {
		int indice = 0;
		// Compruebo que no existe el identificador en la tabla de simbolos, si existe
		// devuelvo un 0 reservado para el error
		if (obtenerIndiceId(id) == 0) {
			// Como los indices de la tabla de simbolos empiezan en 1 hay que sumar uno al
			// indice obtenido
			indice = operandos.size() + 1;
			Entrada operando = new Entrada(id);
			operandos.add(operando);
		}

		return indice;
	}

	/**
	 * Metodo que se encarga de obtener el indice de un identificador
	 * 
	 * @param id
	 *            identificador del cual se quiere obtener el indice
	 * @return indice del identificador o 0 si no se encuentra
	 */
	public int obtenerIndiceId(String id) {
		for (int i = 0; i < operandos.size(); i++) {
			if (operandos.get(i).getLexema().equals(id))
				return i + 1;
		}
		return 0;
	}

	/**
	 * Metodo que se encarga de obtener un identificador dado un indice
	 * 
	 * @param indice
	 *            Indice de la tabla en el cual esta el identificador a obtener
	 * @return identificador que se quiere obtener
	 */
	public Entrada obtenerOperando(int indice) {
		// Como el 0 esta reservacdo para el error le llegara un indice desplazado en 1
		// por eso se resta 1
		return operandos.get(Math.abs(indice) - 1);
	}

	/**
	 * Metodo que modifica el desplazamiento de la tabla de simbolos
	 * 
	 * @param ancho
	 *            Valor que se quiere desplazar
	 */
	public void incrementarDesp(int ancho) {
		this.desp += ancho;
	}

	public int getDesp() {
		return desp;
	}

	@Override
	/**
	 * Metodo que imprime de una manera clara la tabla de simbolos de la siguiente
	 * forma
	 * 
	 * <pre>
	 * TABLA PRINCIPAL #1:
	 * * LEXEMA : 'lexema'
	 * 		+ tipo: 'tipo del identificador'
	 * 		+ desplazamiento: 'desplazamiento del identificador
	 * * LEXEMA : 'lexema2'
	 * 		+ tipo: 'tipo del identificador2'
	 * 		+ desplazamiento: 'desplazamiento del identificador2
	 * * LEXEMA : 'lexema'
	 * 		+ tipo: 'funcion'
	 * 		+ etiqueta: 'etiqueta de la funcion'
	 * 		+ parametros: 'n_parametros'
	 * 		+ parametro1: 'tipo del parametro 1'	
	 * 			+ modo: 'modo del parametro'
	 * 		+ parametro2: 'tipo del parametro 2'
	 * 			+ modo: 'modo del parametro'
	 * 		+ ....
	 * 		+ tiporetorno: 'tipo de retorno de la funcion'
	 * 
	 * 	---------------SI NO ES LA TABLA GENERAL---------------
	 * 
	 * TABLA de la FUNCI�N 'nombre de la funcion' #'id de la funcion':
	 * * LEXEMA : 'lexema'
	 * 		+ tipo: 'tipo del identificador'
	 * 		+ desplazamiento: 'desplazamiento del identificador'
	 * * LEXEMA : 'lexema2'
	 * 		+ tipo: 'tipo del identificador2'
	 * 		+ desplazamiento: 'desplazamiento del identificador2'
	 * </pre>
	 */
	public String toString() {
		StringBuffer tabla = new StringBuffer();
		int cont = -1; // Contador para saber si ya se han leido todos los parametros

		// SI su id es 1 se trata de la tabla principal
		if (id == 1)
			tabla.append("TABLA PRINCIPAL #1:\n");
		else {
			tabla.append("TABLA de la FUNCI�N " + nombreFuncion + " #" + id + ":\n");
			cont = 0; // Se inicia el contador a 0 para poder tener en cuenta el numero de parametros
		}

		// recorro los operandos de la tabla de simbolos
		for (Entrada operando : operandos) {
			// Si no es una funcion va a tener 3 campos, si es una funcion tendra 4 campos
			// mas un campo por cada parametro
			if (!operando.getTipo().equals(Entrada.FUNC)) {

				tabla.append("* LEXEMA: '" + operando.getLexema() + "'\n");
				tabla.append("\t+ tipo: " + operando.getTipo() + "\n");
				tabla.append("\t+ desplazamiento: " + operando.getDespl() + "\n\n");
			} else {
				tabla.append("* LEXEMA: '" + operando.getLexema() + "' (tipo de entrada 'funci�n')\n");
				tabla.append("\t+ tipo: " + operando.getTipo() + "\n");
				tabla.append("\t+ etiqueta: " + operando.getEtiqFuncion() + "\n");
				tabla.append("\t+ parametros: " + operando.getNumParam() + "\n");

				// Recorro la lista de parametros los cuales van desde el indice 2 (Tercer
				// elemento) hasta el antepenultimo por eso le resto 4 al length (los 2 primeros
				// + los 2 ultimos)
				for (int i = 0; i < operando.getTipoParam().size(); i++) {
					tabla.append("\t\t+ parametro" + (i) + ": " + operando.getTipoParam().get(i) + "\n");
					tabla.append("\t\t\t+ modo: " + (operando.getModoParam().get(i) ? "referencia" : "valor") + "\n");
					i++;
				}
				tabla.append("\t+ tipo retorno: " + operando.getTipoRetorno() + "\n\n");
			}

			if (cont != -1)
				cont++;
		}

		return tabla.toString();
	}
}
