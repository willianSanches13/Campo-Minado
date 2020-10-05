package br.com.cod3r.cm.modelo;

import java.util.ArrayList;
import java.util.List;

import br.com.cod3r.cm.excecao.ExplosaoException;

public class Campo {  //cada quadradinho
	//coordenadas do quadrado
	private final int linha;
	private final int coluna;
	
	private boolean aberto;   //por padrão o boolean ja recebe o valor false
	private boolean minado;
	private boolean marcado;
	
	private List<Campo> vizinhos = new ArrayList<>();  //auto relacionamento, ou seja, você tem uma relação de 1 para N consigo mesmo 
	
	Campo (int linha, int coluna){
	this.linha = linha;
	this.coluna = coluna;
	}
	boolean adicionarVizinho(Campo vizinho) {
		boolean linhaDiferente = this.linha != vizinho.linha;
		boolean colunaDiferente = this.coluna != vizinho.coluna;
		boolean diagonal = linhaDiferente && colunaDiferente;
		
		int deltaLinha = Math.abs(linha - vizinho.linha);
		int deltaColuna = Math.abs(coluna - vizinho.coluna);
		int deltaGeral = deltaColuna + deltaLinha;
		
		if(deltaGeral == 1 && !diagonal) {
			vizinhos.add(vizinho);
			return true;
		}else if(deltaGeral == 2 && diagonal){
			vizinhos.add(vizinho);
			return true;
		}else{
			return false;
		}
	}
	void alternarMarcacao() {
		if(!aberto) {
			marcado = !marcado; //alterna os valores, se estiver true vai atribuir false e vice e versa.
		}
	}
	
	boolean abrir() {
		if(!aberto && !marcado) {  //o "!" verifica apenas se o valor é igual a falso
			aberto = true;
			if(minado) {
				throw new ExplosaoException(); //quebrando o fluxo de execução do método 
			}
			if(vizinhancaSegura()){
				vizinhos.forEach(v -> v.abrir()); //enquanto a vizinhanca estiver segura ele vai abrindo recursivamente
			}
			return true;
		}else {
		return false;
		}
	}
	
	boolean vizinhancaSegura() {
		return vizinhos.stream().noneMatch(v -> v.minado); //se nenhum vizinho da match com esse predicado
	}
	void minar() {
		minado = true;
	}
	
	public boolean isMinado() {
		return minado;
	}
	
	public boolean isMarcado() {
		return marcado;
	}
	
	void setAberto(boolean aberto){
		this.aberto = aberto; 
	}
	public boolean isAberto() {
		return aberto;
	}
	public boolean isFechado() {
		return !isAberto();
	}
	public int getLinha() {
		return linha;
	}
	public int getColuna() {
		return coluna;
	}
	
	boolean objetivoAlcançado() {
		boolean desvendado = !minado && aberto;
		boolean protegido = minado && marcado;
		return desvendado || protegido;
	}
		long minasNaVizinhanca() { 
		return vizinhos.stream().filter(v -> v.minado).count(); //o count retorna um long, usando a stream com um predicate para filtrar e o count conta os vizinhos com minas	
	}
	void reiniciar () {
		aberto = false;
		minado = false;
		marcado = false;
	}
	
		public String toString() {
			if(marcado) {
				return "x";
			}else if(aberto && minado) {
				return "*";
			}else if(aberto && minasNaVizinhanca() > 0) {
				return Long.toString(minasNaVizinhanca());
			}else if(aberto) {
				return " ";
			}else {
				return "?";
		}
	}
}

