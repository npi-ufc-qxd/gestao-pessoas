package br.ufc.quixada.npi.ge.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.ufc.quixada.npi.ge.model.Estagio;


public interface EstagioRepository extends JpaRepository<Estagio, Long> {
	
	@Query("select e from Estagio e where e.id = :idEstagio")
	Estagio findById(@Param("idEstagio") Long idEstagio);

	Estagio findByIdAndEstagiario_Pessoa_Cpf(Long idEstagio, String cpf);
	
	@Query("select e from Estagio e where e.estagiario.pessoa.cpf = :cpf")
	List<Estagio> findByEstagiarioCpf(@Param("cpf") String cpf);

	@Query("select e from Estagio e where e.estagiario.id = :idEstagio and e.turma.id = :idTurma")
	Estagio findByIdEstagiarioAndIdTurma(@Param("idEstagio")Long idEstagiario, @Param("idTurma") Long idTurma);

	@Query("select e from Estagio e where e.id = :idEstagio and (e.turma.orientador.id = :idServidor OR :idServidor MEMBER OF e.turma.supervisores)")
	Estagio findByIdAndOrientadorOrSupervisor(@Param("idEstagio") Long idEstagio, @Param("idServidor") Long idServidor);
	

}
