package TablaDeSimbolos;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Ismael Ortega Sanchez
 *
 *         Clase que simboliza una tabla de simbolos
 */
public class TablaDeSimbolos {
	private final int TAMANIO_FILA = 10;

	private int id, nParams;
	private String nombreFuncion;
	private List<String[]> operandos;

	public TablaDeSimbolos(int id) {
		this.operandos = new ArrayList<>();
		this.id = id;
	}

	public TablaDeSimbolos(int id, String nombreFuncion) {
		this.operandos = new ArrayList<>();
		this.id = id;
		this.nombreFuncion = nombreFuncion;
	}

	public void setNParams(int nParams) {
		this.nParams = nParams;
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
			String[] elemento = new String[TAMANIO_FILA];
			elemento[0] = id;
			operandos.add(elemento);
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
			if (operandos.get(i)[0].equals(id))
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
	public String[] obtenerIdentificador(int indice) {
		// Como el 0 esta reservacdo para el error le llegara un indice desplazado en 1
		// por eso se resta 1
		return operandos.get(indice - 1);
	}

	/**
	 * Metodo que se encarfa de añadir un operando a la tabla de simbolos
	 * 
	 * @param operando
	 *            Operando a aniadir a la tabla de simbolos
	 */
	public void insertarOperando(String[] operando) {
		this.operandos.add(operando);
	}

	@Override
	/**
	 * Metodo que imprime de una manera clara la tabla de simbolos
	 */
	public String toString() {
		StringBuffer tabla = new StringBuffer();
		int cont = -1; // Contador para saber si ya se han leido todos los parametros

		// SI su id es 1 se trata de la tabla principal
		if (id == 1)
			tabla.append("TABLA PRINCIPAL #1:\n");
		else {
			tabla.append("TABLA de la FUNCIÓN " + nombreFuncion + "#" + id + ":\n");
			cont = 0; // Se inicia el contador a 0 para poder tener en cuenta el numero de parametros
		}

		// recorro los operandos de la tabla de simbolos
		for (String[] operando : operandos) {
			// Si no es una funcion va a tener 3 campos, si es una funcion tendra 4 campos
			// mas un campo por cada parametro
			if (!operando[1].equals("función")) {
				String tipoEntrada = "variable";

				// Si el contador menor que el numero de parametros de la funcion el tipo de la
				// entrada es parametro
				if (cont < nParams)
					tipoEntrada = "parámetro";

				// Si el tipo es chars en vez de una variable se trata de un puntero
				if (operando[1].equals("chars"))
					tipoEntrada = "puntero";

				tabla.append("* LEXEMA: '" + operando[0] + "' (tipo de entrada'" + tipoEntrada + "')\n");
				tabla.append("\t+ tipo: " + operando[1] + "\n");
				tabla.append("\t+ desplazamiento: " + operando[2] + "\n\n");
			} else {
				tabla.append("* LEXEMA: '" + operando[0] + "' (tipo de entrada 'función')\n");
				tabla.append("\t+ tipo: " + operando[1] + "\n");
				tabla.append("\t+ parametros: " + (operando.length - 4) + "\n");

				// Recorro la lista de parametros los cuales van desde el indice 2 (Tercer
				// elemento) hasta el antepenultimo por eso le resto 4 al length (los 2 primeros
				// + los 2 ultimos)
				for (int i = 2; i < operando.length - 4; i++) {
					tabla.append("\t\t+ parametro" + (i - 1) + ": " + operando[i] + "\n");
				}
				tabla.append("\t+ idtabla: " + operando[operando.length - 2] + "\n");
				tabla.append("\t+ tiporetorno" + operando[operando.length - 1] + "\n\n");
			}

			if (cont != -1)
				cont++;
		}

		return tabla.toString();
	}
}
