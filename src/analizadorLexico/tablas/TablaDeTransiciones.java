package analizadorLexico.tablas;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import analizadorLexico.TransicionLexico;

/**
 * 
 * @author Ismael Ortega Sanchez
 *
 *         Esta clase representa la tabla de transiciones correspondiente al
 *         automata del analizador lexico
 */
public class TablaDeTransiciones {
	// Tabla de transiciones del automata
	private static List<List<TransicionLexico>> tabla = new ArrayList<List<TransicionLexico>>();

	static {
		// Creo las listas de transicion por cada estado
		TransicionLexico[] estado0 = {
				// Si llega una / se va a al estado 1 y la accion semantica es la de lectura
				// se espera que sea un comentario, de line o bloque, o el token / o /=
				new TransicionLexico("/", 1, new String[] { "L" }),
				// Si llega un - se va al estado 5 y la accion semantica es la de lectura
				// se espera un token de tipo -, -- o -=
				new TransicionLexico("-", 5, new String[] { "L" }),
				// Si llega un numero del 1 al 9 se va al estado 6 y las acciones sematicas son
				// la de creacion de numero y de lectura
				// Se espera que se genere un token de tipo decimal no 0
				new TransicionLexico("[1-9]", 6, new String[] { "N", "L" }),
				// Si llega un 0 se va al estado 7 y las acciones semanticas son la de creacion
				// de un numero y de lectura
				// Se espera un token numerico octar, hexadecimal o decimal siendo este un 0
				new TransicionLexico("0", 7, new String[] { "N", "L" }),
				// Si llegan unas " se va al estado 11 y las acciones semanticas son la de
				// creacion de una cadena y de lectura
				// Se espera la creacion de una cadena String con comillas dobles
				new TransicionLexico("\"", 11, new String[] { "S", "L" }),
				// Si llegan unas comillas simples se va al estado 13 y las acciones semanticas
				// son de creacion de cadena y de lectura
				// Se espera la creacion de una cadena String con comillas simples
				new TransicionLexico("'", 13, new String[] { "S", "L" }),
				// Si llega un ampersand se va al estado 19 y la accion semantica es de lectura
				// Se espera un token && o &=
				new TransicionLexico("&", 19, new String[] { "L" }),
				// Si llega una barra vertical se va al estado 20 y la accion semantica es de
				// lectura
				// Se espera un token || o |=
				new TransicionLexico("|", 20, new String[] { "L" }),
				// Si llega una letra minuscula o mayuscula se va al estado 21 y las acciones
				// semanticas son de creacion de cadena y de lectura
				// Se espera un token de tipo palabra reservada o id
				new TransicionLexico("[a-zA-z]", 21, new String[] { "S", "L" }),
				// Si llega una coma se va al estado final -1 y la accion semantica es de crear
				// un token 50 reservado para la coma
				new TransicionLexico(",", -1, new String[] { "T50" }),
				// Si llega un punto y coma se va al estado final -1 y la accion semantica es la
				// creacion de un token 51 reservado para el punto y coma
				new TransicionLexico(";", -1, new String[] { "T51" }),
				// Si llega una exclamacion se va al estado 24 y la accion semantica es de
				// lectura
				// Se espera un toke ! o !=
				new TransicionLexico("!", 24, new String[] { "L" }),
				// Si llega una { se va al estado final -1 y se genera un token 48 reservado
				// para el abrir llave
				new TransicionLexico("{", -1, new String[] { "48" }),
				// Si llega una } se va al estado final -1 y se genera un token 49 reservado
				// para el cerrar llave
				new TransicionLexico("}", -1, new String[] { "T49" }),
				// Si llega un ( se va al estado final -1 y se genera el token
				// 46 reservado para abrir parentesis
				new TransicionLexico("\\(", -1, new String[] { "T46" }),
				// Si llega un ) se va al estado final -1 y se genera el token 47 reservado para
				// cerrar parentesis
				new TransicionLexico("\\)", -1, new String[] { "T47" }),
				// Si llega un < se va al estado 23 y la accion semantica es de lectura
				// Se espera un token < o <=
				new TransicionLexico("<", 23, new String[] { "L" }),
				// Si llega un < se va al estado 22 y la accion semantica es de lectura
				// Se espera un token > o >=
				new TransicionLexico(">", 22, new String[] { "L" }),
				// Si llega un igual se va al estado 18 y la accion semantica es de lectura
				// Se espera un toke = o ==
				new TransicionLexico("=", 18, new String[] { "L" }),
				// Si llega un % se va al estado 17 y la accion semantica es de lectura
				// Se espera un toke % o %=
				new TransicionLexico("%", 17, new String[] { "L" }),
				// Si llega un * se va al estado 16 y la accion semantica es de lectura
				// Se espera un token * o *=
				new TransicionLexico("\\*", 16, new String[] { "L" }),
				// Si llega un + se va al estado 15 y la accion semantica es de lectura
				// Se espera un token +, += o ++
				new TransicionLexico("\\+", 15, new String[] { "L" }),
				// Si llega un char nulo quiere decir que se ha alcanzado el final del fichero y
				// se crea el token 54 reservado para el EOF
				new TransicionLexico('\u0000' + "", -1, new String[] { "T54" }),
				// Si llega un espacio, una tabulacion, un retorno de carro o un salto de linea,
				// se queda en el estado 0 y lee el siguiente caracter
				new TransicionLexico("( |\r|\t|\n)", 0, new String[] { "L" }) };

		TransicionLexico[] estado1 = {
				// Si llega una / es que se ha generado un comentario de line, se va al estado 2
				// y se lee el siguiente caracter
				new TransicionLexico("/", 2, new String[] { "L" }),
				// Si llega un asterisco es que se ha generado un comentario de bloque, se va al
				// estado 3 y se lee el siguiente caracter
				new TransicionLexico("*", 3, new String[] { "L" }),
				// Si llega un igual hay que generar el token /= codificado como 21
				new TransicionLexico("=", -1, new String[] { "T21" }),
				// Si llega cualquier otro caracter hay que generar el token de division /
				// codificado como 4
				// "[^/|\\*|=]" Esto simboliza cualquier cosa que no se /, * o =
				new TransicionLexico("[^/|\\*|=]", -1, new String[] { "T4" }) };

		TransicionLexico[] estado2 = {
				// Si llega un salto de linea el comentario de linea ha acabado y volvemos al
				// estado 0 y leemos el siguiente caracter
				new TransicionLexico("\n", 0, new String[] {}),
				// Todo lo que sea distinto del salto de linea se considera comentado asi que se
				// sigue en el estado dos y se lee el siguiente caracter
				new TransicionLexico("[^\n]", 2, new String[] { "L" }) };

		TransicionLexico[] estado3 = {
				// Si llega un * es posible que se este acabando el comentario de bloque asi que
				// se va al estado 4 que se va a encargar de comprobar si finaliza o solo es un
				// * comentado y se lee el siguiente caracter
				new TransicionLexico("\\*", 4, new String[] { "L" }),
				// Todo lo que no sea un asterisco se considera comentado y se queda en el
				// estado 3, ademas se lee el siguiente caracter
				new TransicionLexico("[^\\*]", 3, new String[] { "L" }) };

		TransicionLexico[] estado4 = {
				// Si llega un / el comentario de bloque se ha acabado y se vuelve al estado 0
				// leyendo el siguiente caracter
				new TransicionLexico("/", 0, new String[] { "L" }),
				// Si siguen llegando asteriscos existe todavia la posibilidad de que vaya a
				// acabar el comentario asi que se queda en el estado 4 y lee el siguiente
				// caracter
				new TransicionLexico("\\*", 4, new String[] { "L" }),
				// Si no llega una / ni un asterisco, no va a acabar el comentario de bloque asi
				// que se vuelve al estado 3 y se lee el siguiente caracter
				new TransicionLexico("[^/|\\*]", 3, new String[] { "L" }) };

		TransicionLexico[] estado5 = {};
		
		TransicionLexico[] estado6 = {};
		
		TransicionLexico[] estado7 = {};
		
		TransicionLexico[] estado8 = {};
		
		TransicionLexico[] estado9 = {};
		
		TransicionLexico[] estado10 = {};
		
		TransicionLexico[] estado11 = {};
		
		TransicionLexico[] estado12 = {};
		
		TransicionLexico[] estado13 = {};
		
		TransicionLexico[] estado14 = {};
		
		TransicionLexico[] estado15 = {};
		
		TransicionLexico[] estado16 = {};
		
		TransicionLexico[] estado17 = {};
		
		TransicionLexico[] estado18 = {};
		
		TransicionLexico[] estado19 = {};
		
		TransicionLexico[] estado20 = {};
		
		TransicionLexico[] estado21 = {};
		
		TransicionLexico[] estado22 = {};
		
		TransicionLexico[] estado23 = {};
		
		TransicionLexico[] estado24 = {};

		// Añado las transiciones de los estados a la tabla
		tabla.add(Arrays.asList(estado0));
		tabla.add(Arrays.asList(estado1));
		tabla.add(Arrays.asList(estado2));
		tabla.add(Arrays.asList(estado3));
		tabla.add(Arrays.asList(estado4));
		tabla.add(Arrays.asList(estado5));
		tabla.add(Arrays.asList(estado6));
		tabla.add(Arrays.asList(estado7));
		tabla.add(Arrays.asList(estado8));
		tabla.add(Arrays.asList(estado9));
		tabla.add(Arrays.asList(estado10));
		tabla.add(Arrays.asList(estado11));
		tabla.add(Arrays.asList(estado12));
		tabla.add(Arrays.asList(estado13));
		tabla.add(Arrays.asList(estado14));
		tabla.add(Arrays.asList(estado15));
		tabla.add(Arrays.asList(estado16));
		tabla.add(Arrays.asList(estado17));
		tabla.add(Arrays.asList(estado18));
		tabla.add(Arrays.asList(estado19));
		tabla.add(Arrays.asList(estado20));
		tabla.add(Arrays.asList(estado21));
		tabla.add(Arrays.asList(estado22));
		tabla.add(Arrays.asList(estado23));
		tabla.add(Arrays.asList(estado24));

	}

	/**
	 * Metodo que se encarga de devolver la transicion correspondiente dado un
	 * estado y el caracter
	 * 
	 * @param estado
	 *            Estado en el que se encuentra el automata
	 * @param caracter
	 *            Caracter leido del fichero que lanza la transicion
	 * @return Transicion a realizar
	 */
	public static TransicionLexico getTransicion(int estado, char caracter) {
		@SuppressWarnings("unlikely-arg-type") // Como el equals de transicion realmente trabaja con un string, nos vale
												// lo siguiente pero java te pone un warning para que tengas cuidad por
												// eso el supress warning
		int indice = tabla.get(estado).indexOf("" + caracter);
		return tabla.get(estado).get(indice);
	}
}
