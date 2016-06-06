package br.ufc.quixada.npi.gp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.ufc.quixada.npi.gp.model.Submissao;
import br.ufc.quixada.npi.gp.model.Submissao.TipoSubmissao;

public interface SubmissaoRepository extends JpaRepository<Submissao, Long> {

	Submissao findByTipoSubmissaoAndEstagio_Id(TipoSubmissao tipoSubmissao, Long idEstagio);

	Submissao findByTipoSubmissaoAndEstagio_IdAndEstagio_Estagiario_Pessoa_Cpf(TipoSubmissao tipoSubmissao, Long idEstagio, String cpf);
	
}
