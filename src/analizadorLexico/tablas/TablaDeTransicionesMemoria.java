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
public class TablaDeTransicionesMemoria {
	// Tabla de transiciones del automata
	private static List<List<TransicionLexico>> tabla = new ArrayList<List<TransicionLexico>>();

	static {
		// Creo las listas de transicion por cada estado

		// Este estado es el estado inicial del automata del analizador lexico, se
		// encarga de iniciar la lectura de algunos tokens y crear los tokens
		// , ; ( ) { } y EOF
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

		// Este estado se encarga de identificar que hacer con el / recibido
		// anteriormente, si llega otra / es un comentario de linea, si llega un * es un
		// comentario de bloque, si llega un igual es un token /= y si llega cualquier
		// otra cosa es un token /
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

		// Estado que se encarga del comentario en linea y se encarfa de pasar todos los
		// caracteres esperando a un salto de linea
		TransicionLexico[] estado2 = {
				// Si llega un salto de linea el comentario de linea ha acabado y volvemos al
				// estado 0 y leemos el siguiente caracter
				new TransicionLexico("\n", 0, new String[] {}),
				// Todo lo que sea distinto del salto de linea se considera comentado asi que se
				// sigue en el estado dos y se lee el siguiente caracter
				new TransicionLexico("[^\n]", 2, new String[] { "L" }) };

		// Estado que se encarga del comentario en bloque y pasa todos los caracteres
		// del programa esperando por un asterisco que es el primero de los dos
		// caracteres que finalizan un comentario de bloque
		TransicionLexico[] estado3 = {
				// Si llega un * es posible que se este acabando el comentario de bloque asi que
				// se va al estado 4 que se va a encargar de comprobar si finaliza o solo es un
				// * comentado y se lee el siguiente caracter
				new TransicionLexico("\\*", 4, new String[] { "L" }),
				// Todo lo que no sea un asterisco se considera comentado y se queda en el
				// estado 3, ademas se lee el siguiente caracter
				new TransicionLexico("[^\\*]", 3, new String[] { "L" }) };

		// Estado que comprueba si un comentario de bloque va a finalizar o no
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

		// Estado que se encarga de generar los tokens que empiezan por '-':
		// -, -- y -=
		TransicionLexico[] estado5 = {
				// Si llega un - es el segundo - seguido asi que se va al estado final -1 y se
				// genera un token 16 reservado para el --
				new TransicionLexico("-", -1, new String[] { "T16" }),
				// Si llega un igual se va al estado final -1 y se genera un token 19 reservado
				// al -=
				new TransicionLexico("=", -1, new String[] { "T19" }),
				// Si llega cualquier otro caracter se va al estado finale -1 y se genera un
				// token 2 reservado al -
				new TransicionLexico("[^-|=]", -1, new String[] { "T2" }) };

		// Este estado se encarga de montar el token numerico decimal distinto de 0
		TransicionLexico[] estado6 = {
				// Si llega un numero cualquiera se aniade el valor a la variable interna
				// numerica de la siguiente manera:
				// numero = 10 * numero + valor_leido
				// Ademas se lee el siguiente caracter y se queda en el estado 6
				new TransicionLexico("[0-9]", 6, new String[] { "D", "L" }),
				// Si llega cualquier otro caracter se comprueba que el numero acumulado no sea
				// mayor a 32767 y se genera el token numerico codificado con el valor 25 y que
				// de segundo valor tiene el valor numerico de la variable interna y pasamos al
				// estado final -1
				new TransicionLexico("[^0-9]", -1, new String[] { "E", "T25" }) };

		// Este estado se encarga de identificar si se va a generar un token numerico
		// ( el cual genera ) decimal igual a cero, un numero octar o uno hexadecimal
		TransicionLexico[] estado7 = {
				// Si llega un numero del 0 al 7 se esta recibiendo un numero octal asi que se
				// va al estado 8 y se inicia un numero nuevo con el valor leido en la variable
				// interna y se lee el siguiente valor
				new TransicionLexico("[0-7]", 8, new String[] { "N", "L" }),
				// Si llega una equis se esta recibiendo un numero hexadecimal asi que se viaja
				// al estado 9 y se lee el siguiente caracter
				new TransicionLexico("x", 9, new String[] { "L" }),
				// Si llega cualquier otro caracter se comprueba que el valor de la variable
				// numerica interna no supere 32767 y se pasa a crear un token 25 cuyo segundo
				// valor es el valor de la variable numerica interna, ademas se viaja al estado
				// final -1
				new TransicionLexico("[^0-7|x]", -1, new String[] { "E", "T25" }) };

		// Este estado se encarga de montar el numero octal y de generar su token
		TransicionLexico[] estado8 = {
				// Si llega un valor numerico octal se añade a la variable numerica interna de
				// la siguiente manera:
				// numero = 8 * numero + valor_leido
				// ademas se lee el siguiente caracter y nos quedamos en el estado 8
				new TransicionLexico("[0-7]", 8, new String[] { "O", "L" }),
				// Si llega cualquier otro caracter se comprueba que el valor de la variable
				// numerica interna no supere 32767 y se pasa a crear un token 25 cuyo segundo
				// valor es el valor de la variable numerica interna, ademas se viaja al estado
				// final -1
				new TransicionLexico("[^0-7]", -1, new String[] { "E", "T25" }) };

		// Este estado se encarga de iniciar la lectura del numero hexadecimal
		TransicionLexico[] estado9 = {
				// Si llega un numero hexadecimal se le asigna a la variable numerica interna el
				// valor recibido, si es una letra se transforma a decimal, ademas se va al
				// estado 10 y se lee el sigueinte caracter
				new TransicionLexico("[0-9|A-F]", 10, new String[] { "F", "L" }) };

		// Este estado se encarga de montar el numero hexadecimal y de generar el token
		// numerico
		TransicionLexico[] estado10 = {
				// Si llega un valor numerico hexadecimal se añade su valor decimal al la
				// variable numerica interna de la siguiente manera:
				// numero = 16 * numero + valor_leido
				// ademas se lee el siguiente caracter y nos quedamos en el estado 10
				new TransicionLexico("[0-9|A-F]", 10, new String[] { "H", "L" }),
				// Si llega cualquier otro caracter se comprueba que el valor de la variable
				// numerica interna no supere 32767 y se pasa a crear un token 25 cuyo segundo
				// valor es el valor de la variable numerica interna, ademas se viaja al estado
				// final -1
				new TransicionLexico("[^0-9|A-F]", -1, new String[] { "E", "T25" }) };

		// Este estado se encarga de montar la cadena de variable String creado con
		// comillas dobles
		TransicionLexico[] estado11 = {
				// Si llegan unas comillas el string se ha acabado con lo que se va al estado
				// final -1 y se genera el token 26 cuyo segundo valor es la cadena en si
				new TransicionLexico("\"", -1, new String[] { "T26" }),
				// Si llega un \ el cual es un caracter de escape, se va al estado 12, se
				// concatena y se lee
				// el siguiente el caracter
				new TransicionLexico("\\\\", 12, new String[] { "C", "L" }),
				// Si llega cualquier otro caracter se concatena en la cadena interna y se lee
				// el siguiente caracter, ademas se queda en el estado 11
				new TransicionLexico("[^\"|\\\\]", 11, new String[] { "C", "L" }) };

		// Este estado se encarga de comprobar que detras del caracter de escape de un
		// string sea un caracter valido siendo estos:
		// n, t, ", \
		TransicionLexico[] estado12 = {
				// Si llega un caracter valido se va al estado 11, se concatena el valor y se
				// lee el siguiente caracter
				new TransicionLexico("[n|t|\"|\\\\]", 11, new String[] { "C", "L" }) };

		// Este estado se encarga de montar la cadena de variable String creado con
		// comillas simples
		TransicionLexico[] estado13 = {
				// Si llegan unas comillas simples el string se ha acabado con lo que se va al
				// estado final -1 y se genera el token 26 cuyo segundo valor es la cadena en si
				new TransicionLexico("'", -1, new String[] { "T26" }),
				// Si llega un \ el cual es un caracter de escape, se va al estado 14, se
				// concatena y se lee
				// el siguiente el caracter
				new TransicionLexico("\\\\", 14, new String[] { "C", "L" }),
				// Si llega cualquier otro caracter se concatena en la cadena interna y se lee
				// el siguiente caracter, ademas se queda en el estado 13
				new TransicionLexico("[^'|\\\\]", 13, new String[] { "C", "L" }) };

		// Este estado se encarga de comprobar que detras del caracter de escape de un
		// string sea un caracter valido siendo estos:
		// n, t, ', \
		TransicionLexico[] estado14 = {
				// Si llega un caracter valido se va al estado 13, se concatena el valor y se
				// lee el siguiente caracter
				new TransicionLexico("[n|t|'|\\\\]", 13, new String[] { "C", "L" }) };

		// este estado se encarga de comprobar que token generar con el mas:
		// ++, += o +
		TransicionLexico[] estado15 = {
				// Si llega un mas se va al estado final -1 y se genera el token 15 reservado
				// para el ++
				new TransicionLexico("+", -1, new String[] { "T15" }),
				// Si llega un igual se va al estado final -1 y se genera el token 18 reservado
				// para el +=
				new TransicionLexico("=", -1, new String[] { "T18" }),
				// Si llega cualquier otro caracter se va al estado final -1 y se genera el
				// token 1 reservado al +
				new TransicionLexico("[^=|+]", -1, new String[] { "T1" }) };

		// este estado se encarga de comprobar que token generar con el por:
		// * o *=
		TransicionLexico[] estado16 = {
				// Si llega un igual se va al estado final -1 y se genera el token 20 reservado
				// para el *=
				new TransicionLexico("=", -1, new String[] { "T20" }),
				// Si llega cualquier otro caracter se va al estado final -1 y se genera el
				// token 3 reservado al *
				new TransicionLexico("[^=]", -1, new String[] { "T3" }) };

		// este estado se encarga de comprobar que token generar con el modelo:
		// % o %=
		TransicionLexico[] estado17 = {
				// Si llega un igual se va al estado final -1 y se genera el token 22 reservado
				// para el %=
				new TransicionLexico("=", -1, new String[] { "T22" }),
				// Si llega cualquier otro caracter se va al estado final -1 y se genera el
				// token 5 reservado al %
				new TransicionLexico("[^=]", -1, new String[] { "T5" }) };

		// este estado se encarga de comprobar que token generar con el igual:
		// = o ==
		TransicionLexico[] estado18 = {
				// Si llega un igual se va al estado final -1 y se genera el token 6 reservado
				// para el ==
				new TransicionLexico("=", -1, new String[] { "T6" }),
				// Si llega cualquier otro caracter se va al estado final -1 y se genera el
				// token 17 reservado al =
				new TransicionLexico("[^=]", -1, new String[] { "T17" }) };

		// este estado se encarga de comprobar que token generar con el &:
		// & o &=
		TransicionLexico[] estado19 = {
				// Si llega un igual se va al estado final -1 y se genera el token 23 reservado
				// para el &=
				new TransicionLexico("=", -1, new String[] { "T23" }),
				// Si llega un & se va al estado final -1 y se genera el token 12 reservado al
				// &&
				new TransicionLexico("&", -1, new String[] { "T12" }) };

		// este estado se encarga de comprobar que token generar con el |:
		// | o |=
		TransicionLexico[] estado20 = {
				// TODO revisa numero token
				// Si llega un igual se va al estado final -1 y se genera el token 24 reservado
				// para el |=
				new TransicionLexico("=", -1, new String[] { "T24" }),
				// Si llega un | se va al estado final -1 y se genera el token 13 reservado al
				// ||
				new TransicionLexico("|", -1, new String[] { "T13" }) };

		// este estado se encarga de concatenar la cadena que hace de identificador
		TransicionLexico[] estado21 = {
				// Si llega una letra, una barra baja o un digito, se sigue leyendo el
				// identificador asi que se concatena la cadena y se pasa al siguiente caracter
				new TransicionLexico("[a-zA-Z|_|[0-9]", -1, new String[] { "C", "L" }),
				// Se llega cualquier otro caracter se comprueba que la cadena formada es una
				// palabra reservada o un identificador, ademas si es un identificador, se
				// mirara si estamos en zona de declaracion, por ultimo se generara el token
				// necesario, no sale en el array de acciones semanticas porque se añadira
				// dinamicamente segun sea necesario
				new TransicionLexico("[^a-zA-Z|_|[0-9]", -1, new String[] { "P", "" }) };

		// este estado se encarga de comprobar que token generar con el >:
		// > o >=
		TransicionLexico[] estado22 = {
				// Si llega un igual se va al estado final -1 y se genera el token 11 reservado
				// para el >=
				new TransicionLexico("=", -1, new String[] { "T11" }),
				// Si llega cualquier otro caracter se va al estado final -1 y se genera el
				// token 9 reservado al >
				new TransicionLexico("[^=]", -1, new String[] { "T9" }) };

		// este estado se encarga de comprobar que token generar con el >:
		// < o <=
		TransicionLexico[] estado23 = {
				// Si llega un igual se va al estado final -1 y se genera el token 10 reservado
				// para el <=
				new TransicionLexico("=", -1, new String[] { "T10" }),
				// Si llega cualquier otro caracter se va al estado final -1 y se genera el
				// token 8 reservado al <
				new TransicionLexico("[^=]", -1, new String[] { "T8" }) };

		// este estado se encarga de comprobar que token generar con la !:
		// ! o !=
		TransicionLexico[] estado24 = {
				// Si llega un igual se va al estado final -1 y se genera el token 7 reservado
				// para el !=
				new TransicionLexico("=", -1, new String[] { "T7" }),
				// Si llega cualquier otro caracter se va al estado final -1 y se genera el
				// token 14 reservado al !
				new TransicionLexico("[^=]", -1, new String[] { "T14" }) };

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
		// Como el equals de transicion realmente trabaja con un string, nos vale
		// lo siguiente pero java te pone un warning para que tengas cuidad por
		// eso el supress warning
		@SuppressWarnings("unlikely-arg-type")
		int indice = tabla.get(estado).indexOf("" + caracter);
		return tabla.get(estado).get(indice);
	}
}
