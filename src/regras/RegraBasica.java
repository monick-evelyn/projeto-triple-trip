package regras;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import modelo.Carta;
import modelo.Celula;
import modelo.Direcao;
import modelo.Jogador;
import modelo.Tabuleiro;

public class RegraBasica implements Regra {

	@Override
	public List<Integer> capturas(Tabuleiro tabuleiro, int posicao, Jogador jogador) throws Exception {
		List<Integer> celulasCapturadas = new ArrayList<Integer>();
		
		Celula celulaAtual = tabuleiro.getCelula(posicao);

		if (celulaAtual.estaVazia()) {
			throw new IllegalStateException("A célula " + posicao + " está vazia.");
		}

		Carta carta = celulaAtual.getCarta();
		Jogador dono = celulaAtual.getDono();
		
		for (Direcao direcao : Direcao.values()) {
			int posicaoVizinha = direcao.getPosicaoVizinha(posicao);
			if (posicaoVizinha != Direcao.FORA) {
				Celula celulaVizinha = tabuleiro.getCelula(posicaoVizinha);
				
				if (!celulaVizinha.estaVazia() && celulaVizinha.getDono() != dono) {
					if (carta.getLado(direcao) > celulaVizinha.getCarta().getLado(direcao.oposta())) {
						celulasCapturadas.add(posicaoVizinha);
					}
				}
			}
		}
		return celulasCapturadas;
	}

	@Override
	public String getNome() {
		return "Básica";
	}

}
