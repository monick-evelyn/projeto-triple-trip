package modelo;

public class Carta {
	private String nome;
	private String tipo;
	private int lado1; // cima
	private int lado2; // direita
	private int lado3; // baixo
	private int lado4; // esquerda

	public Carta(String nome, int lado1, int lado2, int lado3, int lado4, String tipo) {
		this.nome = nome;
		if (lado1 < 0 || lado1 > 10 || lado2 < 0 || lado2 > 10 || lado3 < 0 || lado3 > 10 || lado4 < 0 || lado4 > 10) {
			throw new IllegalArgumentException("ERRO: O lado não pode ser menor que zero ou maior que 10.");
		}
		this.lado1 = lado1;
		this.lado2 = lado2;
		this.lado3 = lado3;
		this.lado4 = lado4;
		this.tipo = tipo;
	}

	public int getLado(Direcao direcao) {
		if (direcao == Direcao.CIMA) {
			return lado1;
		} else if (direcao == Direcao.DIREITA) {
			return lado2;
		} else if (direcao == Direcao.BAIXO) {
			return lado3;
		} else if (direcao == Direcao.ESQUERDA) {
			return lado4;
		}
		throw new IllegalArgumentException("ERRO: Não existe um lado para essa direção");
	}

	public String getNome() {
		return nome;
	}

	public String getTipo() {
		return tipo;
	}
}
