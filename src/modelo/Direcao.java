package modelo;

public enum Direcao {
	CIMA(-1,0), 
	BAIXO(1,0), 
	ESQUERDA(0,-1), 
	DIREITA (0,1); 

	private int deslocamentoLinha;
	private int deslocamentoColuna;
	
	private Direcao(int deslocamentoLinha, int deslocamentoColuna) {
		this.deslocamentoLinha = deslocamentoLinha;
		this.deslocamentoColuna = deslocamentoColuna;
	}
	
	public Direcao oposta() {
		if(this==Direcao.CIMA) {
			return Direcao.BAIXO;
		}
		else if(this==Direcao.BAIXO) {
			return Direcao.CIMA;
		}
		else if(this==Direcao.ESQUERDA) {
			return Direcao.DIREITA;
		}
		else if(this==Direcao.DIREITA) {
			return Direcao.ESQUERDA;
		}
		throw new IllegalArgumentException("ERRO:Essa direção não existe!");
	}

	public int getDeslocamentoLinha() {
		return deslocamentoLinha;
	}

	public int getDeslocamentoColuna() {
		return deslocamentoColuna;
	}

	
	
}
