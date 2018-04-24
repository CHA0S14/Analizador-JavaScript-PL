package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import analizadorLexico.AnalizadorLexico;
import analizadorLexico.Token;
import tablaDeSimbolos.TablaDeSimbolos;

public class TestAnalizadorLéxico {
	private final String CARPETA_PROGRAMAS_LEXICO = "Programas/Test/AnalizadorLexico/";
	private final String CARPETA_RESULTADOS_LEXICO = "Resultados/Test/AnalizadorLexico/";
	private final String SEPARATOR = "***************************************************";
	private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();

	private final String[] FILENAMES = { "DetectaTodosTokens", "ProgramaBasico", "ProgramaBucles",
			"ProgramaFunciones" };

	private static int contFilenames = 0;
	private AnalizadorLexico analizador;

	/**
	 * Metodo que se ejecuta antes de cada test, se encarga de preparar el terreno
	 */
	@Before
	public void prePrueba() {
		// Inicio el analizador lexico de la prueba
		analizador = new AnalizadorLexico(
				Paths.get(CARPETA_PROGRAMAS_LEXICO, FILENAMES[contFilenames] + ".javascript").toString());

		// Cambio la salida de error a errContent para poder tratarlo
		//System.setErr(new PrintStream(errContent));

		// Imprimo un separador para que los output de las pruebas sean mas claros
		System.out.println(SEPARATOR);
	}

	/**
	 * Metodo que se ejecuta despues de cada test, limpia los cambios hecho por
	 * before y por el test
	 */
	@After
	public void postPrueba() {
		// Cambio la salida de error a la de por defecto
		System.setErr(System.err);

		// Aumento el contrador para que el siguiente test coja el siguiente nombre de
		// fichero
		contFilenames++;

		// Borro el analizador usado anteriormente
		analizador = null;
		
		// Limpio las tablas de simbolos
		TablaDeSimbolos.resetTablaDeSimbolos();

		// Imprimo mensajes de finalizacion de test
		System.out.println("\n  -> Prueba finalizada con exito");
		System.out.println(SEPARATOR + "\n");
	}

	/**
	 * Prueba que el analizador lexico es capaz de encontrar todos los tipos de
	 * tokens
	 */
	@Test
	public void AnalizadorLexicoDetectaTiposTokens() {
		System.out.println("Probando que el analizador lexico es capaz de\ndetectar todos los tokens");

		List<String> tokens = getTokens();
		List<String> resultado = getResultados(FILENAMES[contFilenames]);

		assertEquals(tokens, resultado);
	}

	/**
	 * Prueba que el analizador lexico es capaz de detectar todos los tokens de un
	 * programa basico
	 */
	@Test
	public void AnalizadorLexicoDetectaTokensProgramaBasico() {
		System.out.println("Probando que el analizador lexico es capaz de\ndetectar los tokens de un programa basico");
		
		List<String> tokens = getTokens();		
		List<String> resultado = getResultados(FILENAMES[contFilenames]);

		assertEquals(tokens, resultado);
	}

	/**
	 * Prueba que el analizador lexico es capaz de detectar todos los tokens de un
	 * programa basico
	 */
	@Test
	public void AnalizadorLexicoDetectaTokensProgramaBucles() {
		System.out.println(
				"Probando que el analizador lexico es capaz de\ndetectar los tokens de un programa con bucles");

		List<String> tokens = getTokens();
		List<String> resultado = getResultados(FILENAMES[contFilenames]);

		assertEquals(tokens, resultado);
	}

	/**
	 * Prueba que el analizador lexico es capaz de detectar todos los tokens de un
	 * programa basico
	 */
	public void AnalizadorLexicoDetectaTokensProgramaFunciones() {
		System.out.println(
				"Probando que el analizador lexico es capaz de\ndetectar los tokens de un programa con funciones");

		List<String> tokens = getTokens();
		List<String> resultado = getResultados(FILENAMES[contFilenames]);

		assertEquals(tokens, resultado);
	}

	public void err() {
		System.err.print("hello again");
		assertEquals("hello again", errContent.toString());
	}

	/**
	 * Metodo que se encarga de obtener todos los tokens del fichero y devolver un
	 * array con ellos
	 * 
	 * @return Lista con los tokens que ha detectado el analizador lexico
	 */
	private List<String> getTokens() {
		List<String> tokens = new ArrayList<>();

		Token token = analizador.getToken();
		tokens.add(token.toString());
		while (token.getToken() != 53) {
			token = analizador.getToken();
			tokens.add(token.toString());
		}

		return tokens;
	}

	/**
	 * Metodo que se encarga de leer el resultado de los tokens del fichero
	 * correspondiente
	 * 
	 * @param fichero
	 *            Fichero del cual leer los tokens resultado
	 * @return Lista de tokens del fichero resultado
	 */
	private List<String> getResultados(String fichero) {
		List<String> result = new ArrayList<>();

		try (BufferedReader br = new BufferedReader(
				new FileReader(Paths.get(CARPETA_RESULTADOS_LEXICO, fichero).toFile()))) {
			String line;
			while ((line = br.readLine()) != null) {
				result.add(line);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;
	}
}
