package analizadorLexico;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;

import analizadorLexico.tablas.TablaDeTransiciones;

/**
 * 
 * @author Ismael Ortega Sanchez
 *
 *         Clase que simboliza el analizador lexico del lenguaje
 */
public class AnalizadorLexico {
	private Reader fichero; // Fichero con el programa a analizar
	private int estado = 0; // Estado en el que se encuentra el automata
	private char caracter; // Caracter que se acaba de leer del fichero

	private int numero = -1;
	private String cadena = null;

	public AnalizadorLexico(String fichero) {
		try {
			InputStream in = new FileInputStream(fichero);
			Reader reader = new InputStreamReader(in, Charset.defaultCharset());
			this.fichero = new BufferedReader(reader);
		} catch (FileNotFoundException e) {
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
	public Token getToken() {
		Token token = null;
		leerCaracter();
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
	private Token realizarAccionSemantica(String[] accionesSemanticas) {
		Token token = null;
		// Recorro la lista de acciones semanticas recibidas
		for (String accion : accionesSemanticas) {
			// Dependiendo del valor realizo una accion u otra
			switch (accion) {
			case "L": // Leer
				leerCaracter();
				break;
			case "N": // Crear numero
				break;
			case "D": // Incrementar numero decimal
				break;
			case "O": // Incrementar numero octal
				break;
			case "H": // Incrementar numero hexadecimal
				break;
			case "S": // Crear cadena
				break;
			case "C": // Concatenar caracter
				break;
			case "T1": // Crear token +
				token = new Token(1);
				break;
			case "T2": // Crear token -
				token = new Token(2);
				break;
			case "T3": // Crear token *
				token = new Token(3);
				break;
			case "T4": // Crear token /
				token = new Token(4);
				break;
			case "T5": // Crear token %
				token = new Token(5);
				break;
			case "T6": // Crear token ==
				token = new Token(6);
				break;
			case "T7": // Crear token !=
				token = new Token(7);
				break;
			case "T8": // Crear token <
				token = new Token(8);
				break;
			case "T9": // Crear token >
				token = new Token(9);
				break;
			case "T10": // Crear token <=
				token = new Token(10);
				break;
			case "T11": // Crear token >=
				token = new Token(11);
				break;
			case "T12": // Crear token &&
				token = new Token(12);
				break;
			case "T13": // Crear token ||
				token = new Token(13);
				break;
			case "T14": // Crear token !
				token = new Token(14);
				break;
			case "T15": // Crear token ++
				token = new Token(15);
				break;
			case "T16": // Crear token --
				token = new Token(16);
				break;
			case "T17": // Crear token =
				token = new Token(17);
				break;
			case "T18": // Crear token +=
				token = new Token(18);
				break;
			case "T19": // Crear token -=
				token = new Token(19);
				break;
			case "T20": // Crear token *=
				token = new Token(20);
				break;
			case "T21": // Crear token /=
				token = new Token(21);
				break;
			case "T22": // Crear token %=
				token = new Token(22);
				break;
			case "T23": // Crear token &=
				token = new Token(23);
				break;
			case "T24": // Crear token |=
				token = new Token(24);
				break;
			case "T25": // Crear token entero
				token = new Token(25, numero);
				break;
			case "T26": // Crear token cadena
				token = new Token(26, cadena);
				break;
			case "T27": // Crear token true
				token = new Token(27);
				break;
			case "T28": // Crear token false
				token = new Token(28);
				break;
			case "T29": // Crear token var
				token = new Token(29);
				break;
			case "T30": // Crear token int
				token = new Token(30);
				break;
			case "T31": // Crear token bool
				token = new Token(31);
				break;
			case "T32": // Crear token chars
				token = new Token(32);
				break;
			case "T33": // Crear token write
				token = new Token(33);
				break;
			case "T34": // Crear token promp
				token = new Token(34);
				break;
			case "T35": // Crear token return
				token = new Token(35);
				break;
			case "T36": // Crear token if
				token = new Token(36);
				break;
			case "T37": // Crear token else
				token = new Token(37);
				break;
			case "T38": // Crear token while
				token = new Token(38);
				break;
			case "T39": // Crear token do
				token = new Token(39);
				break;
			case "T40": // Crear token for
				token = new Token(40);
				break;
			case "T41": // Crear token switch
				token = new Token(41);
				break;
			case "T42": // Crear token case
				token = new Token(42);
				break;
			case "T43": // Crear token break
				token = new Token(43);
				break;
			case "T44": // Crear token default
				token = new Token(44);
				break;
			case "T45": // Crear token funtion
				token = new Token(45);
				break;
			case "T46": // Crear token (
				token = new Token(46);
				break;
			case "T47": // Crear token )
				token = new Token(47);
				break;
			case "T48": // Crear token {
				token = new Token(48);
				break;
			case "T49": // Crear token }
				token = new Token(49);
				break;
			case "T50": // Crear token ,
				token = new Token(50);
				break;
			case "T51": // Crear token ;
				token = new Token(51);
				break;
			case "T52": // Crear token fin de fichero
				token = new Token(54);
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
			int caracterInt = this.fichero.read();
			if (caracterInt == -1) {
				caracter = '\u0000';
			} else {
				caracter = (char) caracterInt;
			}
		} catch (IOException e) {
			// TODO Gestor de errores
			e.printStackTrace();
		}
	}
}
