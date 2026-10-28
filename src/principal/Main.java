package principal;

import java.io.FileNotFoundException;
import java.util.List;

import modelo.Carta;
import terminal.LeitorCSV;

public class Main {
	public static void main(String[] args) throws FileNotFoundException {
		//TESTE
		List<Carta> cartas = LeitorCSV.ler("cards.csv");
		System.out.println(cartas.size() + " cartas lidas");
	}
}
