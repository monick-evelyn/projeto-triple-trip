package terminal;

import modelo.Carta;
import modelo.Celula;
import modelo.Tabuleiro;
import modelo.Jogador;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import jogo.ConfiguracoesRegras;

public class Tela {
	private static final Scanner leitor=new Scanner(System.in);

	public static void desenharTabuleiro(Tabuleiro t, Jogador j1, Jogador j2) {
		for (int i = 0; i < 3; i++) {
			String[][] desenhos = new String[3][];

			for (int j = 0; j < 3; j++) {
				int posicao = i * 3 + j + 1;
				Celula celulaAux = t.getCelula(posicao);

				if (celulaAux.estaVazia()) {
					desenhos[j] = celulaVazia(posicao);
				} else {
					String[] desenho = DesenhoCarta.desenhar(celulaAux.getCarta());
					String nomeDono;

					if (celulaAux.getDono().equals(j1)) {
						nomeDono = j1.getNome();
					} else {
						nomeDono = j2.getNome();
					}

					String[] desenhoFinal = new String[9];
					for (int a = 0; a < 7; a++) {
						desenhoFinal[a] = desenho[a];
					}
					desenhoFinal[7] = "|" + centralizar(nomeDono) + "|";
					desenhoFinal[8] = "+-------------+";
					desenhos[j] = desenhoFinal;

				}
			}

			for (int x = 0; x < 9; x++) {
				System.out.print(desenhos[0][x] + " ");
				System.out.print(desenhos[1][x] + " ");
				System.out.println(desenhos[2][x]);
			}
		}
	}

	private static String[] celulaVazia(int posicao) {
		String[] desenho = new String[9];
		desenho[0] = "+-------------+";
		desenho[1] = "|             |";
		desenho[2] = "|             |";
		desenho[3] = "|" + "      " + posicao + "      " + "|";
		desenho[4] = "|             |";
		desenho[5] = "|             |";
		desenho[6] = "+-------------+";
		desenho[7] = "|             |";
		desenho[8] = "+-------------+";
		return desenho;

	}

	private static String centralizar(String texto) {
		int LARGURA = 13;

		if (texto.length() > LARGURA) {
			texto = texto.substring(0, LARGURA);
		}

		int esquerda = (LARGURA - texto.length()) / 2;
		int direita = LARGURA - texto.length() - esquerda;

		return " ".repeat(esquerda) + texto + " ".repeat(direita);
	}

	public static void desenharMao(Jogador jogador) {
		List<Carta> mao = jogador.getMao();
		List<String[]> desenhos = new ArrayList<>();
		for (int i = 0; i < mao.size(); i++) {
			Carta carta = mao.get(i);
			String[] desenho = DesenhoCarta.desenhar(carta);
			desenhos.add(desenho);
		}
		for (int j = 0; j < 7; j++) {
			for (int y = 0; y < desenhos.size(); y++) {
				System.out.print(desenhos.get(y)[j] + " ");
			}
			System.out.println();
		}
		for (int i = 0; i < mao.size(); i++) {
			System.out.print("       " + (i + 1) + "        ");
		}
		System.out.println();
	}

	public static void desenharMaoOponente(Jogador jogador, boolean threeOpen) {
		List<Carta> mao = jogador.getMao();
		List<String[]> desenhos = new ArrayList<>();
		for (int i = 0; i < mao.size(); i++) {
			String[] desenho;
			Carta carta = mao.get(i);
			if (threeOpen && i < 3) {
				desenho = DesenhoCarta.desenhar(carta);
			} else {
				desenho = desenharVerso();
			}
			desenhos.add(desenho);
		}
		for (int j = 0; j < 7; j++) {
			for (int y = 0; y < desenhos.size(); y++) {
				System.out.print(desenhos.get(y)[j] + " ");
			}
			System.out.println();
		}

	}

	private static String[] desenharVerso() {
		String[] desenho = new String[7];
		desenho[0] = "+-------------+";
		desenho[1] = "|             |";
		desenho[2] = "|             |";
		desenho[3] = "|" + "      " + "?" + "      " + "|";
		desenho[4] = "|             |";
		desenho[5] = "|             |";
		desenho[6] = "+-------------+";
		return desenho;
	}

	public static void desenharPlacar(Jogador j1, Jogador j2) {
		String borda = "+--------------------------------------------------+";
		String divisor = "|==================================================|";

		String lineJ1 = String.format("  %s: %d pontos", j1.getNome(), j1.getPontos());
		String lineJ2 = String.format("  %s: %d pontos", j2.getNome(), j2.getPontos());

		System.out.println(borda);
		System.out.println("|                      PLACAR                      |");
		System.out.println(divisor);
		System.out.println("|                                                  |");
		System.out.printf("|%-50s|\n", lineJ1);
		System.out.printf("|%-50s|\n", lineJ2);
		System.out.println("|                                                  |");
		System.out.println(borda);
	}

	// ESTOU USANDO O ECLIPSE, NÃO FUNCIONA OUTROS COMANDOS PARA LIMPAR, APENAS
	// MANUALMENTE
	// VOU ANEXAR OUTRAS OPÇÕES QUE PODEM SER USADAS, GERADAS PELO GEMINI

	/*public static void limparTela() {
		try {
			String os = System.getProperty("os.name").toLowerCase();

			if (os.contains("win")) {
				new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
			} else {
				new ProcessBuilder("clear").inheritIO().start().waitFor();
			}
		} catch (Exception e) {
			System.out.println("Não foi possível limpar o terminal.");
		}
	}*/

	/*public static void limparTel2a() {
		System.out.print("\033[H\033[2J");
		System.out.flush();
	}*/

	public static void limpar() {
		for (int i = 0; i < 50; i++) {
			System.out.println();
		}
	}

	public static void telaDeTroca(Jogador proximo) {
		limpar();
		System.out.println("Jogador da vez: " + proximo.getNome());
		System.out.println("Pressione Enter para continuar...");

		leitor.nextLine();
	}

	public static void mostrarMenu() {
		System.out.println("+--------------------------------------------------+");
		System.out.println("|                   TRIPLE TRIAD                   |");
		System.out.println("|==================================================|");
		System.out.println("|  [1] Iniciar jogo                                |");
		System.out.println("|  [2] Consultar regras                            |");
		System.out.println("|  [3] Ver instruções do jogo                      |");
		System.out.println("|  [4] Sair                                        |");
		System.out.println("+--------------------------------------------------+");
	}

	public static String[] juntarLadoALado(List<String[]> blocos) {
		int quantidadeLinhas = blocos.get(0).length;
		String[] resultado = new String[quantidadeLinhas];

		for (int linha = 0; linha < quantidadeLinhas; linha++) {
			String linhaCompleta = "";
			for (int i = 0; i < blocos.size(); i++) {
				linhaCompleta += blocos.get(i)[linha] + " ";
			}
			resultado[linha] = linhaCompleta;
		}
		return resultado;
	}

	public static void mostrarRegras(ConfiguracoesRegras regras) {
		System.out.println(
				"======================================== REGRAS ATIVAS ========================================");
		if (regras.isSame()) {
			System.out.println("[ SAME ]");
			System.out.println(
					"• Se 2 ou mais lados encostados forem iguais, as cartas adversárias envolvidas são capturadas.\n");
		} else if (regras.isPlus()) {
			System.out.println("[ PLUS ]");
			System.out.println(
					"• Se 2 ou mais pares de lados encostados tiverem a mesma soma, as cartas são capturadas.\n");
		} else if (regras.isCombo()) {
			System.out.println("[ COMBO ]");
			System.out.println("• Cartas recém-capturadas capturam vizinhas mais fracas, em cadeia.\n");
		} else if (regras.isThreeOpen()) {
			System.out.println("[ THREE OPEN ]");
			System.out.println("• Três cartas da mão de cada jogador ficam visíveis ao oponente.\n");
		}
		System.out.println(
				"===============================================================================================");
	}

	public static void mostrarJogada(String nome, int capturas, List<String> regras, int emCadeia) {
		String mostrarCapturas = "";
		for (int i = 0; i < regras.size(); i++) {
			if (i > 0) {
				mostrarCapturas += ", ";
			}
			mostrarCapturas += regras.get(i);
		}
		System.out.println(
				"======================================== JOGADA ATUAL ========================================");
		System.out.println("Nome: " + nome + " capturou " + capturas + " cartas (" + mostrarCapturas + "!)");
		System.out.println("Capturas em cadeia: " + emCadeia);
		System.out.println(
				"==============================================================================================");
	}

	public static void mostrarResultado(Tabuleiro t, Jogador j1, Jogador j2) {
		int pontosJ1 = j1.getPontos();
		int pontosJ2 = j2.getPontos();
		System.out
				.println("======================================== RESULTADO ========================================");
		desenharTabuleiro(t, j1, j2);
		if (pontosJ1 > pontosJ2) {
			System.out.println("Parabénss " + j1.getNome() + " você ganhou esta rodada!");
		} else if (pontosJ1 == pontosJ2) {
			System.out.println("Parabénss vocês dois empataram!!");
		} else {
			System.out.println("Parabénss " + j2.getNome() + " você ganhou esta rodada!");
		}
		System.out
				.println("===========================================================================================");
	}

}
