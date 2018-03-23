package main;

import analizadorLexico.AnalizadorLexico;
import analizadorLexico.Token;
import gestorDeErrores.GestorDeErrores;

public class Analizador {
	private AnalizadorLexico analizadorLexico;

	public static void main(String[] args) {
		if (args.length < 1)
			// Se envia un error 1001 reservado cuando no se han recibido suficientes
			// argumentos
			GestorDeErrores.gestionarError(1001);
		new Analizador(args[0]);
	}

	private void init() {
		Token token = new Token(1);
		while (token.getToken() != 52) {
			token = analizadorLexico.getToken();
			System.out.println(token);
			if (token == null)
				token = new Token(1);
		}
	}

	public Analizador(String fichero) {
		analizadorLexico = new AnalizadorLexico(fichero);
		init();
	}
}
