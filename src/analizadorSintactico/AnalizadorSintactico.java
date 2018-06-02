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
			b();
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
			writeParse(4);
			equip(29); // var
			t();
			equip(54); // identificador
			b2();
			id();
			equip(51); // ;
			break;
		case 36: // if
			writeParse(5);
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
			writeParse(6);
			s();
			equip(51); // ;
			break;
		case 41: // switch
			writeParse(7);
			equip(41); // switch
			equip(46); // (
			e();
			equip(47); // )
			equip(48); // {
			bloqueCase();
			equip(49); // }
			break;
		case 38: // while
			writeParse(8);
			equip(38); // while
			equip(46); // (
			e();
			equip(47); // )
			equip(48); // {
			c();
			equip(49); // }
			break;
		case 39: // do
			writeParse(9);
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
			writeParse(10);
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
			writeParse(11);
			asignacion();
			e();
			break;
		default:
			writeParse(12);
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
			writeParse(13);
			equip(54); // identificador
			asignacion();
			e();
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
		switch (sigToken.getToken()) {
		case 54: // identificador
			writeParse(15);
			equip(54); // identificador
			actualizacion2();
			break;
		case 15: // ++
		case 16: // --
			writeParse(16);
			incDec();
			equip(54); // identificador
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
			writeParse(18);
			asignacion();
			e();
			break;
		case 15: // ++
		case 16: // --
			writeParse(19);
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
	 */
	private void asignacion() {
		switch (sigToken.getToken()) {
		case 17: // =
			writeParse(22);
			equip(17);
			break;
		case 18: // +=
		case 19: // -=
		case 20: // *=
		case 21: // /=
		case 22: // %=
		case 23: // &=
		case 24: // |=
			writeParse(23);
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
			writeParse(24);
			equip(18);
			break;
		case 19: // -=
			writeParse(25);
			equip(19);
			break;
		case 20: // *=
			writeParse(26);
			equip(20);
			break;
		case 21: // /=
			writeParse(27);
			equip(21);
			break;
		case 22: // %=
			writeParse(28);
			equip(22);
			break;
		case 23: // &=
			writeParse(29);
			equip(23);
			break;
		case 24: // |=
			writeParse(30);
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
			writeParse(31);
			equip(48); // {
			c();
			equip(49); // }
			bloqueElse();
			break;
		case 54: // identificador
		case 15: // ++
		case 16: // --
		case 35: // return
		case 33: // write
		case 34: // prompt
		case 43: // break
			writeParse(32);
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
			writeParse(33);
			equip(37); // else
			equip(48); // {
			c();
			equip(49); // }
			break;
		default:
			writeParse(34);
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
			writeParse(35);
			equip(50); // ,
			equip(54); // identificador
			b2();
			id();
		} else {
			writeParse(36);
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
			writeParse(37);
			equip(42); // case
			equip(25); // entero
			equip(52); // :
			c();
			bloqueCase();
			break;
		case 44: // default
			writeParse(38);
			equip(44); // default
			equip(52); // :
			c();
			bloqueCase2();
			break;
		default:
			writeParse(39);
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
			writeParse(40);
			equip(42); // case
			equip(25); // entero
			equip(52); // :
			c();
			bloqueCase2();
			break;
		default:
			writeParse(41);
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
			writeParse(42);
			equip(30);
			break;
		case 31: // bool
			writeParse(43);
			equip(31);
			break;
		case 32: // chars
			writeParse(44);
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
			writeParse(45);
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
			writeParse(46);
			asignacionOp();
			n();
			e2();
			break;
		default:
			writeParse(47);
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
			writeParse(48);
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
			writeParse(49);
			equip(13);
			g();
			n2();
			break;
		default:
			writeParse(50);
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
			writeParse(51);
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
			writeParse(52);
			equip(12);
			d();
			g2();
			break;
		default:
			writeParse(53);
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
			writeParse(54);
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
			writeParse(55);
			equip(6);
			i();
			d2();
			break;
		case 7: // !=
			writeParse(56);
			equip(7);
			i();
			d2();
			break;
		default:
			writeParse(57);
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
			writeParse(58);
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
			writeParse(59);
			equip(9);
			j();
			i2();
			break;
		case 11: // >=
			writeParse(60);
			equip(11);
			j();
			i2();
			break;
		case 8: // <
			writeParse(61);
			equip(8);
			j();
			i2();
			break;
		case 10: // <=
			writeParse(62);
			equip(10);
			j();
			i2();
			break;
		default:
			writeParse(63);
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
			writeParse(64);
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
			writeParse(65);
			equip(1);
			m();
			j2();
			break;
		case 2: // -
			writeParse(66);
			equip(2);
			m();
			j2();
			break;
		default:
			writeParse(67);
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
			writeParse(68);
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
			writeParse(69);
			equip(3);
			v();
			m2();
			break;
		case 4: // /
			writeParse(70);
			equip(4);
			v();
			m2();
			break;
		case 5: // %
			writeParse(71);
			equip(5);
			v();
			m2();
			break;
		default:
			writeParse(72);
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
			writeParse(73);
			equip(54);
			v2();
			break;
		case 25: // entero
			writeParse(74);
			equip(25);
			break;
		case 26: // cadena
			writeParse(75);
			equip(26);
			break;
		case 27: // true
			writeParse(76);
			equip(27);
			break;
		case 28: // false
			writeParse(77);
			equip(28);
			break;
		case 46: // (
			writeParse(78);
			equip(46); // (
			e();
			equip(47); // )
			break;
		case 15: // ++
		case 16: // --
			writeParse(79);
			incDec();
			equip(54); // identificador
			break;
		case 1: // +
			writeParse(80);
			equip(1);
			v();
			break;
		case 2: // -
			writeParse(81);
			equip(2);
			v();
			break;
		case 14: // !
			writeParse(82);
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
			writeParse(83);
			equip(46); // (
			l();
			equip(47); // )
			break;
		default:
			writeParse(84);
			break;
		case 15: // ++
		case 16: // --
			writeParse(85);
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
			writeParse(86);
			e();
			q();
			break;
		default:
			writeParse(87);
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
			writeParse(88);
			equip(50);
			e();
			q();
			break;
		default:
			writeParse(89);
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
			writeParse(90);
			equip(54);
			s2();
			break;
		case 15: // ++
		case 16: // --
			writeParse(91);
			incDec();
			equip(54);
			break;
		case 35: // return
			writeParse(92);
			equip(35);
			x();
			break;
		case 33: // write
			writeParse(93);
			equip(33);
			equip(46); // (
			l();
			equip(47); // )
			break;
		case 34: // prompt
			writeParse(94);
			equip(34);
			equip(46); // (
			equip(54); // identificador
			equip(47); // )
			break;
		case 43: // break
			writeParse(95);
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
			writeParse(96);
			asignacion();
			e();
			break;
		case 46: // (
			writeParse(97);
			equip(46); // (
			l();
			equip(47); // )
			break;
		case 15: // ++
		case 16: // --
			writeParse(98);
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
			writeParse(99);
			e();
			break;
		default:
			writeParse(100);
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
			writeParse(101);
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
			writeParse(102);
			t();
			equip(54); // identificador
			k();
			break;
		default:
			writeParse(103);
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
			writeParse(104);
			equip(50);
			t();
			equip(54); // identificador
			k();
		} else {
			writeParse(105);
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
			writeParse(106);
			b();
			c();
			break;
		default:
			writeParse(107);
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
			writeParse(108);
			t();
			break;
		default:
			writeParse(109);
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
