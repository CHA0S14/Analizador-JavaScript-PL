package analizadorSintactico;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import analizadorLexico.AnalizadorLexico;
import analizadorLexico.Token;
import analizadorSemantico.AnalizadorSemantico;
import analizadorSemantico.Tipo;
import gestorDeErrores.GestorDeErrores;
import javafx.util.Pair;
import tablaDeSimbolos.Entrada;

public class AnalizadorSintactico {
	private static final String FICHERO_PARSE = "./Resultados/parse.txt";

	private FileWriter parse;
	private Token sigToken = null;
	private AnalizadorLexico lexico = null;

	public AnalizadorSintactico(AnalizadorLexico lexico) {
		this.lexico = lexico;
		sigToken = lexico.getToken();

		try {
			parse = new FileWriter(FICHERO_PARSE);
			parse.write("D");
		} catch (IOException e) {
			// Envio al gestor de errore el codigo 1002 reservado a error de compilador
			// cuando un fichero no se puede abrir
			GestorDeErrores.gestionarError(1002, FICHERO_PARSE);
		}
	}

	/**
	 * <pre>
	 * 		P -> B P
	 * 		P -> F P
	 * 		P -> eof
	 * </pre>
	 */
	public void p() {
		switch (sigToken.getToken()) {
		case 29: // var
		case 54: // identificador
		case 15: // ++
		case 16: // --
		case 35: // return
		case 33: // write
		case 34: // prompt
		case 43: // break
		case 41: // switch
		case 38: // while
		case 39: // do
		case 40: // for
		case 36: // if
			writeParse(1);
			b(false, false);
			p();
			break;
		case 45: // function
			writeParse(2);
			f();
			p();
			break;
		case 53: // eof
			writeParse(3);
			equip(53);

			// Acciones del analizador semantico
			AnalizadorSemantico.cerrarTablaDeSimbolos();
			////////////////////////////////////
			break;
		default:
			GestorDeErrores.gestionarError(3001, null);
		}
	}

	/**
	 * <pre>
	 * 		B -> var T identificador B2 ID ;
	 * 		B -> if ( E ) THEN
	 * 		B -> S ;
	 * 		B -> switch ( E ) { CASE }
	 * 		B -> while ( E ) { C }
	 * 		B -> do { C } while ( E ) ;
	 * 		B -> for ( INICIALIZACION ; E ; ACTUALIZACION ) iniBloq  C endBloq
	 * </pre>
	 * 
	 * Correspondiente al analizador Semantico:
	 * 
	 * @param isReturn
	 *            Si estamos dentro de una funcion sera true
	 * @param isBreak
	 *            Si estamos dentro de un switch sera true
	 * 
	 * @return Tipo de retorno si existe, si no devuelve TIPO_OK
	 */
	private String b(boolean isReturn, boolean isBreak) {
		String tipoReturn = null;
		Tipo tipoE = null;

		switch (sigToken.getToken()) {
		case 29: // var
			writeParse(4);
			equip(29); // var

			// Acciones del analizador semantico
			AnalizadorSemantico.activarZonaDeDeclaracion();
			////////////////////////////////////

			Tipo tipoT = t();

			// Accion del analizador semantico
			Token identificador = sigToken; // reservo el identificador para despues
			/////////////////////////////////

			equip(54); // identificador

			// Accion del analizador semantico
			// Inserto los datos del identificador en la tabla de simbolos
			AnalizadorSemantico.aniadirTS(identificador.getAtributo(), tipoT.getTipo(), tipoT.getAncho(), false);
			/////////////////////////////////

			Tipo tipoB2 = b2();

			// Acciones del analizador semantico

			if (!tipoT.getTipo().equals(tipoB2.getTipo()) && !tipoB2.getTipo().equals(AnalizadorSemantico.TIPO_OK)) {
				GestorDeErrores.gestionarError(4001, "El tipo del identificador no corresponde con el valor asignado");
			}

			////////////////////////////////////

			id(tipoT);

			// Acciones del analizador semantico
			AnalizadorSemantico.desactivarZonaDeDeclaracion();
			tipoReturn = AnalizadorSemantico.TIPO_OK;
			////////////////////////////////////

			equip(51); // ;
			break;
		case 36: // if
			writeParse(5);
			equip(36); // if
			equip(46); // (
			tipoE = e();

			// Acciones del analizador semantico
			if (!tipoE.getTipo().equals(Entrada.BOOL) && !tipoE.getTipo().equals(Entrada.INT)) {
				GestorDeErrores.gestionarError(4001, "El tipo de una expresion detro de un If tiene que ser booleana");
			}
			////////////////////////////////////

			equip(47); // )
			tipoReturn = bloqueThen(isReturn, isBreak);
			break;
		case 54: // identificador
		case 15: // ++
		case 16: // --
		case 35: // return
		case 33: // write
		case 34: // prompt
		case 43: // break
			writeParse(6);
			tipoReturn = s(isReturn, isBreak);
			equip(51); // ;
			break;
		case 41: // switch
			writeParse(7);
			equip(41); // switch
			equip(46); // (
			tipoE = e();

			// Acciones del analizador semantico
			if (tipoE.getTipo() != Entrada.INT) {
				GestorDeErrores.gestionarError(4001, "El tipo de la expresion de un Switch tiene que ser int");
			}
			////////////////////////////////////

			equip(47); // )
			equip(48); // {
			tipoReturn = bloqueCase(isReturn);
			equip(49); // }
			break;
		case 38: // while
			writeParse(8);
			equip(38); // while
			equip(46); // (
			tipoE = e();

			// Acciones del analizador semantico
			if (!tipoE.getTipo().equals(Entrada.BOOL) && !tipoE.getTipo().equals(Entrada.INT)) {
				GestorDeErrores.gestionarError(4001, "El tipo de una expresion de un While tiene que ser boolean");
			}
			////////////////////////////////////

			equip(47); // )
			equip(48); // {
			tipoReturn = c(isReturn, isBreak);
			equip(49); // }
			break;
		case 39: // do
			writeParse(9);
			equip(39); // do
			equip(48); // {
			tipoReturn = c(isReturn, isBreak);
			equip(49); // }
			equip(38); // while
			equip(46); // (
			tipoE = e();
			equip(47); // )
			equip(51); // ;

			// Acciones del analizador semantico
			if (!tipoE.getTipo().equals(Entrada.BOOL) && !tipoE.getTipo().equals(Entrada.INT)) {
				GestorDeErrores.gestionarError(4001, "El tipo de una expresion de un do-while tiene que ser booleana");
			}
			////////////////////////////////////
			break;
		case 40: // for
			writeParse(10);
			equip(40); // for
			equip(46); // (
			inicializacion();
			equip(51); // ;
			tipoE = e();

			// Acciones del analizador semantico
			if (!tipoE.getTipo().equals(Entrada.BOOL) && !tipoE.getTipo().equals(Entrada.INT)) {
				GestorDeErrores.gestionarError(4001,
						"El tipo de la condicion de parada de un for tiene que ser booleana");
			}
			////////////////////////////////////

			equip(51); // ;
			actualizacion();
			equip(47); // )
			equip(48); // {
			tipoReturn = c(isReturn, isBreak);
			equip(49); // }
			break;
		default:
			GestorDeErrores.gestionarError(3001, null);
		}

		return tipoReturn;
	}

	/**
	 * <pre>
	 * 		B2 -> ASIGNACION E
	 * 		B2 -> lambda
	 * </pre>
	 * 
	 * Correspondiente al analizador Semantico:
	 * 
	 * @return tipo de la asinacion o TIPO_OK si corresponde
	 */
	private Tipo b2() {
		switch (sigToken.getToken()) {
		case 17: // =
		case 18: // +=
		case 19: // -=
		case 20: // *=
		case 21: // /=
		case 22: // %=
		case 23: // &=
		case 24: // |=
			writeParse(11);
			Tipo tipoAsignacion = asignacion();
			Tipo tipoE = e();

			// Acciones del analizador semantico
			if (tipoAsignacion.getTipo().equals(tipoE.getTipo())
					|| tipoAsignacion.getTipo().equals(AnalizadorSemantico.TIPO_OK)) {
				return tipoE;
			} else {
				GestorDeErrores.gestionarError(4001, "El tipo de la variable con el valor asignado no coincide");
			}
			////////////////////////////////////
		default:
			writeParse(12);
			return new Tipo(AnalizadorSemantico.TIPO_OK);
		}
	}

	/**
	 * <pre>
	 * 		INICIALIZACION  -> identificador ASIGNACION E
	 * 		INICIALIZACION  -> lambda
	 * </pre>
	 */
	private void inicializacion() {
		switch (sigToken.getToken()) {
		case 54: // identificador
			writeParse(13);

			// Acciones del analizador semantico
			Token identificador = sigToken; // Almaceno el token para su uso posterior
			////////////////////////////////////

			equip(54); // identificador
			Tipo tipoAsignacion = asignacion();
			Tipo tipoE = e();

			// Acciones del analizador semantico
			if (tipoAsignacion.getTipo().equals(tipoE.getTipo())
					|| tipoAsignacion.getTipo().equals(AnalizadorSemantico.TIPO_OK)) {

				// Si el tipo del identificador esta vacio hay que rellenarlo
				if (AnalizadorSemantico.tipoID(identificador.getAtributo()).equals(AnalizadorSemantico.TIPO_VACIO)) {
					AnalizadorSemantico.aniadirTS(identificador.getAtributo(), Entrada.INT,
							AnalizadorSemantico.DESP_INT, false);

					// Si el tipo del identificador no coincide con la de la expresion hay un error
				} else if (!AnalizadorSemantico.tipoID(identificador.getAtributo()).equals(tipoE.getTipo())) {
					GestorDeErrores.gestionarError(4001,
							"El tipo de la variable y de la asignacion no coinciden en la inicializacion del for");
				}

			} else {
				GestorDeErrores.gestionarError(4001,
						"El tipo de la asignacion y de la expresion no coinciden en la inicializacion del for");
			}
			////////////////////////////////////
			break;
		default:
			writeParse(14);
			break;
		}
	}

	/**
	 * <pre>
	 * 		ACTUALIZACION -> identificador ACTUALIZACION2							
	 *		ACTUALIZACION -> INCDEC identificador					
	 *		ACTUALIZACION -> lambda
	 * </pre>
	 */
	private void actualizacion() {
		Token identificador;
		switch (sigToken.getToken()) {
		case 54: // identificador
			writeParse(15);

			// Acciones del analizador semantico
			identificador = sigToken; // Almaceno el token para su uso posterior
			////////////////////////////////////

			equip(54); // identificador
			Tipo tipoActualizacion2 = actualizacion2();

			// Acciones del analizador semantico
			if (AnalizadorSemantico.tipoID(identificador.getAtributo()).equals(AnalizadorSemantico.TIPO_VACIO)) {
				AnalizadorSemantico.aniadirTS(identificador.getAtributo(), Entrada.INT, AnalizadorSemantico.DESP_INT,
						false);
			}

			if (!tipoActualizacion2.getTipo().equals(AnalizadorSemantico.tipoID(identificador.getAtributo()))) {
				GestorDeErrores.gestionarError(4001,
						"El tipo de la variable y de la asignacion no coinciden en la actualizacion del for");
			}
			////////////////////////////////////
			break;
		case 15: // ++
		case 16: // --
			writeParse(16);
			incDec();

			// Acciones del analizador semantico
			identificador = sigToken; // Almaceno el token para su uso posterior
			////////////////////////////////////

			equip(54); // identificador

			// Acciones del analizador semantico
			if (!AnalizadorSemantico.tipoID(identificador.getAtributo()).equals(Entrada.INT)) {
				if (!AnalizadorSemantico.tipoID(identificador.getAtributo()).equals(AnalizadorSemantico.TIPO_VACIO)) {
					AnalizadorSemantico.aniadirTS(identificador.getAtributo(), Entrada.INT,
							AnalizadorSemantico.DESP_INT, false);
				} else {
					GestorDeErrores.gestionarError(4001,
							"El tipo de la variable tiene que ser int con un incremento o decremento en la inicializacion del for");
				}
			}
			////////////////////////////////////
			break;
		default:
			writeParse(17);
			break;
		}
	}

	/**
	 * <pre>
	 * 		ACTUALIZACION2 -> ASIGNACION E
	 * 		ACTUALIZACION2 -> INCDEC
	 * </pre>
	 * 
	 * Correspondiente al analizador Semantico:
	 * 
	 * @return Tipo de la asignacion o del INCDEC
	 */
	private Tipo actualizacion2() {
		switch (sigToken.getToken()) {
		case 17: // =
		case 18: // +=
		case 19: // -=
		case 20: // *=
		case 21: // /=
		case 22: // %=
		case 23: // &=
		case 24: // |=
			writeParse(18);
			Tipo tipoAsignacion = asignacion();
			Tipo tipoE = e();

			// Acciones del analizador semantico
			if (tipoAsignacion.getTipo().equals(tipoE.getTipo())
					|| tipoAsignacion.getTipo().equals(AnalizadorSemantico.TIPO_OK)) {
				return tipoE;
			} else {
				GestorDeErrores.gestionarError(4001,
						"El tipo de la asignacion y de la expresion no coinciden en la actualizacion del for");
			}
			////////////////////////////////////
			break;
		case 15: // ++
		case 16: // --
			writeParse(19);
			incDec();
			return new Tipo(Entrada.INT);
		default:
			GestorDeErrores.gestionarError(3002, null);
		}

		return null;
	}

	/**
	 * <pre>
	 * 		INCDEC -> ++
	 * 		INCDEC -> --
	 * </pre>
	 */
	private void incDec() {
		switch (sigToken.getToken()) {
		case 15: // ++
			writeParse(20);
			equip(15);
			break;
		case 16: // --
			writeParse(21);
			equip(16);
			break;
		default:
			GestorDeErrores.gestionarError(3003, sigToken.getToken() + "");
		}
	}

	/**
	 * <pre>
	 * 		ASIGNACION -> = 
	 *		ASIGNACION -> ASIGNACION_OP
	 * </pre>
	 * 
	 * Correspondiente al analizador Semantico:
	 * 
	 * @return Tipo que necesita la asignacion para funcionar
	 * 
	 */
	private Tipo asignacion() {
		switch (sigToken.getToken()) {
		case 17: // =
			writeParse(22);
			equip(17);
			return new Tipo(AnalizadorSemantico.TIPO_OK);
		case 18: // +=
		case 19: // -=
		case 20: // *=
		case 21: // /=
		case 22: // %=
		case 23: // &=
		case 24: // |=
			writeParse(23);
			return asignacionOp();
		default:
			GestorDeErrores.gestionarError(3004, sigToken.getToken() + "");
		}
		return null;
	}

	/**
	 * <pre>
	 * 		ASIGNACION_OP -> +=
	 *		ASIGNACION_OP -> -=
	 *		ASIGNACION_OP -> *=
	 *		ASIGNACION_OP -> /=
	 *		ASIGNACION_OP -> %=
	 *		ASIGNACION_OP -> &=
	 *		ASIGNACION_OP -> |=
	 * </pre>
	 * 
	 * Correspondiente al analizador Semantico:
	 * 
	 * @return Tipo que necesita la asignacion para funcionar
	 */
	private Tipo asignacionOp() {
		switch (sigToken.getToken()) {
		case 18: // +=
			writeParse(24);
			equip(18);
			return new Tipo(Entrada.INT);
		case 19: // -=
			writeParse(25);
			equip(19);
			return new Tipo(Entrada.INT);
		case 20: // *=
			writeParse(26);
			equip(20);
			return new Tipo(Entrada.INT);
		case 21: // /=
			writeParse(27);
			equip(21);
			return new Tipo(Entrada.INT);
		case 22: // %=
			writeParse(28);
			equip(22);
			return new Tipo(Entrada.INT);
		case 23: // &=
			writeParse(29);
			equip(23);
			return new Tipo(Entrada.BOOL);
		case 24: // |=
			writeParse(30);
			equip(24);
			return new Tipo(Entrada.BOOL);
		default:
			GestorDeErrores.gestionarError(3004, sigToken.getToken() + "");
		}
		return null;
	}

	/**
	 * <pre>
	 * 		THEN -> { C } ELSE
	 * 		THEN -> S ;
	 * </pre>
	 * 
	 * Correspondiente al analizador Semantico:
	 * 
	 * @param isReturn
	 *            Si estamos dentro de una funcion sera true
	 * @param isBreak
	 *            Si estamos dentro de un switch sera true
	 * 
	 * @return Tipo de retorno si existe, si no devuelve TIPO_OK
	 */
	private String bloqueThen(boolean isReturn, boolean isBreak) {
		String tipoReturn = null;
		switch (sigToken.getToken()) {
		case 48: // {
			writeParse(31);
			equip(48); // {
			String tipoC = c(isReturn, isBreak);
			equip(49); // }
			String tipoElse = bloqueElse(isReturn, isBreak);

			// Acciones del analizador semantico
			if (tipoC.equals(tipoElse) || tipoElse.equals(AnalizadorSemantico.TIPO_OK)) {
				tipoReturn = tipoC;
			} else {
				GestorDeErrores.gestionarError(4001, "El tipo del return en el then y en el else no coinciden");
			}
			////////////////////////////////////
			break;
		case 54: // identificador
		case 15: // ++
		case 16: // --
		case 35: // return
		case 33: // write
		case 34: // prompt
		case 43: // break
			writeParse(32);
			tipoReturn = s(isReturn, isBreak);
			equip(51); // ;
			break;
		}

		return tipoReturn;
	}

	/**
	 * <pre>
	 * 		ELSE -> else { C }
	 * 		ELSE -> lambda
	 * </pre>
	 * 
	 * Correspondiente al analizador Semantico:
	 * 
	 * @param isReturn
	 *            Si estamos dentro de una funcion sera true
	 * @param isBreak
	 *            Si estamos dentro de un switch sera true
	 * 
	 * @return Tipo de retorno si existe, si no devuelve TIPO_OK
	 */
	private String bloqueElse(boolean isReturn, boolean isBreak) {
		String tipoReturn;
		switch (sigToken.getToken()) {
		case 37: // else
			writeParse(33);
			equip(37); // else
			equip(48); // {
			tipoReturn = c(isReturn, isBreak);
			equip(49); // }
			break;
		default:
			writeParse(34);
			tipoReturn = AnalizadorSemantico.TIPO_OK;
			break;
		}

		return tipoReturn;
	}

	/**
	 * <pre>
	 * 		ID -> , identificador B2 ID
	 * 		ID -> lambda
	 * </pre>
	 * 
	 * Correspondiente al analizador Semantico:
	 * 
	 * @param tipo
	 *            tipo del identificador que se esta declarando
	 */
	private void id(Tipo tipo) {
		if (sigToken.getToken() == 50) {
			writeParse(35);
			equip(50); // ,

			// Acciones del analizador semantico
			Token identificador = sigToken; // Almaceno el token para su uso posterior
			////////////////////////////////////

			equip(54); // identificador
			Tipo tipoB2 = b2();

			// Acciones del analizador semantico
			if (tipo.getTipo().equals(tipoB2.getTipo()) || tipoB2.getTipo().equals(AnalizadorSemantico.TIPO_OK)) {
				AnalizadorSemantico.aniadirTS(identificador.getAtributo(), tipo.getTipo(), tipo.getAncho(), false);
			} else {
				GestorDeErrores.gestionarError(4001, "El tipo de la variable y del valor asignado no coinciden");
			}
			////////////////////////////////////

			id(tipo);
		} else {
			writeParse(36);
		}
	}

	/**
	 * <pre>
	 * 		CASE -> case entero : C CASE									
	 * 		CASE -> default : C
	 *		CASE -> lambda
	 * </pre>
	 * 
	 * Correspondiente al analizador Semantico:
	 * 
	 * @param isReturn
	 *            Si estamos dentro de una funcion sera true
	 * 
	 * @return Tipo de retorno si existe, si no devuelve TIPO_OK
	 */
	private String bloqueCase(boolean isReturn) {
		String tipoReturn = null;
		switch (sigToken.getToken()) {
		case 42: // case
			writeParse(37);
			equip(42); // case
			equip(25); // entero
			equip(52); // :
			String tipoC = c(isReturn, true);
			String tipoCase = bloqueCase(isReturn);

			// Acciones del analizador semantico
			if (tipoC.equals(tipoCase) || tipoCase.equals(AnalizadorSemantico.TIPO_OK)) {
				tipoReturn = tipoC;
			} else if (tipoC.equals(AnalizadorSemantico.TIPO_OK)) {
				tipoReturn = tipoCase;
			} else {
				GestorDeErrores.gestionarError(4001,
						"El tipo de return de los cases del switch no es el mismo en todos los casos");
			}
			////////////////////////////////////

			break;
		case 44: // default
			writeParse(38);
			equip(44); // default
			equip(52); // :
			tipoReturn = c(isReturn, true);
			break;
		default:
			writeParse(39);
			tipoReturn = AnalizadorSemantico.TIPO_OK;
			break;
		}

		return tipoReturn;
	}

	/**
	 * <pre>
	 * 		T -> int
	 * 		T -> bool
	 * 		T -> chars
	 * </pre>
	 * 
	 * Correspondiente al analizador Semantico:
	 * 
	 * @return Tipo
	 */
	private Tipo t() {
		switch (sigToken.getToken()) {
		case 30: // int
			writeParse(42);
			equip(30);
			return new Tipo(Entrada.INT, AnalizadorSemantico.DESP_INT);
		case 31: // bool
			writeParse(43);
			equip(31);
			return new Tipo(Entrada.BOOL, AnalizadorSemantico.DESP_BOOL);
		case 32: // chars
			writeParse(44);
			equip(32);
			return new Tipo(Entrada.CHARS, AnalizadorSemantico.DESP_CHARS);
		default:
			GestorDeErrores.gestionarError(3005, null);
		}
		return null;
	}

	/**
	 * <pre>
	 * 		E -> G E2
	 * </pre>
	 * 
	 * Correspondiente al analizador Semantico:
	 * 
	 * @return Tipo
	 */
	private Tipo e() {
		switch (sigToken.getToken()) {
		case 14: // !
		case 54: // identificador
		case 25: // entero
		case 26: // cadena
		case 27: // true
		case 28: // false
		case 46: // (
		case 15: // ++
		case 16: // --
		case 1: // +
		case 2: // -
			writeParse(48);
			Tipo tipoG = g();
			Tipo tipoE2 = e2();

			// Acciones del analizador semantico
			if (tipoE2.getTipo().equals(Entrada.BOOL) && !tipoG.getTipo().equals(Entrada.BOOL)) {
				GestorDeErrores.gestionarError(4001,
						"El tipo de la expresion se esperaba que fuese boolean, pero no lo es");
			} else {
				return tipoG;
			}
			////////////////////////////////////
			break;
		default:
			GestorDeErrores.gestionarError(3006, null);
		}
		return null;
	}

	/**
	 * <pre>
	 * 		E2 -> || G E2
	 * 		E2 -> lambda
	 * </pre>
	 * 
	 * Correspondiente al analizador Semantico:
	 * 
	 * @return Tipo
	 */
	private Tipo e2() {
		switch (sigToken.getToken()) {
		case 13: // ||
			writeParse(49);
			equip(13);
			Tipo tipoG = g();

			// Acciones del analizador semantico
			if (!tipoG.getTipo().equals(Entrada.BOOL)) {
				GestorDeErrores.gestionarError(4001,
						"El tipo de la expresion se esperaba que fuese boolean, pero no lo es");
			}

			e2();
			return new Tipo(Entrada.BOOL, AnalizadorSemantico.DESP_BOOL);
		default:
			writeParse(50);
			return new Tipo(AnalizadorSemantico.TIPO_OK);
		}
	}

	/**
	 * <pre>
	 * 		G -> D G2
	 * </pre>
	 * 
	 * Correspondiente al analizador Semantico:
	 * 
	 * @return Tipo
	 */
	private Tipo g() {
		switch (sigToken.getToken()) {
		case 14: // !
		case 54: // identificador
		case 25: // entero
		case 26: // cadena
		case 27: // true
		case 28: // false
		case 46: // (
		case 15: // ++
		case 16: // --
		case 1: // +
		case 2: // -
			writeParse(51);
			Tipo tipoD = d();
			Tipo tipoG2 = g2();

			// Acciones del analizador semantico
			if (tipoG2.getTipo().equals(Entrada.BOOL) && !tipoD.getTipo().equals(Entrada.BOOL)) {
				GestorDeErrores.gestionarError(4001,
						"El tipo de la expresion se esperaba que fuese boolean, pero no lo es");
			} else {
				return tipoD;
			}
			////////////////////////////////////
			break;
		default:
			GestorDeErrores.gestionarError(3006, null);
		}
		return null;
	}

	/**
	 * <pre>
	 * 		G2 -> && D G2
	 *		G2 -> lambda
	 * </pre>
	 * 
	 * Correspondiente al analizador Semantico:
	 * 
	 * @return Tipo
	 */
	private Tipo g2() {
		switch (sigToken.getToken()) {
		case 12: // &&
			writeParse(52);
			equip(12);
			Tipo tipoD = d();

			// Acciones del analizador semantico
			if (!tipoD.getTipo().equals(Entrada.BOOL)) {
				GestorDeErrores.gestionarError(4001,
						"El tipo de la expresion se esperaba que fuese boolean, pero no lo es");
			}
			////////////////////////////////////

			g2();
			return new Tipo(Entrada.BOOL, AnalizadorSemantico.DESP_BOOL);
		default:
			writeParse(53);
			return new Tipo(AnalizadorSemantico.TIPO_OK);
		}
	}

	/**
	 * <pre>
	 * 		D -> I D2
	 * </pre>
	 * 
	 * Correspondiente al analizador Semantico:
	 * 
	 * @return Tipo
	 */
	private Tipo d() {
		switch (sigToken.getToken()) {
		case 14: // !
		case 54: // identificador
		case 25: // entero
		case 26: // cadena
		case 27: // true
		case 28: // false
		case 46: // (
		case 15: // ++
		case 16: // --
		case 1: // +
		case 2: // -
			writeParse(54);
			Tipo tipoI = i();
			Tipo tipoD2 = d2();

			// Acciones del analizador semantico
			if (tipoD2.getTipo().equals(Entrada.BOOL)) {
				return tipoD2;
			} else {
				return tipoI;
			}
			////////////////////////////////////
		default:
			GestorDeErrores.gestionarError(3006, null);
		}

		return null;
	}

	/**
	 * <pre>
	 * 		D2 -> == I D2
	 *		D2 -> != I D2
	 *		D2 -> lambda
	 * </pre>
	 * 
	 * Correspondiente al analizador Semantico:
	 * 
	 * @return Tipo
	 */
	private Tipo d2() {
		switch (sigToken.getToken()) {
		case 6: // ==
			writeParse(55);
			equip(6);
			i();
			d2();
			return new Tipo(Entrada.BOOL, AnalizadorSemantico.DESP_BOOL);
		case 7: // !=
			writeParse(56);
			equip(7);
			i();
			d2();
			return new Tipo(Entrada.BOOL, AnalizadorSemantico.DESP_BOOL);
		default:
			writeParse(57);
			return new Tipo(AnalizadorSemantico.TIPO_OK);
		}
	}

	/**
	 * <pre>
	 * 		I -> J I2
	 * </pre>
	 * 
	 * Correspondiente al analizador Semantico:
	 * 
	 * @return Tipo
	 */
	private Tipo i() {
		switch (sigToken.getToken()) {
		case 14: // !
		case 54: // identificador
		case 25: // entero
		case 26: // cadena
		case 27: // true
		case 28: // false
		case 46: // (
		case 15: // ++
		case 16: // --
		case 1: // +
		case 2: // -
			writeParse(58);
			Tipo tipoJ = j();
			Tipo tipoI2 = i2(tipoJ);

			// Acciones del analizador semantico
			if (tipoI2.getTipo().equals(Entrada.BOOL)) {
				return tipoI2;
			} else {
				return tipoJ;
			}
			////////////////////////////////////
		default:
			GestorDeErrores.gestionarError(3006, null);
		}

		return null;
	}

	/**
	 * <pre>
	 *		I2 -> > J I2
	 *		I2 -> >= J I2
	 *		I2 -> < J I2
	 *		I2 -> <= J I2
	 *		I2 -> lambda
	 * </pre>
	 * 
	 * Correspondiente al analizador Semantico:
	 * 
	 * @return Tipo
	 */
	private Tipo i2(Tipo tipo) {
		Tipo tipoJ = null;
		switch (sigToken.getToken()) {
		case 9: // >
			writeParse(59);
			equip(9);
			tipoJ = j();

			// Acciones del analizador semantico
			if (!tipoJ.getTipo().equals(tipo.getTipo()) || !tipoJ.getTipo().equals(Entrada.INT)) {
				GestorDeErrores.gestionarError(4001,
						"El tipo de la expresion se esperaba que fuese int, pero no lo es");
			}
			////////////////////////////////////

			i2(tipoJ);
			return new Tipo(Entrada.BOOL, AnalizadorSemantico.DESP_BOOL);
		case 11: // >=
			writeParse(60);
			equip(11);
			tipoJ = j();

			// Acciones del analizador semantico
			if (!tipoJ.getTipo().equals(tipo.getTipo()) || !tipoJ.getTipo().equals(Entrada.INT)) {
				GestorDeErrores.gestionarError(4001,
						"El tipo de la expresion se esperaba que fuese int, pero no lo es");
			}
			////////////////////////////////////

			i2(tipoJ);
			return new Tipo(Entrada.BOOL, AnalizadorSemantico.DESP_BOOL);
		case 8: // <
			writeParse(61);
			equip(8);
			tipoJ = j();

			// Acciones del analizador semantico
			if (!tipoJ.getTipo().equals(tipo.getTipo()) || !tipoJ.getTipo().equals(Entrada.INT)) {
				GestorDeErrores.gestionarError(4001,
						"El tipo de la expresion se esperaba que fuese int, pero no lo es");
			}
			////////////////////////////////////

			i2(tipoJ);
			return new Tipo(Entrada.BOOL, AnalizadorSemantico.DESP_BOOL);
		case 10: // <=
			writeParse(62);
			equip(10);
			tipoJ = j();

			// Acciones del analizador semantico
			if (!tipoJ.getTipo().equals(tipo.getTipo()) || !tipoJ.getTipo().equals(Entrada.INT)) {
				GestorDeErrores.gestionarError(4001,
						"El tipo de la expresion se esperaba que fuese int, pero no lo es");
			}
			////////////////////////////////////

			i2(tipoJ);
			return new Tipo(Entrada.BOOL, AnalizadorSemantico.DESP_BOOL);
		default:
			writeParse(63);
			return new Tipo(AnalizadorSemantico.TIPO_OK);
		}
	}

	/**
	 * <pre>
	 * 		J -> M J2
	 * </pre>
	 * 
	 * Correspondiente al analizador Semantico:
	 * 
	 * @return Tipo
	 */
	private Tipo j() {
		switch (sigToken.getToken()) {
		case 14: // !
		case 54: // identificador
		case 25: // entero
		case 26: // cadena
		case 27: // true
		case 28: // false
		case 46: // (
		case 15: // ++
		case 16: // --
		case 1: // +
		case 2: // -
			writeParse(64);
			Tipo tipoM = m();
			Tipo tipoJ2 = j2();

			// Acciones del analizador semantico
			if (tipoM.getTipo().equals(tipoJ2.getTipo()) || tipoJ2.getTipo().equals(AnalizadorSemantico.TIPO_OK)) {
				return tipoM;
			}
			////////////////////////////////////
			break;
		default:
			GestorDeErrores.gestionarError(3006, null);
		}

		return null;
	}

	/**
	 * <pre>
	 * 		J2 -> + M J2
	 *		J2 -> - M J2
	 *		J2 -> lambda
	 * </pre>
	 * 
	 * Correspondiente al analizador Semantico:
	 * 
	 * @return Tipo
	 */
	private Tipo j2() {
		Tipo tipoM = null, tipoJ2 = null;
		switch (sigToken.getToken()) {
		case 1: // +
			writeParse(65);
			equip(1);
			tipoM = m();
			tipoJ2 = j2();

			// Acciones del analizador semantico

			// Si se estan intentando sumar ints o chars, bien si es otra cosa mal
			if (tipoM.getTipo().equals(Entrada.INT)
					&& (tipoJ2.getTipo().equals(Entrada.INT) || tipoJ2.getTipo().equals(AnalizadorSemantico.TIPO_OK))) {
				return tipoM;
			} else {
				GestorDeErrores.gestionarError(4001,
						"El tipo de la expresion se esperaba que fuese int, pero no lo es");
			}
			////////////////////////////////////
			break;
		case 2: // -
			writeParse(66);
			equip(2);
			tipoM = m();
			tipoJ2 = j2();

			// Acciones del analizador semantico
			if (tipoM.getTipo().equals(Entrada.INT)
					&& (tipoJ2.getTipo().equals(Entrada.INT) || tipoJ2.getTipo().equals(AnalizadorSemantico.TIPO_OK))) {
				return tipoM;
			} else {
				GestorDeErrores.gestionarError(4001,
						"El tipo de la expresion se esperaba que fuese int, pero no lo es");
			}
			////////////////////////////////////
			break;
		default:
			writeParse(67);
			return new Tipo(AnalizadorSemantico.TIPO_OK);
		}

		return null;
	}

	/**
	 * <pre>
	 * 		M -> V M2
	 * </pre>
	 * 
	 * Correspondiente al analizador Semantico:
	 * 
	 * @return Tipo
	 */
	private Tipo m() {
		switch (sigToken.getToken()) {
		case 14: // !
		case 54: // identificador
		case 25: // entero
		case 26: // cadena
		case 27: // true
		case 28: // false
		case 46: // (
		case 15: // ++
		case 16: // --
		case 1: // +
		case 2: // -
			writeParse(68);
			Tipo tipoV = v();
			Tipo tipoM2 = m2();

			// Acciones del analizador semantico
			if (tipoV.getTipo().equals(tipoM2.getTipo()) || tipoM2.getTipo().equals(AnalizadorSemantico.TIPO_OK)) {
				return tipoV;
			} else {
				GestorDeErrores.gestionarError(4001, "El tipo de la expresion no es consistente");
			}
			////////////////////////////////////
			break;
		default:
			GestorDeErrores.gestionarError(3006, null);
		}

		return null;
	}

	/**
	 * <pre>
	 * 		M2 -> * V M2
	 *		M2 -> / V M2
	 *		M2 -> % V M2
	 *		M2 -> lambda
	 * </pre>
	 * 
	 * Correspondiente al analizador Semantico:
	 * 
	 * @return Tipo
	 */
	private Tipo m2() {
		Tipo tipoV = null, tipoM2 = null;

		switch (sigToken.getToken()) {
		case 3: // *
			writeParse(69);
			equip(3);
			tipoV = v();
			tipoM2 = m2();

			// Acciones del analizador semantico
			if (tipoV.getTipo().equals(Entrada.INT)
					&& (tipoM2.getTipo().equals(Entrada.INT) || tipoM2.getTipo().equals(AnalizadorSemantico.TIPO_OK))) {
				return tipoV;
			} else {
				GestorDeErrores.gestionarError(4001,
						"El tipo de la expresion se esperaba que fuese int, pero no lo es");
			}
			////////////////////////////////////
			break;
		case 4: // /
			writeParse(70);
			equip(4);
			tipoV = v();
			tipoM2 = m2();

			// Acciones del analizador semantico
			if (tipoV.getTipo().equals(Entrada.INT)
					&& (tipoM2.getTipo().equals(Entrada.INT) || tipoM2.getTipo().equals(AnalizadorSemantico.TIPO_OK))) {
				return tipoV;
			} else {
				GestorDeErrores.gestionarError(4001,
						"El tipo de la expresion se esperaba que fuese int, pero no lo es");
			}
			////////////////////////////////////
			break;
		case 5: // %
			writeParse(71);
			equip(5);
			tipoV = v();
			tipoM2 = m2();

			// Acciones del analizador semantico
			if (tipoV.getTipo().equals(Entrada.INT)
					&& (tipoM2.getTipo().equals(Entrada.INT) || tipoM2.getTipo().equals(AnalizadorSemantico.TIPO_OK))) {
				return tipoV;
			} else {
				GestorDeErrores.gestionarError(4001,
						"El tipo de la expresion se esperaba que fuese int, pero no lo es");
			}
			////////////////////////////////////
			break;
		default:
			writeParse(72);
			return new Tipo(AnalizadorSemantico.TIPO_OK);
		}

		return null;
	}

	/**
	 * <pre>
	 * 		V -> identificador V2
	 * 		V -> CTE_Entera
	 * 		V -> cadena
	 * 		V -> true
	 * 		V -> false
	 * 		V -> ( E )	
	 * 		V -> INCDEC identificador		
	 *		V -> + V
	 *		V -> - V
	 *		V -> ! V
	 * </pre>
	 * 
	 * Correspondiente al analizador Semantico:
	 * 
	 * @return Tipo
	 */
	private Tipo v() {

		Token identificador = null;
		Tipo tipoV = null;

		switch (sigToken.getToken()) {
		case 54: // identificador
			writeParse(73);

			// Acciones del analizador semantico
			identificador = sigToken; // Almaceno el token para su uso posterior
			////////////////////////////////////

			equip(54);

			// Acciones del analizador semantico
			if (AnalizadorSemantico.tipoID(identificador.getAtributo()).equals(AnalizadorSemantico.TIPO_VACIO)) {
				AnalizadorSemantico.aniadirTS(identificador.getAtributo(), Entrada.INT, AnalizadorSemantico.DESP_INT,
						false);
			}
			////////////////////////////////////

			v2(identificador.getAtributo());
			return new Tipo(AnalizadorSemantico.tipoID(identificador.getAtributo()));
		case 25: // entero
			writeParse(74);
			equip(25);
			return new Tipo(Entrada.INT, AnalizadorSemantico.DESP_INT);
		case 26: // cadena
			writeParse(75);
			equip(26);
			return new Tipo(Entrada.CHARS, AnalizadorSemantico.DESP_CHARS);
		case 27: // true
			writeParse(76);
			equip(27);
			return new Tipo(Entrada.BOOL, AnalizadorSemantico.DESP_BOOL);
		case 28: // false
			writeParse(77);
			equip(28);
			return new Tipo(Entrada.BOOL, AnalizadorSemantico.DESP_BOOL);
		case 46: // (
			writeParse(78);
			equip(46); // (
			Tipo tipoE = e();
			equip(47); // )
			return tipoE;
		case 15: // ++
		case 16: // --
			writeParse(79);
			incDec();

			// Acciones del analizador semantico
			identificador = sigToken; // Almaceno el token para su uso posterior
			////////////////////////////////////

			equip(54); // identificador

			// Acciones del analizador semantico
			if (!AnalizadorSemantico.tipoID(identificador.getAtributo()).equals(Entrada.INT)) {
				GestorDeErrores.gestionarError(4001,
						"El tipo de la expresion se esperaba que fuese int, pero no lo es");
			}
			////////////////////////////////////
			return new Tipo(Entrada.INT);
		case 1: // +
			writeParse(80);
			equip(1);
			tipoV = v();

			// Acciones del analizador semantico
			if (tipoV.getTipo().equals(Entrada.INT)) {
				return new Tipo(Entrada.INT, AnalizadorSemantico.DESP_INT);
			} else {
				GestorDeErrores.gestionarError(4001,
						"El tipo de la expresion se esperaba que fuese int, pero no lo es");
			}
			////////////////////////////////////
			break;
		case 2: // -
			writeParse(81);
			equip(2);
			tipoV = v();

			// Acciones del analizador semantico
			if (tipoV.getTipo().equals(Entrada.INT)) {
				return new Tipo(Entrada.INT, AnalizadorSemantico.DESP_INT);
			} else {
				GestorDeErrores.gestionarError(4001,
						"El tipo de la expresion se esperaba que fuese int, pero no lo es");
			}
			////////////////////////////////////
			break;
		case 14: // !
			writeParse(82);
			equip(14);
			tipoV = v();

			// Acciones del analizador semantico
			if (tipoV.getTipo().equals(Entrada.BOOL)) {
				return new Tipo(Entrada.BOOL, AnalizadorSemantico.DESP_BOOL);
			} else {
				GestorDeErrores.gestionarError(4001,
						"El tipo de la expresion se esperaba que fuese boolean, pero no lo es");
			}
			////////////////////////////////////
			break;
		default:
			GestorDeErrores.gestionarError(3006, null);
		}

		return null;
	}

	/**
	 * <pre>
	 * 		V2 -> ( L )
	 * 		V2 -> lambda
	 * 		V2 -> INCDEC
	 * </pre>
	 * 
	 * Correspondiente al analizador Semantico:
	 * 
	 * @return Tipo
	 */
	private Tipo v2(int indice) {
		switch (sigToken.getToken()) {
		case 46: // (
			writeParse(83);
			equip(46); // (
			List<String> param = l();

			// Acciones del analizador semantico
			if (!AnalizadorSemantico.validarParam(indice, param)) {
				GestorDeErrores.gestionarError(4001, "El tipo de los parametros introducidos no son los esperados");
			}
			////////////////////////////////////

			equip(47); // )

			Tipo tipoReturn = new Tipo(AnalizadorSemantico.tipoID(indice));
			// Acciones del analizador semantico
			if (tipoReturn.getTipo().equals(Entrada.INT)) {
				tipoReturn.setAncho(AnalizadorSemantico.DESP_INT);
			} else if (tipoReturn.getTipo().equals(Entrada.CHARS)) {
				tipoReturn.setAncho(AnalizadorSemantico.DESP_CHARS);
			} else {
				tipoReturn.setAncho(AnalizadorSemantico.DESP_BOOL);
			}
			////////////////////////////////////
			break;
		default:
			writeParse(84);
			return new Tipo(AnalizadorSemantico.TIPO_OK);
		case 15: // ++
		case 16: // --
			writeParse(85);
			incDec();
			return new Tipo(Entrada.INT, AnalizadorSemantico.DESP_INT);
		}

		return null;
	}

	/**
	 * <pre>
	 * 		L -> E Q
	 * 		L -> lambda
	 * </pre>
	 * 
	 * Correspondiente al analizador Semantico:
	 * 
	 * @return Lista de los tipos de los parametros
	 */
	private List<String> l() {
		List<String> param = new ArrayList<>();
		switch (sigToken.getToken()) {
		case 14: // !
		case 54: // identificador
		case 25: // entero
		case 26: // cadena
		case 27: // true
		case 28: // false
		case 46: // (
		case 15: // ++
		case 16: // --
		case 1: // +
		case 2: // -
			writeParse(86);
			Tipo tipoE = e();
			param.add(tipoE.getTipo());
			List<String> paramQ = q();

			param.addAll(paramQ);
			break;
		default:
			writeParse(87);
			break;
		}

		return param;
	}

	/**
	 * <pre>
	 * 		L2 -> E Q
	 * </pre>
	 * 
	 * Correspondiente al analizador Semantico:
	 * 
	 * @return Lista de los tipos de los parametros
	 */
	private List<String> l2() {
		List<String> param = new ArrayList<>();
		switch (sigToken.getToken()) {
		case 14: // !
		case 54: // identificador
		case 25: // entero
		case 26: // cadena
		case 27: // true
		case 28: // false
		case 46: // (
		case 15: // ++
		case 16: // --
		case 1: // +
		case 2: // -
			writeParse(86);
			Tipo tipoE = e();
			param.add(tipoE.getTipo());
			List<String> paramQ = q();

			param.addAll(paramQ);
			break;
		default:
			GestorDeErrores.gestionarError(3008, null);
			break;
		}

		return param;
	}

	/**
	 * <pre>
	 * 		Q -> , E Q
	 * 		Q -> lambda
	 * </pre>
	 * 
	 * Correspondiente al analizador Semantico:
	 * 
	 * @return Lista de los tipos de los parametros
	 */
	private List<String> q() {
		List<String> param = new ArrayList<>();
		switch (sigToken.getToken()) {
		case 50: // ,
			writeParse(88);
			equip(50);
			Tipo tipoE = e();
			param.add(tipoE.getTipo());
			List<String> paramQ = q();
			param.addAll(paramQ);
			break;
		default:
			writeParse(89);
			break;
		}

		return param;
	}

	/**
	 * <pre>
	 * 		S -> identificador S2
	 * 		S -> INCDEC identificador
	 * 		S -> return X
	 * 		S -> write ( L2 )
	 * 		S -> prompt ( identificador )
	 * 		S -> break
	 * </pre>
	 * 
	 * Correspondiente al analizador Semantico:
	 * 
	 * @param isReturn
	 *            Si estamos dentro de una funcion sera true
	 * @param isBreak
	 *            Si estamos dentro de un switch sera true
	 * 
	 * @return Tipo de retorno si existe, si no devuelve TIPO_OK
	 */
	private String s(boolean isReturn, boolean isBreak) {
		Token identificador = null;
		String tipoReturn = AnalizadorSemantico.TIPO_OK;
		switch (sigToken.getToken()) {
		case 54: // identificador
			writeParse(90);

			// Acciones del analizador semantico
			identificador = sigToken; // Almaceno el token para su uso posterior
			////////////////////////////////////

			equip(54);// Identificador

			// Acciones del analizador semantico
			if (AnalizadorSemantico.tipoID(identificador.getAtributo()).equals(AnalizadorSemantico.TIPO_VACIO)) {
				AnalizadorSemantico.aniadirTS(identificador.getAtributo(), Entrada.INT, AnalizadorSemantico.DESP_INT,
						false);
			}
			////////////////////////////////////

			s2(identificador.getAtributo());
			break;
		case 15: // ++
		case 16: // --
			writeParse(91);
			incDec();

			// Acciones del analizador semantico
			identificador = sigToken; // Almaceno el token para su uso posterior
			////////////////////////////////////

			equip(54);

			// Acciones del analizador semantico
			if (!AnalizadorSemantico.tipoID(identificador.getAtributo()).equals(Entrada.INT)) {
				if (AnalizadorSemantico.tipoID(identificador.getAtributo()).equals(AnalizadorSemantico.TIPO_VACIO)) {
					AnalizadorSemantico.aniadirTS(identificador.getAtributo(), Entrada.INT,
							AnalizadorSemantico.DESP_INT, false);
				} else {
					GestorDeErrores.gestionarError(4001,
							"El tipo de la expresion se esperaba que fuese int, pero no lo es");
				}
			}
			////////////////////////////////////
			break;
		case 35: // return
			writeParse(92);

			// Acciones del analizador semantico
			if (!isReturn) {
				GestorDeErrores.gestionarError(4003, null);
			}
			////////////////////////////////////

			equip(35);
			tipoReturn = x();
			break;
		case 33: // write
			writeParse(93);
			equip(33);
			equip(46); // (
			l2();
			equip(47); // )
			break;
		case 34: // prompt
			writeParse(94);
			equip(34);
			equip(46); // (

			// Acciones del analizador semantico
			identificador = sigToken; // Almaceno el token para su uso posterior
			////////////////////////////////////

			equip(54); // identificador
			equip(47); // )

			// Acciones del analizador semantico
			if (AnalizadorSemantico.tipoID(identificador.getAtributo()).equals(AnalizadorSemantico.TIPO_VACIO)) {
				AnalizadorSemantico.aniadirTS(identificador.getAtributo(), Entrada.INT, AnalizadorSemantico.DESP_INT,
						false);
			}

			if (!AnalizadorSemantico.tipoID(identificador.getAtributo()).equals(Entrada.INT)
					&& !AnalizadorSemantico.tipoID(identificador.getAtributo()).equals(Entrada.CHARS)) {
				GestorDeErrores.gestionarError(4001, "Prompt solo acepta ints o chars como parametros");
			}
			////////////////////////////////////
			break;
		case 43: // break
			writeParse(95);

			// Acciones del analizador semantico
			if (!isBreak) {
				GestorDeErrores.gestionarError(4002, null);
			}
			////////////////////////////////////

			equip(43);
			break;
		default:
			GestorDeErrores.gestionarError(3007, null);
		}

		return tipoReturn;
	}

	/**
	 * <pre>
	 * 		S2 -> ASIGNACION E
	 * 		S2 -> ( L )
	 * 		S2 -> INCDEC
	 * </pre>
	 */
	private void s2(int indice) {
		switch (sigToken.getToken()) {
		case 17: // =
		case 18: // +=
		case 19: // -=
		case 20: // *=
		case 21: // /=
		case 22: // %=
		case 23: // &=
		case 24: // |=
			writeParse(96);
			Tipo tipoAsignacion = asignacion();
			Tipo tipoE = e();

			// Acciones del analizador semantico
			if (!AnalizadorSemantico.tipoID(indice).equals(tipoE.getTipo())
					|| (!AnalizadorSemantico.tipoID(indice).equals(tipoAsignacion.getTipo())
							&& !tipoAsignacion.getTipo().equals(AnalizadorSemantico.TIPO_OK))) {
				GestorDeErrores.gestionarError(4001,
						"El tipo que se esta intentando asignar al identificador no es el correcto");
			}
			////////////////////////////////////
			break;
		case 46: // (
			writeParse(97);
			equip(46); // (
			List<String> param = l();

			// Acciones del analizador semantico
			if (!AnalizadorSemantico.validarParam(indice, param)) {
				GestorDeErrores.gestionarError(4001, "Los tipos de los parametros pasados no son los correctos");
			}
			////////////////////////////////////

			equip(47); // )
			break;
		case 15: // ++
		case 16: // --
			writeParse(98);

			// Acciones del analizador semantico
			if (!AnalizadorSemantico.tipoID(indice).equals(Entrada.INT)) {
				GestorDeErrores.gestionarError(4001,
						"El tipo de la expresion se esperaba que fuese int, pero no lo es");
			}
			////////////////////////////////////

			incDec();
			break;
		default:
			GestorDeErrores.gestionarError(3007, null);
		}
	}

	/**
	 * <pre>
	 * 		X -> E
	 * 		X -> lambda
	 * </pre>
	 * 
	 * Correspondiente al analizador Semantico:
	 * 
	 * @return Tipo de retorno si existe, si no devuelve TIPO_OK
	 */
	private String x() {
		switch (sigToken.getToken()) {
		case 14: // !
		case 54: // identificador
		case 25: // entero
		case 26: // cadena
		case 27: // true
		case 28: // false
		case 46: // (
		case 15: // ++
		case 16: // --
		case 1: // +
		case 2: // -
			writeParse(99);
			return e().getTipo();
		default:
			writeParse(100);
			return AnalizadorSemantico.TIPO_VACIO;
		}
	}

	/**
	 * <pre>
	 * 		F -> function H identificador ( A ) { C }
	 * </pre>
	 */
	private void f() {
		if (sigToken.getToken() == 45) { // function
			writeParse(101);
			equip(45); // function
			// Acciones del analizador semantico
			AnalizadorSemantico.activarZonaDeDeclaracion();
			////////////////////////////////////
			String tipoH = h();

			// Acciones del analizador semantico
			Token identificador = sigToken;
			////////////////////////////////////

			equip(54); // identificador

			// Acciones del analizador semantico
			AnalizadorSemantico.crearTablaDeSimbolos(AnalizadorSemantico.lexemaID(identificador.getAtributo()));
			////////////////////////////////////

			equip(46); // (
			List<Pair<String, Boolean>> params = a();

			// Acciones del analizador semantico
			AnalizadorSemantico.actualizarIndiceFuncion(identificador.getAtributo(), tipoH, params);
			AnalizadorSemantico.desactivarZonaDeDeclaracion();
			////////////////////////////////////

			equip(47); // )
			equip(48); // {
			String tipoC = c(true, false);

			// Acciones del analizador semantico
			if (!tipoC.equals(tipoH) && tipoC.equals(AnalizadorSemantico.TIPO_OK)) {
				if (tipoH.equals(AnalizadorSemantico.TIPO_VACIO) && !tipoC.equals(AnalizadorSemantico.TIPO_OK)) {
					GestorDeErrores.gestionarError(4001, "El tipo devuelto no coincide con el declarado en la funcion");
				}
			}
			////////////////////////////////////

			equip(49); // }

			// Acciones del analizador semantico
			AnalizadorSemantico.cerrarTablaDeSimbolos();
			////////////////////////////////////
		} else

		{
			GestorDeErrores.gestionarError(3009, null);
		}
	}

	/**
	 * <pre>
	 * 		A -> T identificador K
	 * 		A -> lambda
	 * </pre>
	 * 
	 * Correspondiente al analizador Semantico:
	 * 
	 * @return Tipo deLista de pares con los tipos y modos de los parametros
	 */
	private List<Pair<String, Boolean>> a() {
		List<Pair<String, Boolean>> params = new ArrayList<>();

		switch (sigToken.getToken()) {
		case 30: // int
		case 31: // bool
		case 32: // chars
			writeParse(102);
			Tipo tipoT = t();

			// Acciones del analizador semantico
			Token identificador = sigToken;
			////////////////////////////////////

			equip(54); // identificador

			// Acciones del analizador semantico
			AnalizadorSemantico.aniadirTS(identificador.getAtributo(), tipoT.getTipo(), tipoT.getAncho(), false);
			params.add(new Pair<String, Boolean>(tipoT.getTipo(), false));
			////////////////////////////////////

			params.addAll(k());
			break;
		default:
			writeParse(103);
			break;
		}
		return params;
	}

	/**
	 * <pre>
	 * 		K -> , T identificador K
	 * 		K -> lambda
	 * </pre>
	 */
	private List<Pair<String, Boolean>> k() {
		List<Pair<String, Boolean>> params = new ArrayList<>();
		if (sigToken.getToken() == 50) { // ,
			writeParse(104);
			equip(50);
			Tipo tipoT = t();

			// Acciones del analizador semantico
			Token identificador = sigToken;
			////////////////////////////////////

			equip(54); // identificador

			// Acciones del analizador semantico
			AnalizadorSemantico.aniadirTS(identificador.getAtributo(), tipoT.getTipo(), tipoT.getAncho(), false);
			params.add(new Pair<String, Boolean>(tipoT.getTipo(), false));
			////////////////////////////////////

			params.addAll(k());
		} else {
			writeParse(105);
		}
		return params;
	}

	/**
	 * <pre>
	 * 		C -> B C
	 * 		C -> lambda
	 * </pre>
	 * 
	 * Correspondiente al analizador Semantico:
	 * 
	 * @param isReturn
	 *            Si estamos dentro de una funcion sera true
	 * @param isBreak
	 *            Si estamos dentro de un switch sera true
	 * 
	 * @return Tipo de retorno si existe, si no devuelve TIPO_OK
	 */
	private String c(boolean isReturn, boolean isBreak) {
		String tipoReturn = null;
		switch (sigToken.getToken()) {
		case 29: // var
		case 36: // if
		case 54: // identificador
		case 15: // ++
		case 16: // --
		case 35: // return
		case 33: // write
		case 34: // prompt
		case 43: // break
		case 41: // switch
		case 38: // while
		case 39: // do
		case 40: // for
			writeParse(106);
			String tipoB = b(isReturn, isBreak);
			String tipoC = c(isReturn, isBreak);

			// Acciones del analizador semantico
			if (tipoC.equals(tipoB) || tipoC.equals(AnalizadorSemantico.TIPO_OK)) {
				tipoReturn = tipoB;
			} else if (tipoB.equals(AnalizadorSemantico.TIPO_OK)) {
				tipoReturn = tipoC;
			} else {
				GestorDeErrores.gestionarError(4001, "El tipo return no es igual en todo el bloque de codigo");
			}
			////////////////////////////////////
			break;
		default:
			writeParse(107);
			tipoReturn = AnalizadorSemantico.TIPO_OK;
			break;
		}

		return tipoReturn;
	}

	/**
	 * <pre>
	 * 		H -> T
	 * 		H -> lambda
	 * </pre>
	 */
	private String h() {
		switch (sigToken.getToken()) {
		case 30: // int
		case 31: // bool
		case 32: // chars
			writeParse(108);
			return t().getTipo();
		default:
			writeParse(109);
			return AnalizadorSemantico.TIPO_VACIO;
		}
	}

	/**
	 * Metodo que se encarga de comprobar que el token actual es el esperado y leer
	 * el siguiente
	 * 
	 * @param token
	 *            codigo del token que se espera
	 */
	private void equip(int token) {
		if (sigToken.getToken() == token) {
			sigToken = lexico.getToken();
		} else {
			GestorDeErrores.gestionarError(3010, sigToken.getToken() + "");
		}
	}

	private void writeParse(int regla) {
		try {
			parse.write(" " + regla);
		} catch (IOException e) {
			// Envio al gestor de errore el codigo 1002 reservado a error de compilador
			// cuando un fichero no se puede abrir
			GestorDeErrores.gestionarError(1002, FICHERO_PARSE);
		}
	}

	/**
	 * Metodo que se encarga de cerrar el fichero parse
	 */
	public void close() {
		try {
			parse.close();
		} catch (IOException e) {
			// Envio al gestor de errore el codigo 1002 reservado a error de compilador
			// cuando un fichero no se puede abrir
			GestorDeErrores.gestionarError(1002, FICHERO_PARSE);
		}
	}
}
