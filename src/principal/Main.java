package principal;

import java.io.FileNotFoundException;
import java.util.List;

import modelo.Carta;
import terminal.DesenhoCarta;
import terminal.LeitorCSV;

public class Main {
	public static void main(String[] args) throws FileNotFoundException {
		// 1. Carta feita à mão: cima 9, direita 2, baixo 3, esquerda 9
		Carta ifrit = new Carta("Ifrit", 9, 2, 3, 9, "FIRE");
		DesenhoCarta.imprimir(ifrit);

		// 2. Valor 10 vira A, nome longo é cortado, carta sem tipo
		Carta teste = new Carta("Chubby Chocobo", 10, 0, 10, 0);
		DesenhoCarta.imprimir(teste);

		// 3. Todas as cartas do CSV 
		List<Carta> baralho = LeitorCSV.ler("cards.csv");
		for (int i = 1; i < 100; i++) {
			DesenhoCarta.imprimir(baralho.get(i));
		}
		

		

	}
}
