package test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.ExpectedSystemExit;
import org.junit.rules.TemporaryFolder;

import analizadorLexico.AnalizadorLexico;
import analizadorSemantico.AnalizadorSemantico;
import analizadorSintactico.AnalizadorSintactico;
import tablaDeSimbolos.TablaDeSimbolos;

public class TestAnalizadorSemantico {

	// private final String CARPETA_PROGRAMAS_SEMANTICO =
	// "Programas/Test/AnalizadorSemantico/";

	private final String SEPARATOR = "***************************************************";
	private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();

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
		
		AnalizadorSemantico.desactivarZonaDeDeclaracion();

		// Limpio las tablas de simbolos
		TablaDeSimbolos.resetTablaDeSimbolos();

		// Imprimo mensajes de finalizacion de test
		System.out.println(SEPARATOR + "\n");
	}

	// Las pruebas de funcionamiento normal se suplen con las pruebas del analizador
	// sintactico asi que no se van a repetir aqui

	@Test
	/**
	 * Prueba que se detecta un break fuera de un switch
	 * 
	 * @throws IOException
	 */
	public void deteccionDeBreakFueraDeSwitch() throws IOException {
		String caracter = "break;";
		System.out.println(
				"Probando que el analizador semantico es capaz de\ndetectar un break fuera de un switch " + caracter);

		AnalizadorLexico lexico = new AnalizadorLexico(crearFicheroTemporal(caracter).getAbsolutePath());
		AnalizadorSintactico analizador = new AnalizadorSintactico(lexico);

		exit.expectSystemExitWithStatus(4002);
		analizador.p();
	}

	@Test
	/**
	 * Prueba que se detecta un break fuera de un switch
	 * 
	 * @throws IOException
	 */
	public void deteccionDeBreakFueraDeSwitch2() throws IOException {
		String caracter = "if(true) {break;}";
		System.out.println(
				"Probando que el analizador semantico es capaz de\ndetectar un break fuera de un switch " + caracter);

		AnalizadorLexico lexico = new AnalizadorLexico(crearFicheroTemporal(caracter).getAbsolutePath());
		AnalizadorSintactico analizador = new AnalizadorSintactico(lexico);

		exit.expectSystemExitWithStatus(4002);
		analizador.p();
	}

	@Test
	/**
	 * Prueba que se detecta un break fuera de un switch
	 * 
	 * @throws IOException
	 */
	public void deteccionDeBreakFueraDeSwitch3() throws IOException {
		String caracter = "if(true) {} else{break;}";
		System.out.println(
				"Probando que el analizador semantico es capaz de\ndetectar un break fuera de un switch " + caracter);

		AnalizadorLexico lexico = new AnalizadorLexico(crearFicheroTemporal(caracter).getAbsolutePath());
		AnalizadorSintactico analizador = new AnalizadorSintactico(lexico);

		exit.expectSystemExitWithStatus(4002);
		analizador.p();
	}

	@Test
	/**
	 * Prueba que se detecta un break fuera de un switch
	 * 
	 * @throws IOException
	 */
	public void deteccionDeBreakFueraDeSwitch4() throws IOException {
		String caracter = "for (i = 0; i< 10 ; i++) {break;}";
		System.out.println(
				"Probando que el analizador semantico es capaz de\ndetectar un break fuera de un switch " + caracter);

		AnalizadorLexico lexico = new AnalizadorLexico(crearFicheroTemporal(caracter).getAbsolutePath());
		AnalizadorSintactico analizador = new AnalizadorSintactico(lexico);

		exit.expectSystemExitWithStatus(4002);
		analizador.p();
	}

	@Test
	/**
	 * Prueba que se detecta un break fuera de un switch
	 * 
	 * @throws IOException
	 */
	public void deteccionDeBreakFueraDeSwitch5() throws IOException {
		String caracter = "while(true) {break;}";
		System.out.println(
				"Probando que el analizador semantico es capaz de\ndetectar un break fuera de un switch " + caracter);

		AnalizadorLexico lexico = new AnalizadorLexico(crearFicheroTemporal(caracter).getAbsolutePath());
		AnalizadorSintactico analizador = new AnalizadorSintactico(lexico);

		exit.expectSystemExitWithStatus(4002);
		analizador.p();
	}

	@Test
	/**
	 * Prueba que se detecta un break fuera de un switch
	 * 
	 * @throws IOException
	 */
	public void deteccionDeBreakFueraDeSwitch6() throws IOException {
		String caracter = "do {break;}while(true);";
		System.out.println(
				"Probando que el analizador semantico es capaz de\ndetectar un break fuera de un switch " + caracter);

		AnalizadorLexico lexico = new AnalizadorLexico(crearFicheroTemporal(caracter).getAbsolutePath());
		AnalizadorSintactico analizador = new AnalizadorSintactico(lexico);

		exit.expectSystemExitWithStatus(4002);
		analizador.p();
	}

	@Test
	/**
	 * Prueba que se detecta un break fuera de un switch
	 * 
	 * @throws IOException
	 */
	public void deteccionDeReturnFueraDeSwitch() throws IOException {
		String caracter = "return;";
		System.out.println(
				"Probando que el analizador semantico es capaz de\ndetectar un break fuera de un switch " + caracter);

		AnalizadorLexico lexico = new AnalizadorLexico(crearFicheroTemporal(caracter).getAbsolutePath());
		AnalizadorSintactico analizador = new AnalizadorSintactico(lexico);

		exit.expectSystemExitWithStatus(4003);
		analizador.p();
	}

	@Test
	/**
	 * Prueba que se detecta un break fuera de un switch
	 * 
	 * @throws IOException
	 */
	public void deteccionDeReturnFueraDeSwitch2() throws IOException {
		String caracter = "if(true) {return;}";
		System.out.println(
				"Probando que el analizador semantico es capaz de\ndetectar un break fuera de un switch " + caracter);

		AnalizadorLexico lexico = new AnalizadorLexico(crearFicheroTemporal(caracter).getAbsolutePath());
		AnalizadorSintactico analizador = new AnalizadorSintactico(lexico);

		exit.expectSystemExitWithStatus(4003);
		analizador.p();
	}

	@Test
	/**
	 * Prueba que se detecta un break fuera de un switch
	 * 
	 * @throws IOException
	 */
	public void deteccionDeReturnFueraDeSwitch3() throws IOException {
		String caracter = "if(true) {} else{return;}";
		System.out.println(
				"Probando que el analizador semantico es capaz de\ndetectar un break fuera de un switch " + caracter);

		AnalizadorLexico lexico = new AnalizadorLexico(crearFicheroTemporal(caracter).getAbsolutePath());
		AnalizadorSintactico analizador = new AnalizadorSintactico(lexico);

		exit.expectSystemExitWithStatus(4003);
		analizador.p();
	}

	@Test
	/**
	 * Prueba que se detecta un break fuera de un switch
	 * 
	 * @throws IOException
	 */
	public void deteccionDeReturnFueraDeSwitch4() throws IOException {
		String caracter = "for (i = 0; i< 10 ; i++) {return;}";
		System.out.println(
				"Probando que el analizador semantico es capaz de\ndetectar un break fuera de un switch " + caracter);

		AnalizadorLexico lexico = new AnalizadorLexico(crearFicheroTemporal(caracter).getAbsolutePath());
		AnalizadorSintactico analizador = new AnalizadorSintactico(lexico);

		exit.expectSystemExitWithStatus(4003);
		analizador.p();
	}

	@Test
	/**
	 * Prueba que se detecta un break fuera de un switch
	 * 
	 * @throws IOException
	 */
	public void deteccionDeReturnFueraDeSwitch5() throws IOException {
		String caracter = "while(true) {return;}";
		System.out.println(
				"Probando que el analizador semantico es capaz de\ndetectar un break fuera de un switch " + caracter);

		AnalizadorLexico lexico = new AnalizadorLexico(crearFicheroTemporal(caracter).getAbsolutePath());
		AnalizadorSintactico analizador = new AnalizadorSintactico(lexico);

		exit.expectSystemExitWithStatus(4003);
		analizador.p();
	}

	@Test
	/**
	 * Prueba que se detecta un break fuera de un switch
	 * 
	 * @throws IOException
	 */
	public void deteccionDeReturnFueraDeSwitch6() throws IOException {
		String caracter = "do {return;}while(true);";
		System.out.println(
				"Probando que el analizador semantico es capaz de\ndetectar un break fuera de un switch " + caracter);

		AnalizadorLexico lexico = new AnalizadorLexico(crearFicheroTemporal(caracter).getAbsolutePath());
		AnalizadorSintactico analizador = new AnalizadorSintactico(lexico);

		exit.expectSystemExitWithStatus(4003);
		analizador.p();
	}

	@Test
	/**
	 * Prueba que existe consistencia en los tipos de diversos programas
	 * 
	 * @throws IOException
	 */
	public void deteccionDeErrorDeTipos() throws IOException {
		String caracter = "var bool hola = 'hola';";
		System.out.println(
				"Probando que el analizador semantico es capaz de\ndetectar un break fuera de un switch " + caracter);

		AnalizadorLexico lexico = new AnalizadorLexico(crearFicheroTemporal(caracter).getAbsolutePath());
		AnalizadorSintactico analizador = new AnalizadorSintactico(lexico);

		exit.expectSystemExitWithStatus(4001);
		analizador.p();
	}
	
	@Test
	/**
	 * Prueba que existe consistencia en los tipos de diversos programas
	 * 
	 * @throws IOException
	 */
	public void deteccionDeErrorDeTipos2() throws IOException {
		String caracter = "if('casa') {}";
		System.out.println(
				"Probando que el analizador semantico es capaz de\ndetectar un break fuera de un switch " + caracter);

		AnalizadorLexico lexico = new AnalizadorLexico(crearFicheroTemporal(caracter).getAbsolutePath());
		AnalizadorSintactico analizador = new AnalizadorSintactico(lexico);

		exit.expectSystemExitWithStatus(4001);
		analizador.p();
	}
	
	@Test
	/**
	 * Prueba que existe consistencia en los tipos de diversos programas
	 * 
	 * @throws IOException
	 */
	public void deteccionDeErrorDeTipos3() throws IOException {
		String caracter = "switch('casa') {}";
		System.out.println(
				"Probando que el analizador semantico es capaz de\ndetectar un break fuera de un switch " + caracter);

		AnalizadorLexico lexico = new AnalizadorLexico(crearFicheroTemporal(caracter).getAbsolutePath());
		AnalizadorSintactico analizador = new AnalizadorSintactico(lexico);

		exit.expectSystemExitWithStatus(4001);
		analizador.p();
	}
	
	@Test
	/**
	 * Prueba que existe consistencia en los tipos de diversos programas
	 * 
	 * @throws IOException
	 */
	public void deteccionDeErrorDeTipos4() throws IOException {
		String caracter = "switch(true) {}";
		System.out.println(
				"Probando que el analizador semantico es capaz de\ndetectar un break fuera de un switch " + caracter);

		AnalizadorLexico lexico = new AnalizadorLexico(crearFicheroTemporal(caracter).getAbsolutePath());
		AnalizadorSintactico analizador = new AnalizadorSintactico(lexico);

		exit.expectSystemExitWithStatus(4001);
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
