package analizadorLexico;

import java.io.File;

public class AnalizadorLexico {
	private File fichero;
	private int estado = 0;
	
	public AnalizadorLexico(String fichero) {
		this.fichero = new File(fichero);
	}
	
	public int[] getToken() {
		return null;
	}
}
