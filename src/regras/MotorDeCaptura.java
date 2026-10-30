package regras;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import jogo.ConfiguracoesRegras;
import modelo.Celula;
import modelo.Jogador;
import modelo.Tabuleiro;

public class MotorDeCaptura {
	private List<Regra> regras;
	private boolean combo;
	private List<String> regrasAtivadas = new ArrayList<>();
	private int capturasEmCadeia;
	private final Regra basica = new RegraBasica();

	public MotorDeCaptura(ConfiguracoesRegras config) {
		this.regras = config.regrasAtivas();
		this.combo = config.isCombo();
	}

	public List<Integer> aplicar(Tabuleiro tabuleiro, int posicao, Jogador jogador) throws Exception {
		regrasAtivadas = new ArrayList<>();
		capturasEmCadeia = 0;

		Set<Integer> capturadas = new LinkedHashSet<>(basica.capturas(tabuleiro, posicao, jogador));

		for (Regra regra : regras) {
			List<Integer> excucoesDaRegra = regra.capturas(tabuleiro, posicao, jogador);
			if (!excucoesDaRegra.isEmpty()) {
				regrasAtivadas.add(regra.getNome());
			}
			capturadas.addAll(excucoesDaRegra);
		}

		trocarDonos(tabuleiro, capturadas, jogador);

		List<Integer> viraramCombo = new ArrayList<>(capturadas);
		if (combo == true) {
			for (int capturada : capturadas) {
				capturarEmCadeia(tabuleiro, capturada, jogador, viraramCombo);
			}
		}
		return viraramCombo;
	}

	public List<String> getRegrasAtivadas() {
		return regrasAtivadas;
	}

	public int getCapturasEmCadeia() {
		return capturasEmCadeia;
	}

	private void trocarDonos(Tabuleiro tabuleiro, Set<Integer> capturadas, Jogador jogador) {
		for (int capturada : capturadas) {
			if (tabuleiro.getCelula(capturada).getDono() == jogador) {
				capturadas.remove(capturada);
			}
		}

		for (int capturada : capturadas) {
			trocarDono(tabuleiro.getCelula(capturada), jogador);
		}
	}

	private void trocarDono(Celula celula, Jogador jogador) {
		Jogador antigoDono = celula.getDono();

		celula.trocarDono(jogador);

		jogador.ganharPonto();
		antigoDono.perderPonto();
	}

	private void capturarEmCadeia(Tabuleiro tabuleiro, int posicao, Jogador jogador, List<Integer> viraramCombo) throws Exception {
		for (int vizinha : basica.capturas(tabuleiro, posicao, jogador)) {
			Celula celula = tabuleiro.getCelula(vizinha);

			if (celula.getDono() != jogador) {
				trocarDono(celula, jogador);
				viraramCombo.add(vizinha);
				capturasEmCadeia++;
				capturarEmCadeia(tabuleiro, vizinha, jogador, viraramCombo);
			}
		}
	}
}
