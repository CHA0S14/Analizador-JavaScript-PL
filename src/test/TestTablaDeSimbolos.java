package test;

import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.ExpectedSystemExit;

import tablaDeSimbolos.Entrada;
import tablaDeSimbolos.TablaDeSimbolos;

public class TestTablaDeSimbolos {
	private final String SEPARATOR = "***************************************************";
	private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();

	@Rule
	// Usamos la libreria http://stefanbirkner.github.io/system-rules/download.html
	// Para evitar la salida de ejecucion por el gestor de errores cuando sea
	// necesario
	public final ExpectedSystemExit exit = ExpectedSystemExit.none();

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

		TablaDeSimbolos.resetTablaDeSimbolos();
	}

	/**
	 * Metodo que comprueba que se añade un operando correctamente
	 */
	@Test
	public void insertarNuevoOperando() {
		System.out.println("Probando que se puede insertar un identificador \ncorrectamente");

		Entrada op = new Entrada("lexema");

		int indice = TablaDeSimbolos.insertarId("lexema");

		assertEquals(TablaDeSimbolos.obtenerOperando(indice), op);
	}

	/**
	 * Metodo que comprueba que se aniaden atributos de variable correctos
	 */
	@Test
	public void insertarAtributosDeVariableIntCorrectos() {
		System.out.println("Probando que se insertan atributos de variable \nint correctos");

		Entrada op = new Entrada("lexema");
		op.setTipo(Entrada.INT);
		op.setDespl(4);

		int indice = TablaDeSimbolos.insertarId("lexema");

		TablaDeSimbolos.insetarAtributo(indice, Entrada.TIPO, Entrada.INT, false);
		TablaDeSimbolos.insetarAtributo(indice, Entrada.DESP, "4", false);

		assertEquals(TablaDeSimbolos.obtenerOperando(indice), op);
	}

	/**
	 * Metodo que comprueba que se aniaden atributos de variable correctos
	 */
	@Test
	public void insertarAtributosDeVariableBoolCorrectos() {
		System.out.println("Probando que se insertan atributos de variable \nbool correctos");

		Entrada op = new Entrada("lexema");
		op.setTipo(Entrada.BOOL);
		op.setDespl(1);

		int indice = TablaDeSimbolos.insertarId("lexema");

		TablaDeSimbolos.insetarAtributo(indice, Entrada.TIPO, Entrada.BOOL, false);
		TablaDeSimbolos.insetarAtributo(indice, Entrada.DESP, "1", false);

		assertEquals(TablaDeSimbolos.obtenerOperando(indice), op);
	}

	/**
	 * Metodo que comprueba que se aniaden atributos de variable correctos
	 */
	@Test
	public void insertarAtributosDeVariableCharsCorrectos() {
		System.out.println("Probando que se insertan atributos de variable \nchars correctos");

		Entrada op = new Entrada("lexema");
		op.setTipo(Entrada.CHARS);
		op.setDespl(4);

		int indice = TablaDeSimbolos.insertarId("lexema");

		TablaDeSimbolos.insetarAtributo(indice, Entrada.TIPO, Entrada.CHARS, false);
		TablaDeSimbolos.insetarAtributo(indice, Entrada.DESP, "4", false);

		assertEquals(TablaDeSimbolos.obtenerOperando(indice), op);
	}

	/**
	 * Metodo que comprueba que se detecta un error cuando el desplazamiento no es
	 * un numero
	 */
	@Test
	public void insertarDespIncorrectos() {
		System.out.println("Probando que se detecta un error cuand el \ndesp es incorrectos");

		int indice = TablaDeSimbolos.insertarId("lexema");

		exit.expectSystemExitWithStatus(1012);
		TablaDeSimbolos.insetarAtributo(indice, Entrada.DESP, "ASDASD", false);
	}

	/**
	 * Metodo que comprueba que se aniaden atributos de funcion correctos
	 */
	@Test
	public void insertarAtributosDeVariableFuncionReturnIntCorrectos() {
		System.out.println("Probando que se insertan atributos de funcion \ncon return de tipo int correctos");

		Entrada op = new Entrada("lexema");
		op.setTipo(Entrada.FUNC);
		op.setTipoRetorno(Entrada.INT);
		op.setEtiqFuncion("1");

		int indice = TablaDeSimbolos.insertarId("lexema");

		TablaDeSimbolos.insetarAtributo(indice, Entrada.TIPO, Entrada.FUNC, false);
		TablaDeSimbolos.insetarAtributo(indice, Entrada.RET, Entrada.INT, false);
		TablaDeSimbolos.insetarAtributo(indice, Entrada.TAG, "1", false);

		assertEquals(TablaDeSimbolos.obtenerOperando(indice), op);
	}

	/**
	 * Metodo que comprueba que se aniaden atributos de funcion correctos
	 */
	@Test
	public void insertarAtributosDeVariableFuncionReturnBoolCorrectos() {
		System.out.println("Probando que se insertan atributos de funcion \ncon return de tipo bool correctos");

		Entrada op = new Entrada("lexema");
		op.setTipo(Entrada.FUNC);
		op.setTipoRetorno(Entrada.BOOL);
		op.setEtiqFuncion("1");

		int indice = TablaDeSimbolos.insertarId("lexema");

		TablaDeSimbolos.insetarAtributo(indice, Entrada.TIPO, Entrada.FUNC, false);
		TablaDeSimbolos.insetarAtributo(indice, Entrada.RET, Entrada.BOOL, false);
		TablaDeSimbolos.insetarAtributo(indice, Entrada.TAG, "1", false);

		assertEquals(TablaDeSimbolos.obtenerOperando(indice), op);
	}

	/**
	 * Metodo que comprueba que se aniaden atributos de funcion correctos
	 */
	@Test
	public void insertarAtributosDeVariableFuncionReturnCharsCorrectos() {
		System.out.println("Probando que se insertan atributos de funcion \ncon return de tipo chars correctos");

		Entrada op = new Entrada("lexema");
		op.setTipo(Entrada.FUNC);
		op.setTipoRetorno(Entrada.CHARS);
		op.setEtiqFuncion("1");

		int indice = TablaDeSimbolos.insertarId("lexema");

		TablaDeSimbolos.insetarAtributo(indice, Entrada.TIPO, Entrada.FUNC, false);
		TablaDeSimbolos.insetarAtributo(indice, Entrada.RET, Entrada.CHARS, false);
		TablaDeSimbolos.insetarAtributo(indice, Entrada.TAG, "1", false);

		assertEquals(TablaDeSimbolos.obtenerOperando(indice), op);
	}

	/**
	 * Metodo que comprueba que se aniaden atributos de funcion con parametros
	 * correctos
	 */
	@Test
	public void insertarAtributosDeVariableFuncionReturnIntParametrosCorrectos() {
		System.out.println(
				"Probando que se insertan atributos de funcion \ncon return de tipo int y parametros correctos");

		Entrada op = new Entrada("lexema");
		op.setTipo(Entrada.FUNC);
		op.setTipoRetorno(Entrada.INT);
		op.setEtiqFuncion("1");
		op.addTipoParam(Entrada.CHARS);
		op.addModoParam(Entrada.REF);
		op.addTipoParam(Entrada.INT);
		op.addModoParam(Entrada.VAL);
		op.addTipoParam(Entrada.BOOL);
		op.addModoParam(Entrada.VAL);

		if (op.getNumParam() != 3)
			fail("El numero de parametros de la funcion no se actualiza correctamente");

		int indice = TablaDeSimbolos.insertarId("lexema");

		TablaDeSimbolos.insetarAtributo(indice, Entrada.TIPO, Entrada.FUNC, false);
		TablaDeSimbolos.insetarAtributo(indice, Entrada.RET, Entrada.INT, false);
		TablaDeSimbolos.insetarAtributo(indice, Entrada.TAG, "1", false);
		TablaDeSimbolos.insetarAtributo(indice, Entrada.PARAM, Entrada.CHARS, Entrada.REF);
		TablaDeSimbolos.insetarAtributo(indice, Entrada.PARAM, Entrada.INT, Entrada.VAL);
		TablaDeSimbolos.insetarAtributo(indice, Entrada.PARAM, Entrada.BOOL, Entrada.VAL);

		assertEquals(TablaDeSimbolos.obtenerOperando(indice), op);
	}

	/**
	 * Metodo que comprueba que se aniaden atributos de funcion con parametros
	 * correctos
	 */
	@Test
	public void insertarAtributosDeVariableFuncionReturnBoolParametrosCorrectos() {
		System.out.println(
				"Probando que se insertan atributos de funcion \ncon return de tipo bool y parametros correctos");

		Entrada op = new Entrada("lexema");
		op.setTipo(Entrada.FUNC);
		op.setTipoRetorno(Entrada.BOOL);
		op.setEtiqFuncion("1");
		op.addTipoParam(Entrada.CHARS);
		op.addModoParam(Entrada.REF);
		op.addTipoParam(Entrada.INT);
		op.addModoParam(Entrada.VAL);
		op.addTipoParam(Entrada.BOOL);
		op.addModoParam(Entrada.VAL);

		if (op.getNumParam() != 3)
			fail("El numero de parametros de la funcion no se actualiza correctamente");

		int indice = TablaDeSimbolos.insertarId("lexema");

		TablaDeSimbolos.insetarAtributo(indice, Entrada.TIPO, Entrada.FUNC, false);
		TablaDeSimbolos.insetarAtributo(indice, Entrada.RET, Entrada.BOOL, false);
		TablaDeSimbolos.insetarAtributo(indice, Entrada.TAG, "1", false);
		TablaDeSimbolos.insetarAtributo(indice, Entrada.PARAM, Entrada.CHARS, Entrada.REF);
		TablaDeSimbolos.insetarAtributo(indice, Entrada.PARAM, Entrada.INT, Entrada.VAL);
		TablaDeSimbolos.insetarAtributo(indice, Entrada.PARAM, Entrada.BOOL, Entrada.VAL);

		assertEquals(TablaDeSimbolos.obtenerOperando(indice), op);
	}

	/**
	 * Metodo que comprueba que se aniaden atributos de funcion con parametros
	 * correctos
	 */
	@Test
	public void insertarAtributosDeVariableFuncionReturnCharsParametrosCorrectos() {
		System.out.println(
				"Probando que se detecta atributos de funcion \ncon return de tipo chars y parametros correctos");

		Entrada op = new Entrada("lexema");
		op.setTipo(Entrada.FUNC);
		op.setTipoRetorno(Entrada.CHARS);
		op.setEtiqFuncion("1");
		op.addTipoParam(Entrada.CHARS);
		op.addModoParam(Entrada.REF);
		op.addTipoParam(Entrada.INT);
		op.addModoParam(Entrada.VAL);
		op.addTipoParam(Entrada.BOOL);
		op.addModoParam(Entrada.VAL);

		if (op.getNumParam() != 3)
			fail("El numero de parametros de la funcion no se actualiza correctamente");

		int indice = TablaDeSimbolos.insertarId("lexema");

		TablaDeSimbolos.insetarAtributo(indice, Entrada.TIPO, Entrada.FUNC, false);
		TablaDeSimbolos.insetarAtributo(indice, Entrada.RET, Entrada.CHARS, false);
		TablaDeSimbolos.insetarAtributo(indice, Entrada.TAG, "1", false);
		TablaDeSimbolos.insetarAtributo(indice, Entrada.PARAM, Entrada.CHARS, Entrada.REF);
		TablaDeSimbolos.insetarAtributo(indice, Entrada.PARAM, Entrada.INT, Entrada.VAL);
		TablaDeSimbolos.insetarAtributo(indice, Entrada.PARAM, Entrada.BOOL, Entrada.VAL);

		assertEquals(TablaDeSimbolos.obtenerOperando(indice), op);
	}

	/**
	 * Prueba que comprueba que se envia un error cuando el tipo no es valido
	 */
	@Test
	public void insertarTipoInvalido() {
		System.out.println("Probando que se detecta un atributo tipo invalido");

		int indice = TablaDeSimbolos.insertarId("lexema");

		exit.expectSystemExitWithStatus(1006);
		TablaDeSimbolos.insetarAtributo(indice, Entrada.TIPO, "ASDASD", false);
	}

	/**
	 * Prueba que comprueba que se envia un error cuando el tipo no es funcion y que
	 * se le intente poner un tipoRetorno
	 */
	@Test
	public void insertarTipoRetornoAVariable() {
		System.out.println("Probando que se detecta un tipo retorno a una variable");

		int indice = TablaDeSimbolos.insertarId("lexema");
		TablaDeSimbolos.insetarAtributo(indice, Entrada.TIPO, Entrada.INT, false);

		exit.expectSystemExitWithStatus(1008);
		TablaDeSimbolos.insetarAtributo(indice, Entrada.RET, Entrada.INT, false);
	}

	/**
	 * Prueba que comprueba que se envia un error cuando el tipo no es funcion y que
	 * se le intente poner una etiqueta
	 */
	@Test
	public void insertarEtiquetaAVariable() {
		System.out.println("Probando que se detecta una etiqueta a una variable");

		int indice = TablaDeSimbolos.insertarId("lexema");
		TablaDeSimbolos.insetarAtributo(indice, Entrada.TIPO, Entrada.INT, false);

		exit.expectSystemExitWithStatus(1008);
		TablaDeSimbolos.insetarAtributo(indice, Entrada.TAG, "1", false);
	}

	/**
	 * Prueba que comprueba que se envia un error cuando el tipo no es funcion y que
	 * se le intenta aniadir un parametro
	 */
	@Test
	public void insertarParametroAVariable() {
		System.out.println("Probando que se detecta un parametro a una variable");

		int indice = TablaDeSimbolos.insertarId("lexema");
		TablaDeSimbolos.insetarAtributo(indice, Entrada.TIPO, Entrada.INT, false);

		exit.expectSystemExitWithStatus(1008);
		TablaDeSimbolos.insetarAtributo(indice, Entrada.PARAM, Entrada.INT, Entrada.VAL);
	}

	/**
	 * Prueba que comprueba que se envia un error cuando el tipo de retorno de una
	 * funcion no es valido
	 */
	@Test
	public void insertarTipoRetornoErroneoFuncion() {
		System.out.println("Probando que se detecta un tipo de retorno invalido \na una funcion");

		int indice = TablaDeSimbolos.insertarId("lexema");
		TablaDeSimbolos.insetarAtributo(indice, Entrada.TIPO, Entrada.FUNC, false);

		exit.expectSystemExitWithStatus(1006);
		TablaDeSimbolos.insetarAtributo(indice, Entrada.RET, "ASDASD", false);
	}

	/**
	 * Prueba que comprueba que se envia un error cuando el tipo de un parametro no
	 * es valido
	 */
	@Test
	public void insertarTipoParamErroneoFuncion() {
		System.out.println("Probando que se detecta un tipo de parametro invalido \na una funcion");

		int indice = TablaDeSimbolos.insertarId("lexema");
		TablaDeSimbolos.insetarAtributo(indice, Entrada.TIPO, Entrada.FUNC, false);

		exit.expectSystemExitWithStatus(1006);
		TablaDeSimbolos.insetarAtributo(indice, Entrada.PARAM, "ASDASD", Entrada.VAL);
	}

	/**
	 * Prueba que comprueba que se envia un error cuando se inserta un
	 * desplazamiento a una funcion
	 */
	@Test
	public void insertarDespFuncion() {
		System.out.println("Probando que se detecta un desplazamiento en una funcion");

		int indice = TablaDeSimbolos.insertarId("lexema");
		TablaDeSimbolos.insetarAtributo(indice, Entrada.TIPO, Entrada.FUNC, false);

		exit.expectSystemExitWithStatus(1007);
		TablaDeSimbolos.insetarAtributo(indice, Entrada.DESP, "4", false);
	}
}
