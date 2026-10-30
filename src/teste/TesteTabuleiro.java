package teste;

import modelo.Carta;
import modelo.Jogador;
import modelo.Tabuleiro;

public class TesteTabuleiro {

	private static int falhas = 0;

	public static void main(String[] args) throws Exception {
		Jogador ana = new Jogador("Ana");
		Jogador beto = new Jogador("Beto");
		Carta carta = new Carta("Teste", 1, 2, 3, 4, "NEUTRAL");

		// 1. Tabuleiro novo
		Tabuleiro tabuleiro = new Tabuleiro();
		boolean todasVazias = true;
		
		for (int i = 1; i <= 9; i++) {
			if (!tabuleiro.getCelula(i).estaVazia()) {
				todasVazias = false;
			}
		}
		verificar("Tabuleiro novo começa com as 9 células vazias", todasVazias);
		verificar("Tabuleiro novo não está cheio", !tabuleiro.estaCheio());

		// 2. Colocar numa posição válida
		tabuleiro.colocarCarta(3, carta, ana);
		verificar("Posição 3 fica ocupada", !tabuleiro.getCelula(3).estaVazia());
		verificar("Dono da posição 3 é a Ana", tabuleiro.getCelula(3).getDono() == ana);

		boolean outrasVazias = true;
		for (int i = 1; i <= 9; i++) {
			if (i != 3 && !tabuleiro.getCelula(i).estaVazia()) {
				outrasVazias = false;
			}
		}
		verificar("Só a posição 3 foi ocupada", outrasVazias);

		// 3. Posição já ocupada
		try {
			tabuleiro.colocarCarta(3, carta, beto);
			verificar("Recusa posição ocupada", false);
		} catch (IllegalStateException e) {
			verificar("Recusa posição ocupada", true);
		}
		verificar("Dono da posição 3 continua sendo a Ana", tabuleiro.getCelula(3).getDono() == ana);

		// 4. Posições fora de 1 a 9
		int[] invalidas = { 0, 10, -1 };
		for (int p : invalidas) {
			try {
				tabuleiro.colocarCarta(p, carta, ana);
				verificar("Recusa a posição " + p, false);
			} catch (IllegalArgumentException e) {
				verificar("Recusa a posição " + p, true);
			}
		}

		// 5. Dono nulo
		try {
			tabuleiro.colocarCarta(5, carta, null);
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

		// 7. Captura básica (TT-09)
		testarCaptura();

		System.out.println();
		if (falhas == 0) {
			System.out.println("Todos os testes passaram.");
		} else {
			System.out.println(falhas + " teste(s) falharam.");
		}
	}

	private static void testarCaptura() throws Exception {
		// 7.1 Exemplo do PDF: lado maior captura
		Jogador azul = new Jogador("Azul");
		Jogador verde = new Jogador("Verde");
		Tabuleiro t1 = new Tabuleiro();
		t1.colocarCarta(4, new Carta("Azul", 9, 2, 3, 9, "NEUTRAL"), azul);
		t1.colocarCarta(5, new Carta("Verde", 3, 1, 4, 6, "NEUTRAL"), verde);
		int capturadas = t1.capturar(5);
		verificar("Captura: 6 > 2 retorna 1 captura", capturadas == 1);
		verificar("Captura: posição 4 passa a ser do Verde", t1.getCelula(4).getDono() == verde);
		verificar("Captura: Verde fica com 6 pontos", verde.getPontos() == 6);
		verificar("Captura: Azul fica com 4 pontos", azul.getPontos() == 4);

		// 7.2 Lados iguais não capturam
		Jogador a2 = new Jogador("A");
		Jogador b2 = new Jogador("B");
		Tabuleiro t2 = new Tabuleiro();
		t2.colocarCarta(4, new Carta("A", 1, 5, 1, 1, "NEUTRAL"), a2);
		t2.colocarCarta(5, new Carta("B", 1, 1, 1, 5, "NEUTRAL"), b2);
		verificar("Empate: não captura", t2.capturar(5) == 0);
		verificar("Empate: posição 4 continua do A", t2.getCelula(4).getDono() == a2);
		verificar("Empate: pontos não mudam", a2.getPontos() == 5 && b2.getPontos() == 5);

		// 7.3 Lado menor não captura
		Jogador a3 = new Jogador("A");
		Jogador b3 = new Jogador("B");
		Tabuleiro t3 = new Tabuleiro();
		t3.colocarCarta(2, new Carta("A", 1, 1, 8, 1, "NEUTRAL"), a3);
		t3.colocarCarta(5, new Carta("B", 3, 1, 1, 1, "NEUTRAL"), b3);
		verificar("Lado menor (3 < 8): não captura", t3.capturar(5) == 0);
		verificar("Lado menor: posição 2 continua do A", t3.getCelula(2).getDono() == a3);

		// 7.4 Carta do mesmo dono não é capturada
		Jogador a4 = new Jogador("A");
		Tabuleiro t4 = new Tabuleiro();
		t4.colocarCarta(4, new Carta("A1", 1, 1, 1, 1, "NEUTRAL"), a4);
		t4.colocarCarta(5, new Carta("A2", 9, 9, 9, 9, "NEUTRAL"), a4);
		verificar("Mesmo dono: não captura", t4.capturar(5) == 0);
		verificar("Mesmo dono: pontos não mudam", a4.getPontos() == 5);

		// 7.5 Captura dupla (cima e direita)
		Jogador a5 = new Jogador("A");
		Jogador b5 = new Jogador("B");
		Tabuleiro t5 = new Tabuleiro();
		t5.colocarCarta(2, new Carta("A1", 1, 1, 2, 1, "NEUTRAL"), a5); // baixo = 2
		t5.colocarCarta(6, new Carta("A2", 1, 1, 1, 3, "NEUTRAL"), a5); // esquerda = 3
		t5.colocarCarta(5, new Carta("B", 7, 7, 1, 1, "NEUTRAL"), b5); // cima = 7, direita = 7
		verificar("Captura dupla: retorna 2", t5.capturar(5) == 2);
		verificar("Captura dupla: posições 2 e 6 passam a ser do B",
				t5.getCelula(2).getDono() == b5 && t5.getCelula(6).getDono() == b5);
		verificar("Captura dupla: B fica com 7 e A com 3", b5.getPontos() == 7 && a5.getPontos() == 3);

		// 7.6 Canto do tabuleiro não gera erro
		Jogador a6 = new Jogador("A");
		Jogador b6 = new Jogador("B");
		Tabuleiro t6 = new Tabuleiro();
		t6.colocarCarta(2, new Carta("A", 1, 1, 1, 2, "NEUTRAL"), a6); // esquerda = 2
		t6.colocarCarta(1, new Carta("B", 9, 5, 9, 9, "NEUTRAL"), b6); // direita = 5
		try {
			int n = t6.capturar(1);
			verificar("Canto: captura sem erro de posição", n == 1 && t6.getCelula(2).getDono() == b6);
		} catch (Exception e) {
			verificar("Canto: captura sem erro de posição", false);
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