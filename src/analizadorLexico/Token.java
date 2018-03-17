package analizadorLexico;

/**
 * 
 * @author Ismael Ortega Sanchez
 *
 *         Clase que simboliza un token del analizador lexico
 */
public class Token {
	private int codigo; // Codigo del token
	private int atributo = -1; // Valor del atributo del token
	private String atributoCadena = null; // Lexema de la cadena

	public Token(int token) {
		this.codigo = token;
	}

	public Token(int token, int valorNumerico) {
		this.codigo = token;
		this.atributo = valorNumerico;
	}

	public Token(int token, String cadena) {
		this.codigo = token;
		this.atributoCadena = cadena;
	}

	public int getToken() {
		return codigo;
	}

	public int getAtributo() {
		return atributo;
	}

	public String getAtributoCadena() {
		return atributoCadena;
	}

	@Override
	public String toString() {
		return "<" + codigo + ", " + (atributo != -1 ? atributo : atributoCadena)  + ">";
	}
	
}
