package jogo;

import java.util.ArrayList;
import java.util.List;

import regras.Regra;
import regras.RegraBasica;

public class ConfiguracoesRegras {
	private boolean same;
	private boolean plus;
	private boolean combo;
	private boolean threeOpen;
	
	public ConfiguracoesRegras() {
		this.same = true;
		this.plus = true;
		this.combo = true;
		this.threeOpen = true;
	}

	public boolean isSame() {
		return same;
	}
	public void setSame(boolean same) {
		this.same = same;
	}

	public boolean isPlus() {
		return plus;
	}
	public void setPlus(boolean plus) {
		this.plus = plus;
	}

	public boolean isCombo() {
		return combo;
	}
	public void setCombo(boolean combo) {
		this.combo = combo;
	}

	public boolean isThreeOpen() {
		return threeOpen;
	}
	public void setThreeOpen(boolean threeOpen) {
		this.threeOpen = threeOpen;
	}
	
	
	public List<Regra> regrasAtivas() {
		List<Regra> regras = new ArrayList();
		regras.add(new RegraBasica()); 
		
		/* TODO: Criar classes com demais regras
		if (same) {
			regras.add(new RegraSame()); 
		}
		
		if (plus) {
			regras.add(new RegraPlus());
		} 
		*/
		return regras;
	}
	
	
	
}
