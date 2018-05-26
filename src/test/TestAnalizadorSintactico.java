package test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.nio.file.Paths;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.ExpectedSystemExit;
import org.junit.rules.TemporaryFolder;

import analizadorLexico.AnalizadorLexico;
import analizadorSintactico.AnalizadorSintactico;
import tablaDeSimbolos.TablaDeSimbolos;

public class TestAnalizadorSintactico {
	private final String CARPETA_PROGRAMAS_SINTACTICO = "Programas/Test/AnalizadorSintactico/";
	private final String SEPARATOR = "***************************************************";
	private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();

	private final String[] caracteres3001 = { "+", "-", "*", "/", "%", "==", "!=", "<", ">", "<=", ">=", "&&", "||",
			"!", "=", "+=", "-=", "*=", "/=", "%=", "&=", "|=", "1234", "0xAB9", "01234", "'hola'", "\"hola\"", "true ",
			"false ", "int ", "bool ", "chars ", "case ", "default ", "(", ")", "{", "}", ",", ";", ":" };
	private static int contCaracteres3001 = -1;

	private final String[] caracteres3002 = { "for(+", "for(-", "for(*", "for(/", "for(%", "for(==", "for(!=", "for(<",
			"for(>", "for(<=", "for(>=", "for(&&", "for(||", "for(!", "for(=", "for(+=", "for(-=", "for(*=", "for(/=",
			"for(%=", "for(&=", "for(|=", "for(1234", "for(0xAB9", "for(01234", "for('hola'", "for(\"hola\"",
			"for(true ", "for(false ", "for(int ", "for(bool ", "for(chars ", "for(case ", "for(default ", "for((",
			"for()", "for({", "for(}", "for(,", "for(;", "for(:", "for(while ", "for(if ", "for(switch ", "for(do ",
			"for(for ", "for(prompt ", "for(write ", "for(return ", "for(++ ", "for(-- ", "for(break " };
	private static int contCaracteres3002 = -1;

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

		// if (errContent.size() != 0) {
		// System.out.println(errContent.toString());
		// errContent.reset();
		// }

		// Limpio las tablas de simbolos
		TablaDeSimbolos.resetTablaDeSimbolos();

		// Imprimo mensajes de finalizacion de test
		System.out.println(SEPARATOR + "\n");
	}

	/**
	 * Prueba que el analizador sintactico es capaz analizar correctamente un
	 * programa basico
	 */
	@Test
	public void AnalizadorSintacticoProgramaBasico() {
		// Inicio el analizador lexico de la prueba
		AnalizadorSintactico analizador = new AnalizadorSintactico(
				new AnalizadorLexico(Paths.get(CARPETA_PROGRAMAS_SINTACTICO, "ProgramaBasico.javascript").toString()));

		System.out.println(
				"Probando que el analizador sintactico es capaz de\ndetectar los tokens de un programa basico");

		analizador.p();

	}

	/**
	 * Prueba que el analizador sintactico es capaz analizar correctamente un
	 * programa con bucles
	 */
	@Test
	public void AnalizadorSintacticoProgramaBucles() {
		// Inicio el analizador lexico de la prueba
		AnalizadorSintactico analizador = new AnalizadorSintactico(
				new AnalizadorLexico(Paths.get(CARPETA_PROGRAMAS_SINTACTICO, "ProgramaBucles.javascript").toString()));

		System.out.println(
				"Probando que el analizador sintactico es capaz de\ndetectar los tokens de un programa con bucles");

		analizador.p();

	}

	/**
	 * Prueba que el analizador sintactico es capaz analizar correctamente un
	 * programa con funciones
	 */
	@Test
	public void AnalizadorSintacticoTokensProgramaFunciones() {
		// Inicio el analizador lexico de la prueba
		AnalizadorSintactico analizador = new AnalizadorSintactico(new AnalizadorLexico(
				Paths.get(CARPETA_PROGRAMAS_SINTACTICO, "ProgramaFunciones.javascript").toString()));

		System.out.println(
				"Probando que el analizador sintactico es capaz de\ndetectar los tokens de un programa con funciones");

		analizador.p();

	}

	/**
	 * Metodo que prueba que se lanza correctamente un error 3001
	 * 
	 * @throws IOException
	 *             Error que puede dar cuando hacer manejo de ficheros
	 */
	@Test
	public void error3001_1() throws IOException {
		contCaracteres3001++;
		exit.expectSystemExitWithStatus(3001);
		generarError(caracteres3001, contCaracteres3001, 3001);
	}

	/**
	 * Metodo que prueba que se lanza correctamente un error 3001
	 * 
	 * @throws IOException
	 *             Error que puede dar cuando hacer manejo de ficheros
	 */
	@Test
	public void error3001_2() throws IOException {
		contCaracteres3001++;
		exit.expectSystemExitWithStatus(3001);
		generarError(caracteres3001, contCaracteres3001, 3001);
	}

	/**
	 * Metodo que prueba que se lanza correctamente un error 3001
	 * 
	 * @throws IOException
	 *             Error que puede dar cuando hacer manejo de ficheros
	 */
	@Test
	public void error3001_3() throws IOException {
		contCaracteres3001++;
		exit.expectSystemExitWithStatus(3001);
		generarError(caracteres3001, contCaracteres3001, 3001);
	}

	/**
	 * Metodo que prueba que se lanza correctamente un error 3001
	 * 
	 * @throws IOException
	 *             Error que puede dar cuando hacer manejo de ficheros
	 */
	@Test
	public void error3001_4() throws IOException {
		contCaracteres3001++;
		exit.expectSystemExitWithStatus(3001);
		generarError(caracteres3001, contCaracteres3001, 3001);
	}

	/**
	 * Metodo que prueba que se lanza correctamente un error 3001
	 * 
	 * @throws IOException
	 *             Error que puede dar cuando hacer manejo de ficheros
	 */
	@Test
	public void error3001_5() throws IOException {
		contCaracteres3001++;
		exit.expectSystemExitWithStatus(3001);
		generarError(caracteres3001, contCaracteres3001, 3001);
	}

	/**
	 * Metodo que prueba que se lanza correctamente un error 3001
	 * 
	 * @throws IOException
	 *             Error que puede dar cuando hacer manejo de ficheros
	 */
	@Test
	public void error3001_6() throws IOException {
		contCaracteres3001++;
		exit.expectSystemExitWithStatus(3001);
		generarError(caracteres3001, contCaracteres3001, 3001);
	}

	/**
	 * Metodo que prueba que se lanza correctamente un error 3001
	 * 
	 * @throws IOException
	 *             Error que puede dar cuando hacer manejo de ficheros
	 */
	@Test
	public void error3001_7() throws IOException {
		contCaracteres3001++;
		exit.expectSystemExitWithStatus(3001);
		generarError(caracteres3001, contCaracteres3001, 3001);
	}

	/**
	 * Metodo que prueba que se lanza correctamente un error 3001
	 * 
	 * @throws IOException
	 *             Error que puede dar cuando hacer manejo de ficheros
	 */
	@Test
	public void error3001_8() throws IOException {
		contCaracteres3001++;
		exit.expectSystemExitWithStatus(3001);
		generarError(caracteres3001, contCaracteres3001, 3001);
	}

	/**
	 * Metodo que prueba que se lanza correctamente un error 3001
	 * 
	 * @throws IOException
	 *             Error que puede dar cuando hacer manejo de ficheros
	 */
	@Test
	public void error3001_9() throws IOException {
		contCaracteres3001++;
		exit.expectSystemExitWithStatus(3001);
		generarError(caracteres3001, contCaracteres3001, 3001);
	}

	/**
	 * Metodo que prueba que se lanza correctamente un error 3001
	 * 
	 * @throws IOException
	 *             Error que puede dar cuando hacer manejo de ficheros
	 */
	@Test
	public void error3001_10() throws IOException {
		contCaracteres3001++;
		exit.expectSystemExitWithStatus(3001);
		generarError(caracteres3001, contCaracteres3001, 3001);
	}

	/**
	 * Metodo que prueba que se lanza correctamente un error 3001
	 * 
	 * @throws IOException
	 *             Error que puede dar cuando hacer manejo de ficheros
	 */
	@Test
	public void error3001_11() throws IOException {
		contCaracteres3001++;
		exit.expectSystemExitWithStatus(3001);
		generarError(caracteres3001, contCaracteres3001, 3001);
	}

	/**
	 * Metodo que prueba que se lanza correctamente un error 3001
	 * 
	 * @throws IOException
	 *             Error que puede dar cuando hacer manejo de ficheros
	 */
	@Test
	public void error3001_12() throws IOException {
		contCaracteres3001++;
		exit.expectSystemExitWithStatus(3001);
		generarError(caracteres3001, contCaracteres3001, 3001);
	}

	/**
	 * Metodo que prueba que se lanza correctamente un error 3001
	 * 
	 * @throws IOException
	 *             Error que puede dar cuando hacer manejo de ficheros
	 */
	@Test
	public void error3001_13() throws IOException {
		contCaracteres3001++;
		exit.expectSystemExitWithStatus(3001);
		generarError(caracteres3001, contCaracteres3001, 3001);
	}

	/**
	 * Metodo que prueba que se lanza correctamente un error 3001
	 * 
	 * @throws IOException
	 *             Error que puede dar cuando hacer manejo de ficheros
	 */
	@Test
	public void error3001_14() throws IOException {
		contCaracteres3001++;
		exit.expectSystemExitWithStatus(3001);
		generarError(caracteres3001, contCaracteres3001, 3001);
	}

	/**
	 * Metodo que prueba que se lanza correctamente un error 3001
	 * 
	 * @throws IOException
	 *             Error que puede dar cuando hacer manejo de ficheros
	 */
	@Test
	public void error3001_15() throws IOException {
		contCaracteres3001++;
		exit.expectSystemExitWithStatus(3001);
		generarError(caracteres3001, contCaracteres3001, 3001);
	}

	/**
	 * Metodo que prueba que se lanza correctamente un error 3001
	 * 
	 * @throws IOException
	 *             Error que puede dar cuando hacer manejo de ficheros
	 */
	@Test
	public void error3001_16() throws IOException {
		contCaracteres3001++;
		exit.expectSystemExitWithStatus(3001);
		generarError(caracteres3001, contCaracteres3001, 3001);
	}

	/**
	 * Metodo que prueba que se lanza correctamente un error 3001
	 * 
	 * @throws IOException
	 *             Error que puede dar cuando hacer manejo de ficheros
	 */
	@Test
	public void error3001_17() throws IOException {
		contCaracteres3001++;
		exit.expectSystemExitWithStatus(3001);
		generarError(caracteres3001, contCaracteres3001, 3001);
	}

	/**
	 * Metodo que prueba que se lanza correctamente un error 3001
	 * 
	 * @throws IOException
	 *             Error que puede dar cuando hacer manejo de ficheros
	 */
	@Test
	public void error3001_18() throws IOException {
		contCaracteres3001++;
		exit.expectSystemExitWithStatus(3001);
		generarError(caracteres3001, contCaracteres3001, 3001);
	}

	/**
	 * Metodo que prueba que se lanza correctamente un error 3001
	 * 
	 * @throws IOException
	 *             Error que puede dar cuando hacer manejo de ficheros
	 */
	@Test
	public void error3001_19() throws IOException {
		contCaracteres3001++;
		exit.expectSystemExitWithStatus(3001);
		generarError(caracteres3001, contCaracteres3001, 3001);
	}

	/**
	 * Metodo que prueba que se lanza correctamente un error 3001
	 * 
	 * @throws IOException
	 *             Error que puede dar cuando hacer manejo de ficheros
	 */
	@Test
	public void error3001_20() throws IOException {
		contCaracteres3001++;
		exit.expectSystemExitWithStatus(3001);
		generarError(caracteres3001, contCaracteres3001, 3001);
	}

	/**
	 * Metodo que prueba que se lanza correctamente un error 3001
	 * 
	 * @throws IOException
	 *             Error que puede dar cuando hacer manejo de ficheros
	 */
	@Test
	public void error3001_21() throws IOException {
		contCaracteres3001++;
		exit.expectSystemExitWithStatus(3001);
		generarError(caracteres3001, contCaracteres3001, 3001);
	}

	/**
	 * Metodo que prueba que se lanza correctamente un error 3001
	 * 
	 * @throws IOException
	 *             Error que puede dar cuando hacer manejo de ficheros
	 */
	@Test
	public void error3001_22() throws IOException {
		contCaracteres3001++;
		exit.expectSystemExitWithStatus(3001);
		generarError(caracteres3001, contCaracteres3001, 3001);
	}

	/**
	 * Metodo que prueba que se lanza correctamente un error 3001
	 * 
	 * @throws IOException
	 *             Error que puede dar cuando hacer manejo de ficheros
	 */
	@Test
	public void error3001_23() throws IOException {
		contCaracteres3001++;
		exit.expectSystemExitWithStatus(3001);
		generarError(caracteres3001, contCaracteres3001, 3001);
	}

	/**
	 * Metodo que prueba que se lanza correctamente un error 3001
	 * 
	 * @throws IOException
	 *             Error que puede dar cuando hacer manejo de ficheros
	 */
	@Test
	public void error3001_24() throws IOException {
		contCaracteres3001++;
		exit.expectSystemExitWithStatus(3001);
		generarError(caracteres3001, contCaracteres3001, 3001);
	}

	/**
	 * Metodo que prueba que se lanza correctamente un error 3001
	 * 
	 * @throws IOException
	 *             Error que puede dar cuando hacer manejo de ficheros
	 */
	@Test
	public void error3001_25() throws IOException {
		contCaracteres3001++;
		exit.expectSystemExitWithStatus(3001);
		generarError(caracteres3001, contCaracteres3001, 3001);
	}

	/**
	 * Metodo que prueba que se lanza correctamente un error 3001
	 * 
	 * @throws IOException
	 *             Error que puede dar cuando hacer manejo de ficheros
	 */
	@Test
	public void error3001_26() throws IOException {
		contCaracteres3001++;
		exit.expectSystemExitWithStatus(3001);
		generarError(caracteres3001, contCaracteres3001, 3001);
	}

	/**
	 * Metodo que prueba que se lanza correctamente un error 3001
	 * 
	 * @throws IOException
	 *             Error que puede dar cuando hacer manejo de ficheros
	 */
	@Test
	public void error3001_27() throws IOException {
		contCaracteres3001++;
		exit.expectSystemExitWithStatus(3001);
		generarError(caracteres3001, contCaracteres3001, 3001);
	}

	/**
	 * Metodo que prueba que se lanza correctamente un error 3001
	 * 
	 * @throws IOException
	 *             Error que puede dar cuando hacer manejo de ficheros
	 */
	@Test
	public void error3001_28() throws IOException {
		contCaracteres3001++;
		exit.expectSystemExitWithStatus(3001);
		generarError(caracteres3001, contCaracteres3001, 3001);
	}

	/**
	 * Metodo que prueba que se lanza correctamente un error 3001
	 * 
	 * @throws IOException
	 *             Error que puede dar cuando hacer manejo de ficheros
	 */
	@Test
	public void error3001_29() throws IOException {
		contCaracteres3001++;
		exit.expectSystemExitWithStatus(3001);
		generarError(caracteres3001, contCaracteres3001, 3001);
	}

	/**
	 * Metodo que prueba que se lanza correctamente un error 3001
	 * 
	 * @throws IOException
	 *             Error que puede dar cuando hacer manejo de ficheros
	 */
	@Test
	public void error3001_30() throws IOException {
		contCaracteres3001++;
		exit.expectSystemExitWithStatus(3001);
		generarError(caracteres3001, contCaracteres3001, 3001);
	}

	/**
	 * Metodo que prueba que se lanza correctamente un error 3001
	 * 
	 * @throws IOException
	 *             Error que puede dar cuando hacer manejo de ficheros
	 */
	@Test
	public void error3001_31() throws IOException {
		contCaracteres3001++;
		exit.expectSystemExitWithStatus(3001);
		generarError(caracteres3001, contCaracteres3001, 3001);
	}

	/**
	 * Metodo que prueba que se lanza correctamente un error 3001
	 * 
	 * @throws IOException
	 *             Error que puede dar cuando hacer manejo de ficheros
	 */
	@Test
	public void error3001_32() throws IOException {
		contCaracteres3001++;
		exit.expectSystemExitWithStatus(3001);
		generarError(caracteres3001, contCaracteres3001, 3001);
	}

	/**
	 * Metodo que prueba que se lanza correctamente un error 3001
	 * 
	 * @throws IOException
	 *             Error que puede dar cuando hacer manejo de ficheros
	 */
	@Test
	public void error3001_33() throws IOException {
		contCaracteres3001++;
		exit.expectSystemExitWithStatus(3001);
		generarError(caracteres3001, contCaracteres3001, 3001);
	}

	/**
	 * Metodo que prueba que se lanza correctamente un error 3001
	 * 
	 * @throws IOException
	 *             Error que puede dar cuando hacer manejo de ficheros
	 */
	@Test
	public void error3001_34() throws IOException {
		contCaracteres3001++;
		exit.expectSystemExitWithStatus(3001);
		generarError(caracteres3001, contCaracteres3001, 3001);
	}

	/**
	 * Metodo que prueba que se lanza correctamente un error 3001
	 * 
	 * @throws IOException
	 *             Error que puede dar cuando hacer manejo de ficheros
	 */
	@Test
	public void error3001_35() throws IOException {
		contCaracteres3001++;
		exit.expectSystemExitWithStatus(3001);
		generarError(caracteres3001, contCaracteres3001, 3001);
	}

	/**
	 * Metodo que prueba que se lanza correctamente un error 3001
	 * 
	 * @throws IOException
	 *             Error que puede dar cuando hacer manejo de ficheros
	 */
	@Test
	public void error3001_36() throws IOException {
		contCaracteres3001++;
		exit.expectSystemExitWithStatus(3001);
		generarError(caracteres3001, contCaracteres3001, 3001);
	}

	/**
	 * Metodo que prueba que se lanza correctamente un error 3001
	 * 
	 * @throws IOException
	 *             Error que puede dar cuando hacer manejo de ficheros
	 */
	@Test
	public void error3001_37() throws IOException {
		contCaracteres3001++;
		exit.expectSystemExitWithStatus(3001);
		generarError(caracteres3001, contCaracteres3001, 3001);
	}

	/**
	 * Metodo que prueba que se lanza correctamente un error 3001
	 * 
	 * @throws IOException
	 *             Error que puede dar cuando hacer manejo de ficheros
	 */
	@Test
	public void error3001_38() throws IOException {
		contCaracteres3001++;
		exit.expectSystemExitWithStatus(3001);
		generarError(caracteres3001, contCaracteres3001, 3001);
	}

	/**
	 * Metodo que prueba que se lanza correctamente un error 3001
	 * 
	 * @throws IOException
	 *             Error que puede dar cuando hacer manejo de ficheros
	 */
	@Test
	public void error3001_39() throws IOException {
		contCaracteres3001++;
		exit.expectSystemExitWithStatus(3001);
		generarError(caracteres3001, contCaracteres3001, 3001);
	}

	/**
	 * Metodo que prueba que se lanza correctamente un error 3001
	 * 
	 * @throws IOException
	 *             Error que puede dar cuando hacer manejo de ficheros
	 */
	@Test
	public void error3001_40() throws IOException {
		contCaracteres3001++;
		exit.expectSystemExitWithStatus(3001);
		generarError(caracteres3001, contCaracteres3001, 3001);
	}

	/**
	 * Metodo que prueba que se lanza correctamente un error 3001
	 * 
	 * @throws IOException
	 *             Error que puede dar cuando hacer manejo de ficheros
	 */
	@Test
	public void error3001_41() throws IOException {
		contCaracteres3001++;
		exit.expectSystemExitWithStatus(3001);
		generarError(caracteres3001, contCaracteres3001, 3001);
	}

	/**
	 * Metodo que prueba que se lanza correctamente un error 3002
	 * 
	 * @throws IOException
	 *             Error que puede dar cuando hacer manejo de ficheros
	 */
	@Test
	public void error3002_1() throws IOException {
		contCaracteres3002++;
		exit.expectSystemExitWithStatus(3002);
		generarError(caracteres3002, contCaracteres3002, 3002);
	}

	/**
	 * Metodo que prueba que se lanza correctamente un error 3002
	 * 
	 * @throws IOException
	 *             Error que puede dar cuando hacer manejo de ficheros
	 */
	@Test
	public void error3002_2() throws IOException {
		contCaracteres3002++;
		exit.expectSystemExitWithStatus(3002);
		generarError(caracteres3002, contCaracteres3002, 3002);
	}

	/**
	 * Metodo que prueba que se lanza correctamente un error 3002
	 * 
	 * @throws IOException
	 *             Error que puede dar cuando hacer manejo de ficheros
	 */
	@Test
	public void error3002_3() throws IOException {
		contCaracteres3002++;
		exit.expectSystemExitWithStatus(3002);
		generarError(caracteres3002, contCaracteres3002, 3002);
	}

	/**
	 * Metodo que prueba que se lanza correctamente un error 3002
	 * 
	 * @throws IOException
	 *             Error que puede dar cuando hacer manejo de ficheros
	 */
	@Test
	public void error3002_4() throws IOException {
		contCaracteres3002++;
		exit.expectSystemExitWithStatus(3002);
		generarError(caracteres3002, contCaracteres3002, 3002);
	}

	/**
	 * Metodo que prueba que se lanza correctamente un error 3002
	 * 
	 * @throws IOException
	 *             Error que puede dar cuando hacer manejo de ficheros
	 */
	@Test
	public void error3002_5() throws IOException {
		contCaracteres3002++;
		exit.expectSystemExitWithStatus(3002);
		generarError(caracteres3002, contCaracteres3002, 3002);
	}

	/**
	 * Metodo que prueba que se lanza correctamente un error 3002
	 * 
	 * @throws IOException
	 *             Error que puede dar cuando hacer manejo de ficheros
	 */
	@Test
	public void error3002_6() throws IOException {
		contCaracteres3002++;
		exit.expectSystemExitWithStatus(3002);
		generarError(caracteres3002, contCaracteres3002, 3002);
	}

	/**
	 * Metodo que prueba que se lanza correctamente un error 3002
	 * 
	 * @throws IOException
	 *             Error que puede dar cuando hacer manejo de ficheros
	 */
	@Test
	public void error3002_7() throws IOException {
		contCaracteres3002++;
		exit.expectSystemExitWithStatus(3002);
		generarError(caracteres3002, contCaracteres3002, 3002);
	}

	/**
	 * Metodo que prueba que se lanza correctamente un error 3002
	 * 
	 * @throws IOException
	 *             Error que puede dar cuando hacer manejo de ficheros
	 */
	@Test
	public void error3002_8() throws IOException {
		contCaracteres3002++;
		exit.expectSystemExitWithStatus(3002);
		generarError(caracteres3002, contCaracteres3002, 3002);
	}

	/**
	 * Metodo que prueba que se lanza correctamente un error 3002
	 * 
	 * @throws IOException
	 *             Error que puede dar cuando hacer manejo de ficheros
	 */
	@Test
	public void error3002_9() throws IOException {
		contCaracteres3002++;
		exit.expectSystemExitWithStatus(3002);
		generarError(caracteres3002, contCaracteres3002, 3002);
	}

	/**
	 * Metodo que prueba que se lanza correctamente un error 3002
	 * 
	 * @throws IOException
	 *             Error que puede dar cuando hacer manejo de ficheros
	 */
	@Test
	public void error3002_10() throws IOException {
		contCaracteres3002++;
		exit.expectSystemExitWithStatus(3002);
		generarError(caracteres3002, contCaracteres3002, 3002);
	}

	/**
	 * Metodo que prueba que se lanza correctamente un error 3002
	 * 
	 * @throws IOException
	 *             Error que puede dar cuando hacer manejo de ficheros
	 */
	@Test
	public void error3002_11() throws IOException {
		contCaracteres3002++;
		exit.expectSystemExitWithStatus(3002);
		generarError(caracteres3002, contCaracteres3002, 3002);
	}

	/**
	 * Metodo que prueba que se lanza correctamente un error 3002
	 * 
	 * @throws IOException
	 *             Error que puede dar cuando hacer manejo de ficheros
	 */
	@Test
	public void error3002_12() throws IOException {
		contCaracteres3002++;
		exit.expectSystemExitWithStatus(3002);
		generarError(caracteres3002, contCaracteres3002, 3002);
	}

	/**
	 * Metodo que prueba que se lanza correctamente un error 3002
	 * 
	 * @throws IOException
	 *             Error que puede dar cuando hacer manejo de ficheros
	 */
	@Test
	public void error3002_13() throws IOException {
		contCaracteres3002++;
		exit.expectSystemExitWithStatus(3002);
		generarError(caracteres3002, contCaracteres3002, 3002);
	}

	/**
	 * Metodo que prueba que se lanza correctamente un error 3002
	 * 
	 * @throws IOException
	 *             Error que puede dar cuando hacer manejo de ficheros
	 */
	@Test
	public void error3002_14() throws IOException {
		contCaracteres3002++;
		exit.expectSystemExitWithStatus(3002);
		generarError(caracteres3002, contCaracteres3002, 3002);
	}

	/**
	 * Metodo que prueba que se lanza correctamente un error 3002
	 * 
	 * @throws IOException
	 *             Error que puede dar cuando hacer manejo de ficheros
	 */
	@Test
	public void error3002_15() throws IOException {
		contCaracteres3002++;
		exit.expectSystemExitWithStatus(3002);
		generarError(caracteres3002, contCaracteres3002, 3002);
	}

	/**
	 * Metodo que prueba que se lanza correctamente un error 3002
	 * 
	 * @throws IOException
	 *             Error que puede dar cuando hacer manejo de ficheros
	 */
	@Test
	public void error3002_16() throws IOException {
		contCaracteres3002++;
		exit.expectSystemExitWithStatus(3002);
		generarError(caracteres3002, contCaracteres3002, 3002);
	}

	/**
	 * Metodo que prueba que se lanza correctamente un error 3002
	 * 
	 * @throws IOException
	 *             Error que puede dar cuando hacer manejo de ficheros
	 */
	@Test
	public void error3002_17() throws IOException {
		contCaracteres3002++;
		exit.expectSystemExitWithStatus(3002);
		generarError(caracteres3002, contCaracteres3002, 3002);
	}

	/**
	 * Metodo que prueba que se lanza correctamente un error 3002
	 * 
	 * @throws IOException
	 *             Error que puede dar cuando hacer manejo de ficheros
	 */
	@Test
	public void error3002_18() throws IOException {
		contCaracteres3002++;
		exit.expectSystemExitWithStatus(3002);
		generarError(caracteres3002, contCaracteres3002, 3002);
	}

	/**
	 * Metodo que prueba que se lanza correctamente un error 3002
	 * 
	 * @throws IOException
	 *             Error que puede dar cuando hacer manejo de ficheros
	 */
	@Test
	public void error3002_19() throws IOException {
		contCaracteres3002++;
		exit.expectSystemExitWithStatus(3002);
		generarError(caracteres3002, contCaracteres3002, 3002);
	}

	/**
	 * Metodo que prueba que se lanza correctamente un error 3002
	 * 
	 * @throws IOException
	 *             Error que puede dar cuando hacer manejo de ficheros
	 */
	@Test
	public void error3002_20() throws IOException {
		contCaracteres3002++;
		exit.expectSystemExitWithStatus(3002);
		generarError(caracteres3002, contCaracteres3002, 3002);
	}

	/**
	 * Metodo que prueba que se lanza correctamente un error 3002
	 * 
	 * @throws IOException
	 *             Error que puede dar cuando hacer manejo de ficheros
	 */
	@Test
	public void error3002_21() throws IOException {
		contCaracteres3002++;
		exit.expectSystemExitWithStatus(3002);
		generarError(caracteres3002, contCaracteres3002, 3002);
	}

	/**
	 * Metodo que prueba que se lanza correctamente un error 3002
	 * 
	 * @throws IOException
	 *             Error que puede dar cuando hacer manejo de ficheros
	 */
	@Test
	public void error3002_22() throws IOException {
		contCaracteres3002++;
		exit.expectSystemExitWithStatus(3002);
		generarError(caracteres3002, contCaracteres3002, 3002);
	}

	/**
	 * Metodo que prueba que se lanza correctamente un error 3002
	 * 
	 * @throws IOException
	 *             Error que puede dar cuando hacer manejo de ficheros
	 */
	@Test
	public void error3002_23() throws IOException {
		contCaracteres3002++;
		exit.expectSystemExitWithStatus(3002);
		generarError(caracteres3002, contCaracteres3002, 3002);
	}

	/**
	 * Metodo que prueba que se lanza correctamente un error 3002
	 * 
	 * @throws IOException
	 *             Error que puede dar cuando hacer manejo de ficheros
	 */
	@Test
	public void error3002_24() throws IOException {
		contCaracteres3002++;
		exit.expectSystemExitWithStatus(3002);
		generarError(caracteres3002, contCaracteres3002, 3002);
	}

	/**
	 * Metodo que prueba que se lanza correctamente un error 3002
	 * 
	 * @throws IOException
	 *             Error que puede dar cuando hacer manejo de ficheros
	 */
	@Test
	public void error3002_25() throws IOException {
		contCaracteres3002++;
		exit.expectSystemExitWithStatus(3002);
		generarError(caracteres3002, contCaracteres3002, 3002);
	}

	/**
	 * Metodo que prueba que se lanza correctamente un error 3002
	 * 
	 * @throws IOException
	 *             Error que puede dar cuando hacer manejo de ficheros
	 */
	@Test
	public void error3002_26() throws IOException {
		contCaracteres3002++;
		exit.expectSystemExitWithStatus(3002);
		generarError(caracteres3002, contCaracteres3002, 3002);
	}

	/**
	 * Metodo que prueba que se lanza correctamente un error 3002
	 * 
	 * @throws IOException
	 *             Error que puede dar cuando hacer manejo de ficheros
	 */
	@Test
	public void error3002_27() throws IOException {
		contCaracteres3002++;
		exit.expectSystemExitWithStatus(3002);
		generarError(caracteres3002, contCaracteres3002, 3002);
	}

	/**
	 * Metodo que prueba que se lanza correctamente un error 3002
	 * 
	 * @throws IOException
	 *             Error que puede dar cuando hacer manejo de ficheros
	 */
	@Test
	public void error3002_28() throws IOException {
		contCaracteres3002++;
		exit.expectSystemExitWithStatus(3002);
		generarError(caracteres3002, contCaracteres3002, 3002);
	}

	/**
	 * Metodo que prueba que se lanza correctamente un error 3002
	 * 
	 * @throws IOException
	 *             Error que puede dar cuando hacer manejo de ficheros
	 */
	@Test
	public void error3002_29() throws IOException {
		contCaracteres3002++;
		exit.expectSystemExitWithStatus(3002);
		generarError(caracteres3002, contCaracteres3002, 3002);
	}

	/**
	 * Metodo que prueba que se lanza correctamente un error 3002
	 * 
	 * @throws IOException
	 *             Error que puede dar cuando hacer manejo de ficheros
	 */
	@Test
	public void error3002_30() throws IOException {
		contCaracteres3002++;
		exit.expectSystemExitWithStatus(3002);
		generarError(caracteres3002, contCaracteres3002, 3002);
	}

	/**
	 * Metodo que prueba que se lanza correctamente un error 3002
	 * 
	 * @throws IOException
	 *             Error que puede dar cuando hacer manejo de ficheros
	 */
	@Test
	public void error3002_31() throws IOException {
		contCaracteres3002++;
		exit.expectSystemExitWithStatus(3002);
		generarError(caracteres3002, contCaracteres3002, 3002);
	}

	/**
	 * Metodo que prueba que se lanza correctamente un error 3002
	 * 
	 * @throws IOException
	 *             Error que puede dar cuando hacer manejo de ficheros
	 */
	@Test
	public void error3002_32() throws IOException {
		contCaracteres3002++;
		exit.expectSystemExitWithStatus(3002);
		generarError(caracteres3002, contCaracteres3002, 3002);
	}

	/**
	 * Metodo que prueba que se lanza correctamente un error 3002
	 * 
	 * @throws IOException
	 *             Error que puede dar cuando hacer manejo de ficheros
	 */
	@Test
	public void error3002_33() throws IOException {
		contCaracteres3002++;
		exit.expectSystemExitWithStatus(3002);
		generarError(caracteres3002, contCaracteres3002, 3002);
	}

	/**
	 * Metodo que prueba que se lanza correctamente un error 3002
	 * 
	 * @throws IOException
	 *             Error que puede dar cuando hacer manejo de ficheros
	 */
	@Test
	public void error3002_34() throws IOException {
		contCaracteres3002++;
		exit.expectSystemExitWithStatus(3002);
		generarError(caracteres3002, contCaracteres3002, 3002);
	}

	/**
	 * Metodo que prueba que se lanza correctamente un error 3002
	 * 
	 * @throws IOException
	 *             Error que puede dar cuando hacer manejo de ficheros
	 */
	@Test
	public void error3002_35() throws IOException {
		contCaracteres3002++;
		exit.expectSystemExitWithStatus(3002);
		generarError(caracteres3002, contCaracteres3002, 3002);
	}

	/**
	 * Metodo que prueba que se lanza correctamente un error 3002
	 * 
	 * @throws IOException
	 *             Error que puede dar cuando hacer manejo de ficheros
	 */
	@Test
	public void error3002_36() throws IOException {
		contCaracteres3002++;
		exit.expectSystemExitWithStatus(3002);
		generarError(caracteres3002, contCaracteres3002, 3002);
	}

	/**
	 * Metodo que prueba que se lanza correctamente un error 3002
	 * 
	 * @throws IOException
	 *             Error que puede dar cuando hacer manejo de ficheros
	 */
	@Test
	public void error3002_37() throws IOException {
		contCaracteres3002++;
		exit.expectSystemExitWithStatus(3002);
		generarError(caracteres3002, contCaracteres3002, 3002);
	}

	/**
	 * Metodo que prueba que se lanza correctamente un error 3002
	 * 
	 * @throws IOException
	 *             Error que puede dar cuando hacer manejo de ficheros
	 */
	@Test
	public void error3002_38() throws IOException {
		contCaracteres3002++;
		exit.expectSystemExitWithStatus(3002);
		generarError(caracteres3002, contCaracteres3002, 3002);
	}

	/**
	 * Metodo que prueba que se lanza correctamente un error 3002
	 * 
	 * @throws IOException
	 *             Error que puede dar cuando hacer manejo de ficheros
	 */
	@Test
	public void error3002_39() throws IOException {
		contCaracteres3002++;
		exit.expectSystemExitWithStatus(3002);
		generarError(caracteres3002, contCaracteres3002, 3002);
	}

	/**
	 * Metodo que prueba que se lanza correctamente un error 3002
	 * 
	 * @throws IOException
	 *             Error que puede dar cuando hacer manejo de ficheros
	 */
	@Test
	public void error3002_40() throws IOException {
		contCaracteres3002++;
		exit.expectSystemExitWithStatus(3002);
		generarError(caracteres3002, contCaracteres3002, 3002);
	}

	/**
	 * Metodo que prueba que se lanza correctamente un error 3002
	 * 
	 * @throws IOException
	 *             Error que puede dar cuando hacer manejo de ficheros
	 */
	@Test
	public void error3002_41() throws IOException {
		contCaracteres3002++;
		exit.expectSystemExitWithStatus(3002);
		generarError(caracteres3002, contCaracteres3002, 3002);
	}

	/**
	 * Metodo que prueba que se lanza correctamente un error 3002
	 * 
	 * @throws IOException
	 *             Error que puede dar cuando hacer manejo de ficheros
	 */
	@Test
	public void error3002_42() throws IOException {
		contCaracteres3002++;
		exit.expectSystemExitWithStatus(3002);
		generarError(caracteres3002, contCaracteres3002, 3002);
	}

	/**
	 * Metodo que prueba que se lanza correctamente un error 3002
	 * 
	 * @throws IOException
	 *             Error que puede dar cuando hacer manejo de ficheros
	 */
	@Test
	public void error3002_43() throws IOException {
		contCaracteres3002++;
		exit.expectSystemExitWithStatus(3002);
		generarError(caracteres3002, contCaracteres3002, 3002);
	}

	/**
	 * Metodo que prueba que se lanza correctamente un error 3002
	 * 
	 * @throws IOException
	 *             Error que puede dar cuando hacer manejo de ficheros
	 */
	@Test
	public void error3002_44() throws IOException {
		contCaracteres3002++;
		exit.expectSystemExitWithStatus(3002);
		generarError(caracteres3002, contCaracteres3002, 3002);
	}

	/**
	 * Metodo que prueba que se lanza correctamente un error 3002
	 * 
	 * @throws IOException
	 *             Error que puede dar cuando hacer manejo de ficheros
	 */
	@Test
	public void error3002_45() throws IOException {
		contCaracteres3002++;
		exit.expectSystemExitWithStatus(3002);
		generarError(caracteres3002, contCaracteres3002, 3002);
	}

	/**
	 * Metodo que prueba que se lanza correctamente un error 3002
	 * 
	 * @throws IOException
	 *             Error que puede dar cuando hacer manejo de ficheros
	 */
	@Test
	public void error3002_46() throws IOException {
		contCaracteres3002++;
		exit.expectSystemExitWithStatus(3002);
		generarError(caracteres3002, contCaracteres3002, 3002);
	}

	/**
	 * Metodo que prueba que se lanza correctamente un error 3002
	 * 
	 * @throws IOException
	 *             Error que puede dar cuando hacer manejo de ficheros
	 */
	@Test
	public void error3002_47() throws IOException {
		contCaracteres3002++;
		exit.expectSystemExitWithStatus(3002);
		generarError(caracteres3002, contCaracteres3002, 3002);
	}

	/**
	 * Metodo que prueba que se lanza correctamente un error 3002
	 * 
	 * @throws IOException
	 *             Error que puede dar cuando hacer manejo de ficheros
	 */
	@Test
	public void error3002_48() throws IOException {
		contCaracteres3002++;
		exit.expectSystemExitWithStatus(3002);
		generarError(caracteres3002, contCaracteres3002, 3002);
	}

	/**
	 * Metodo que prueba que se lanza correctamente un error 3002
	 * 
	 * @throws IOException
	 *             Error que puede dar cuando hacer manejo de ficheros
	 */
	@Test
	public void error3002_49() throws IOException {
		contCaracteres3002++;
		exit.expectSystemExitWithStatus(3002);
		generarError(caracteres3002, contCaracteres3002, 3002);
	}

	/**
	 * Metodo que prueba que se lanza correctamente un error 3002
	 * 
	 * @throws IOException
	 *             Error que puede dar cuando hacer manejo de ficheros
	 */
	@Test
	public void error3002_50() throws IOException {
		contCaracteres3002++;
		exit.expectSystemExitWithStatus(3002);
		generarError(caracteres3002, contCaracteres3002, 3002);
	}

	/**
	 * Metodo que prueba que se lanza correctamente un error 3002
	 * 
	 * @throws IOException
	 *             Error que puede dar cuando hacer manejo de ficheros
	 */
	@Test
	public void error3002_51() throws IOException {
		contCaracteres3002++;
		exit.expectSystemExitWithStatus(3002);
		generarError(caracteres3002, contCaracteres3002, 3002);
	}

	/**
	 * Metodo que prueba que se lanza correctamente un error 3002
	 * 
	 * @throws IOException
	 *             Error que puede dar cuando hacer manejo de ficheros
	 */
	@Test
	public void error3002_52() throws IOException {
		contCaracteres3002++;
		exit.expectSystemExitWithStatus(3002);
		generarError(caracteres3002, contCaracteres3002, 3002);
	}

	/**
	 * Metodo que prueba que se lanza correctamente un error 3004
	 * 
	 * @throws IOException
	 *             Error que puede dar cuando hacer manejo de ficheros
	 */
	@Test
	public void error3004() throws IOException {
		exit.expectSystemExitWithStatus(3004);
		generarError(new String[] { "switch(identificador){}" }, 0, 3004);
	}

	/**
	 * Test que comprueba que se genera un error con ciertos caracteres y actualiza
	 * un contador
	 * 
	 * @throws IOException
	 *             Excepcion que se puede dar al trabajar con ficheros
	 */
	public void generarError(String[] caracteres, int contador, int error) throws IOException {
		String caracter = caracteres[contador];

		System.out.println("Probando que el analizador sintactico es capaz de\ndetectar un error " + error + " con el caracter "
				+ caracter);

		AnalizadorLexico lexico = new AnalizadorLexico(crearFicheroTemporal(caracter).getAbsolutePath());
		AnalizadorSintactico analizador = new AnalizadorSintactico(lexico);

		analizador.p();

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
}
