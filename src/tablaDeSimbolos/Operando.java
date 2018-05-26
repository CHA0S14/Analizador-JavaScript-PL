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
	public static final String FUNC = "function";
	public static final String VAL = "valor";
	public static final String REF = "referencia";

	private String lexema, tipo, tipoRetorno, etiqFuncion;
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
			numParam = 0;
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
		} else if (!tipoRetorno.equals(CHARS) && !tipoRetorno.equals(INT) && !tipoRetorno.equals(BOOL)) {
			GestorDeErrores.gestionarError(1006, tipo);
		}
		this.tipoRetorno = tipoRetorno;
	}

	public String getEtiqFuncion() {
		return etiqFuncion;
	}

	public void setEtiqFuncion(String etiqFuncion) {
		if (!getTipo().equals(FUNC)) {
			GestorDeErrores.gestionarError(1008, TAG);
		}
		this.etiqFuncion = etiqFuncion;
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

	public void addTipoParam(String tipo) {
		if (!getTipo().equals(FUNC)) {
			GestorDeErrores.gestionarError(1008, PARAM);
		} else if (!tipo.equals(CHARS) && !tipo.equals(INT) && !tipo.equals(BOOL)) {
			GestorDeErrores.gestionarError(1006, tipo);
		}
		this.tipoParam.add(tipo);
		numParam++;
	}

	public void addModoParam(String modo) {
		if (!getTipo().equals(FUNC)) {
			GestorDeErrores.gestionarError(1008, PARAM);
		} else if (!modo.equals(VAL) && !modo.equals(REF)) {
			GestorDeErrores.gestionarError(1011, modo);
		}

		if ((tipoParam.get(numParam - 1).equals(INT) || tipoParam.get(numParam - 1).equals(BOOL))
				&& !modo.equals(VAL)) {
			GestorDeErrores.gestionarError(1010, "El tipo es " + tipo + " y el modo no es " + VAL);
		} else if (tipoParam.get(numParam - 1).equals(CHARS) && !modo.equals(REF)) {
			GestorDeErrores.gestionarError(1010, "El tipo es " + tipo + " y el modo no es " + REF);
		}

		this.modoParam.add(modo);
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Operando)) {
			return false;
		}
		Operando op = (Operando) obj;

		boolean response = op.getLexema().equals(this.lexema) && op.getDespl() == this.despl
				&& op.getNumParam() == this.numParam && op.isParam() == this.param;

		if (this.tipo != null) {
			response = response && this.tipo.equals(op.getTipo());
		} else {
			response = response && this.tipo == op.getTipo();
		}

		if (this.tipoRetorno != null) {
			response = response && this.tipoRetorno.equals(op.getTipoRetorno());
		} else {
			response = response && this.tipoRetorno == op.getTipoRetorno();
		}

		if (this.tipoParam != null) {
			response = response && this.tipoParam.equals(op.getTipoParam());
		} else {
			response = response && this.tipoParam == op.getTipoParam();
		}

		if (this.etiqFuncion != null) {
			response = response && this.etiqFuncion.equals(op.getEtiqFuncion());
		} else {
			response = response && this.etiqFuncion == op.getEtiqFuncion();
		}

		if (this.modoParam != null) {
			response = response && this.modoParam.equals(op.getModoParam());
		} else {
			response = response && this.modoParam == op.getModoParam();
		}

		return response;
	}
}
