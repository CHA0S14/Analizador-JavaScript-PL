package analizadorSemantico;

public class Tipo {
	private String tipo;
	private int ancho;

	public Tipo(String tipo, int ancho) {
		this.tipo = tipo;
		this.ancho = ancho;
	}

	public Tipo(String tipo) {
		this.tipo = tipo;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public int getAncho() {
		return ancho;
	}

	public void setAncho(int ancho) {
		this.ancho = ancho;
	}
}