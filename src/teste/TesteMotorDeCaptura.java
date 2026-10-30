package teste;

import java.util.ArrayList;
import java.util.List;

import jogo.ConfiguracoesRegras;
import modelo.Carta;
import modelo.Jogador;
import modelo.Tabuleiro;
import regras.MotorDeCaptura;
import regras.Regra;

public class TesteMotorDeCaptura {

	private static int falhas = 0;

	public static void main(String[] args) throws Exception {
		testarBasica();
		testarVizinhas();
		testarDuasRegras();
		testarCombo();

		System.out.println();
		if (falhas == 0) {
			System.out.println("Todos os testes passaram.");
		} else {
			System.out.println(falhas + " teste(s) falharam.");
		}
	}

	private static ConfiguracoesRegras configuracao(boolean combo) {
		ConfiguracoesRegras config = new ConfiguracoesRegras();
		config.setCombo(combo);
		return config;
	}

	// Mesmo cenário da captura básica do TesteTabuleiro
	private static void testarBasica() throws Exception {
		Jogador azul = new Jogador("Azul");
		Jogador verde = new Jogador("Verde");
		Tabuleiro t = new Tabuleiro();
		t.colocarCarta(4, new Carta("Azul", 9, 2, 3, 9, "NEUTRAL"), azul);
		t.colocarCarta(5, new Carta("Verde", 3, 1, 4, 6, "NEUTRAL"), verde);

		List<Integer> viraram = new MotorDeCaptura(configuracao(false)).aplicar(t, 5, verde);

		verificar("Básica: 6 > 2 captura a posição 4", viraram.equals(List.of(4)));
		verificar("Básica: posição 4 passa a ser do Verde", t.getCelula(4).getDono() == verde);
		verificar("Básica: Verde 6 e Azul 4", verde.getPontos() == 6 && azul.getPontos() == 4);
	}

	private static void testarVizinhas() throws Exception {
		Jogador a = new Jogador("A");
		Jogador b = new Jogador("B");
		Tabuleiro t = new Tabuleiro();
		t.colocarCarta(4, new Carta("B1", 1, 1, 1, 1, "NEUTRAL"), b);
		t.colocarCarta(5, new Carta("B2", 9, 9, 9, 9, "NEUTRAL"), b);

		List<Integer> viraram = new MotorDeCaptura(configuracao(true)).aplicar(t, 5, b);

		verificar("Vizinhas vazias não geram erro e mesmo dono não é capturado", viraram.isEmpty());
		verificar("Mesmo dono: pontos não mudam", a.getPontos() == 5 && b.getPontos() == 5);
	}

	private static void testarDuasRegras() throws Exception {
		Jogador azul = new Jogador("Azul");
		Jogador verde = new Jogador("Verde");
		Tabuleiro t = new Tabuleiro();
		t.colocarCarta(4, new Carta("Azul", 9, 2, 3, 9, "NEUTRAL"), azul);
		t.colocarCarta(5, new Carta("Verde", 3, 1, 4, 6, "NEUTRAL"), verde);

		// Regra de teste que captura a mesma carta que a básica
		ConfiguracoesRegras config = new ConfiguracoesRegras() {
			@Override
			public List<Regra> regrasAtivas() {
				List<Regra> regras = new ArrayList<>();
				regras.add(new Regra() {
					@Override
					public List<Integer> capturas(Tabuleiro tabuleiro, int posicao, Jogador jogador) {
						return List.of(4);
					}

					@Override
					public String getNome() {
						return "Repetida";
					}
				});
				return regras;
			}
		};
		config.setCombo(false);

		MotorDeCaptura motor = new MotorDeCaptura(config);
		List<Integer> viraram = motor.aplicar(t, 5, verde);

		verificar("Duas regras: carta conta uma vez só", viraram.equals(List.of(4)));
		verificar("Duas regras: ponto conta uma vez só", verde.getPontos() == 6 && azul.getPontos() == 4);
		verificar("Duas regras: regra ativada é registrada", motor.getRegrasAtivadas().equals(List.of("Repetida")));
	}

	private static void testarCombo() throws Exception {
		// Sem combo: só a captura direta
		Jogador a1 = new Jogador("A");
		Jogador b1 = new Jogador("B");
		Tabuleiro t1 = montarCadeia(a1, b1);
		MotorDeCaptura semCombo = new MotorDeCaptura(configuracao(false));
		verificar("Sem combo: só a posição 4 vira", semCombo.aplicar(t1, 5, b1).equals(List.of(4)));
		verificar("Sem combo: posição 1 continua do A", t1.getCelula(1).getDono() == a1);

		// Com combo: a carta capturada em 4 captura a posição 1
		Jogador a2 = new Jogador("A");
		Jogador b2 = new Jogador("B");
		Tabuleiro t2 = montarCadeia(a2, b2);
		MotorDeCaptura comCombo = new MotorDeCaptura(configuracao(true));
		verificar("Combo: posições 4 e 1 viram", comCombo.aplicar(t2, 5, b2).equals(List.of(4, 1)));
		verificar("Combo: 1 captura em cadeia", comCombo.getCapturasEmCadeia() == 1);
		verificar("Combo: B 7 e A 3", b2.getPontos() == 7 && a2.getPontos() == 3);

		// Dois caminhos da cadeia chegam na posição 1: conta uma vez só
		Jogador a3 = new Jogador("A");
		Jogador b3 = new Jogador("B");
		Tabuleiro t3 = new Tabuleiro();
		t3.colocarCarta(1, new Carta("Alvo", 1, 1, 1, 1, "NEUTRAL"), a3);
		t3.colocarCarta(2, new Carta("Cima", 1, 1, 1, 9, "NEUTRAL"), a3);
		t3.colocarCarta(4, new Carta("Esq", 9, 1, 1, 1, "NEUTRAL"), a3);
		t3.colocarCarta(5, new Carta("Centro", 5, 1, 1, 5, "NEUTRAL"), b3);
		MotorDeCaptura doisCaminhos = new MotorDeCaptura(configuracao(true));
		verificar("Dois caminhos: 3 cartas viram", doisCaminhos.aplicar(t3, 5, b3).size() == 3);
		verificar("Dois caminhos: B 8 e A 2", b3.getPontos() == 8 && a3.getPontos() == 2);
	}

	private static Tabuleiro montarCadeia(Jogador a, Jogador b) {
		Tabuleiro t = new Tabuleiro();
		t.colocarCarta(1, new Carta("Alvo", 1, 1, 1, 1, "NEUTRAL"), a);
		t.colocarCarta(4, new Carta("Elo", 9, 1, 1, 1, "NEUTRAL"), a);
		t.colocarCarta(5, new Carta("Atacante", 1, 1, 1, 5, "NEUTRAL"), b);
		return t;
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
