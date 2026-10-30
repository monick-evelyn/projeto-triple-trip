package terminal;

import modelo.Carta;
import modelo.Celula;
import modelo.Tabuleiro;
import modelo.Jogador;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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
		System.out.println("Jogador da vez: "+proximo.getNome());
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

}
