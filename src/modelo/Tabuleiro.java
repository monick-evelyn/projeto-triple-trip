package modelo;

import java.util.LinkedHashMap;
import java.util.Map;

public class Tabuleiro {
	private static final int TAMANHO = 3;
	private Celula[][] celulas = new Celula[TAMANHO][TAMANHO];

	public Tabuleiro() {
		for (int i = 0; i < TAMANHO; i++) {
			for (int j = 0; j < TAMANHO; j++) {
				celulas[i][j] = new Celula();
			}
		}
	}

	public void colocarCarta(int posicao, Carta carta, Jogador dono) {
		posicaoValida(posicao);

		Celula celula = getCelula(posicao);

		celula.ocupar(carta, dono);
	}

	public boolean posicaoValida(int posicao) {
		if (posicao < 1 || posicao > 9) {
			throw new IllegalArgumentException("Posição deve estar entre 1 e 9.");
		}
		return true;
	}

	public Celula getCelula(int posicao) {
		int linha = linha(posicao);
		int coluna = coluna(posicao);

		Celula celula = celulas[linha][coluna];

		return celula;
	}

	private Celula getVizinha(int posicao, Direcao direcao) {
		int linha = linha(posicao) + direcao.getDeslocamentoLinha();
		int coluna = coluna(posicao) + direcao.getDeslocamentoColuna();

		if (linha < 0 || linha > (TAMANHO - 1) || coluna < 0 || coluna > (TAMANHO - 1)) {
			return null; // saiu do tabuleiro
		}
		return celulas[linha][coluna];
	}

	public Map<Direcao, Celula> getVizinhas(int posicao) {
		Map<Direcao, Celula> vizinhas = new LinkedHashMap<>();

		for (Direcao direcao : Direcao.values()) {
			Celula vizinha = getVizinha(posicao, direcao);
			if (vizinha != null && !vizinha.estaVazia()) {
				vizinhas.put(direcao, vizinha);
			}
		}
		return vizinhas;
	}

	public boolean estaCheio() {
		int contador = 0;
		for (int i = 1; i < 10; i++) {
			Celula celula = getCelula(i);
			if (celula != null && !celula.estaVazia()) {
				contador++;
			}
		}

		if (contador == 9) {
			return true;
		}
		return false;
	}

	public int contarCelulas(Jogador jogador) {
		int contador = 0;
		for (int i = 1; i < 10; i++) {
			Celula celula = getCelula(i);
			if (celula != null && celula.getDono().equals(jogador)) {
				contador++;
			}
		}

		return contador;
	}

	private int linha(int posicao) {
		int linha = (posicao - 1) / 3;
		return linha;
	}

	private int coluna(int posicao) {
		int coluna = (posicao - 1) % 3;
		return coluna;
	}

	// Captura Básica
	public int capturar(int posicao) throws Exception {
		Celula celulaAtual = getCelula(posicao);

		if (celulaAtual.estaVazia() || celulaAtual == null) {
			throw new Exception("A célula está vazia.");
		}

		Carta carta = celulaAtual.getCarta();
		Jogador dono = celulaAtual.getDono();
		
		if (carta == null || dono == null) {
			throw new Exception("A célula está incompleta.");
		}
		
		int celulasCapturadas = 0;
		Map<Direcao, Celula> celulasVizinhas = getVizinhas(posicao);

		for (Map.Entry<Direcao, Celula> entrada : celulasVizinhas.entrySet()) {
			Direcao direcao = entrada.getKey();
			Celula vizinha = entrada.getValue();
			
			if (vizinha.getDono() != dono) {
				int ladoCarta  = carta.getLado(direcao); //lado direito
				int ladoVizinha = vizinha.getCarta().getLado(direcao.oposta()); //lado esquerdo

				if (ladoCarta > ladoVizinha) {
					Jogador antigoDono = vizinha.getDono();
					
					vizinha.trocarDono(dono);
					
					dono.ganharPonto();
					antigoDono.perderPonto();
					
					celulasCapturadas++;
				}
			}
		}
		return celulasCapturadas;
	}

}
