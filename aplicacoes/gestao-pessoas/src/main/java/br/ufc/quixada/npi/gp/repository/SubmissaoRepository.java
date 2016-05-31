package br.ufc.quixada.npi.gp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import br.ufc.quixada.npi.gp.model.Submissao;

public interface SubmissaoRepository extends JpaRepository<Submissao, Long> {
	
	@Query("select s from Submissao s where s.tipoSubmissao = :tipoSubmissao and s.estagio.id = :idEstagio")
	Submissao findByTipoEId(@Param("idEstagio") Long idEstagio, @Param("tipoSubmissao") Submissao.TipoSubmissao tipoSubmissao);
	
}
