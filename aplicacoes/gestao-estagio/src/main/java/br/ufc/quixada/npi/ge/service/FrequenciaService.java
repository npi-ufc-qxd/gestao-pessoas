package br.ufc.quixada.npi.ge.service;

import java.util.List;

import br.ufc.quixada.npi.ge.model.Estagio;
import br.ufc.quixada.npi.ge.model.Frequencia;
import br.ufc.quixada.npi.ge.model.Presenca;

public interface FrequenciaService {

	List<Presenca> permitirPresencaEstagio(Estagio estagio);

	public boolean realizarSaida(Estagio estagio);
	
	public boolean realizarEntrada(Estagio estagio);

	boolean realizarEntradaReposicao(Frequencia frequencia);

	boolean realizarSaidaReposicao(Frequencia frequencia);

	public Frequencia buscarReposicao(Estagio estagio);

	public Frequencia buscarPorId(Long id);

	public void salvar(Frequencia frequencia);

}
