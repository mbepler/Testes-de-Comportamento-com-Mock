package br.com.caelum.leilao.servico;

import java.util.Calendar;
import java.util.List;

import br.com.caelum.leilao.dominio.Leilao;
import br.com.caelum.leilao.dominio.Pagamento;
import br.com.caelum.leilao.infra.dao.RelogioDoSistema;
import br.com.caelum.leilao.infra.interfac.Relogio;
import br.com.caelum.leilao.infra.interfac.RepositorioDeLeiloes;
import br.com.caelum.leilao.infra.interfac.RepositorioDePagamentos;

public class GeradorDePagamento {

	private final RepositorioDeLeiloes leiloes;
	private Avaliador avaliador;
	private RepositorioDePagamentos pagamentos;
	private Relogio relogio;
	
	public GeradorDePagamento(RepositorioDeLeiloes leiloes,RepositorioDePagamentos pagamentos, Avaliador avaliador
			, Relogio relogio) {
		this.leiloes = leiloes;
		this.pagamentos = pagamentos;
		this.avaliador = avaliador;
		this.relogio = relogio;
	}
	
	public GeradorDePagamento(RepositorioDeLeiloes leiloes,RepositorioDePagamentos pagamentos, Avaliador avaliador) {
		this(leiloes,pagamentos,avaliador,new RelogioDoSistema());
	}
	
	public void gera() {
		List<Leilao> leiloesEncerrados = this.leiloes.encerrados();
		
		for(Leilao leilao : leiloesEncerrados) {
			this.avaliador.avalia(leilao);
			
			Pagamento novoPagamento =  new Pagamento(avaliador.getMaiorLance(), primeiroDiaUtil());
			this.pagamentos.salva(novoPagamento);
			
		}
	}


	private Calendar primeiroDiaUtil() {
		Calendar data = relogio.hoje();
		int diaDaSemana = data.get(Calendar.DAY_OF_WEEK);
		
		if(diaDaSemana == Calendar.SATURDAY) {
			data.add(Calendar.DAY_OF_MONTH, 2);
		}
		if(diaDaSemana == Calendar.SUNDAY) {
			data.add(Calendar.DAY_OF_MONTH, 1);
		}
		return data;
	}
	
}
