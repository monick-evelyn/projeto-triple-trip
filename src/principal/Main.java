package principal;

import modelo.Carta;
import terminal.DesenhoCarta;

public class Main {
	public static void main(String[] args) {
		// 1. Carta feita à mão: cima 9, direita 2, baixo 3, esquerda 9
		Carta ifrit = new Carta("Ifrit", 9, 2, 3, 9, "FIRE");
		DesenhoCarta.imprimir(ifrit);
		DesenhoCarta.imprimirVerso();
	}
}
