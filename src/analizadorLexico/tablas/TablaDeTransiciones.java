package analizadorLexico.tablas;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import analizadorLexico.AnalizadorLexico;
import analizadorLexico.TransicionLexico;

/***
 * 
 * @author Ismael Ortega Sanchez
 *
 */
public class TablaDeTransiciones {
	private static final String SEPARATOR = ";";
	private static final String QUOTE = "\"";
	private static String[] cabeceras = null;
	private static String[][] tablaDeTransiciones = null;

	/**
	 * Este metodo funciona igual que un constructor pero para una clase estatica,
	 * como las clases estaticas no se instancian este metodo se ejecuta segun se
	 * inicia el programa
	 */
	static {
		try {
			// abro el fichero que contiene la tabla de transiciones
			// Hacer que la tabla de transiciones sea recibida por parametro
			BufferedReader br = new BufferedReader(new FileReader("TablaDeTransiciones.csv"));

			// Leo la primera linea, la cual contiene las cabeceras de las columnas
			String linea = br.readLine();
			// linea.replaceAll(";;", ";") me quita todas las celdas vacias que estan para
			// mayor legibilidad en excel
			// split(SEPARATOR) me separa el string en un array segun el caracter de
			// separacion en este caso ;
			cabeceras = quitarComasDeEscape(
					linea.replaceAll(";;", ";").replaceAll("\";\"", "punto y coma").split(SEPARATOR));

			// metodo que sustituye las notaciones especiales
			sustituirNotacionesEspeciales();

			// Creo un array list que contendra la tabla de transiciones
			List<String[]> tablaDeTransiciones = new ArrayList<>();
			// Leo la siguiente linea para empezar a generar la tabla de transiciones
			linea = br.readLine();
			// Hasta que no queden lineas que leer, cojo los string de las linea y los
			// separo en arrays segun ;
			// en este caso no quito las celdas vacias porque me indican cuando una
			// transicion lanza un error
			while (null != linea) {
				tablaDeTransiciones.add(quitarComasDeEscape(linea.split(SEPARATOR)));
				linea = br.readLine();
			}

			// Guardo la tabla de transiciones generada en una variable de clase estatica
			// para poder usarla luego
			TablaDeTransiciones.tablaDeTransiciones = tablaDeTransiciones.toArray(new String[][] {});

			// Cierro el fichero
			br.close();
		} catch (IOException e) {
			// TODO Gestor de errores
		}
	}

	/**
	 * Este metodo recibiendo el estado en el que se encuentra el automata y el
	 * caracter leido del fichero, devuelve la transicion correspondiente
	 * 
	 * @param estado
	 *            Estado en el que se encuentra el automata, por como esta formada
	 *            la matriz corresponde con la fila de la tabla de transiciones
	 * @param caracter
	 *            Caracter que se ha leido del fichero
	 * @return Objeto transicion con el estado y las acciones semanticas a realizar
	 */
	public static TransicionLexico getTransicion(int estado, char caracter) {
		// Obtengo la columna de la matriz
		// Por cada columna de la cabecera corresponden dos columnas de la tabla de
		// transiciones por eso hay que multiplicar por dos el valor de la coluna
		int columna = getColumna(caracter + "") * 2;

		if (columna == -2) {
			// TODO Gestor de errores
			System.out.println("Estado: " + estado + " " + AnalizadorLexico.getNlinea() + ": " + caracter);
			System.out.println(Arrays.toString(cabeceras));
			System.exit(1);
		}

		// Primero obtengo el estado destino
		String estadoDestino = tablaDeTransiciones[estado][columna];
		// Luego las acciones semanticas correspondientes
		String accionSemantica = tablaDeTransiciones[estado][columna + 1];
		// Si el estado destino esta vacio, las acciones semanticas contendran un codigo
		// de error que tendra que tratar el gestor de errores
		if (estadoDestino.equals("")) {
			// TODO Gestor de errores codigo Accion semantica
			System.out.println("Estado: " + estado + " " + AnalizadorLexico.getNlinea() + ": " + caracter);
			System.out.println(Arrays.toString(cabeceras));
			System.exit(1);
			// return new TransicionLexico(-1, accionSemantica);
		}

		// Creo la transicion y la devuelvo
		return new TransicionLexico(Integer.valueOf(estadoDestino), accionSemantica);
	}

	/**
	 * Este metodo dado un caracter te devuelve la columna de la trabla de
	 * transiciones realizando un macheo con la expresion regular de la cabecera
	 * 
	 * @param caracter
	 *            Caracter que se acaba de leer del fichero
	 * @return indice de la columna de la tabla de transiciones
	 */
	private static int getColumna(String caracter) {
		int i = 0;

		for (; i < cabeceras.length; i++) {
			if (Pattern.matches(cabeceras[i], caracter))
				return i;
		}

		return -1;
	}

	/**
	 * Este metodo se encarga de quitar las comillas dobres usadas para escapar
	 * caracteres especiales en los csv
	 * 
	 * @param campos
	 *            Campos a los cuales se les quiere quitar las comillas
	 * @return Array con los campos ya editados
	 */
	private static String[] quitarComasDeEscape(String[] campos) {
		for (int i = 0; i < campos.length; i++) {
			campos[i] = campos[i].replaceAll("^" + QUOTE, "").replaceAll(QUOTE + "$", "");
			if (campos[i].equals("\"\""))
				campos[i] = "\"";
		}

		return campos;
	}

	/**
	 * Metodo que se encarga de sustituir las notaciones especiales del csv con los
	 * valores esperados
	 */
	private static void sustituirNotacionesEspeciales() {
		for (int i = 0; i < cabeceras.length; i++) {
			if (cabeceras[i].equals("punto y coma")) {
				cabeceras[i] = ";";
			}
			if (cabeceras[i].equals("EOF")) {
				cabeceras[i] = '\u0000' + "";
			}
		}
	}
}
