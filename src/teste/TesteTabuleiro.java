package teste;

import modelo.Carta;
import modelo.Jogador;
import modelo.Tabuleiro;

public class TesteTabuleiro {

	private static int falhas = 0;

	public static void main(String[] args) {
		Jogador ana = new Jogador("Ana");
		Jogador beto = new Jogador("Beto");
		Carta carta = new Carta("Teste", 1, 2, 3, 4, "NEUTRAL");

		// 1. Tabuleiro novo
		Tabuleiro t = new Tabuleiro();
		boolean todasVazias = true;
		for (int p = 1; p <= 9; p++) {
			if (!t.getCelula(p).estaVazia()) {
				todasVazias = false;
			}
		}
		verificar("Tabuleiro novo começa com as 9 células vazias", todasVazias);
		verificar("Tabuleiro novo não está cheio", !t.estaCheio());

		// 2. Colocar numa posição válida
		t.colocarCarta(3, carta, ana);
		verificar("Posição 3 fica ocupada", !t.getCelula(3).estaVazia());
		verificar("Dono da posição 3 é a Ana", t.getCelula(3).getDono() == ana);

		boolean outrasVazias = true;
		for (int p = 1; p <= 9; p++) {
			if (p != 3 && !t.getCelula(p).estaVazia()) {
				outrasVazias = false;
			}
		}
		verificar("Só a posição 3 foi ocupada", outrasVazias);

		// 3. Posição já ocupada
		try {
			t.colocarCarta(3, carta, beto);
			verificar("Recusa posição ocupada", false);
		} catch (IllegalStateException e) {
			verificar("Recusa posição ocupada", true);
		}
		verificar("Dono da posição 3 continua sendo a Ana", t.getCelula(3).getDono() == ana);

		// 4. Posições fora de 1 a 9
		int[] invalidas = { 0, 10, -1 };
		for (int p : invalidas) {
			try {
				t.colocarCarta(p, carta, ana);
				verificar("Recusa a posição " + p, false);
			} catch (IllegalArgumentException e) {
				verificar("Recusa a posição " + p, true);
			}
		}

		// 5. Dono nulo
		try {
			t.colocarCarta(5, carta, null);
			verificar("Recusa dono nulo", false);
		} catch (IllegalArgumentException e) {
			verificar("Recusa dono nulo", true);
		}

		// 6. Tabuleiro cheio e contagem
		Tabuleiro cheio = new Tabuleiro();
		for (int p = 1; p <= 8; p++) {
			cheio.colocarCarta(p, carta, ana);
		}
		verificar("Com 8 cartas, não está cheio", !cheio.estaCheio());

		cheio.colocarCarta(9, carta, beto);
		verificar("Com 9 cartas, está cheio", cheio.estaCheio());
		verificar("Ana tem 8 células", cheio.contarCelulas(ana) == 8);
		verificar("Beto tem 1 célula", cheio.contarCelulas(beto) == 1);

		System.out.println();
		if (falhas == 0) {
			System.out.println("Todos os testes passaram.");
		} else {
			System.out.println(falhas + " teste(s) falharam.");
		}
	}

	private static void verificar(String descricao, boolean passou) {
		if (passou) {
			System.out.println("[OK] " + descricao);
		} else {
			System.out.println("[FALHOU] " + descricao);
			falhas++;
		}
	}
}