package br.ufc.quixada.npi.ge.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.ufc.quixada.npi.ge.model.Submissao;
import br.ufc.quixada.npi.ge.model.Submissao.TipoSubmissao;

public interface SubmissaoRepository extends JpaRepository<Submissao, Long> {
	
	@Query("select s from Submissao s where s.tipoSubmissao = :tipoSubmissao and s.estagio.id = :idEstagio")
	Submissao findByIdETipo(@Param("tipoSubmissao") Submissao.TipoSubmissao tipoSubmissao, @Param("idEstagio") Long idEstagio);

	Submissao findByTipoSubmissaoAndEstagio_Id(TipoSubmissao tipoSubmissao, Long idEstagio);
	
	Submissao findByTipoSubmissaoAndEstagio_IdAndEstagio_Estagiario_Pessoa_Cpf(TipoSubmissao tipoSubmissao, Long idEstagio, String cpf);	
}
