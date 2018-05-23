package tablaDeSimbolos;

import java.util.ArrayList;
import java.util.List;

public class Operando {
	private String lexema, tipo, tipoRetorno, EtiqFuncion;
	private int despl, numParam;
	private boolean param;
	private List<String> tipoParam, modoParam;

	public Operando(String lexema) {
		this.lexema = lexema;
	}

	public String getLexema() {
		return lexema;
	}

	public void setLexema(String lexema) {
		this.lexema = lexema;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		if (tipo.equals(TablaDeSimbolos.FUNC)) {
			tipoParam = new ArrayList<>();
			modoParam = new ArrayList<>();
		}
		this.tipo = tipo;
	}

	public String getTipoRetorno() {
		return tipoRetorno;
	}

	public void setTipoRetorno(String tipoRetorno) {
		this.tipoRetorno = tipoRetorno;
	}

	public String getEtiqFuncion() {
		return EtiqFuncion;
	}

	public void setEtiqFuncion(String etiqFuncion) {
		EtiqFuncion = etiqFuncion;
	}

	public int getDespl() {
		return despl;
	}

	public void setDespl(int despl) {
		this.despl = despl;
	}

	public int getNumParam() {
		return numParam;
	}

	public void setNumParam(int numParam) {
		this.numParam = numParam;
	}

	public boolean isParam() {
		return param;
	}

	public void setParam(boolean param) {
		this.param = param;
	}

	public List<String> getTipoParam() {
		return tipoParam;
	}

	public List<String> getModoParam() {
		return modoParam;
	}

	public void addTipoParam(int posicion, String tipo) {
		this.tipoParam.set(posicion, tipo);
	}

	public void addModoParam(int posicion, String modo) {
		this.modoParam.set(posicion, modo);
	}

}
