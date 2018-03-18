package main;

import analizadorLexico.AnalizadorLexico;
import analizadorLexico.Token;

public class Analizador {
	private AnalizadorLexico analizadorLexico;
	
	public static void main(String[] args) {
		new Analizador(args[0]);
	}
	
	private void init() {
		Token token = new Token(1);
		while (token.getToken() != 52) {
			token = analizadorLexico.getToken();
			System.out.println(token);
			if(token == null)
				token = new Token(1);
		}
	}

	public Analizador(String fichero) {
		analizadorLexico = new AnalizadorLexico(fichero);
		init();
	}
}
