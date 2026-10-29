package terminal;

import modelo.Carta;
import modelo.Direcao;

public class DesenhoCarta {
	private static final int LARGURA = 13;
	private static final String BORDA = "+-------------+";
	
	public static String[] desenhar(Carta carta) {
		String cima = converteValor(carta.getLado(Direcao.CIMA));
		String baixo = converteValor(carta.getLado(Direcao.BAIXO));
		String esquerda = converteValor(carta.getLado(Direcao.ESQUERDA));
		String direita = converteValor(carta.getLado(Direcao.DIREITA));

		return new String[] { 
			BORDA, 
			"|" + centralizar(carta.getNome()) + "|",
			"|      " + cima + "      |",
			"|    " + esquerda + "   " + direita + "    |",
			"|      " + baixo + "      |",
			"|" + centralizar(carta.getTipo()) + "|",
			BORDA
		};
	}
	
	private static String converteValor(int lado) {
		if (lado == 10) {
			return "A";
		}
		return String.valueOf(lado);
	}
	
	private static String centralizar(String texto) {
		if (texto == null) {
			texto = "";
		}
		
		if (texto.length() > LARGURA) {
			texto = texto.substring(0, LARGURA - 1) + ".";
		}
		
		int esquerda = (LARGURA - texto.length()) / 2;
		int direita = LARGURA - texto.length() - esquerda;
		
		String espacoEsquerda = espacoLado(esquerda, "");
		String espacoDireita  = espacoLado(direita, "");;
		
		return espacoEsquerda + texto + espacoDireita;
	}
	
	public static String espacoLado(int valor, String espaco) {
		for (int i = 0; i < valor; i++) {
			espaco += " ";
		}
		return espaco;
	}
	
	public static void imprimir(Carta carta) {
		for (String linha : desenhar(carta)) {
			System.out.println(linha);
		}
	}
}
