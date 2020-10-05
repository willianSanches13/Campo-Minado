package br.com.cod3r.cm.modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import br.com.cod3r.cm.excecao.ExplosaoException;

public class Tabuleiro {
	private int linhas;
	private int colunas; 
	private int minas;
	
	private final List<Campo> campos = new ArrayList<>();  //não vai ser necessário criar o tabuleiro como sendo uma matriz, ja que cada campo ja tem sua posição 

	public Tabuleiro(int linhas, int colunas, int minas) {
		this.linhas = linhas;
		this.colunas = colunas;
		this.minas = minas;
		
		gerarCampos();
		associarVizinhos();
		sortearMinas();
	}
	
	public void abrir(int linha, int coluna) {
		
		try {
			campos.parallelStream()
			.filter(c -> c.getLinha() == linha && c.getColuna() == coluna)
			.findFirst()
			.ifPresent(c -> c.abrir());
		}catch(ExplosaoException e) {
			campos.forEach(c -> c.setAberto(true));
			throw e; //relançando a excessão que eu ja tenho 
		}
		
	}
	public void alterarMarcacao(int linha, int coluna) {
		campos.parallelStream()
		.filter(c -> c.getLinha() == linha && c.getColuna() == coluna)
		.findFirst()
		.ifPresent(c -> c.alternarMarcacao());
	}

	private void sortearMinas() {
		long minasArmadas = 0;
		Predicate<Campo> minado = c -> c.isMinado();
		do {
			int aleatorio = (int)(Math.random()*campos.size()); //o cast vem primeiro em ordem de precedência, se não colocasse os parenteses na multiplicação ia dar erro  
			campos.get(aleatorio).minar();
			minasArmadas = campos.stream().filter(minado).count();
		}while(minasArmadas < minas);
	}
	private void associarVizinhos() {
		for (Campo c1: campos) {
			for (Campo c2: campos) {
				c1.adicionarVizinho(c2);
			}
		}
	}
	private void gerarCampos() {
		for (int i = 0; i < linhas; i++) {
			for (int j = 0; j < colunas; j++) {
				campos.add(new Campo(i, j));
			}
		}
	}
	public boolean objetivoAlcançado() {
		return campos.stream().allMatch(c -> c.objetivoAlcançado());
	}
	
	public void reiniciar () {
		campos.stream().forEach(c -> c.reiniciar());
		sortearMinas();
	}
	public String toString() {
		StringBuilder sb = new StringBuilder();  //sempre que tiver uma necessidade de concatenação muito grande é melhor usar o string builder 
		sb.append("  ");
		for (int c = 0; c < colunas; c++) {
			sb.append(" ");
			sb.append(c);
			sb.append(" ");
		}
		sb.append("\n");
		int i = 0;
		for (int l = 0; l < linhas; l++) {
			sb.append(l);
			sb.append(" ");
			for (int c = 0; c < colunas; c++) {
				sb.append(" ");
				sb.append(campos.get(i));
				sb.append(" ");
				i++;
			}
			sb.append("\n");
		}
		return sb.toString();
	}
}


















