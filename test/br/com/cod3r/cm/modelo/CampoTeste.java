package br.com.cod3r.cm.modelo;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue; //se colocar um * no lugar do assertTrue, ele importa n�o s� o true mas tamb�m o false

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.cod3r.cm.excecao.ExplosaoException;

public class CampoTeste {
	private Campo campo;
	@BeforeEach
	void iniciarCampo() {
       campo = new Campo(3, 3);  //ele vai instanciar o objeto dessa forma para cada m�todo dessa classe
	}
	
	//sequencia de testes 
	@Test
	void testeVizinhoRealDistancia1Esquerda () {
		Campo vizinho = new Campo(3, 2);
		boolean resultado = campo.adicionarVizinho(vizinho);	
		assertTrue(resultado);
	}
	@Test
	void testeVizinhoRealDistancia1Direita() {
		Campo vizinho = new Campo(3, 4); 
		boolean resultado = campo.adicionarVizinho(vizinho);	
		assertTrue(resultado);
	}
	@Test
	void testeVizinhoRealDistancia1EmCima() {
		Campo vizinho = new Campo(2, 3);
		boolean resultado = campo.adicionarVizinho(vizinho);	
		assertTrue(resultado);
	}
	@Test
	void testeVizinhoRealDistancia1Embaixo() {
		Campo vizinho = new Campo(4, 3);
		boolean resultado = campo.adicionarVizinho(vizinho);	
		assertTrue(resultado);
	}
	@Test
	void testeVizinhoRealDistanciaDiagonal() {
		Campo vizinho = new Campo(2, 2);
		boolean resultado = campo.adicionarVizinho(vizinho);	
		assertTrue(resultado);
	}
	@Test
	void testeVizinhoNaoVizinho() {
		Campo vizinho = new Campo(1, 1 );
		boolean resultado = campo.adicionarVizinho(vizinho);	
		assertFalse(resultado);
	}
	@Test
	void testeValorPadraoAtributoMarcado() {
		assertFalse(campo.isMarcado());
	}
	
	@Test
	void testeAlternarMarcacao() {
		campo.alternarMarcacao();
		assertTrue(campo.isMarcado());
	}
	
	@Test
	void testeAlternarMarcacao2vezes() {
		campo.alternarMarcacao();
		campo.alternarMarcacao();
		assertFalse(campo.isMarcado());
	}
	@Test
	void testeAbrirNaoMinadoNaoMarcado() {
		assertTrue(campo.abrir());
	}
	@Test
	void testeAbrirNaoMinadoMarcado() {
		campo.alternarMarcacao();
		assertFalse(campo.abrir());
	}
	@Test
	void testeAbrirMinadoMarcado() {
		campo.alternarMarcacao();
		campo.minar();
		assertFalse(campo.abrir());
	}
	@Test
	void testeAbrirMinadoNaoMarcado() {
		campo.minar();
		assertThrows(ExplosaoException.class, () -> {  //primeiro parametro a excess�o que est� esperando, segundo uma lambda com o m�todo que dispara ela 
			campo.abrir();
			});
	}
	@Test 
	void testeAbrirComVizinhos1() {
		Campo campo22 = new Campo(2, 2);
		Campo campo11 = new Campo(1, 1);
		campo22.adicionarVizinho(campo11);
		campo.adicionarVizinho(campo22);
		campo.abrir();
		assertTrue(campo11.isAberto() && campo22.isAberto());
	}
	@Test 
	void testeAbrirComVizinhos2() {
		Campo campo22 = new Campo(2, 2);
		Campo campo11 = new Campo(1, 1);
		Campo campo12 = new Campo(1, 2);
		campo12.minar();
		campo22.adicionarVizinho(campo11);
		campo22.adicionarVizinho(campo12);
		campo.adicionarVizinho(campo22);
		campo.abrir();
		assertTrue(campo22.isAberto() && campo11.isFechado());
	}
}
