package terminal;

import java.io.FileNotFoundException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import modelo.Carta;

public class LeitorCSV {
	
	private static final int MINIMO_CARTAS = 10;

	public static List<Carta> ler(String caminho) throws FileNotFoundException {
		String linha;
		String csvSeparador = ";";

		List<String> linhas = lerLinhas(caminho);
		List<Carta> cartas = new ArrayList<>();

		// pula o cabeçalho (id;name;ranks;element)
		int contador = 1;
		
		while (contador < linhas.size()){
			try {
				linha = linhas.get(contador).trim();
				cartas.add(converterLinha(linha));
				contador++;
			} catch (RuntimeException e) {
				System.out.println("Aviso: linha " + (contador) + " ignorada (" + e.getMessage() + ")");
			}
		}
		

		if (cartas.size() < MINIMO_CARTAS) {
			System.out.println(
					"Erro: o CSV tem " + cartas.size() + 
					" cartas válidas; são necessárias " + MINIMO_CARTAS + ".");
			System.exit(1);
		}
		return cartas;

	}

	private static List<String> lerLinhas(String caminho) {
		try {
			return Files.readAllLines(Path.of(caminho), StandardCharsets.UTF_16);
		} catch (NoSuchFileException e) {
			System.out.println("Erro: arquivo de cartas não encontrado em " + caminho);
		} catch (Exception e) {
			System.out.println("Erro ao ler " + caminho + ": " + e.getMessage());
		}
		System.exit(1);
		return List.of(); // nunca chega aqui
	}

	private static Carta converterLinha(String linha) {
		String csvSeparador = ";";
		String[] colunas = linha.split(csvSeparador);

		if (colunas.length < 4) {
			throw new IllegalArgumentException("Esperava 4 colunas, veio " + colunas.length);
		}

		String nome = colunas[1].trim();
		String tipo = colunas[3].trim();

		int cima = -1, baixo = -1, esquerda = -1, direita = -1;
		String lados = colunas[2].replace("[", "").replace("]", ""); // retira []

		for (String par : lados.split(",")) {
			String[] chaveValor = par.split(":");

			if (chaveValor.length != 2) {
				throw new IllegalArgumentException("lado não está no padrão detectável: " + par);
			}

			int valor = converterValor(chaveValor[1]);

			switch (chaveValor[0].trim().toUpperCase()) {
			case "UP":
				cima = valor;
				break;

			case "DOWN":
				baixo = valor;
				break;

			case "LEFT":
				esquerda = valor;
				break;

			case "RIGHT":
				direita = valor;
				break;

			default:
				throw new IllegalArgumentException("lado desconhecido: " + chaveValor[0]);
			}
		}

		if (cima < 0 || baixo < 0 || esquerda < 0 || direita < 0) {
			throw new IllegalArgumentException("Faltou algum dos 4 lados");
		}
		return new Carta(nome, cima, baixo, esquerda, direita, tipo);
	}

	private static int converterValor(String texto) {
		if (texto.trim().equalsIgnoreCase("A")) {
			return 10;
		}
		return Integer.parseInt(texto);
	}
}
