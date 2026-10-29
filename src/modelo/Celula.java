package modelo;

public class Celula {
	private Carta carta;
	private Jogador dono;
	
	public Celula() {
		this.carta = null;
		this.dono = null;
	}
	
	public void ocupar(Carta carta, Jogador dono) {
		if (!estaVazia()) {
			throw new IllegalStateException("Carta não está vazia.");
		}
		if (estaVazia() && dono == null) {
			throw new IllegalArgumentException("Carta sem dono.");
		}
		
		this.carta = carta;
		this.dono = dono;
	}

	public Carta getCarta() {
		return carta;
	}

	public Jogador getDono() {
		return dono;
	}
	
	//Representa a captura de uma carta
	public void trocarDono(Jogador novoDono) {
		if (estaVazia()) {
			throw new IllegalStateException("Carta está vazia.");
		}
		
		this.dono = novoDono;
	}
	
	public boolean estaVazia() {
		if (carta == null) {
			return true;
		}
		return false;
	}
	
	
}
