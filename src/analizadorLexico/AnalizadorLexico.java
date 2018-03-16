package analizadorLexico;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;

import analizadorLexico.tablas.TablaDeTransicionesMemoria;
import analizadorLexico.tablas.TablaPalabrasReservadas;

/**
 * 
 * @author Ismael Ortega Sanchez
 *
 *         Clase que simboliza el analizador lexico del lenguaje
 */
public class AnalizadorLexico {
	private final int MAX_INT = 32767; // maximo valor del int en el lenguaje

	private Reader fichero; // Fichero con el programa a analizar
	private int estado = 0; // Estado en el que se encuentra el automata
	private char caracter; // Caracter que se acaba de leer del fichero

	private int numero; // variable que ira guardando el valor del token numerico
	private String cadena; // variable que ira guardando las cadenas para distintos tokens
	// TODO permitir acceso a la variable desde fuera
	private boolean zonaDeclaracion = false; // Si nos encontramos en zona de declaracion

	public AnalizadorLexico(String fichero) {
		try {
			InputStream in = new FileInputStream(fichero);
			Reader reader = new InputStreamReader(in, Charset.defaultCharset());
			this.fichero = new BufferedReader(reader);
			leerCaracter();
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
		while (estado != -1) {
			// Obtengo la transicion de la tabla de transiciones
			TransicionLexico transicion = TablaDeTransicionesMemoria.getTransicion(estado, caracter);
			// Actualizo el estado segun la transicion
			this.estado = transicion.getEstado();
			// Proceso las acciones semanticas segun la transicion, si alguna accion
			// semantica genera un token lo capturo para devolverlo
			token = realizarAccionSemantica(transicion.getAccionesSemanticas());
		}

		// Reseteo los valores de las variables auxiliares
		numero = -1;
		cadena = null;

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
			case "N": // Inicializar numero
				numero = Character.getNumericValue(caracter);
				break;
			case "F": // Inicializar numero hexadecimal
				numero = fromHexToDec(caracter);
				break;
			case "D": // Calcular numero decimal
				numero = numero * 10 + Character.getNumericValue(caracter);
				break;
			case "O": // Calcular numero octal
				numero = numero * 8 + Character.getNumericValue(caracter);
				break;
			case "H": // Calcular numero hexadecimal
				numero = numero * 16 + fromHexToDec(caracter);
				break;
			case "E": // Comprobar que el numero no supera el valor maximo
				if (numero > MAX_INT) {
					// TODO gestor de errores
				}
				break;
			case "S": // Crear cadena
				if (caracter == '"' || caracter == '\'') {
					cadena = "";
				} else {
					cadena = caracter + "";
				}
				break;
			case "C": // Concatenar caracter
				cadena.concat(caracter + "");
				break;
			case "P": // Comprobar si identificador es una palabra reservada
				int index = TablaPalabrasReservadas.getCodigoPalabraReservada(cadena);
				if (index == -1) {
					// Se aniade la accion semantica de comprobar si estamos en zona de declaracion
					accionesSemanticas[1] = "T53";
				} else {
					// Se aniade la accion semantica de crear el token de palabra reservada
					// correspondiente
					accionesSemanticas[1] = "T" + index;
				}
				break;
			case "T1": // Generar token +
				token = new Token(1);
				break;
			case "T2": // Generar token -
				token = new Token(2);
				break;
			case "T3": // Generar token *
				token = new Token(3);
				break;
			case "T4": // Generar token /
				token = new Token(4);
				break;
			case "T5": // Generar token %
				token = new Token(5);
				break;
			case "T6": // Generar token ==
				token = new Token(6);
				break;
			case "T7": // Generar token !=
				token = new Token(7);
				break;
			case "T8": // Generar token <
				token = new Token(8);
				break;
			case "T9": // Generar token >
				token = new Token(9);
				break;
			case "T10": // Generar token <=
				token = new Token(10);
				break;
			case "T11": // Generar token >=
				token = new Token(11);
				break;
			case "T12": // Generar token &&
				token = new Token(12);
				break;
			case "T13": // Generar token ||
				token = new Token(13);
				break;
			case "T14": // Generar token !
				token = new Token(14);
				break;
			case "T15": // Generar token ++
				token = new Token(15);
				break;
			case "T16": // Generar token --
				token = new Token(16);
				break;
			case "T17": // Generar token =
				token = new Token(17);
				break;
			case "T18": // Generar token +=
				token = new Token(18);
				break;
			case "T19": // Generar token -=
				token = new Token(19);
				break;
			case "T20": // Generar token *=
				token = new Token(20);
				break;
			case "T21": // Generar token /=
				token = new Token(21);
				break;
			case "T22": // Generar token %=
				token = new Token(22);
				break;
			case "T23": // Generar token &=
				token = new Token(23);
				break;
			case "T24": // Generar token |=
				token = new Token(24);
				break;
			case "T25": // Generar token entero
				token = new Token(25, numero);
				break;
			case "T26": // Generar token cadena
				token = new Token(26, cadena);
				break;
			case "T27": // Generar token true
				token = new Token(27);
				break;
			case "T28": // Generar token false
				token = new Token(28);
				break;
			case "T29": // Generar token var
				token = new Token(29);
				break;
			case "T30": // Generar token int
				token = new Token(30);
				break;
			case "T31": // Generar token bool
				token = new Token(31);
				break;
			case "T32": // Generar token chars
				token = new Token(32);
				break;
			case "T33": // Generar token write
				token = new Token(33);
				break;
			case "T34": // Generar token promp
				token = new Token(34);
				break;
			case "T35": // Generar token return
				token = new Token(35);
				break;
			case "T36": // Generar token if
				token = new Token(36);
				break;
			case "T37": // Generar token else
				token = new Token(37);
				break;
			case "T38": // Generar token while
				token = new Token(38);
				break;
			case "T39": // Generar token do
				token = new Token(39);
				break;
			case "T40": // Generar token for
				token = new Token(40);
				break;
			case "T41": // Generar token switch
				token = new Token(41);
				break;
			case "T42": // Generar token case
				token = new Token(42);
				break;
			case "T43": // Generar token break
				token = new Token(43);
				break;
			case "T44": // Generar token default
				token = new Token(44);
				break;
			case "T45": // Generar token funtion
				token = new Token(45);
				break;
			case "T46": // Generar token (
				token = new Token(46);
				break;
			case "T47": // Generar token )
				token = new Token(47);
				break;
			case "T48": // Generar token {
				token = new Token(48);
				break;
			case "T49": // Generar token }
				token = new Token(49);
				break;
			case "T50": // Generar token ,
				token = new Token(50);
				break;
			case "T51": // Generar token ;
				token = new Token(51);
				break;
			case "T52": // Generar token fin de fichero
				token = new Token(52);
				break;
			case "T53": // Generar Token identificador
				token = new Token(53, cadena);
				/*
				 * if (zonaDeclaracion && !tablaSimbolos.existe(token){
				 * tablaSimbolor.insertarIdentificador(token); } else if {
				 *   si el indice es positivo global, si el indice es negativo de funcion
				 * }
				 */

			}
		}

		return token;
	}

	private int fromHexToDec(char caracter) {
		switch (caracter) {
		case 'A':
			return 10;
		case 'B':
			return 11;
		case 'C':
			return 12;
		case 'D':
			return 13;
		case 'E':
			return 14;
		case 'F':
			return 15;
		default:
			return Character.getNumericValue(caracter);
		}
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
