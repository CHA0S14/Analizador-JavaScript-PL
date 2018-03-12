package analizadorLexico;

/**
 * 
 * @author Ismael Ortega Sanchez
 *
 *         Clase que simboliza un token del analizador lexico
 */
public class Token {
	private int token; // Valor codificado del token
	private int valorNumerico = -1; // Valor numerico del token si procede
	private String valorCadena = null; // Valor de cadena del token si procede
	
	public Token(int token) {
		super();
		this.token = token;
	}

	public Token(int token, int valorNumerico) {
		this.token = token;
		this.valorNumerico = valorNumerico;
	}

	public Token(int token, String cadena) {
		this.token = token;
		this.valorCadena = cadena;
	}

	public int getToken() {
		return token;
	}

	public int getValorNumerico() {
		return valorNumerico;
	}

	public String getValorCadena() {
		return valorCadena;
	}
}
