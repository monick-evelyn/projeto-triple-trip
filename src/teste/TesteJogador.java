package teste;

import modelo.Jogador;

import java.util.ArrayList;
import java.util.List;

import modelo.Carta;
import modelo.Direcao;

public class TesteJogador {

	public static void main(String[] args) {
		Jogador clarissa = new Jogador("Clarissa");
		Jogador elyson = new Jogador("Elyson");

		List<Carta> baralho = new ArrayList<>();

		for (int i = 1; i <= 10; i++) {
			Carta carta = new Carta("Carta" + i, 1, 2, 3, 4);
			baralho.add(carta);
		}

		Jogador.distribuirCartas(baralho, clarissa, elyson);

		System.out.println("Nome: " + clarissa.getNome() + " Pontos: " + clarissa.getPontos());
		for (Carta carta : clarissa.getMao()) {
			System.out.println(carta.getNome());
		}

		System.out.println("Nome: " + elyson.getNome() + " Pontos: " + elyson.getPontos());
		for (Carta carta : elyson.getMao()) {
			System.out.println(carta.getNome());
		}

		System.out.println("Teste jogar carta");
		Carta jogada = clarissa.jogarCarta(-1);
		for (Carta carta : clarissa.getMao()) {
			System.out.println(carta.getNome());
		}
	}

}
