package test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import analizadorLexico.AnalizadorLexico;
import analizadorLexico.Token;

public class TestAnalizadorLéxico {
	private static final String CARPETA_PROGRAMAS_LEXICO = "Programas/Test/AnalizadorLexico/";
	private static final String CARPETA_RESULTADOS_LEXICO = "Resultados/Test/AnalizadorLexico/";
	private static final String SEPARATOR = "***************************************************";

	public static void main(String[] args) {
		iniciar();
	}

	public static void iniciar() {
		boolean error = false;
		if (!test1())
			error = true;

		if (error) {
			System.err.println("Todos los test del analizador lexico no han sido satisfactorios");
		} else {
			System.out.println();
			System.out.println(SEPARATOR);
			System.out.println("     Todos los test han acabado correctamente");
			System.out.println(SEPARATOR);
		}
	}

	private static boolean test1() {

		System.out.println("Probando que el analizador lexico es capaz de detectar todos los tokens");
		
		AnalizadorLexico analizador = new AnalizadorLexico(
				Paths.get(CARPETA_PROGRAMAS_LEXICO, "DetectaTodosTokens.javascript").toString());

		String[] tokens = getTokens(analizador);
		String[] resultado = getResultados("DetectaTodosTokens");

		boolean result = Arrays.equals(tokens, resultado);
		
		if(result)
			System.out.println("  --> El analizador lexico es capaz de identificar todos los tipos de tokens");
		else
			System.err.println("  --> El analizador lexico no ha sido capaz de identificar todos los tipos de tokens");
		
		System.out.println(SEPARATOR);
		return result;
	}

	private static String[] getTokens(AnalizadorLexico analizador) {
		List<String> tokens = new ArrayList<>();

		Token token = analizador.getToken();
		tokens.add(token.toString());
		while (token.getToken() != 53) {
			token = analizador.getToken();
			tokens.add(token.toString());
		}

		return tokens.toArray(new String[] {});
	}

	private static String[] getResultados(String fichero) {
		List<String> result = new ArrayList<>();

		try (BufferedReader br = new BufferedReader(
				new FileReader(Paths.get(CARPETA_RESULTADOS_LEXICO, fichero).toFile()))) {
			String line;
			while ((line = br.readLine()) != null) {
				result.add(line);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result.toArray(new String[] {});
	}
}
