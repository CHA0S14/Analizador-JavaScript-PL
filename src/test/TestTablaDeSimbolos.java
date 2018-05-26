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

import tablaDeSimbolos.Operando;
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

		if (errContent.size() != 0) {
			System.out.println(errContent.toString());
			errContent.reset();
		}

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

		Operando op = new Operando("lexema");

		int indice = TablaDeSimbolos.insertarId("lexema");

		assertEquals(TablaDeSimbolos.obtenerOperando(indice), op);
	}

	/**
	 * Metodo que comprueba que se aniaden atributos de variable correctos
	 */
	@Test
	public void insertarAtributosDeVariableIntCorrectos() {
		System.out.println("Probando que se insertan atributos de variable \nint correctos");

		Operando op = new Operando("lexema");
		op.setTipo(Operando.INT);
		op.setDespl(4);

		int indice = TablaDeSimbolos.insertarId("lexema");

		TablaDeSimbolos.insetarAtributo(indice, Operando.TIPO, Operando.INT, null);
		TablaDeSimbolos.insetarAtributo(indice, Operando.DESP, "4", null);

		assertEquals(TablaDeSimbolos.obtenerOperando(indice), op);
	}

	/**
	 * Metodo que comprueba que se aniaden atributos de variable correctos
	 */
	@Test
	public void insertarAtributosDeVariableBoolCorrectos() {
		System.out.println("Probando que se insertan atributos de variable \nbool correctos");

		Operando op = new Operando("lexema");
		op.setTipo(Operando.BOOL);
		op.setDespl(1);

		int indice = TablaDeSimbolos.insertarId("lexema");

		TablaDeSimbolos.insetarAtributo(indice, Operando.TIPO, Operando.BOOL, null);
		TablaDeSimbolos.insetarAtributo(indice, Operando.DESP, "1", null);

		assertEquals(TablaDeSimbolos.obtenerOperando(indice), op);
	}

	/**
	 * Metodo que comprueba que se aniaden atributos de variable correctos
	 */
	@Test
	public void insertarAtributosDeVariableCharsCorrectos() {
		System.out.println("Probando que se insertan atributos de variable \nchars correctos");

		Operando op = new Operando("lexema");
		op.setTipo(Operando.CHARS);
		op.setDespl(4);

		int indice = TablaDeSimbolos.insertarId("lexema");

		TablaDeSimbolos.insetarAtributo(indice, Operando.TIPO, Operando.CHARS, null);
		TablaDeSimbolos.insetarAtributo(indice, Operando.DESP, "4", null);

		assertEquals(TablaDeSimbolos.obtenerOperando(indice), op);
	}

	/**
	 * Metodo que comprueba que se aniaden atributos de funcion correctos
	 */
	@Test
	public void insertarAtributosDeVariableFuncionReturnIntCorrectos() {
		System.out.println("Probando que se insertan atributos de funcion \ncon return de tipo int correctos");

		Operando op = new Operando("lexema");
		op.setTipo(Operando.FUNC);
		op.setTipoRetorno(Operando.INT);
		op.setEtiqFuncion("1");

		int indice = TablaDeSimbolos.insertarId("lexema");

		TablaDeSimbolos.insetarAtributo(indice, Operando.TIPO, Operando.FUNC, null);
		TablaDeSimbolos.insetarAtributo(indice, Operando.RET, Operando.INT, null);
		TablaDeSimbolos.insetarAtributo(indice, Operando.TAG, "1", null);

		assertEquals(TablaDeSimbolos.obtenerOperando(indice), op);
	}

	/**
	 * Metodo que comprueba que se aniaden atributos de funcion correctos
	 */
	@Test
	public void insertarAtributosDeVariableFuncionReturnBoolCorrectos() {
		System.out.println("Probando que se insertan atributos de funcion \ncon return de tipo bool correctos");

		Operando op = new Operando("lexema");
		op.setTipo(Operando.FUNC);
		op.setTipoRetorno(Operando.BOOL);
		op.setEtiqFuncion("1");

		int indice = TablaDeSimbolos.insertarId("lexema");

		TablaDeSimbolos.insetarAtributo(indice, Operando.TIPO, Operando.FUNC, null);
		TablaDeSimbolos.insetarAtributo(indice, Operando.RET, Operando.BOOL, null);
		TablaDeSimbolos.insetarAtributo(indice, Operando.TAG, "1", null);

		assertEquals(TablaDeSimbolos.obtenerOperando(indice), op);
	}

	/**
	 * Metodo que comprueba que se aniaden atributos de funcion correctos
	 */
	@Test
	public void insertarAtributosDeVariableFuncionReturnCharsCorrectos() {
		System.out.println("Probando que se insertan atributos de funcion \ncon return de tipo chars correctos");

		Operando op = new Operando("lexema");
		op.setTipo(Operando.FUNC);
		op.setTipoRetorno(Operando.CHARS);
		op.setEtiqFuncion("1");

		int indice = TablaDeSimbolos.insertarId("lexema");

		TablaDeSimbolos.insetarAtributo(indice, Operando.TIPO, Operando.FUNC, null);
		TablaDeSimbolos.insetarAtributo(indice, Operando.RET, Operando.CHARS, null);
		TablaDeSimbolos.insetarAtributo(indice, Operando.TAG, "1", null);

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

		Operando op = new Operando("lexema");
		op.setTipo(Operando.FUNC);
		op.setTipoRetorno(Operando.INT);
		op.setEtiqFuncion("1");
		op.addTipoParam(Operando.CHARS);
		op.addModoParam(Operando.REF);
		op.addTipoParam(Operando.INT);
		op.addModoParam(Operando.VAL);
		op.addTipoParam(Operando.BOOL);
		op.addModoParam(Operando.VAL);

		if (op.getNumParam() != 3)
			fail("El numero de parametros de la funcion no se actualiza correctamente");

		int indice = TablaDeSimbolos.insertarId("lexema");

		TablaDeSimbolos.insetarAtributo(indice, Operando.TIPO, Operando.FUNC, null);
		TablaDeSimbolos.insetarAtributo(indice, Operando.RET, Operando.INT, null);
		TablaDeSimbolos.insetarAtributo(indice, Operando.TAG, "1", null);
		TablaDeSimbolos.insetarAtributo(indice, Operando.PARAM, Operando.CHARS, Operando.REF);
		TablaDeSimbolos.insetarAtributo(indice, Operando.PARAM, Operando.INT, Operando.VAL);
		TablaDeSimbolos.insetarAtributo(indice, Operando.PARAM, Operando.BOOL, Operando.VAL);

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

		Operando op = new Operando("lexema");
		op.setTipo(Operando.FUNC);
		op.setTipoRetorno(Operando.BOOL);
		op.setEtiqFuncion("1");
		op.addTipoParam(Operando.CHARS);
		op.addModoParam(Operando.REF);
		op.addTipoParam(Operando.INT);
		op.addModoParam(Operando.VAL);
		op.addTipoParam(Operando.BOOL);
		op.addModoParam(Operando.VAL);

		if (op.getNumParam() != 3)
			fail("El numero de parametros de la funcion no se actualiza correctamente");

		int indice = TablaDeSimbolos.insertarId("lexema");

		TablaDeSimbolos.insetarAtributo(indice, Operando.TIPO, Operando.FUNC, null);
		TablaDeSimbolos.insetarAtributo(indice, Operando.RET, Operando.BOOL, null);
		TablaDeSimbolos.insetarAtributo(indice, Operando.TAG, "1", null);
		TablaDeSimbolos.insetarAtributo(indice, Operando.PARAM, Operando.CHARS, Operando.REF);
		TablaDeSimbolos.insetarAtributo(indice, Operando.PARAM, Operando.INT, Operando.VAL);
		TablaDeSimbolos.insetarAtributo(indice, Operando.PARAM, Operando.BOOL, Operando.VAL);

		assertEquals(TablaDeSimbolos.obtenerOperando(indice), op);
	}

	/**
	 * Metodo que comprueba que se aniaden atributos de funcion con parametros
	 * correctos
	 */
	@Test
	public void insertarAtributosDeVariableFuncionReturnCharsParametrosCorrectos() {
		System.out.println(
				"Probando que se insertan atributos de funcion \ncon return de tipo chars y parametros correctos");

		Operando op = new Operando("lexema");
		op.setTipo(Operando.FUNC);
		op.setTipoRetorno(Operando.CHARS);
		op.setEtiqFuncion("1");
		op.addTipoParam(Operando.CHARS);
		op.addModoParam(Operando.REF);
		op.addTipoParam(Operando.INT);
		op.addModoParam(Operando.VAL);
		op.addTipoParam(Operando.BOOL);
		op.addModoParam(Operando.VAL);

		if (op.getNumParam() != 3)
			fail("El numero de parametros de la funcion no se actualiza correctamente");

		int indice = TablaDeSimbolos.insertarId("lexema");

		TablaDeSimbolos.insetarAtributo(indice, Operando.TIPO, Operando.FUNC, null);
		TablaDeSimbolos.insetarAtributo(indice, Operando.RET, Operando.CHARS, null);
		TablaDeSimbolos.insetarAtributo(indice, Operando.TAG, "1", null);
		TablaDeSimbolos.insetarAtributo(indice, Operando.PARAM, Operando.CHARS, Operando.REF);
		TablaDeSimbolos.insetarAtributo(indice, Operando.PARAM, Operando.INT, Operando.VAL);
		TablaDeSimbolos.insetarAtributo(indice, Operando.PARAM, Operando.BOOL, Operando.VAL);

		assertEquals(TablaDeSimbolos.obtenerOperando(indice), op);
	}

	/**
	 * Prueba que comprueba que se envia un error cuando el tipo no es valido
	 */
	@Test
	public void insertarTipoInvalido() {
		System.out.println("Probando que se inserta un atributo tipo invalido");

		int indice = TablaDeSimbolos.insertarId("lexema");

		exit.expectSystemExitWithStatus(1006);
		TablaDeSimbolos.insetarAtributo(indice, Operando.TIPO, "ASDASD", null);
	}

	/**
	 * Prueba que comprueba que se envia un error cuando el tipo no es funcion y que
	 * se le intente poner un tipoRetorno
	 */
	@Test
	public void insertarTipoRetornoAVariable() {
		System.out.println("Probando que se inserta un tipo retorno a una variable");

		int indice = TablaDeSimbolos.insertarId("lexema");
		TablaDeSimbolos.insetarAtributo(indice, Operando.TIPO, Operando.INT, null);

		exit.expectSystemExitWithStatus(1008);
		TablaDeSimbolos.insetarAtributo(indice, Operando.RET, Operando.INT, null);
	}

	/**
	 * Prueba que comprueba que se envia un error cuando el tipo no es funcion y que
	 * se le intente poner una etiqueta
	 */
	@Test
	public void insertarEtiquetaAVariable() {
		System.out.println("Probando que se inserta una etiqueta a una variable");

		int indice = TablaDeSimbolos.insertarId("lexema");
		TablaDeSimbolos.insetarAtributo(indice, Operando.TIPO, Operando.INT, null);

		exit.expectSystemExitWithStatus(1008);
		TablaDeSimbolos.insetarAtributo(indice, Operando.TAG, "1", null);
	}

	/**
	 * Prueba que comprueba que se envia un error cuando el tipo no es funcion y que
	 * se le intenta aniadir un parametro
	 */
	@Test
	public void insertarParametroAVariable() {
		System.out.println("Probando que se inserta un parametro a una variable");

		int indice = TablaDeSimbolos.insertarId("lexema");
		TablaDeSimbolos.insetarAtributo(indice, Operando.TIPO, Operando.INT, null);

		exit.expectSystemExitWithStatus(1008);
		TablaDeSimbolos.insetarAtributo(indice, Operando.PARAM, Operando.INT, Operando.VAL);
	}

	/**
	 * Prueba que comprueba que se envia un error cuando el tipo es funcion y que se
	 * le intenta aniadir un desplazamiento
	 */
	@Test
	public void insertarDespAFuncion() {
		// TODO Editar esa funcion para que haga lo que tiene que hacer
		System.out.println("Probando que se inserta un parametro a una variable");

		int indice = TablaDeSimbolos.insertarId("lexema");
		TablaDeSimbolos.insetarAtributo(indice, Operando.TIPO, Operando.INT, null);

		exit.expectSystemExitWithStatus(1008);
		TablaDeSimbolos.insetarAtributo(indice, Operando.PARAM, Operando.INT, Operando.VAL);
	}
}
