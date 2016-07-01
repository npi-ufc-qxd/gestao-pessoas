package br.ufc.quixada.npi.ge.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.ufc.quixada.npi.ge.model.Estagiario;
import br.ufc.quixada.npi.ge.model.Estagio;

public interface EstagiarioRepository extends JpaRepository<Estagiario, Long> {

	@Query("select e from Estagiario e where e.pessoa.cpf = :cpf")
	Estagiario findByPessoaByCpf(@Param("cpf") String cpf);
	
	@Query("select distinct e1 from Estagiario e1 where e1.id not in(select distinct e.id from Estagiario e join e.estagios estagio where estagio.turma.id = :turmaId)")
	List<Estagiario> findByIdNotInTurma(@Param("turmaId") Long turmaId);
	
	@Query("select distinct e1 from Estagiario e1 where UPPER(e1.nomeCompleto) like %:nomeEstagiario% and e1.id not in(select distinct e.id from Estagiario e join e.estagios estagio where estagio.turma.id = :turmaId)")
	List<Estagiario> finByIdNotInTurmaQueryByNome(@Param("turmaId")Long turmaId, @Param("nomeEstagiario") String nomeEstagiario);

	@Query("select e from Estagio e where e.id = :idEstagio AND (e.turma.orientador.id = :idServidor OR :idServidor MEMBER OF e.turma.supervisores)")
	Estagio findByIdAndOrientadorOrSupervisor(@Param("idEstagio") Long idEstagio, @Param("idServidor") Long idServidor);
}
