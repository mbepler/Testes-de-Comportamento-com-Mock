package br.com.caelum.leilao.infra.dao;

import java.util.Calendar;

import br.com.caelum.leilao.infra.interfac.Relogio;

public class RelogioDoSistema implements Relogio{

	public Calendar hoje() {
		return Calendar.getInstance();
	}
}
