package br.com.caelum.leilao.servico;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import org.junit.Test;

import br.com.caelum.leilao.builder.CriadorDeLeilao;
import br.com.caelum.leilao.dominio.Leilao;
import br.com.caelum.leilao.infra.interfac.RepositorioDeLeiloes;

public class EncerradorDeLeilaoTest {

//	A grande vantagem é trabalhar sempre voltado para interfaces, que é um importante princípio de orientação a objetos.
//
//	Sempre que for trabalhar com mocks, pense em criar interfaces entre suas classes. 
//	Dessa forma, seu teste e código passam a depender apenas de um contrato, e não de uma classe concreta.
//	
	@Test
	public void deveEncerrarLeiloesQueComecaramUmaSemanaAntes() {
		Calendar antiga = Calendar.getInstance();
		antiga.set(1999, 1,20);
		
		Leilao leilao1 = new CriadorDeLeilao().para("Tv De Plasma").naData(antiga).constroi();
		Leilao leilao2 = new CriadorDeLeilao().para("Geladeira").naData(antiga).constroi();
		List<Leilao> leiloesAntigos = Arrays.asList(leilao1,leilao2);
		
		
		RepositorioDeLeiloes daoFalso = mock(RepositorioDeLeiloes.class);
		
		when(daoFalso.correntes()).thenReturn(leiloesAntigos);
		
		
		EncerradorDeLeilao encerrador = new EncerradorDeLeilao(daoFalso);
		encerrador.encerra();
		
		
		
		assertEquals(2 , encerrador.getTotalEncerrados());
		assertTrue(leilao1.isEncerrado());
		assertTrue(leilao2.isEncerrado());
		
		
	}
	
	@Test
	public void deveEncerrarLeiloesQueComecaramOntem() {
		Calendar ontem = Calendar.getInstance();
		ontem.add(Calendar.DATE, -1);
		
		Leilao leilao1 = new CriadorDeLeilao().para("Tv De Plasma").naData(ontem).constroi();
		Leilao leilao2 = new CriadorDeLeilao().para("Geladeira").naData(ontem).constroi();
		List<Leilao> leiloesAntigos = Arrays.asList(leilao1,leilao2);
		
		
		RepositorioDeLeiloes daoFalso = mock(RepositorioDeLeiloes.class);
		
		when(daoFalso.correntes()).thenReturn(leiloesAntigos);
		
		
		EncerradorDeLeilao encerrador = new EncerradorDeLeilao(daoFalso);
		encerrador.encerra();
		
		
		
		assertEquals(0 , encerrador.getTotalEncerrados());
		assertFalse(leilao1.isEncerrado());
		assertFalse(leilao2.isEncerrado());
		
		
	}
	
	
	@Test
	public void naoFazNadaQuandoNaoHouverLeiloes() {
		RepositorioDeLeiloes daoFalso = mock(RepositorioDeLeiloes.class);
		
		when(daoFalso.correntes()).thenReturn(new ArrayList<Leilao>());
		
		
		EncerradorDeLeilao encerrador = new EncerradorDeLeilao(daoFalso);
		encerrador.encerra();
		
		
		
		assertEquals(0 , encerrador.getTotalEncerrados());
		
		
	}
	
	
}
