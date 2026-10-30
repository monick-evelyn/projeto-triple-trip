package regras;

import java.util.List;

import modelo.Jogador;
import modelo.Tabuleiro;

public interface Regra {
	
	public List<Integer> capturas(Tabuleiro tabuleiro, int posicao, Jogador jogador);
	
	public String getNome();
}
