package tablaDeSimbolos;

import java.util.ArrayList;
import java.util.List;

import analizadorLexico.AnalizadorLexico;
import analizadorSemantico.AnalizadorSemantico;
import gestorDeErrores.GestorDeErrores;

public class Entrada {
	public static final String TIPO = "tipo";
	public static final String DESP = "desplazamiento";
	public static final String PARAM = "parametro";
	public static final String RET = "retorno";
	public static final String TAG = "etiqueta";
	public static final String CHARS = "chars";
	public static final String BOOL = "bool";
	public static final String INT = "int";
	public static final String VOID = "void";
	public static final String FUNC = "function";
	public static final boolean VAL = false;
	public static final boolean REF = true;
	public static final String IS_PARAM = "isParam";

	// Atributos de la entrada
	private String lexema, tipo = AnalizadorSemantico.TIPO_VACIO, tipoRetorno, etiqFuncion;
	private int desp;
	private boolean param;
	private List<String> tipoParam;
	private List<Boolean> modoParam;

	public Entrada(String lexema) {
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
		if (tipo.equals(FUNC)) { // Si el tipo es funcion se inican los datos correspondientes a la funcion
			tipoParam = new ArrayList<>();
			modoParam = new ArrayList<>();
		} else if (!tipo.equals(CHARS) && !tipo.equals(INT) && !tipo.equals(BOOL) && !tipo.equals(FUNC)) {
			GestorDeErrores.gestionarError(1006, "Tipo " + tipo);
		}

		this.tipo = tipo;
	}

	public String getTipoRetorno() {
		return tipoRetorno;
	}

	public void setTipoRetorno(String tipoRetorno) {
		// Solo se puede poner tipo de retorno a las funciones
		if (!this.tipo.equals(FUNC)) {
			GestorDeErrores.gestionarError(1008, RET);
			// Compruebo que el tipo introducido es valido
		} else if (!tipoRetorno.equals(CHARS) && !tipoRetorno.equals(INT) && !tipoRetorno.equals(BOOL)
				&& !tipoRetorno.equals(VOID)) {
			GestorDeErrores.gestionarError(1006, "Tipo retorno " + tipo + AnalizadorLexico.getNlinea());
		}
		this.tipoRetorno = tipoRetorno;
	}

	public String getEtiqFuncion() {
		return etiqFuncion;
	}

	public void setEtiqFuncion(String etiqFuncion) {
		// Solo se puede aniadir una etiqueta si el tipo de la entrada es una funcion
		if (!this.tipo.equals(FUNC)) {
			GestorDeErrores.gestionarError(1008, TAG);
		}
		this.etiqFuncion = etiqFuncion;
	}

	public int getDespl() {
		return desp;
	}

	public void setDespl(int desp) {
		// Si el tipo es funcion no se le puede aniadir ancho
		if (this.tipo.equals(FUNC)) {
			GestorDeErrores.gestionarError(1007, null);
		}
		this.desp = desp;
	}

	public int getNumParam() {
		return tipoParam.size();
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

	public List<Boolean> getModoParam() {
		return modoParam;
	}

	public void addTipoParam(String tipo) {
		// Solo se le pueden aniadir parametros a las funciones
		if (!getTipo().equals(FUNC)) {
			GestorDeErrores.gestionarError(1008, PARAM);
			// Se comprueba que los tipo son validos
		} else if (!tipo.equals(CHARS) && !tipo.equals(INT) && !tipo.equals(BOOL)) {
			GestorDeErrores.gestionarError(1006, "Tipo de parametro " + tipo);
		}
		this.tipoParam.add(tipo);
	}

	public void addModoParam(boolean modo) {
		// Solo tienen parametros las funciones
		if (!getTipo().equals(FUNC)) {
			GestorDeErrores.gestionarError(1008, PARAM);
		}

		this.modoParam.add(modo);
	}

	@Override
	/**
	 * Dos entradas son iguales cuando los atributos son iguales
	 */
	public boolean equals(Object obj) {
		if (!(obj instanceof Entrada)) {
			return false;
		}
		Entrada op = (Entrada) obj;

		boolean response = op.getLexema().equals(this.lexema) && op.getDespl() == this.desp
				&& op.isParam() == this.param;

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
