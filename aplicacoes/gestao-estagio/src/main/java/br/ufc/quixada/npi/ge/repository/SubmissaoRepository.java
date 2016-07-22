package br.ufc.quixada.npi.ge.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import br.ufc.quixada.npi.ge.model.Submissao;
import br.ufc.quixada.npi.ge.model.Submissao.StatusEntrega;
import br.ufc.quixada.npi.ge.model.Submissao.TipoSubmissao;

public interface SubmissaoRepository extends JpaRepository<Submissao, Long> {
	
	@Query("select s from Submissao s where s.tipoSubmissao = :tipoSubmissao and s.estagio.id = :idEstagio")
	Submissao findByIdETipo(@Param("tipoSubmissao") Submissao.TipoSubmissao tipoSubmissao, @Param("idEstagio") Long idEstagio);

	@Query("select s.id from Submissao s where s.tipoSubmissao = :tipoSubmissao and s.estagio.id = :idEstagio")
	Long findIdByIdETipo(@Param("tipoSubmissao") Submissao.TipoSubmissao tipoSubmissao, @Param("idEstagio") Long idEstagio);
	
	Submissao findByTipoSubmissaoAndEstagio_Id(TipoSubmissao tipoSubmissao, Long idEstagio);
	
	Submissao findByTipoSubmissaoAndEstagio_IdAndEstagio_Estagiario_Pessoa_Cpf(TipoSubmissao tipoSubmissao, Long idEstagio, String cpf);
	
	@Transactional
	@Modifying
	@Query("UPDATE Submissao s SET s.statusEntrega = :statusEntrega, s.nota = :nota, s.comentario = :comentario WHERE s.id = :id")
	int updateSubmissaoById(@Param("id") Long id, @Param("statusEntrega") StatusEntrega statusEntrega, @Param("nota") double nota, @Param("comentario") String comentario);
}
