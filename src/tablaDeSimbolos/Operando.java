package tablaDeSimbolos;

import java.util.ArrayList;
import java.util.List;

import gestorDeErrores.GestorDeErrores;

public class Operando {
	public static final String TIPO = "tipo";
	public static final String DESP = "desplazamiento";
	public static final String PARAM = "parametro";
	public static final String RET = "retorno";
	public static final String TAG = "etiqueta";
	public static final String CHARS = "chars";
	public static final String BOOL = "bool";
	public static final String INT = "int";
	public static final String FUNC = "funcion";

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
		if (tipo.equals(FUNC)) {
			tipoParam = new ArrayList<>();
			modoParam = new ArrayList<>();
		} else if (!tipo.equals(CHARS) && !tipo.equals(INT) && !tipo.equals(BOOL) && !tipo.equals(FUNC)) {
			GestorDeErrores.gestionarError(1006, tipo);
		}

		this.tipo = tipo;
	}

	public String getTipoRetorno() {
		return tipoRetorno;
	}

	public void setTipoRetorno(String tipoRetorno) {
		if (!getTipo().equals(FUNC)) {
			GestorDeErrores.gestionarError(1008, RET);
		} else if (!tipo.equals(CHARS) && !tipo.equals(INT) && !tipo.equals(BOOL)) {
			GestorDeErrores.gestionarError(1006, tipo);
		}
		this.tipoRetorno = tipoRetorno;
	}

	public String getEtiqFuncion() {
		return EtiqFuncion;
	}

	public void setEtiqFuncion(String etiqFuncion) {
		if (!getTipo().equals(FUNC)) {
			GestorDeErrores.gestionarError(1008, TAG);
		}
		EtiqFuncion = etiqFuncion;
	}

	public int getDespl() {
		return despl;
	}

	public void setDespl(int despl) {
		if (getTipo().equals(FUNC)) {
			GestorDeErrores.gestionarError(1007, null);
		}
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
		if (!getTipo().equals(FUNC)) {
			GestorDeErrores.gestionarError(1008, PARAM);
		} else if (!tipo.equals(CHARS) && !tipo.equals(INT) && !tipo.equals(BOOL)) {
			GestorDeErrores.gestionarError(1006, tipo);
		}
		this.tipoParam.set(posicion, tipo);
	}

	public void addModoParam(int posicion, String modo) {
		if (!getTipo().equals(FUNC)) {
			GestorDeErrores.gestionarError(1008, PARAM);
		} else if (!tipo.equals(CHARS) && !tipo.equals(INT) && !tipo.equals(BOOL)) {
			GestorDeErrores.gestionarError(1006, tipo);
		}
		this.modoParam.set(posicion, modo);
	}

}
