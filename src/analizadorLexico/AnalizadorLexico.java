package analizadorLexico;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;

public class AnalizadorLexico {
	private Reader fichero;
	private int estado = 0;
	private char caracter;

	public AnalizadorLexico(String fichero) {
		try {
			InputStream in = new FileInputStream(fichero);
			Reader reader = new InputStreamReader(in, Charset.defaultCharset());
			this.fichero = new BufferedReader(reader);
			this.caracter = (char) this.fichero.read();
		} catch (FileNotFoundException e) {
			// TODO Gestor de errores
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Gestor de errores
			e.printStackTrace();
		}
	}

	/**
	 * Metodo que se encarga de entregar el siguiente token al analizador sintactico
	 * cuando este se lo pide
	 * 
	 * @return Par de int que formaria el token
	 */
	public int[] getToken() {
		int[] token = null;
		while (estado != -1) {
			// Obtengo la transicion de la tabla de transiciones
			TransicionLexico transicion = TablaDeTransiciones.getTransicion(estado, caracter);
			// Actualizo el estado segun la transicion
			this.estado = transicion.getEstado();
			// Proceso las acciones semanticas segun la transicion, si alguna accion
			// semantica genera un token lo capturo para devolverlo
			token = realizarAccionSemantica(transicion.getAccionesSemanticas());
		}
		return token;
	}

	/***
	 * Metodo que se encarga de procesar las acciones semanticas, como habra muchas
	 * se ha dividido en metodos para facilitar la lectura
	 * 
	 * @param accionesSemanticas
	 *            Array de int que contiene las codificaciones de las acciones
	 *            semanticas a realizar
	 */
	private int[] realizarAccionSemantica(char[] accionesSemanticas) {
		int[] token = null;
		// Recorro la lista de acciones semanticas recibidas
		for (char accion : accionesSemanticas) {
			// Dependiendo del valor realizo una accion u otra
			switch (accion) {
			case 'L': // Leer
				leerCaracter();
				break;
			case 'N': // Crear numero
				break;
			case 'D': // Incrementar numero decimal
				break;
			case 'O': // Incrementar numero octal
				break;
			case 'H': // Incrementar numero hexadecimal
				break;
			case 'S': // Crear cadena
				break;
			case 'C': // Concatenar caracter
				break;
			case 'T': // Crear token
				break;
			}
		}

		return token;
	}

	/**
	 * Accion semantica de leer
	 * 
	 * Se encarga de leer el siguiente caracter del fichero
	 */
	private void leerCaracter() {
		try {
			// Actualizo el caracter por el siguiente en el fichero
			this.caracter = (char) fichero.read();
		} catch (IOException e) {
			// TODO Gestor de errores
			e.printStackTrace();
		}
	}
}
