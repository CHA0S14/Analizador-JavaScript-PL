package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.ExpectedSystemExit;
import org.junit.rules.TemporaryFolder;

import analizadorLexico.AnalizadorLexico;
import analizadorLexico.Token;
import tablaDeSimbolos.TablaDeSimbolos;

public class TestAnalizadorLéxico {
	private final String CARPETA_PROGRAMAS_LEXICO = "Programas/Test/AnalizadorLexico/";
	private final String CARPETA_RESULTADOS_LEXICO = "Resultados/Test/AnalizadorLexico/";
	private final String SEPARATOR = "***************************************************";
	private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
	private final String[] caracteresNoValidos2006 = { "_", "\\", "¡", "á", "é", "í", "ó", "ú", "// algo", "/* algo",
			"/* algo *", "\"algo", "\"algo \n", "' algo", "' algo \n", "identificador"};

	private static int contCaracteresNoValidos = 0;

	@Rule
	// Usamos la libreria http://stefanbirkner.github.io/system-rules/download.html
	// Para evitar la salida de ejecucion por el gestor de errores cuando sea
	// necesario
	public final ExpectedSystemExit exit = ExpectedSystemExit.none();

	@Rule
	// Creo una carpeta que se va a borrar cuando finalice el test
	public final TemporaryFolder carpetaTemporal = new TemporaryFolder();

	/**
	 * Metodo que se ejecuta antes de cada test, se encarga de preparar el terreno
	 */
	@Before
	public void prePrueba() {

		// Cambio la salida de error a errContent para poder tratarlo
		System.setErr(new PrintStream(errContent));

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

		// Limpio las tablas de simbolos
		TablaDeSimbolos.resetTablaDeSimbolos();

		// Imprimo mensajes de finalizacion de test
		System.out.println(SEPARATOR + "\n");
	}

	/**
	 * Prueba que el analizador lexico es capaz de encontrar todos los tipos de
	 * tokens
	 */
	@Test
	public void AnalizadorLexicoDetectaTiposTokens() {
		// Inicio el analizador lexico de la prueba
		AnalizadorLexico analizador = new AnalizadorLexico(
				Paths.get(CARPETA_PROGRAMAS_LEXICO, "DetectaTodosTokens.javascript").toString());

		System.out.println("Probando que el analizador lexico es capaz de\ndetectar todos los tokens");

		List<String> tokens = getTokens(analizador);
		List<String> resultado = getResultados("DetectaTodosTokens");

		assertEquals(tokens, resultado);
	}

	/**
	 * Prueba que el analizador lexico es capaz de detectar todos los tokens de un
	 * programa basico
	 */
	@Test
	public void AnalizadorLexicoDetectaTokensProgramaBasico() {
		// Inicio el analizador lexico de la prueba
		AnalizadorLexico analizador = new AnalizadorLexico(
				Paths.get(CARPETA_PROGRAMAS_LEXICO, "ProgramaBasico.javascript").toString());

		System.out.println("Probando que el analizador lexico es capaz de\ndetectar los tokens de un programa basico");

		List<String> tokens = getTokens(analizador);
		List<String> resultado = getResultados("ProgramaBasico");

		assertEquals(tokens, resultado);
	}

	/**
	 * Prueba que el analizador lexico es capaz de detectar todos los tokens de un
	 * programa con bucles
	 */
	@Test
	public void AnalizadorLexicoDetectaTokensProgramaBucles() {
		// Inicio el analizador lexico de la prueba
		AnalizadorLexico analizador = new AnalizadorLexico(
				Paths.get(CARPETA_PROGRAMAS_LEXICO, "ProgramaBucles.javascript").toString());

		System.out.println(
				"Probando que el analizador lexico es capaz de\ndetectar los tokens de un programa con bucles");

		List<String> tokens = getTokens(analizador);
		List<String> resultado = getResultados("ProgramaBucles");

		assertEquals(tokens, resultado);
	}

	/**
	 * Prueba que el analizador lexico es capaz de detectar todos los tokens de un
	 * programa con funciones
	 */
	@Test
	public void AnalizadorLexicoDetectaTokensProgramaFunciones() {
		// Inicio el analizador lexico de la prueba
		AnalizadorLexico analizador = new AnalizadorLexico(
				Paths.get(CARPETA_PROGRAMAS_LEXICO, "ProgramaFunciones.javascript").toString());

		System.out.println(
				"Probando que el analizador lexico es capaz de\ndetectar los tokens de un programa con funciones");

		List<String> tokens = getTokens(analizador);
		List<String> resultado = getResultados("ProgramaFunciones");

		assertEquals(tokens, resultado);
	}

	/**
	 * Contenido de los test clonicos para cada caracter no valido
	 * 
	 * @throws IOException
	 *             Excepcion que puede darse cuando trabajas con ficheros
	 */
	private void contenidofalloLeerCaracterNoValido() throws IOException {
		String caracter = caracteresNoValidos2006[contCaracteresNoValidos];
		System.out.println(
				"Probando que el analizador lexico es capaz de\ndetectar errores con caracteres no valido " + caracter);

		AnalizadorLexico analizador = new AnalizadorLexico(crearFicheroTemporal(caracter).getAbsolutePath());
		contCaracteresNoValidos++;

		getTokens(analizador);
	}

	@Test
	/**
	 * Test clonico para probar un caracter no valido
	 * 
	 * @throws IOException
	 *             Excepcion que se puede dar al trabajar con ficheros
	 */
	public void falloLeerCaracterNoValido1() throws IOException {
		exit.expectSystemExitWithStatus(2004);
		contenidofalloLeerCaracterNoValido();
	}

	@Test
	/**
	 * Test clonico para probar un caracter no valido
	 * 
	 * @throws IOException
	 *             Excepcion que se puede dar al trabajar con ficheros
	 */
	public void falloLeerCaracterNoValido2() throws IOException {
		exit.expectSystemExitWithStatus(2004);
		contenidofalloLeerCaracterNoValido();
	}

	@Test
	/**
	 * Test clonico para probar un caracter no valido
	 * 
	 * @throws IOException
	 *             Excepcion que se puede dar al trabajar con ficheros
	 */
	public void falloLeerCaracterNoValido3() throws IOException {
		exit.expectSystemExitWithStatus(2004);
		contenidofalloLeerCaracterNoValido();
	}

	@Test
	/**
	 * Test clonico para probar un caracter no valido
	 * 
	 * @throws IOException
	 *             Excepcion que se puede dar al trabajar con ficheros
	 */
	public void falloLeerCaracterNoValido4() throws IOException {
		exit.expectSystemExitWithStatus(2004);
		contenidofalloLeerCaracterNoValido();
	}

	@Test
	/**
	 * Test clonico para probar un caracter no valido
	 * 
	 * @throws IOException
	 *             Excepcion que se puede dar al trabajar con ficheros
	 */
	public void falloLeerCaracterNoValido5() throws IOException {
		exit.expectSystemExitWithStatus(2004);
		contenidofalloLeerCaracterNoValido();
	}

	@Test
	/**
	 * Test clonico para probar un caracter no valido
	 * 
	 * @throws IOException
	 *             Excepcion que se puede dar al trabajar con ficheros
	 */
	public void falloLeerCaracterNoValido6() throws IOException {
		exit.expectSystemExitWithStatus(2004);
		contenidofalloLeerCaracterNoValido();
	}

	@Test
	/**
	 * Test clonico para probar un caracter no valido
	 * 
	 * @throws IOException
	 *             Excepcion que se puede dar al trabajar con ficheros
	 */
	public void falloLeerCaracterNoValido7() throws IOException {
		exit.expectSystemExitWithStatus(2004);
		contenidofalloLeerCaracterNoValido();
	}

	@Test
	/**
	 * Test clonico para probar un caracter no valido
	 * 
	 * @throws IOException
	 *             Excepcion que se puede dar al trabajar con ficheros
	 */
	public void falloLeerCaracterNoValido8() throws IOException {
		exit.expectSystemExitWithStatus(2004);
		contenidofalloLeerCaracterNoValido();
	}

	@Test
	/**
	 * Test clonico para probar un caracter no valido
	 * 
	 * @throws IOException
	 *             Excepcion que se puede dar al trabajar con ficheros
	 */
	public void falloLeerCaracterNoValido9() throws IOException {
		exit.expectSystemExitWithStatus(2004);
		contenidofalloLeerCaracterNoValido();
	}

	@Test
	/**
	 * Test clonico para probar un caracter no valido
	 * 
	 * @throws IOException
	 *             Excepcion que se puede dar al trabajar con ficheros
	 */
	public void falloLeerCaracterNoValido10() throws IOException {
		exit.expectSystemExitWithStatus(2004);
		contenidofalloLeerCaracterNoValido();
	}

	@Test
	/**
	 * Test clonico para probar un caracter no valido
	 * 
	 * @throws IOException
	 *             Excepcion que se puede dar al trabajar con ficheros
	 */
	public void falloLeerCaracterNoValido11() throws IOException {
		exit.expectSystemExitWithStatus(2004);
		contenidofalloLeerCaracterNoValido();
	}

	@Test
	/**
	 * Test clonico para probar un caracter no valido
	 * 
	 * @throws IOException
	 *             Excepcion que se puede dar al trabajar con ficheros
	 */
	public void falloLeerCaracterNoValido12() throws IOException {
		exit.expectSystemExitWithStatus(2004);
		contenidofalloLeerCaracterNoValido();
	}

	@Test
	/**
	 * Test clonico para probar un caracter no valido
	 * 
	 * @throws IOException
	 *             Excepcion que se puede dar al trabajar con ficheros
	 */
	public void falloLeerCaracterNoValido13() throws IOException {
		exit.expectSystemExitWithStatus(2004);
		contenidofalloLeerCaracterNoValido();
	}

	@Test
	/**
	 * Test clonico para probar un caracter no valido
	 * 
	 * @throws IOException
	 *             Excepcion que se puede dar al trabajar con ficheros
	 */
	public void falloLeerCaracterNoValido14() throws IOException {
		exit.expectSystemExitWithStatus(2004);
		contenidofalloLeerCaracterNoValido();
	}

	@Test
	/**
	 * Test clonico para probar un caracter no valido
	 * 
	 * @throws IOException
	 *             Excepcion que se puede dar al trabajar con ficheros
	 */
	public void falloLeerCaracterNoValido15() throws IOException {
		exit.expectSystemExitWithStatus(2004);
		contenidofalloLeerCaracterNoValido();
	}

	@Test
	/**
	 * Test clonico para probar un caracter no valido
	 * 
	 * @throws IOException
	 *             Excepcion que se puede dar al trabajar con ficheros
	 */
	public void falloLeerCaracterNoValido16() throws IOException {
		exit.expectSystemExitWithStatus(2004);
		contenidofalloLeerCaracterNoValido();
	}
	
	@Test
	/**
	 * Test que comprueba que se adminte un programa con un numero al limite maximo 32767
	 * 
	 * @throws IOException Excepcion que se puede dar al trabajar con ficheros
	 */
	public void valorNumericoMaximoValido() throws IOException {
		String caracter = "32767";
		System.out.println(
				"Probando que el analizador lexico es capaz de\ndetectar un valor numerico excesivo = " + caracter);

		AnalizadorLexico analizador = new AnalizadorLexico(crearFicheroTemporal(caracter).getAbsolutePath());

		List<String> respuesta = new ArrayList<>();
		respuesta.add("<25, 32767>");
		respuesta.add("<53, null>");
		
		List<String> tokens = getTokens(analizador);

		assertEquals(tokens, respuesta);
	}
	
	@Test
	/**
	 * Test que comprueba que se genera un error 2001 de fallo  de limite de numero
	 * 
	 * @throws IOException Excepcion que se puede dar al trabajar con ficheros
	 */
	public void valorNumericoExcedido() throws IOException {
		String caracter = "32768";
		System.out.println(
				"Probando que el analizador lexico es capaz de\ndetectar un valor numerico excesivo = " + caracter);

		AnalizadorLexico analizador = new AnalizadorLexico(crearFicheroTemporal(caracter).getAbsolutePath());

		exit.expectSystemExitWithStatus(2001);
		getTokens(analizador);
	}

	@Test
	/**
	 * Test que comprueba que se genera un error 2006 de fallo de inicializacion de
	 * un numero hexadecimal
	 * 
	 * @throws IOException Excepcion que se puede dar al trabajar con ficheros
	 */
	public void falloInicializacionHexadecimal() throws IOException {
		String caracter = "0xz";
		System.out.println(
				"Probando que el analizador lexico es capaz de\ndetectar errores con caracteres no valido " + caracter);

		AnalizadorLexico analizador = new AnalizadorLexico(crearFicheroTemporal(caracter).getAbsolutePath());

		exit.expectSystemExitWithStatus(2006);
		getTokens(analizador);
	}
	
	@Test
	/**
	 * Test que comprueba que se genera un error 2007 de fallo, caracter invalido tras \ en un string
	 * 
	 * @throws IOException Excepcion que se puede dar al trabajar con ficheros
	 */
	public void caracterEspeciaStringErroneoSimple() throws IOException {
		String caracter = "\" \\z \"";
		System.out.println(
				"Probando que el analizador lexico es capaz de\ndetectar un caracter invalido " + caracter);

		AnalizadorLexico analizador = new AnalizadorLexico(crearFicheroTemporal(caracter).getAbsolutePath());

		exit.expectSystemExitWithStatus(2007);
		getTokens(analizador);
	}
	
	@Test
	/**
	 * Test que comprueba que se genera un error 2008 de fallo, caracter invalido tras &
	 * 
	 * @throws IOException Excepcion que se puede dar al trabajar con ficheros
	 */
	public void caracterAndErroneo() throws IOException {
		String caracter = "&a";
		System.out.println(
				"Probando que el analizador lexico es capaz de\ndetectar un caracter invalido " + caracter);

		AnalizadorLexico analizador = new AnalizadorLexico(crearFicheroTemporal(caracter).getAbsolutePath());

		exit.expectSystemExitWithStatus(2008);
		getTokens(analizador);
	}
	
	@Test
	/**
	 * Test que comprueba que se genera un error 2009 de fallo, caracter invalido tras |
	 * 
	 * @throws IOException Excepcion que se puede dar al trabajar con ficheros
	 */
	public void caracterOrErroneo() throws IOException {
		String caracter = "|a";
		System.out.println(
				"Probando que el analizador lexico es capaz de\ndetectar un caracter invalido " + caracter);

		AnalizadorLexico analizador = new AnalizadorLexico(crearFicheroTemporal(caracter).getAbsolutePath());

		exit.expectSystemExitWithStatus(2009);
		getTokens(analizador);
	}
	 
	@Test
	/**
	 * Test que comprueba que se genera un error 2007 de fallo, caracter invalido tras \ en un string
	 * 
	 * @throws IOException Excepcion que se puede dar al trabajar con ficheros
	 */
	public void caracterEspeciaStringErroneoDoble() throws IOException {
		String caracter = "' \\z '";
		System.out.println(
				"Probando que el analizador lexico es capaz de\ndetectar un caracter invalido " + caracter);

		AnalizadorLexico analizador = new AnalizadorLexico(crearFicheroTemporal(caracter).getAbsolutePath());

		exit.expectSystemExitWithStatus(2007);
		getTokens(analizador);
	}

	/**
	 * Crea un fichero temporal con el texto correspondiente
	 * 
	 * @param texto
	 *            Texto que tiene que contener el fichero temporal
	 * @return Fichero temporal con el cual trabajar
	 * @throws IOException
	 */
	private File crearFicheroTemporal(String texto) throws IOException {
		File ficheroTemporal = carpetaTemporal.newFile();
		PrintWriter out = new PrintWriter(new FileOutputStream(ficheroTemporal, false));
		out.print(texto);
		out.close();
		return ficheroTemporal;
	}

	/**
	 * Metodo que se encarga de obtener todos los tokens del fichero y devolver un
	 * array con ellos
	 * 
	 * @return Lista con los tokens que ha detectado el analizador lexico
	 */
	private List<String> getTokens(AnalizadorLexico analizador) {
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
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return result;
	}
}
