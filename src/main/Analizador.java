package main;

import analizadorLexico.AnalizadorLexico;
import analizadorSintactico.AnalizadorSintactico;
import gestorDeErrores.GestorDeErrores;
import tablaDeSimbolos.TablaDeSimbolos;

public class Analizador {

	public static void main(String[] args) {
		if (args.length < 1)
			// Se envia un error 1001 reservado cuando no se han recibido suficientes
			// argumentos
			GestorDeErrores.gestionarError(1001, null);
		new Analizador(args[0]);
		
		System.out.println("Analisis finalizado correctamente");
	}

	public Analizador(String fichero) {
		AnalizadorLexico analizadorLexico = new AnalizadorLexico(fichero);
		AnalizadorSintactico analizadorSintactico = new AnalizadorSintactico(analizadorLexico);
		
		analizadorSintactico.p();
		
		analizadorLexico.close();
		analizadorSintactico.close();
		TablaDeSimbolos.close();
		
	}
}
