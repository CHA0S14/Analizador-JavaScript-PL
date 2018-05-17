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
	 * 		B -> var T Identificador B2 ID ;
	 * 		B -> if ( E ) IF
	 * 		B -> S ;
	 * 		B -> switch ( E ) { CASE }
	 * 		B -> while ( E ) { C }
	 * 		B -> do { C } while ( E ) ;
	 * 		B -> for ( FOR ; E ; FOR2 ) { C }
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
			initFor();
			equip(51); // ;
			e();
			equip(51); // ;
			endFor();
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
	 * 		B2 -> Op_Asignacion E
	 * 		B2 -> lambda
	 * </pre>
	 */
	private void b2() {
		switch (sigToken.getToken()) {
		case 17: // =
			equip(17);
			e();
			break;
		case 18: // +=
			equip(18);
			e();
			break;
		case 19: // -=
			equip(19);
			e();
			break;
		case 20: // *=
			equip(20);
			e();
			break;
		case 21: // /=
			equip(21);
			e();
			break;
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
		}
	}

	/**
	 * <pre>
	 * 		FOR -> var T FOR4
	 * 		FOR -> FOR4
	 * </pre>
	 */
	private void initFor() {
		switch (sigToken.getToken()) {
		case 29: // var
			equip(29); // var
		case 54: // identificador
			initForCont();
			break;
		default:
			GestorDeErrores.gestionarError(3002, null);
		}
	}

	/**
	 * <pre>
	 * 		FOR4 -> Identificador Op_Asignacion E
	 * </pre>
	 */
	private void initForCont() {
		if (sigToken.getToken() == 54) {
			equip(54); // identificador
			switch (sigToken.getToken()) { // operador de asignacion
			case 17: // =
				equip(17);
				break;
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
			e();
		} else {
			GestorDeErrores.gestionarError(3002, null);
		}
	}

	/**
	 * <pre>
	 * 		FOR2 -> Identificador FOR3
	 * 		FOR2 -> Op_IncDec Identificador
	 * 		FOR2 -> lambda
	 * </pre>
	 */
	private void endFor() {
		switch (sigToken.getToken()) {
		case 54: // Identificador
			equip(54); // Identificador
			endForCont();
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
	 * 		FOR3 -> Op_Asignacion E
	 * 		FOR3 -> Op_IncDec
	 * </pre>
	 */
	private void endForCont() {
		switch (sigToken.getToken()) {
		case 17: // =
			equip(17);
			e();
			break;
		case 18: // +=
			equip(18);
			e();
			break;
		case 19: // -=
			equip(19);
			e();
			break;
		case 20: // *=
			equip(20);
			e();
			break;
		case 21: // /=
			equip(21);
			e();
			break;
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
	 * 		ID -> , Identificador B2 ID
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
	 * 		CASE -> case CTE_Entera : CASE3 C1 CASE2
	 * </pre>
	 */
	private void bloqueCase() {
		if (sigToken.getToken() == 42) {
			equip(42); // case
			equip(25); // entero
			equip(52); // :
			case3();
			c1();
			case2();
		} else {
			GestorDeErrores.gestionarError(3004, null);
		}
	}

	/**
	 * <pre>
	 * 		CASE2 -> CASE
	 * 		CASE2 -> DEFAULT
	 * </pre>
	 */
	private void case2() {
		switch (sigToken.getToken()) {
		case 42: // case
			bloqueCase();
			break;
		case 44: // default
			bloqueDefault();
			break;
		}
	}

	/**
	 * <pre>
	 * 		CASE3 -> case CTE_Entera : CASE3
	 * 		CASE3 -> lambda
	 * </pre>
	 */
	private void case3() {
		if (sigToken.getToken() == 42) { // case
			equip(42); // case
			equip(25); // entero
			equip(52); // :
			case3();
		}
	}

	/**
	 * <pre>
	 * 		DEFAULT -> default : C
	 * 		DEFAULT -> lambda
	 * </pre>
	 */
	private void bloqueDefault() {
		if (sigToken.getToken() == 44) {
			equip(44); // default
			equip(52); // :
			c();
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
	 * 		E -> OPNEG R E2
	 * </pre>
	 */
	private void e() {
		switch (sigToken.getToken()) {
		case 14: // !
			opneg();
		case 54: // identificador
		case 25: // entero
		case 26: // cadena
		case 27: // true
		case 28: // false
		case 46: // (
		case 15: // ++
		case 16: // --
			r();
			e2();
			break;
		default:
			GestorDeErrores.gestionarError(3006, null);
		}
	}

	/**
	 * <pre>
	 * 		E2 -> Op_Logico R E2
	 * 		E2 -> lambda
	 * </pre>
	 */
	private void e2() {
		switch (sigToken.getToken()) {
		case 12: // &&
			equip(12);
			r();
			e2();
			break;
		case 13: // ||
			equip(13);
			r();
			e2();
			break;
		}
	}

	/**
	 * <pre>
	 * 		OPNEG -> Op_Logico R E2
	 * 		OPNEG -> lambda
	 * </pre>
	 */
	private void opneg() {
		if (sigToken.getToken() == 14) { // !
			equip(14); // !
		}
	}

	/**
	 * <pre>
	 * 		R -> U R2
	 * </pre>
	 */
	private void r() {
		switch (sigToken.getToken()) {
		case 54: // identificador
		case 25: // entero
		case 26: // cadena
		case 27: // true
		case 28: // false
		case 46: // (
		case 15: // ++
		case 16: // --
			u();
			r2();
			break;
		default:
			GestorDeErrores.gestionarError(3006, null);
		}
	}

	/**
	 * <pre>
	 * 		R2 -> Op_Relaccion U R2
	 * 		R2 -> lambda
	 * </pre>
	 */
	private void r2() {
		switch (sigToken.getToken()) {
		case 6: // ==
			equip(6);
			u();
			r2();
			break;
		case 7: // !=
			equip(7);
			u();
			r2();
			break;
		case 8: // <
			equip(8);
			u();
			r2();
			break;
		case 9: // >
			equip(9);
			u();
			r2();
			break;
		case 10: // <=
			equip(10);
			u();
			r2();
			break;
		case 11: // >=
			equip(11);
			u();
			r2();
			break;
		}
	}

	/**
	 * <pre>
	 * 		U -> V U2
	 * </pre>
	 */
	private void u() {
		switch (sigToken.getToken()) {
		case 54: // identificador
		case 25: // entero
		case 26: // cadena
		case 27: // true
		case 28: // false
		case 46: // (
		case 15: // ++
		case 16: // --
			v();
			u2();
			break;
		default:
			GestorDeErrores.gestionarError(3006, null);
		}
	}

	/**
	 * <pre>
	 * 		U2 -> Op_Aritmetico V U2
	 * 		U2 -> lambda
	 * </pre>
	 */
	private void u2() {
		switch (sigToken.getToken()) {
		case 1: // +
			equip(1);
			v();
			u2();
			break;
		case 2: // -
			equip(2);
			v();
			u2();
			break;
		case 3: // *
			equip(3);
			v();
			u2();
			break;
		case 4: // /
			equip(4);
			v();
			u2();
			break;
		case 5: // %
			equip(5);
			v();
			u2();
			break;
		}
	}

	/**
	 * <pre>
	 * 		V -> Identificador V2
	 * 		V -> CTE_Entera
	 * 		V -> cadena
	 * 		V -> true
	 * 		V -> false
	 * 		V -> ( E )
	 * 		V -> Op_IncDec Identificador
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
			equip(15); // ++
			equip(54); // identificador
			break;
		case 16: // --
			equip(16); // --
			equip(54); // identificador
			break;
		default:
			GestorDeErrores.gestionarError(3007, null);
		}
	}

	/**
	 * <pre>
	 * 		V2 -> ( L )
	 * 		V2 -> Op_IncDec
	 * 		V2 -> lambda
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
			equip(15);
			break;
		case 16: // --
			equip(16);
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
	 * 		S -> Identificador S2
	 * 		S -> Op_IncDec Identificador
	 * 		S -> return X
	 * 		S -> write ( L )
	 * 		S -> prompt ( Identificador )
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
			equip(15);
			equip(54);
			break;
		case 16: // --
			equip(16);
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
	 * 		S2 -> Op_Asignacion E
	 * 		S2 -> ( L )
	 * 		S2 -> Op_IncDec
	 * </pre>
	 */
	private void s2() {
		switch (sigToken.getToken()) {
		case 17: // =
			equip(17);
			e();
			break;
		case 18: // +=
			equip(18);
			e();
			break;
		case 19: // -=
			equip(19);
			e();
			break;
		case 20: // *=
			equip(20);
			e();
			break;
		case 21: // /=
			equip(21);
			e();
			break;
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
		case 46: // (
			equip(46); // (
			l();
			equip(47); // )
			break;
		case 15: // ++
			equip(15);
			break;
		case 16: // --
			equip(16);
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
			e();
			break;
		}
	}

	/**
	 * <pre>
	 * 		F -> function H Identificador ( A ) { C }
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
	 * 		A -> T Identificador K
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
	 * 		K -> , T Identificador K
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
	 * 		C1 -> B C
	 * </pre>
	 */
	private void c1() {
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
		default:
			GestorDeErrores.gestionarError(3001, null);
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
			GestorDeErrores.gestionarError(3011, null);
		}
	}
}
