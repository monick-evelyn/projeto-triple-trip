package modelo;

public class Carta {
	private String nome;
	private String tipo;
	private int lado1; // cima
	private int lado2; // direita
	private int lado3; // baixo
	private int lado4; // esquerda

	public Carta(String nome, int lado1, int lado2, int lado3, int lado4, String tipo) {
		validarLado(lado1);
		validarLado(lado2);
		validarLado(lado3);
		validarLado(lado4);
		
		this.nome = nome;
		this.lado1 = lado1;
		this.lado2 = lado2;
		this.lado3 = lado3;
		this.lado4 = lado4;
		this.tipo = tipo;
	}
	
	public Carta(String nome, int lado1, int lado2, int lado3, int lado4) {
		validarLado(lado1);
		validarLado(lado2);
		validarLado(lado3);
		validarLado(lado4);
		
		this.nome = nome;
		this.lado1 = lado1;
		this.lado2 = lado2;
		this.lado3 = lado3;
		this.lado4 = lado4;
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
	
	private void validarLado(int lado) {
		if (lado < 0 || lado > 10) {
			throw new IllegalArgumentException("ERRO: O lado deve ser um número de 0 a 10.");
		}
	}

	public String getNome() {
		return nome;
	}

	public String getTipo() {
		return tipo;
	}

}
