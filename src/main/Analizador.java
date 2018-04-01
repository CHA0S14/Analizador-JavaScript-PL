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
			GestorDeErrores.gestionarError(1001, null);
		new Analizador(args[0]);
	}

	private void init() {
		Token token = new Token(1);
		while (token.getToken() != 53) {
			token = analizadorLexico.getToken();
		}
	}

	public Analizador(String fichero) {
		analizadorLexico = new AnalizadorLexico(fichero);
		init();
	}
}
