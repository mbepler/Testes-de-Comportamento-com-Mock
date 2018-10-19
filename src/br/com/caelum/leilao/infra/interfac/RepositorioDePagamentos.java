package br.com.caelum.leilao.infra.interfac;

import br.com.caelum.leilao.dominio.Pagamento;

public interface RepositorioDePagamentos {

	void salva (Pagamento pagamento);
	
}
