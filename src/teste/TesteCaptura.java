package teste;

import java.util.List;

import jogo.ConfiguracoesRegras;
import modelo.Carta;
import modelo.Jogador;
import modelo.Tabuleiro;
import regras.MotorDeCaptura;
import regras.Regra;
import regras.RegraBasica;

public class TesteCaptura {

	private static int falhas = 0;

	public static void main(String[] args) throws Exception {
		testarRegraBasica();
		testarMotorSemRegrasOpcionais();
		testarMotorComRegras();
		testarMotorComCombo();

		System.out.println();
		if (falhas == 0) {
			System.out.println("Todos os testes passaram.");
		} else {
			System.out.println(falhas + " teste(s) falharam.");
		}
	}

	private static void testarRegraBasica() throws Exception {
		Regra basica = new RegraBasica();
		Jogador monick = new Jogador("Monick");
		Jogador clarissa = new Jogador("Clarissa");

		verificar("Básica: nome da regra", basica.getNome().equals("Básica"));

		// 1. Lado maior captura, mas a regra só informa: quem troca o dono é o motor
		Tabuleiro tabuleiro1 = new Tabuleiro();
		tabuleiro1.colocarCarta(4, carta(9, 2, 3, 9), monick);
		tabuleiro1.colocarCarta(5, carta(3, 1, 4, 6), clarissa);
		verificar("Básica: 6 > 2 captura a posição 4", basica.capturas(tabuleiro1, 5, clarissa).equals(List.of(4)));
		verificar("Básica: não troca o dono", tabuleiro1.getCelula(4).getDono() == monick);
		verificar("Básica: não muda os pontos", monick.getPontos() == 5 && clarissa.getPontos() == 5);

		// 2. Lados iguais
		Tabuleiro tabuleiro2 = new Tabuleiro();
		tabuleiro2.colocarCarta(4, carta(1, 5, 1, 1), monick);
		tabuleiro2.colocarCarta(5, carta(1, 1, 1, 5), clarissa);
		verificar("Básica: empate não captura", basica.capturas(tabuleiro2, 5, clarissa).isEmpty());

		// 3. Lado menor
		Tabuleiro tabuleiro3 = new Tabuleiro();
		tabuleiro3.colocarCarta(2, carta(1, 1, 8, 1), monick);
		tabuleiro3.colocarCarta(5, carta(3, 1, 1, 1), clarissa);
		verificar("Básica: lado menor não captura", basica.capturas(tabuleiro3, 5, clarissa).isEmpty());

		// 4. Mesmo dono, mesmo com lado maior
		Tabuleiro tabuleiro4 = new Tabuleiro();
		tabuleiro4.colocarCarta(4, carta(1, 1, 1, 1), clarissa);
		tabuleiro4.colocarCarta(5, carta(9, 9, 9, 9), clarissa);
		verificar("Básica: mesmo dono não é capturado", basica.capturas(tabuleiro4, 5, clarissa).isEmpty());

		// 5. Vizinhas vazias
		Tabuleiro tabuleiro5 = new Tabuleiro();
		tabuleiro5.colocarCarta(5, carta(9, 9, 9, 9), clarissa);
		verificar("Básica: vizinhas vazias não geram erro", basica.capturas(tabuleiro5, 5, clarissa).isEmpty());

		// 6. Quatro direções
		Tabuleiro tabuleiro6 = new Tabuleiro();
		tabuleiro6.colocarCarta(2, carta(1, 1, 2, 1), monick);
		tabuleiro6.colocarCarta(8, carta(2, 1, 1, 1), monick);
		tabuleiro6.colocarCarta(4, carta(1, 2, 1, 1), monick);
		tabuleiro6.colocarCarta(6, carta(1, 1, 1, 2), monick);
		tabuleiro6.colocarCarta(5, carta(5, 5, 5, 5), clarissa);
		verificar("Básica: captura nas quatro direções", basica.capturas(tabuleiro6, 5, clarissa).equals(List.of(2, 8, 4, 6)));

		// 7. Cada direção compara o lado certo
		Tabuleiro tabuleiro7 = new Tabuleiro();
		tabuleiro7.colocarCarta(2, carta(1, 1, 5, 1), monick);
		tabuleiro7.colocarCarta(6, carta(1, 1, 1, 5), monick);
		tabuleiro7.colocarCarta(5, carta(9, 1, 1, 1), clarissa);
		verificar("Básica: só a direção de lado maior captura", basica.capturas(tabuleiro7, 5, clarissa).equals(List.of(2)));

		// 8. Cantos
		Tabuleiro tabuleiro8 = new Tabuleiro();
		tabuleiro8.colocarCarta(2, carta(1, 1, 1, 2), monick);
		tabuleiro8.colocarCarta(1, carta(9, 5, 9, 9), clarissa);
		verificar("Básica: canto 1 não sai do tabuleiro", basica.capturas(tabuleiro8, 1, clarissa).equals(List.of(2)));

		Tabuleiro tabuleiro9 = new Tabuleiro();
		tabuleiro9.colocarCarta(8, carta(1, 2, 1, 1), monick);
		tabuleiro9.colocarCarta(9, carta(9, 9, 9, 5), clarissa);
		verificar("Básica: canto 9 não sai do tabuleiro", basica.capturas(tabuleiro9, 9, clarissa).equals(List.of(8)));

		// 9. Célula sem carta
		try {
			basica.capturas(new Tabuleiro(), 5, clarissa);
			verificar("Básica: recusa célula vazia", false);
		} catch (IllegalStateException e) {
			verificar("Básica: recusa célula vazia", true);
		}
	}

	private static void testarMotorSemRegrasOpcionais() throws Exception {
		// 1. Só a básica: troca o dono e atualiza os pontos
		Jogador ana = new Jogador("Ana");
		Jogador bea = new Jogador("Bea");
		Tabuleiro tabuleiro1 = new Tabuleiro();
		tabuleiro1.colocarCarta(4, carta(9, 2, 3, 9), ana);
		tabuleiro1.colocarCarta(5, carta(3, 1, 4, 6), bea);
		MotorDeCaptura motor1 = new MotorDeCaptura(configuracao(false));
		verificar("Motor: retorna a posição capturada", motor1.aplicar(tabuleiro1, 5, bea).equals(List.of(4)));
		verificar("Motor: troca o dono", tabuleiro1.getCelula(4).getDono() == bea);
		verificar("Motor: B 6 e A 4", bea.getPontos() == 6 && ana.getPontos() == 4);
		verificar("Motor: básica não entra nas regras ativadas", motor1.getRegrasAtivadas().isEmpty());

		// 2. Nenhuma captura
		Jogador a2 = new Jogador("Aninha");
		Jogador b2 = new Jogador("Beatriz");
		Tabuleiro tabuleiro2 = new Tabuleiro();
		tabuleiro2.colocarCarta(4, carta(1, 9, 1, 1), a2);
		tabuleiro2.colocarCarta(5, carta(1, 1, 1, 2), b2);
		MotorDeCaptura motor2 = new MotorDeCaptura(configuracao(true));
		verificar("Motor: sem captura retorna lista vazia", motor2.aplicar(tabuleiro2, 5, b2).isEmpty());
		verificar("Motor: sem captura não muda os pontos", a2.getPontos() == 5 && b2.getPontos() == 5);

		// 3. Célula sem carta
		try {
			motor2.aplicar(new Tabuleiro(), 5, b2);
			verificar("Motor: recusa célula vazia", false);
		} catch (IllegalStateException e) {
			verificar("Motor: recusa célula vazia", true);
		}
	}

	private static void testarMotorComRegras() {
		// 1. Duas regras capturam a mesma carta
		Jogador a1 = new Jogador("Maria");
		Jogador b1 = new Jogador("João");
		Tabuleiro tabuleiro1 = new Tabuleiro();
		tabuleiro1.colocarCarta(4, carta(9, 2, 3, 9), a1);
		tabuleiro1.colocarCarta(5, carta(3, 1, 4, 6), b1);
		MotorDeCaptura motor1 = new MotorDeCaptura(configuracao(false, regra("Repetida", 4)));
		verificarSemErro("Motor: carta de duas regras conta uma vez só", () -> {
			return motor1.aplicar(tabuleiro1, 5, b1).equals(List.of(4))
					&& b1.getPontos() == 6 && a1.getPontos() == 4
					&& motor1.getRegrasAtivadas().equals(List.of("Repetida"));
		});

		// 2. Regra captura além da básica
		Jogador a2 = new Jogador("A");
		Jogador b2 = new Jogador("B");
		Tabuleiro tabuleiro2 = new Tabuleiro();
		tabuleiro2.colocarCarta(4, carta(9, 2, 3, 9), a2);
		tabuleiro2.colocarCarta(8, carta(9, 9, 9, 9), a2);
		tabuleiro2.colocarCarta(5, carta(3, 1, 1, 6), b2);
		MotorDeCaptura motor2 = new MotorDeCaptura(configuracao(false, regra("Extra", 8)));
		verificarSemErro("Motor: soma as capturas da regra com as da básica", () -> {
			return motor2.aplicar(tabuleiro2, 5, b2).equals(List.of(4, 8))
					&& tabuleiro2.getCelula(8).getDono() == b2
					&& b2.getPontos() == 7 && a2.getPontos() == 3;
		});

		// 3. Regra sem captura não aparece como ativada
		Jogador a3 = new Jogador("A");
		Jogador b3 = new Jogador("B");
		Tabuleiro tabuleiro3 = new Tabuleiro();
		tabuleiro3.colocarCarta(4, carta(9, 2, 3, 9), a3);
		tabuleiro3.colocarCarta(5, carta(3, 1, 4, 6), b3);
		MotorDeCaptura motor3 = new MotorDeCaptura(configuracao(false, regra("Vazia"), regra("Ativa", 4)));
		verificarSemErro("Motor: registra só a regra que capturou", () -> {
			motor3.aplicar(tabuleiro3, 5, b3);
			return motor3.getRegrasAtivadas().equals(List.of("Ativa"));
		});

		// 4. Regra aponta uma carta do próprio jogador antes de uma carta adversária
		Jogador a4 = new Jogador("A");
		Jogador b4 = new Jogador("B");
		Tabuleiro tabuleiro4 = new Tabuleiro();
		tabuleiro4.colocarCarta(6, carta(9, 9, 9, 9), b4);
		tabuleiro4.colocarCarta(8, carta(9, 9, 9, 9), a4);
		tabuleiro4.colocarCarta(5, carta(1, 1, 1, 1), b4);
		MotorDeCaptura motor4 = new MotorDeCaptura(configuracao(false, regra("Mista", 6, 8)));
		verificarSemErro("Motor: carta do próprio jogador não conta como captura", () -> {
			return motor4.aplicar(tabuleiro4, 5, b4).equals(List.of(8))
					&& b4.getPontos() == 6 && a4.getPontos() == 4;
		});
	}

	private static void testarMotorComCombo() throws Exception {
		// 1. Combo desligado: para na captura direta
		Jogador a1 = new Jogador("A");
		Jogador b1 = new Jogador("B");
		Tabuleiro t1 = montarCadeia(a1, b1);
		MotorDeCaptura semCombo = new MotorDeCaptura(configuracao(false));
		verificar("Combo desligado: só a posição 4 vira", semCombo.aplicar(t1, 5, b1).equals(List.of(4)));
		verificar("Combo desligado: nenhuma captura em cadeia", semCombo.getCapturasEmCadeia() == 0);

		// 2. Combo ligado: 5 captura 4, 4 captura 1 e 1 captura 2
		Jogador a2 = new Jogador("A");
		Jogador b2 = new Jogador("B");
		Tabuleiro t2 = montarCadeia(a2, b2);
		MotorDeCaptura comCombo = new MotorDeCaptura(configuracao(true));
		verificar("Combo ligado: a cadeia segue até o fim", comCombo.aplicar(t2, 5, b2).equals(List.of(4, 1, 2)));
		verificar("Combo ligado: 2 capturas em cadeia", comCombo.getCapturasEmCadeia() == 2);
		verificar("Combo ligado: B 8 e A 2", b2.getPontos() == 8 && a2.getPontos() == 2);

		// 3. Dois caminhos chegam na mesma carta
		Jogador a3 = new Jogador("A");
		Jogador b3 = new Jogador("B");
		Tabuleiro t3 = new Tabuleiro();
		t3.colocarCarta(1, carta(1, 1, 1, 1), a3);
		t3.colocarCarta(2, carta(1, 1, 1, 9), a3);
		t3.colocarCarta(4, carta(9, 1, 1, 1), a3);
		t3.colocarCarta(5, carta(5, 1, 1, 5), b3);
		verificar("Combo: dois caminhos contam a carta uma vez só", comCombo.aplicar(t3, 5, b3).size() == 3);
		verificar("Combo: dois caminhos dão B 8 e A 2", b3.getPontos() == 8 && a3.getPontos() == 2);

		// 4. O motor zera o resultado da jogada anterior
		Jogador a4 = new Jogador("A");
		Jogador b4 = new Jogador("B");
		Tabuleiro t4 = new Tabuleiro();
		t4.colocarCarta(5, carta(1, 1, 1, 1), b4);
		verificar("Reuso: nova jogada sem captura", comCombo.aplicar(t4, 5, b4).isEmpty());
		verificar("Reuso: contador de cadeia volta a zero", comCombo.getCapturasEmCadeia() == 0);
	}

	private static Tabuleiro montarCadeia(Jogador a, Jogador b) {
		Tabuleiro t = new Tabuleiro();
		t.colocarCarta(1, carta(1, 9, 1, 1), a);
		t.colocarCarta(2, carta(1, 1, 1, 1), a);
		t.colocarCarta(4, carta(9, 1, 1, 1), a);
		t.colocarCarta(5, carta(1, 1, 1, 5), b);
		return t;
	}

	private static Carta carta(int cima, int direita, int baixo, int esquerda) {
		return new Carta("Teste", cima, direita, baixo, esquerda, "NEUTRAL");
	}

	private static ConfiguracoesRegras configuracao(boolean combo, Regra... regras) {
		ConfiguracoesRegras config = new ConfiguracoesRegras() {
			@Override
			public List<Regra> regrasAtivas() {
				return List.of(regras);
			}
		};
		config.setCombo(combo);
		return config;
	}

	// Regra de teste que sempre captura as posições informadas
	private static Regra regra(String nome, Integer... posicoes) {
		return new Regra() {
			@Override
			public List<Integer> capturas(Tabuleiro tabuleiro, int posicao, Jogador jogador) {
				return List.of(posicoes);
			}

			@Override
			public String getNome() {
				return nome;
			}
		};
	}

	private interface Cenario {
		boolean executar() throws Exception;
	}

	// Um erro no motor conta como falha e não interrompe os demais testes
	private static void verificarSemErro(String descricao, Cenario cenario) {
		try {
			verificar(descricao, cenario.executar());
		} catch (Exception e) {
			System.out.println("Erro: " + e);
			verificar(descricao, false);
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
