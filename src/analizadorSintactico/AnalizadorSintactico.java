package analizadorSintactico;

import analizadorLexico.AnalizadorLexico;
import analizadorLexico.Token;
import gestorDeErrores.GestorDeErrores;

public class AnalizadorSintactico {
	private Token sigToken = null;
	private AnalizadorLexico lexico = null;

	public AnalizadorSintactico(AnalizadorLexico lexico) {
		this.lexico = lexico;
		sigToken = lexico.getToken();
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
			b();
			p();
			break;
		case 45: // function
			f();
			p();
			break;
		case 53: // EOF
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
			equip(29); // var
			t();
			equip(54); // identificador
			b2();
			id();
			equip(51); // ;
			break;
		case 36: // if
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
			s();
			equip(51); // ;
			break;
		case 41: // switch
			equip(41); // switch
			equip(46); // (
			e();
			equip(47); // )
			equip(48); // {
			bloqueCase();
			equip(49); // }
			break;
		case 38: // while
			equip(38); // while
			equip(46); // (
			e();
			equip(47); // )
			equip(48); // {
			c();
			equip(49); // }
			break;
		case 39: // do
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
			asignacion();
			e();
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
			equip(54); // identificador
			asignacion();
			e();
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
			equip(54); // identificador
			actualizacion2();
			break;
		case 15: // ++
			equip(15); // ++
			equip(54); // identificador
			break;
		case 16: // --
			equip(15); // --
			equip(54); // identificador
			break;
		}
	}

	/**
	 * <pre>
	 * 		ACTUALIZACION2 -> Op_Asignacion E
	 * 		ACTUALIZACION2 -> Op_IncDec
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
			equip(22);
			e();
			break;
		case 23: // &=
			equip(23);
			e();
			break;
		case 24: // |=
			equip(24);
			e();
			break;
		case 15: // ++
			equip(15);
			break;
		case 16: // --
			equip(16);
			break;
		default:
			GestorDeErrores.gestionarError(3003, null);
		}
	}

	private void incDec() {
		switch (sigToken.getToken()) {
		case 15: // ++
			equip(15);
			break;
		case 16: // --
			equip(16);
			break;
		default:
			// TODO Gestor de errores
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
			equip(17);
			break;
		case 18: // +=
		case 19: // -=
		case 20: // *=
		case 21: // /=
		case 22: // %=
		case 23: // &=
		case 24: // |=
			asignacionOp();
			break;
		default:
			// TODO Gestor de errores
		}
	}

	private void asignacionOp() {
		switch (sigToken.getToken()) {
		case 18: // +=
			equip(18);
			break;
		case 19: // -=
			equip(19);
			break;
		case 20: // *=
			equip(20);
			break;
		case 21: // /=
			equip(21);
			break;
		case 22: // %=
			equip(22);
			break;
		case 23: // &=
			equip(23);
			break;
		case 24: // |=
			equip(24);
			break;
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
			equip(37); // else
			equip(48); // {
			c();
			equip(49); // }
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
			equip(50); // ,
			equip(54); // identificador
			b2();
			id();
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
			equip(42); // case
			equip(25); // entero
			equip(52); // :
			c();
			bloqueCase();
			break;
		case 44: // default
			equip(44); // default
			equip(52); // :
			c();
			bloqueCase2();
			break;
		default:
			// TODO Gestor de errores
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
			equip(42); // case
			equip(25); // entero
			equip(52); // :
			c();
			bloqueCase2();
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
			equip(30);
			break;
		case 31: // bool
			equip(31);
			break;
		case 32: // chars
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
			n();
			e2();
			break;
		default:
			// TODO Gestor de errores
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
			asignacionOp();
			n();
			e2();
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
			g();
			n2();
			break;
		default:
			// TODO Gestor de errores
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
			equip(13);
			g();
			n2();
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
			d();
			g2();
			break;
		default:
			// TODO Gestor de errores
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
			equip(12);
			d();
			g2();
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
			i();
			d2();
			break;
		default:
			// TODO Gestor de errores
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
			equip(6);
			i();
			d2();
			break;
		case 7: // !=
			equip(7);
			i();
			d2();
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
			j();
			i2();
			break;
		default:
			// TODO Gestor de errores
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
			equip(9);
			j();
			i2();
			break;
		case 11: // >=
			equip(11);
			j();
			i2();
			break;
		case 8: // <
			equip(8);
			j();
			i2();
			break;
		case 10:
			equip(10);
			j();
			i2();
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
			m();
			j2();
			break;
		default:
			// TODO Gestor de errores
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
			equip(1);
			m();
			j2();
			break;
		case 2: // -
			equip(2);
			m();
			j2();
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
			v();
			m2();
			break;
		default:
			// TODO Gestor de errores
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
			equip(3);
			v();
			m2();
			break;
		case 4:
			equip(4);
			v();
			m2();
			break;
		case 5:
			equip(5);
			v();
			m2();
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
			equip(54);
			v2();
			break;
		case 25: // entero
			equip(25);
			break;
		case 26: // cadena
			equip(26);
			break;
		case 27: // true
			equip(27);
			break;
		case 28: // false
			equip(28);
			break;
		case 46: // (
			equip(46); // (
			e();
			equip(47); // )
			break;
		case 15: // ++
		case 16: // --
			incDec();
			equip(54); // identificador
			break;
		case 1: // +
			equip(1);
			v();
			break;
		case 2: // -
			equip(2);
			v();
			break;
		case 14: // !
			equip(14);
			v();
			break;
		default:
			GestorDeErrores.gestionarError(3007, null);
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
			equip(46); // (
			l();
			equip(47); // )
			break;
		case 15: // ++
		case 16: // --
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
			e();
			q();
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
			equip(50);
			e();
			q();
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
			equip(54);
			s2();
			break;
		case 15: // ++
		case 16: // --
			incDec();
			equip(54);
			break;
		case 35: // return
			equip(35);
			x();
			break;
		case 33: // write
			equip(33);
			equip(46); // (
			l();
			equip(47); // )
			break;
		case 34: // prompt
			equip(34);
			equip(46); // (
			equip(54); // identificador
			equip(47); // )
			break;
		case 43:
			equip(43);
			break;
		default:
			GestorDeErrores.gestionarError(3008, null);
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
			asignacion();
			e();
			break;
		case 46: // (
			equip(46); // (
			l();
			equip(47); // )
			break;
		case 15: // ++
		case 16: // --
			incDec();
			break;
		default:
			GestorDeErrores.gestionarError(3009, null);
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
			e();
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
			GestorDeErrores.gestionarError(3010, null);
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
			t();
			equip(54); // identificador
			k();
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
			equip(50);
			t();
			equip(54); // identificador
			k();
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
			b();
			c();
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
			t();
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
			GestorDeErrores.gestionarError(3011, sigToken.getToken() + "");
		}
	}
}
