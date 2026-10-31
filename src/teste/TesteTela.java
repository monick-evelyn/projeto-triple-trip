package teste;

import modelo.Carta;
import modelo.Jogador;
import modelo.Tabuleiro;
import terminal.Tela;

import java.util.ArrayList;
import java.util.List;

import jogo.ConfiguracoesRegras;

public class TesteTela {

	public static void main(String[] args) {
		List <Carta> baralho=new ArrayList<>();
		
		baralho.add(new Carta("Mago", 8, 5, 7, 3));
	    baralho.add(new Carta("Guerreiro", 6, 4, 8, 2));
	    baralho.add(new Carta("Arqueiro", 5, 7, 3, 9));
	    baralho.add(new Carta("Dragao", 9, 6, 8, 4));
	    baralho.add(new Carta("Cavaleiro", 7, 5, 6, 8));
	    baralho.add(new Carta("Feiticeira", 4, 8, 5, 7));
	    baralho.add(new Carta("Ladino", 3, 6, 9, 5));
	    baralho.add(new Carta("Paladino", 8, 7, 4, 6));
	    baralho.add(new Carta("Golem", 6, 9, 5, 3));
	    baralho.add(new Carta("Fenix", 7, 4, 8, 9));

	    
		Tabuleiro tabuleiro=new Tabuleiro();
		
		Jogador j1=new Jogador("Clarissa");
		Jogador j2=new Jogador("Elyson");
		
		Jogador.distribuirCartas(baralho, j1, j2);
		Carta carta1=j2.getMao().get(0);
		Carta carta2=j1.getMao().get(2);
		
		tabuleiro.colocarCarta(2, carta1, j2);
		tabuleiro.colocarCarta(5, carta2, j1);
		
		//Tela.desenharTabuleiro(tabuleiro, j1, j2);
		
		//Tela.desenharMao(j2);
		//Tela.desenharMaoOponente(j2,true);
		//Tela.desenharMaoOponente(j2, false);
		//Tela.limpar();
		//Tela.desenharPlacar(j1, j2);
		//Tela.mostrarMenu();
		//Tela.telaDeTroca(j2);
		//ConfiguracoesRegras regras1=new ConfiguracoesRegras();
		//Tela.mostrarRegras(regras1);
		//List<String> regras = new ArrayList<>();
		//regras.add("Same");
		//regras.add("Plus");

		//Tela.mostrarJogada("Ana", 2, regras, 1);
		Tela.mostrarResultado(tabuleiro, j1, j2);

		
		
	}

}
