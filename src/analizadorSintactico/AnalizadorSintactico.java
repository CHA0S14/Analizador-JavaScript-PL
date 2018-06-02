package analizadorSintactico;

import java.io.FileWriter;
import java.io.IOException;

import analizadorLexico.AnalizadorLexico;
import analizadorLexico.Token;
import gestorDeErrores.GestorDeErrores;

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
	 * 		P -> EOF
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
			try {
				parse.write(" 1");
			} catch (IOException e) {
				// Envio al gestor de errore el codigo 1002 reservado a error de compilador
				// cuando un fichero no se puede abrir
				GestorDeErrores.gestionarError(1002, FICHERO_PARSE);
			}
			b();
			p();
			break;
		case 45: // function
			try {
				parse.write(" 2");
			} catch (IOException e) {
				// Envio al gestor de errore el codigo 1002 reservado a error de compilador
				// cuando un fichero no se puede abrir
				GestorDeErrores.gestionarError(1002, FICHERO_PARSE);
			}
			f();
			p();
			break;
		case 53: // EOF
			try {
				parse.write(" 3");
			} catch (IOException e) {
				// Envio al gestor de errore el codigo 1002 reservado a error de compilador
				// cuando un fichero no se puede abrir
				GestorDeErrores.gestionarError(1002, FICHERO_PARSE);
			}
			equip(53);
			break;
		default:
			GestorDeErrores.gestionarError(3001, null);
		}
	}

	/**
	 * <pre>
	 * 		B -> var T identificador B2 ID ;
	 * 		B -> if ( E ) IF
	 * 		B -> S ;
	 * 		B -> switch ( E ) { CASE }
	 * 		B -> while ( E ) { C }
	 * 		B -> do { C } while ( E ) ;
	 * 		B -> for ( INICIALIZACION ; E ; ACTUALIZACION ) iniBloq  C endBloq
	 * </pre>
	 */
	private void b() {
		switch (sigToken.getToken()) {
		case 29: // var
			try {
				parse.write(" 4");
			} catch (IOException e) {
				// Envio al gestor de errore el codigo 1002 reservado a error de compilador
				// cuando un fichero no se puede abrir
				GestorDeErrores.gestionarError(1002, FICHERO_PARSE);
			}
			equip(29); // var
			t();
			equip(54); // identificador
			b2();
			id();
			equip(51); // ;
			break;
		case 36: // if
			try {
				parse.write(" 5");
			} catch (IOException e) {
				// Envio al gestor de errore el codigo 1002 reservado a error de compilador
				// cuando un fichero no se puede abrir
				GestorDeErrores.gestionarError(1002, FICHERO_PARSE);
			}
			equip(36); // if
			equip(46); // (
			e();
			equip(47); // )
			bloqueIf();
			break;
		case 54: // identificador
		case 15: // ++
		case 16: // --
		case 35: // return
		case 33: // write
		case 34: // prompt
		case 43: // break
			try {
				parse.write(" 6");
			} catch (IOException e) {
				// Envio al gestor de errore el codigo 1002 reservado a error de compilador
				// cuando un fichero no se puede abrir
				GestorDeErrores.gestionarError(1002, FICHERO_PARSE);
			}
			s();
			equip(51); // ;
			break;
		case 41: // switch
			try {
				parse.write(" 7");
			} catch (IOException e) {
				// Envio al gestor de errore el codigo 1002 reservado a error de compilador
				// cuando un fichero no se puede abrir
				GestorDeErrores.gestionarError(1002, FICHERO_PARSE);
			}
			equip(41); // switch
			equip(46); // (
			e();
			equip(47); // )
			equip(48); // {
			bloqueCase();
			equip(49); // }
			break;
		case 38: // while
			try {
				parse.write(" 8");
			} catch (IOException e) {
				// Envio al gestor de errore el codigo 1002 reservado a error de compilador
				// cuando un fichero no se puede abrir
				GestorDeErrores.gestionarError(1002, FICHERO_PARSE);
			}
			equip(38); // while
			equip(46); // (
			e();
			equip(47); // )
			equip(48); // {
			c();
			equip(49); // }
			break;
		case 39: // do
			try {
				parse.write(" 9");
			} catch (IOException e) {
				// Envio al gestor de errore el codigo 1002 reservado a error de compilador
				// cuando un fichero no se puede abrir
				GestorDeErrores.gestionarError(1002, FICHERO_PARSE);
			}
			equip(39); // do
			equip(48); // {
			c();
			equip(49); // }
			equip(38); // while
			equip(46); // (
			e();
			equip(47); // )
			equip(51); // ;
			break;
		case 40: // for
			try {
				parse.write(" 10");
			} catch (IOException e) {
				// Envio al gestor de errore el codigo 1002 reservado a error de compilador
				// cuando un fichero no se puede abrir
				GestorDeErrores.gestionarError(1002, FICHERO_PARSE);
			}
			equip(40); // for
			equip(46); // (
			inicializacion();
			equip(51); // ;
			e();
			equip(51); // ;
			actualizacion();
			equip(47); // )
			equip(48); // {
			c();
			equip(49); // }
			break;
		default:
			GestorDeErrores.gestionarError(3001, null);
		}
	}

	/**
	 * <pre>
	 * 		B2 -> ASIGNACION E
	 * 		B2 -> lambda
	 * </pre>
	 */
	private void b2() {
		switch (sigToken.getToken()) {
		case 17: // =
		case 18: // +=
		case 19: // -=
		case 20: // *=
		case 21: // /=
		case 22: // %=
		case 23: // &=
		case 24: // |=
			try {
				parse.write(" 11");
			} catch (IOException e) {
				// Envio al gestor de errore el codigo 1002 reservado a error de compilador
				// cuando un fichero no se puede abrir
				GestorDeErrores.gestionarError(1002, FICHERO_PARSE);
			}
			asignacion();
			e();
			break;
		default:
			try {
				parse.write(" 12");
			} catch (IOException e) {
				// Envio al gestor de errore el codigo 1002 reservado a error de compilador
				// cuando un fichero no se puede abrir
				GestorDeErrores.gestionarError(1002, FICHERO_PARSE);
			}
			break;
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
			try {
				parse.write(" 13");
			} catch (IOException e) {
				// Envio al gestor de errore el codigo 1002 reservado a error de compilador
				// cuando un fichero no se puede abrir
				GestorDeErrores.gestionarError(1002, FICHERO_PARSE);
			}
			equip(54); // identificador
			asignacion();
			e();
			break;
		default:
			try {
				parse.write(" 14");
			} catch (IOException e) {
				// Envio al gestor de errore el codigo 1002 reservado a error de compilador
				// cuando un fichero no se puede abrir
				GestorDeErrores.gestionarError(1002, FICHERO_PARSE);
			}
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
		switch (sigToken.getToken()) {
		case 54: // identificador
			try {
				parse.write(" 15");
			} catch (IOException e) {
				// Envio al gestor de errore el codigo 1002 reservado a error de compilador
				// cuando un fichero no se puede abrir
				GestorDeErrores.gestionarError(1002, FICHERO_PARSE);
			}
			equip(54); // identificador
			actualizacion2();
			break;
		case 15: // ++
			try {
				parse.write(" 16");
			} catch (IOException e) {
				// Envio al gestor de errore el codigo 1002 reservado a error de compilador
				// cuando un fichero no se puede abrir
				GestorDeErrores.gestionarError(1002, FICHERO_PARSE);
			}
			equip(15); // ++
			equip(54); // identificador
			break;
		case 16: // --
			try {
				parse.write(" 17");
			} catch (IOException e) {
				// Envio al gestor de errore el codigo 1002 reservado a error de compilador
				// cuando un fichero no se puede abrir
				GestorDeErrores.gestionarError(1002, FICHERO_PARSE);
			}
			equip(15); // --
			equip(54); // identificador
			break;
		}
	}

	/**
	 * <pre>
	 * 		ACTUALIZACION2 -> ASIGNACION E
	 * 		ACTUALIZACION2 -> INCDEC
	 * </pre>
	 */
	private void actualizacion2() {
		switch (sigToken.getToken()) {
		case 17: // =
		case 18: // +=
		case 19: // -=
		case 20: // *=
		case 21: // /=
		case 22: // %=
		case 23: // &=
		case 24: // |=
			try {
				parse.write(" 18");
			} catch (IOException e) {
				// Envio al gestor de errore el codigo 1002 reservado a error de compilador
				// cuando un fichero no se puede abrir
				GestorDeErrores.gestionarError(1002, FICHERO_PARSE);
			}
			asignacion();
			e();
			break;
		case 15: // ++
		case 16: // --
			try {
				parse.write(" 19");
			} catch (IOException e) {
				// Envio al gestor de errore el codigo 1002 reservado a error de compilador
				// cuando un fichero no se puede abrir
				GestorDeErrores.gestionarError(1002, FICHERO_PARSE);
			}
			incDec();
			break;
		default:
			GestorDeErrores.gestionarError(3002, null);
		}
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
			try {
				parse.write(" 20");
			} catch (IOException e) {
				// Envio al gestor de errore el codigo 1002 reservado a error de compilador
				// cuando un fichero no se puede abrir
				GestorDeErrores.gestionarError(1002, FICHERO_PARSE);
			}
			equip(15);
			break;
		case 16: // --
			try {
				parse.write(" 21");
			} catch (IOException e) {
				// Envio al gestor de errore el codigo 1002 reservado a error de compilador
				// cuando un fichero no se puede abrir
				GestorDeErrores.gestionarError(1002, FICHERO_PARSE);
			}
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
	 */
	private void asignacion() {
		switch (sigToken.getToken()) {
		case 17: // =
			try {
				parse.write(" 22");
			} catch (IOException e) {
				// Envio al gestor de errore el codigo 1002 reservado a error de compilador
				// cuando un fichero no se puede abrir
				GestorDeErrores.gestionarError(1002, FICHERO_PARSE);
			}
			equip(17);
			break;
		case 18: // +=
		case 19: // -=
		case 20: // *=
		case 21: // /=
		case 22: // %=
		case 23: // &=
		case 24: // |=
			try {
				parse.write(" 23");
			} catch (IOException e) {
				// Envio al gestor de errore el codigo 1002 reservado a error de compilador
				// cuando un fichero no se puede abrir
				GestorDeErrores.gestionarError(1002, FICHERO_PARSE);
			}
			asignacionOp();
			break;
		default:
			GestorDeErrores.gestionarError(3004, sigToken.getToken() + "");
		}
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
	 */
	private void asignacionOp() {
		switch (sigToken.getToken()) {
		case 18: // +=
			try {
				parse.write(" 24");
			} catch (IOException e) {
				// Envio al gestor de errore el codigo 1002 reservado a error de compilador
				// cuando un fichero no se puede abrir
				GestorDeErrores.gestionarError(1002, FICHERO_PARSE);
			}
			equip(18);
			break;
		case 19: // -=
			try {
				parse.write(" 25");
			} catch (IOException e) {
				// Envio al gestor de errore el codigo 1002 reservado a error de compilador
				// cuando un fichero no se puede abrir
				GestorDeErrores.gestionarError(1002, FICHERO_PARSE);
			}
			equip(19);
			break;
		case 20: // *=
			try {
				parse.write(" 26");
			} catch (IOException e) {
				// Envio al gestor de errore el codigo 1002 reservado a error de compilador
				// cuando un fichero no se puede abrir
				GestorDeErrores.gestionarError(1002, FICHERO_PARSE);
			}
			equip(20);
			break;
		case 21: // /=
			try {
				parse.write(" 27");
			} catch (IOException e) {
				// Envio al gestor de errore el codigo 1002 reservado a error de compilador
				// cuando un fichero no se puede abrir
				GestorDeErrores.gestionarError(1002, FICHERO_PARSE);
			}
			equip(21);
			break;
		case 22: // %=
			try {
				parse.write(" 28");
			} catch (IOException e) {
				// Envio al gestor de errore el codigo 1002 reservado a error de compilador
				// cuando un fichero no se puede abrir
				GestorDeErrores.gestionarError(1002, FICHERO_PARSE);
			}
			equip(22);
			break;
		case 23: // &=
			try {
				parse.write(" 29");
			} catch (IOException e) {
				// Envio al gestor de errore el codigo 1002 reservado a error de compilador
				// cuando un fichero no se puede abrir
				GestorDeErrores.gestionarError(1002, FICHERO_PARSE);
			}
			equip(23);
			break;
		case 24: // |=
			try {
				parse.write(" 30");
			} catch (IOException e) {
				// Envio al gestor de errore el codigo 1002 reservado a error de compilador
				// cuando un fichero no se puede abrir
				GestorDeErrores.gestionarError(1002, FICHERO_PARSE);
			}
			equip(24);
			break;
		default:
			GestorDeErrores.gestionarError(3004, sigToken.getToken() + "");
		}
	}

	/**
	 * <pre>
	 * 		IF -> { C } ELSE
	 * 		IF -> S ;
	 * </pre>
	 */
	private void bloqueIf() {
		switch (sigToken.getToken()) {
		case 48: // {
			try {
				parse.write(" 31");
			} catch (IOException e) {
				// Envio al gestor de errore el codigo 1002 reservado a error de compilador
				// cuando un fichero no se puede abrir
				GestorDeErrores.gestionarError(1002, FICHERO_PARSE);
			}
			equip(48); // {
			c();
			equip(49); // }
			bloqueElse();
			break;
		case 54: // identificador
		case 15: // ++ o --
		case 35: // return
		case 33: // write
		case 34: // prompt
		case 43: // break
			try {
				parse.write(" 32");
			} catch (IOException e) {
				// Envio al gestor de errore el codigo 1002 reservado a error de compilador
				// cuando un fichero no se puede abrir
				GestorDeErrores.gestionarError(1002, FICHERO_PARSE);
			}
			s();
			equip(51); // ;
			break;
		}
	}

	/**
	 * <pre>
	 * 		ELSE -> else { C }
	 * 		ELSE -> lambda
	 * </pre>
	 */
	private void bloqueElse() {
		switch (sigToken.getToken()) {
		case 37: // else
			try {
				parse.write(" 33");
			} catch (IOException e) {
				// Envio al gestor de errore el codigo 1002 reservado a error de compilador
				// cuando un fichero no se puede abrir
				GestorDeErrores.gestionarError(1002, FICHERO_PARSE);
			}
			equip(37); // else
			equip(48); // {
			c();
			equip(49); // }
			break;
		default:
			try {
				parse.write(" 34");
			} catch (IOException e) {
				// Envio al gestor de errore el codigo 1002 reservado a error de compilador
				// cuando un fichero no se puede abrir
				GestorDeErrores.gestionarError(1002, FICHERO_PARSE);
			}
			break;
		}
	}

	/**
	 * <pre>
	 * 		ID -> , identificador B2 ID
	 * 		ID -> lambda
	 * </pre>
	 */
	private void id() {
		if (sigToken.getToken() == 50) {
			try {
				parse.write(" 35");
			} catch (IOException e) {
				// Envio al gestor de errore el codigo 1002 reservado a error de compilador
				// cuando un fichero no se puede abrir
				GestorDeErrores.gestionarError(1002, FICHERO_PARSE);
			}
			equip(50); // ,
			equip(54); // identificador
			b2();
			id();
		} else {
			try {
				parse.write(" 36");
			} catch (IOException e) {
				// Envio al gestor de errore el codigo 1002 reservado a error de compilador
				// cuando un fichero no se puede abrir
				GestorDeErrores.gestionarError(1002, FICHERO_PARSE);
			}
		}
	}

	/**
	 * <pre>
	 * 		CASE -> case cte_entera : C CASE									
	 * 		CASE -> default : C CASE2
	 *		CASE -> lambda
	 * </pre>
	 */
	private void bloqueCase() {
		switch (sigToken.getToken()) {
		case 42: // case
			try {
				parse.write(" 37");
			} catch (IOException e) {
				// Envio al gestor de errore el codigo 1002 reservado a error de compilador
				// cuando un fichero no se puede abrir
				GestorDeErrores.gestionarError(1002, FICHERO_PARSE);
			}
			equip(42); // case
			equip(25); // entero
			equip(52); // :
			c();
			bloqueCase();
			break;
		case 44: // default
			try {
				parse.write(" 38");
			} catch (IOException e) {
				// Envio al gestor de errore el codigo 1002 reservado a error de compilador
				// cuando un fichero no se puede abrir
				GestorDeErrores.gestionarError(1002, FICHERO_PARSE);
			}
			equip(44); // default
			equip(52); // :
			c();
			bloqueCase2();
			break;
		default:
			try {
				parse.write(" 39");
			} catch (IOException e) {
				// Envio al gestor de errore el codigo 1002 reservado a error de compilador
				// cuando un fichero no se puede abrir
				GestorDeErrores.gestionarError(1002, FICHERO_PARSE);
			}
			break;
		}
	}

	/**
	 * <pre>
	 * 		CASE2 -> case cte_entera : C CASE2
	 *		CASE2 -> lambda
	 * </pre>
	 */
	private void bloqueCase2() {
		switch (sigToken.getToken()) {
		case 42: // case
			try {
				parse.write(" 40");
			} catch (IOException e) {
				// Envio al gestor de errore el codigo 1002 reservado a error de compilador
				// cuando un fichero no se puede abrir
				GestorDeErrores.gestionarError(1002, FICHERO_PARSE);
			}
			equip(42); // case
			equip(25); // entero
			equip(52); // :
			c();
			bloqueCase2();
			break;
		default:
			try {
				parse.write(" 41");
			} catch (IOException e) {
				// Envio al gestor de errore el codigo 1002 reservado a error de compilador
				// cuando un fichero no se puede abrir
				GestorDeErrores.gestionarError(1002, FICHERO_PARSE);
			}
			break;
		}
	}

	/**
	 * <pre>
	 * 		T -> int
	 * 		T -> bool
	 * 		T -> chars
	 * </pre>
	 */
	private void t() {
		switch (sigToken.getToken()) {
		case 30: // int
			try {
				parse.write(" 42");
			} catch (IOException e) {
				// Envio al gestor de errore el codigo 1002 reservado a error de compilador
				// cuando un fichero no se puede abrir
				GestorDeErrores.gestionarError(1002, FICHERO_PARSE);
			}
			equip(30);
			break;
		case 31: // bool
			try {
				parse.write(" 43");
			} catch (IOException e) {
				// Envio al gestor de errore el codigo 1002 reservado a error de compilador
				// cuando un fichero no se puede abrir
				GestorDeErrores.gestionarError(1002, FICHERO_PARSE);
			}
			equip(31);
			break;
		case 32: // chars
			try {
				parse.write(" 44");
			} catch (IOException e) {
				// Envio al gestor de errore el codigo 1002 reservado a error de compilador
				// cuando un fichero no se puede abrir
				GestorDeErrores.gestionarError(1002, FICHERO_PARSE);
			}
			equip(32);
			break;
		default:
			GestorDeErrores.gestionarError(3005, null);
		}
	}

	/**
	 * <pre>
	 * 		E -> N E2
	 * </pre>
	 */
	private void e() {
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
			try {
				parse.write(" 45");
			} catch (IOException e) {
				// Envio al gestor de errore el codigo 1002 reservado a error de compilador
				// cuando un fichero no se puede abrir
				GestorDeErrores.gestionarError(1002, FICHERO_PARSE);
			}
			n();
			e2();
			break;
		default:
			GestorDeErrores.gestionarError(3006, null);
		}
	}

	/**
	 * <pre>
	 * 		E2 -> ASIGNACION_OP N E2
	 *		E2 -> lambda
	 * </pre>
	 */
	private void e2() {
		switch (sigToken.getToken()) {
		case 18: // +=
		case 19: // -=
		case 20: // *=
		case 21: // /=
		case 22: // %=
		case 23: // &=
		case 24: // |=
			try {
				parse.write(" 46");
			} catch (IOException e) {
				// Envio al gestor de errore el codigo 1002 reservado a error de compilador
				// cuando un fichero no se puede abrir
				GestorDeErrores.gestionarError(1002, FICHERO_PARSE);
			}
			asignacionOp();
			n();
			e2();
			break;
		default:
			try {
				parse.write(" 47");
			} catch (IOException e) {
				// Envio al gestor de errore el codigo 1002 reservado a error de compilador
				// cuando un fichero no se puede abrir
				GestorDeErrores.gestionarError(1002, FICHERO_PARSE);
			}
			break;
		}
	}

	/**
	 * <pre>
	 * 		N -> G N2
	 * </pre>
	 */
	private void n() {
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
			try {
				parse.write(" 48");
			} catch (IOException e) {
				// Envio al gestor de errore el codigo 1002 reservado a error de compilador
				// cuando un fichero no se puede abrir
				GestorDeErrores.gestionarError(1002, FICHERO_PARSE);
			}
			g();
			n2();
			break;
		default:
			GestorDeErrores.gestionarError(3006, null);
		}
	}

	/**
	 * <pre>
	 * 		N2 -> || G N2
	 * 		N2 -> lambda
	 * </pre>
	 */
	private void n2() {
		switch (sigToken.getToken()) {
		case 13: // ||
			try {
				parse.write(" 49");
			} catch (IOException e) {
				// Envio al gestor de errore el codigo 1002 reservado a error de compilador
				// cuando un fichero no se puede abrir
				GestorDeErrores.gestionarError(1002, FICHERO_PARSE);
			}
			equip(13);
			g();
			n2();
			break;
		default:
			try {
				parse.write(" 50");
			} catch (IOException e) {
				// Envio al gestor de errore el codigo 1002 reservado a error de compilador
				// cuando un fichero no se puede abrir
				GestorDeErrores.gestionarError(1002, FICHERO_PARSE);
			}
			break;
		}
	}

	/**
	 * <pre>
	 * 		G -> D G2
	 * </pre>
	 */
	private void g() {
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
			try {
				parse.write(" 51");
			} catch (IOException e) {
				// Envio al gestor de errore el codigo 1002 reservado a error de compilador
				// cuando un fichero no se puede abrir
				GestorDeErrores.gestionarError(1002, FICHERO_PARSE);
			}
			d();
			g2();
			break;
		default:
			GestorDeErrores.gestionarError(3006, null);
		}
	}

	/**
	 * <pre>
	 * 		G2 -> && D G2
	 *		G2 -> lambda
	 * </pre>
	 */
	private void g2() {
		switch (sigToken.getToken()) {
		case 12: // &&
			try {
				parse.write(" 52");
			} catch (IOException e) {
				// Envio al gestor de errore el codigo 1002 reservado a error de compilador
				// cuando un fichero no se puede abrir
				GestorDeErrores.gestionarError(1002, FICHERO_PARSE);
			}
			equip(12);
			d();
			g2();
			break;
		default:
			try {
				parse.write(" 52");
			} catch (IOException e) {
				// Envio al gestor de errore el codigo 1002 reservado a error de compilador
				// cuando un fichero no se puede abrir
				GestorDeErrores.gestionarError(1002, FICHERO_PARSE);
			}
			break;
		}
	}

	/**
	 * <pre>
	 * 		D -> I D2
	 * </pre>
	 */
	private void d() {
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
			try {
				parse.write(" 53");
			} catch (IOException e) {
				// Envio al gestor de errore el codigo 1002 reservado a error de compilador
				// cuando un fichero no se puede abrir
				GestorDeErrores.gestionarError(1002, FICHERO_PARSE);
			}
			i();
			d2();
			break;
		default:
			GestorDeErrores.gestionarError(3006, null);
		}
	}

	/**
	 * <pre>
	 * 		D2 -> == I D2
	 *		D2 -> != I D2
	 *		D2 -> lambda
	 * </pre>
	 */
	private void d2() {
		switch (sigToken.getToken()) {
		case 6: // ==
			try {
				parse.write(" 54");
			} catch (IOException e) {
				// Envio al gestor de errore el codigo 1002 reservado a error de compilador
				// cuando un fichero no se puede abrir
				GestorDeErrores.gestionarError(1002, FICHERO_PARSE);
			}
			equip(6);
			i();
			d2();
			break;
		case 7: // !=
			try {
				parse.write(" 55");
			} catch (IOException e) {
				// Envio al gestor de errore el codigo 1002 reservado a error de compilador
				// cuando un fichero no se puede abrir
				GestorDeErrores.gestionarError(1002, FICHERO_PARSE);
			}
			equip(7);
			i();
			d2();
			break;
		default:
			try {
				parse.write(" 56");
			} catch (IOException e) {
				// Envio al gestor de errore el codigo 1002 reservado a error de compilador
				// cuando un fichero no se puede abrir
				GestorDeErrores.gestionarError(1002, FICHERO_PARSE);
			}
			break;
		}
	}

	/**
	 * <pre>
	 * 		I -> J I2
	 * </pre>
	 */
	private void i() {
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
			try {
				parse.write(" 57");
			} catch (IOException e) {
				// Envio al gestor de errore el codigo 1002 reservado a error de compilador
				// cuando un fichero no se puede abrir
				GestorDeErrores.gestionarError(1002, FICHERO_PARSE);
			}
			j();
			i2();
			break;
		default:
			GestorDeErrores.gestionarError(3006, null);
		}
	}

	/**
	 * <pre>
	 *		I2 -> > J I2
	 *		I2 -> >= J I2
	 *		I2 -> < J I2
	 *		I2 -> <= J I2
	 *		I2 -> lambda
	 * </pre>
	 */
	private void i2() {
		switch (sigToken.getToken()) {
		case 9: // >
			try {
				parse.write(" 58");
			} catch (IOException e) {
				// Envio al gestor de errore el codigo 1002 reservado a error de compilador
				// cuando un fichero no se puede abrir
				GestorDeErrores.gestionarError(1002, FICHERO_PARSE);
			}
			equip(9);
			j();
			i2();
			break;
		case 11: // >=
			try {
				parse.write(" 59");
			} catch (IOException e) {
				// Envio al gestor de errore el codigo 1002 reservado a error de compilador
				// cuando un fichero no se puede abrir
				GestorDeErrores.gestionarError(1002, FICHERO_PARSE);
			}
			equip(11);
			j();
			i2();
			break;
		case 8: // <
			try {
				parse.write(" 60");
			} catch (IOException e) {
				// Envio al gestor de errore el codigo 1002 reservado a error de compilador
				// cuando un fichero no se puede abrir
				GestorDeErrores.gestionarError(1002, FICHERO_PARSE);
			}
			equip(8);
			j();
			i2();
			break;
		case 10: // <=
			try {
				parse.write(" 61");
			} catch (IOException e) {
				// Envio al gestor de errore el codigo 1002 reservado a error de compilador
				// cuando un fichero no se puede abrir
				GestorDeErrores.gestionarError(1002, FICHERO_PARSE);
			}
			equip(10);
			j();
			i2();
			break;
		default:
			try {
				parse.write(" 62");
			} catch (IOException e) {
				// Envio al gestor de errore el codigo 1002 reservado a error de compilador
				// cuando un fichero no se puede abrir
				GestorDeErrores.gestionarError(1002, FICHERO_PARSE);
			}
			break;
		}
	}

	/**
	 * <pre>
	 * 		J -> M J2
	 * </pre>
	 */
	private void j() {
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
			try {
				parse.write(" 63");
			} catch (IOException e) {
				// Envio al gestor de errore el codigo 1002 reservado a error de compilador
				// cuando un fichero no se puede abrir
				GestorDeErrores.gestionarError(1002, FICHERO_PARSE);
			}
			m();
			j2();
			break;
		default:
			GestorDeErrores.gestionarError(3006, null);
		}
	}

	/**
	 * <pre>
	 * 		J2 -> + M J2
	 *		J2 -> - M J2
	 *		J2 -> lambda
	 * </pre>
	 */
	private void j2() {
		switch (sigToken.getToken()) {
		case 1: // +
			try {
				parse.write(" 64");
			} catch (IOException e) {
				// Envio al gestor de errore el codigo 1002 reservado a error de compilador
				// cuando un fichero no se puede abrir
				GestorDeErrores.gestionarError(1002, FICHERO_PARSE);
			}
			equip(1);
			m();
			j2();
			break;
		case 2: // -
			try {
				parse.write(" 65");
			} catch (IOException e) {
				// Envio al gestor de errore el codigo 1002 reservado a error de compilador
				// cuando un fichero no se puede abrir
				GestorDeErrores.gestionarError(1002, FICHERO_PARSE);
			}
			equip(2);
			m();
			j2();
			break;
		default:
			try {
				parse.write(" 66");
			} catch (IOException e) {
				// Envio al gestor de errore el codigo 1002 reservado a error de compilador
				// cuando un fichero no se puede abrir
				GestorDeErrores.gestionarError(1002, FICHERO_PARSE);
			}
			break;
		}
	}

	/**
	 * <pre>
	 * 		M -> V M2
	 * </pre>
	 */
	private void m() {
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
			try {
				parse.write(" 67");
			} catch (IOException e) {
				// Envio al gestor de errore el codigo 1002 reservado a error de compilador
				// cuando un fichero no se puede abrir
				GestorDeErrores.gestionarError(1002, FICHERO_PARSE);
			}
			v();
			m2();
			break;
		default:
			GestorDeErrores.gestionarError(3006, null);
		}
	}

	/**
	 * <pre>
	 * 		M2 -> * V M2
	 *		M2 -> / V M2
	 *		M2 -> % V M2
	 *		M2 -> lambda
	 * </pre>
	 */
	private void m2() {
		switch (sigToken.getToken()) {
		case 3: // *
			try {
				parse.write(" 68");
			} catch (IOException e) {
				// Envio al gestor de errore el codigo 1002 reservado a error de compilador
				// cuando un fichero no se puede abrir
				GestorDeErrores.gestionarError(1002, FICHERO_PARSE);
			}
			equip(3);
			v();
			m2();
			break;
		case 4: // /
			try {
				parse.write(" 69");
			} catch (IOException e) {
				// Envio al gestor de errore el codigo 1002 reservado a error de compilador
				// cuando un fichero no se puede abrir
				GestorDeErrores.gestionarError(1002, FICHERO_PARSE);
			}
			equip(4);
			v();
			m2();
			break;
		case 5: // %
			try {
				parse.write(" 70");
			} catch (IOException e) {
				// Envio al gestor de errore el codigo 1002 reservado a error de compilador
				// cuando un fichero no se puede abrir
				GestorDeErrores.gestionarError(1002, FICHERO_PARSE);
			}
			equip(5);
			v();
			m2();
			break;
		default:
			try {
				parse.write(" 71");
			} catch (IOException e) {
				// Envio al gestor de errore el codigo 1002 reservado a error de compilador
				// cuando un fichero no se puede abrir
				GestorDeErrores.gestionarError(1002, FICHERO_PARSE);
			}
			break;
		}
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
	 */
	private void v() {
		switch (sigToken.getToken()) {
		case 54: // identificador
			try {
				parse.write(" 72");
			} catch (IOException e) {
				// Envio al gestor de errore el codigo 1002 reservado a error de compilador
				// cuando un fichero no se puede abrir
				GestorDeErrores.gestionarError(1002, FICHERO_PARSE);
			}
			equip(54);
			v2();
			break;
		case 25: // entero
			try {
				parse.write(" 73");
			} catch (IOException e) {
				// Envio al gestor de errore el codigo 1002 reservado a error de compilador
				// cuando un fichero no se puede abrir
				GestorDeErrores.gestionarError(1002, FICHERO_PARSE);
			}
			equip(25);
			break;
		case 26: // cadena
			try {
				parse.write(" 74");
			} catch (IOException e) {
				// Envio al gestor de errore el codigo 1002 reservado a error de compilador
				// cuando un fichero no se puede abrir
				GestorDeErrores.gestionarError(1002, FICHERO_PARSE);
			}
			equip(26);
			break;
		case 27: // true
			try {
				parse.write(" 75");
			} catch (IOException e) {
				// Envio al gestor de errore el codigo 1002 reservado a error de compilador
				// cuando un fichero no se puede abrir
				GestorDeErrores.gestionarError(1002, FICHERO_PARSE);
			}
			equip(27);
			break;
		case 28: // false
			try {
				parse.write(" 76");
			} catch (IOException e) {
				// Envio al gestor de errore el codigo 1002 reservado a error de compilador
				// cuando un fichero no se puede abrir
				GestorDeErrores.gestionarError(1002, FICHERO_PARSE);
			}
			equip(28);
			break;
		case 46: // (
			try {
				parse.write(" 77");
			} catch (IOException e) {
				// Envio al gestor de errore el codigo 1002 reservado a error de compilador
				// cuando un fichero no se puede abrir
				GestorDeErrores.gestionarError(1002, FICHERO_PARSE);
			}
			equip(46); // (
			e();
			equip(47); // )
			break;
		case 15: // ++
		case 16: // --
			try {
				parse.write(" 78");
			} catch (IOException e) {
				// Envio al gestor de errore el codigo 1002 reservado a error de compilador
				// cuando un fichero no se puede abrir
				GestorDeErrores.gestionarError(1002, FICHERO_PARSE);
			}
			incDec();
			equip(54); // identificador
			break;
		case 1: // +
			try {
				parse.write(" 79");
			} catch (IOException e) {
				// Envio al gestor de errore el codigo 1002 reservado a error de compilador
				// cuando un fichero no se puede abrir
				GestorDeErrores.gestionarError(1002, FICHERO_PARSE);
			}
			equip(1);
			v();
			break;
		case 2: // -
			try {
				parse.write(" 80");
			} catch (IOException e) {
				// Envio al gestor de errore el codigo 1002 reservado a error de compilador
				// cuando un fichero no se puede abrir
				GestorDeErrores.gestionarError(1002, FICHERO_PARSE);
			}
			equip(2);
			v();
			break;
		case 14: // !
			try {
				parse.write(" 81");
			} catch (IOException e) {
				// Envio al gestor de errore el codigo 1002 reservado a error de compilador
				// cuando un fichero no se puede abrir
				GestorDeErrores.gestionarError(1002, FICHERO_PARSE);
			}
			equip(14);
			v();
			break;
		default:
			GestorDeErrores.gestionarError(3006, null);
		}
	}

	/**
	 * <pre>
	 * 		V2 -> ( L )
	 * 		V2 -> lambda
	 * 		V2 -> INCDEC
	 * </pre>
	 */
	private void v2() {
		switch (sigToken.getToken()) {
		case 46: // (
			try {
				parse.write(" 82");
			} catch (IOException e) {
				// Envio al gestor de errore el codigo 1002 reservado a error de compilador
				// cuando un fichero no se puede abrir
				GestorDeErrores.gestionarError(1002, FICHERO_PARSE);
			}
			equip(46); // (
			l();
			equip(47); // )
			break;
		default:
			try {
				parse.write(" 83");
			} catch (IOException e) {
				// Envio al gestor de errore el codigo 1002 reservado a error de compilador
				// cuando un fichero no se puede abrir
				GestorDeErrores.gestionarError(1002, FICHERO_PARSE);
			}
			break;
		case 15: // ++
		case 16: // --
			try {
				parse.write(" 84");
			} catch (IOException e) {
				// Envio al gestor de errore el codigo 1002 reservado a error de compilador
				// cuando un fichero no se puede abrir
				GestorDeErrores.gestionarError(1002, FICHERO_PARSE);
			}
			incDec();
			break;
		}
	}

	/**
	 * <pre>
	 * 		L -> E Q
	 * 		L -> lambda
	 * </pre>
	 */
	private void l() {
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
			try {
				parse.write(" 84");
			} catch (IOException e) {
				// Envio al gestor de errore el codigo 1002 reservado a error de compilador
				// cuando un fichero no se puede abrir
				GestorDeErrores.gestionarError(1002, FICHERO_PARSE);
			}
			e();
			q();
			break;
		default:
			try {
				parse.write(" 85");
			} catch (IOException e) {
				// Envio al gestor de errore el codigo 1002 reservado a error de compilador
				// cuando un fichero no se puede abrir
				GestorDeErrores.gestionarError(1002, FICHERO_PARSE);
			}
			break;
		}
	}

	/**
	 * <pre>
	 * 		Q -> , E Q
	 * 		Q -> lambda
	 * </pre>
	 */
	private void q() {
		switch (sigToken.getToken()) {
		case 50: // ,
			try {
				parse.write(" 86");
			} catch (IOException e) {
				// Envio al gestor de errore el codigo 1002 reservado a error de compilador
				// cuando un fichero no se puede abrir
				GestorDeErrores.gestionarError(1002, FICHERO_PARSE);
			}
			equip(50);
			e();
			q();
			break;
		default:
			try {
				parse.write(" 87");
			} catch (IOException e) {
				// Envio al gestor de errore el codigo 1002 reservado a error de compilador
				// cuando un fichero no se puede abrir
				GestorDeErrores.gestionarError(1002, FICHERO_PARSE);
			}
			break;
		}
	}

	/**
	 * <pre>
	 * 		S -> identificador S2
	 * 		S -> INCDEC identificador
	 * 		S -> return X
	 * 		S -> write ( L )
	 * 		S -> prompt ( identificador )
	 * 		S -> break
	 * </pre>
	 */
	private void s() {
		switch (sigToken.getToken()) {
		case 54: // identificador
			try {
				parse.write(" 88");
			} catch (IOException e) {
				// Envio al gestor de errore el codigo 1002 reservado a error de compilador
				// cuando un fichero no se puede abrir
				GestorDeErrores.gestionarError(1002, FICHERO_PARSE);
			}
			equip(54);
			s2();
			break;
		case 15: // ++
		case 16: // --
			try {
				parse.write(" 89");
			} catch (IOException e) {
				// Envio al gestor de errore el codigo 1002 reservado a error de compilador
				// cuando un fichero no se puede abrir
				GestorDeErrores.gestionarError(1002, FICHERO_PARSE);
			}
			incDec();
			equip(54);
			break;
		case 35: // return
			try {
				parse.write(" 90");
			} catch (IOException e) {
				// Envio al gestor de errore el codigo 1002 reservado a error de compilador
				// cuando un fichero no se puede abrir
				GestorDeErrores.gestionarError(1002, FICHERO_PARSE);
			}
			equip(35);
			x();
			break;
		case 33: // write
			try {
				parse.write(" 91");
			} catch (IOException e) {
				// Envio al gestor de errore el codigo 1002 reservado a error de compilador
				// cuando un fichero no se puede abrir
				GestorDeErrores.gestionarError(1002, FICHERO_PARSE);
			}
			equip(33);
			equip(46); // (
			l();
			equip(47); // )
			break;
		case 34: // prompt
			try {
				parse.write(" 92");
			} catch (IOException e) {
				// Envio al gestor de errore el codigo 1002 reservado a error de compilador
				// cuando un fichero no se puede abrir
				GestorDeErrores.gestionarError(1002, FICHERO_PARSE);
			}
			equip(34);
			equip(46); // (
			equip(54); // identificador
			equip(47); // )
			break;
		case 43: // break
			try {
				parse.write(" 93");
			} catch (IOException e) {
				// Envio al gestor de errore el codigo 1002 reservado a error de compilador
				// cuando un fichero no se puede abrir
				GestorDeErrores.gestionarError(1002, FICHERO_PARSE);
			}
			equip(43);
			break;
		default:
			GestorDeErrores.gestionarError(3007, null);
		}
	}

	/**
	 * <pre>
	 * 		S2 -> ASIGNACION E
	 * 		S2 -> ( L )
	 * 		S2 -> INCDEC
	 * </pre>
	 */
	private void s2() {
		switch (sigToken.getToken()) {
		case 17: // =
		case 18: // +=
		case 19: // -=
		case 20: // *=
		case 21: // /=
		case 22: // %=
		case 23: // &=
		case 24: // |=
			try {
				parse.write(" 94");
			} catch (IOException e) {
				// Envio al gestor de errore el codigo 1002 reservado a error de compilador
				// cuando un fichero no se puede abrir
				GestorDeErrores.gestionarError(1002, FICHERO_PARSE);
			}
			asignacion();
			e();
			break;
		case 46: // (
			try {
				parse.write(" 95");
			} catch (IOException e) {
				// Envio al gestor de errore el codigo 1002 reservado a error de compilador
				// cuando un fichero no se puede abrir
				GestorDeErrores.gestionarError(1002, FICHERO_PARSE);
			}
			equip(46); // (
			l();
			equip(47); // )
			break;
		case 15: // ++
		case 16: // --
			try {
				parse.write(" 96");
			} catch (IOException e) {
				// Envio al gestor de errore el codigo 1002 reservado a error de compilador
				// cuando un fichero no se puede abrir
				GestorDeErrores.gestionarError(1002, FICHERO_PARSE);
			}
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
	 */
	private void x() {
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
			try {
				parse.write(" 97");
			} catch (IOException e) {
				// Envio al gestor de errore el codigo 1002 reservado a error de compilador
				// cuando un fichero no se puede abrir
				GestorDeErrores.gestionarError(1002, FICHERO_PARSE);
			}
			e();
			break;
		default:
			try {
				parse.write(" 98");
			} catch (IOException e) {
				// Envio al gestor de errore el codigo 1002 reservado a error de compilador
				// cuando un fichero no se puede abrir
				GestorDeErrores.gestionarError(1002, FICHERO_PARSE);
			}
			break;
		}
	}

	/**
	 * <pre>
	 * 		F -> function H identificador ( A ) { C }
	 * </pre>
	 */
	private void f() {
		if (sigToken.getToken() == 45) { // function
			try {
				parse.write(" 99");
			} catch (IOException e) {
				// Envio al gestor de errore el codigo 1002 reservado a error de compilador
				// cuando un fichero no se puede abrir
				GestorDeErrores.gestionarError(1002, FICHERO_PARSE);
			}
			equip(45);
			h();
			equip(54);
			equip(46);
			a();
			equip(47);
			equip(48);
			c();
			equip(49);
		} else {
			GestorDeErrores.gestionarError(3009, null);
		}
	}

	/**
	 * <pre>
	 * 		A -> T identificador K
	 * 		A -> lambda
	 * </pre>
	 */
	private void a() {
		switch (sigToken.getToken()) {
		case 30: // int
		case 31: // bool
		case 32: // chars
			try {
				parse.write(" 100");
			} catch (IOException e) {
				// Envio al gestor de errore el codigo 1002 reservado a error de compilador
				// cuando un fichero no se puede abrir
				GestorDeErrores.gestionarError(1002, FICHERO_PARSE);
			}
			t();
			equip(54); // identificador
			k();
			break;
		default:
			try {
				parse.write(" 101");
			} catch (IOException e) {
				// Envio al gestor de errore el codigo 1002 reservado a error de compilador
				// cuando un fichero no se puede abrir
				GestorDeErrores.gestionarError(1002, FICHERO_PARSE);
			}
			break;
		}
	}

	/**
	 * <pre>
	 * 		K -> , T identificador K
	 * 		K -> lambda
	 * </pre>
	 */
	private void k() {
		if (sigToken.getToken() == 50) { // ,
			try {
				parse.write(" 102");
			} catch (IOException e) {
				// Envio al gestor de errore el codigo 1002 reservado a error de compilador
				// cuando un fichero no se puede abrir
				GestorDeErrores.gestionarError(1002, FICHERO_PARSE);
			}
			equip(50);
			t();
			equip(54); // identificador
			k();
		} else {
			try {
				parse.write(" 103");
			} catch (IOException e) {
				// Envio al gestor de errore el codigo 1002 reservado a error de compilador
				// cuando un fichero no se puede abrir
				GestorDeErrores.gestionarError(1002, FICHERO_PARSE);
			}
		}
	}

	/**
	 * <pre>
	 * 		C -> B C
	 * 		C -> lambda
	 * </pre>
	 */
	private void c() {
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
			try {
				parse.write(" 104");
			} catch (IOException e) {
				// Envio al gestor de errore el codigo 1002 reservado a error de compilador
				// cuando un fichero no se puede abrir
				GestorDeErrores.gestionarError(1002, FICHERO_PARSE);
			}
			b();
			c();
			break;
		default:
			try {
				parse.write(" 105");
			} catch (IOException e) {
				// Envio al gestor de errore el codigo 1002 reservado a error de compilador
				// cuando un fichero no se puede abrir
				GestorDeErrores.gestionarError(1002, FICHERO_PARSE);
			}
			break;
		}
	}

	/**
	 * <pre>
	 * 		H -> T
	 * 		H -> lambda
	 * </pre>
	 */
	private void h() {
		switch (sigToken.getToken()) {
		case 30: // int
		case 31: // bool
		case 32: // chars
			try {
				parse.write(" 106");
			} catch (IOException e) {
				// Envio al gestor de errore el codigo 1002 reservado a error de compilador
				// cuando un fichero no se puede abrir
				GestorDeErrores.gestionarError(1002, FICHERO_PARSE);
			}
			t();
			break;
		default:
			try {
				parse.write(" 107");
			} catch (IOException e) {
				// Envio al gestor de errore el codigo 1002 reservado a error de compilador
				// cuando un fichero no se puede abrir
				GestorDeErrores.gestionarError(1002, FICHERO_PARSE);
			}
			break;
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
