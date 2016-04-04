package ufc.quixada.npi.gp.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.ufc.quixada.npi.ldap.service.UsuarioService;

import ufc.quixada.npi.gp.model.Estagiario;
import ufc.quixada.npi.gp.model.Frequencia;
import ufc.quixada.npi.gp.model.Papel;
import ufc.quixada.npi.gp.model.Pessoa;
import ufc.quixada.npi.gp.model.Servidor;
import ufc.quixada.npi.gp.model.Submissao;
import ufc.quixada.npi.gp.model.Turma;
import ufc.quixada.npi.gp.model.enums.StatusFrequencia;
import ufc.quixada.npi.gp.service.AvaliacaoService;
import ufc.quixada.npi.gp.model.enums.TipoFrequencia;
import ufc.quixada.npi.gp.service.EstagiarioService;
import ufc.quixada.npi.gp.service.FrequenciaService;
import ufc.quixada.npi.gp.service.PapelService;
import ufc.quixada.npi.gp.service.PessoaService;
import ufc.quixada.npi.gp.service.ServidorService;
import ufc.quixada.npi.gp.service.SubmissaoService;
import ufc.quixada.npi.gp.service.TurmaService;
import ufc.quixada.npi.gp.utils.Constants;

@Component
@Controller
@RequestMapping("supervisor")
public class SupervisorController {

	@Inject
	private PessoaService pessoaService;

	@Inject
	private AvaliacaoService avaliacaoService;

	@Inject
	private ServidorService servidorService;

	@Inject
	private UsuarioService usuarioService;

	@Inject
	private EstagiarioService estagiarioService;

	@Inject
	private PapelService papelService;

	@Inject
	private TurmaService turmaService;

	@Inject
	private FrequenciaService frequenciaService;

	@Inject
	private SubmissaoService submissaoService;
	
	@RequestMapping(value = { "", "/" }, method = RequestMethod.GET)
	public String paginaInicial(Model Model, HttpSession session) {

		String cpf = SecurityContextHolder.getContext().getAuthentication().getName();

		if (!pessoaService.isPessoa(cpf)) {

			Papel papel = papelService.getPapel("ROLE_SUPERVISOR");

			Pessoa pessoa = new Pessoa(cpf);
			pessoa.setPapeis(new ArrayList<Papel>());
			pessoa.getPapeis().add(papel);

			pessoaService.save(pessoa);

			Servidor servidor = new Servidor(pessoa, usuarioService.getByCpf(cpf).getSiape());
			servidorService.save(servidor);
		}

		return "redirect:/supervisor/turmas";
	}

	@RequestMapping(value = "/turmas", method = RequestMethod.GET)
	public String listarTurmas(Model model, HttpSession session) {
		Pessoa pessoa = getUsuarioLogado(session);
		model.addAttribute("turmas", turmaService.getTurmasBySupervisorId(pessoa.getId()));

		return "supervisor/list-turmas";
	}
	
	//@RequestMapping(value = "/")

	@RequestMapping(value = "/estagiario/{idEstagiario}/turma/{idTurma}/frequencia/pendente", method = RequestMethod.POST)
	public String lancarFrequencia(@PathVariable("idEstagiario") Long idEstagiario, @PathVariable("idTurma") Long idTurma, @RequestParam("data") Date data, @RequestParam("statusFrequencia") StatusFrequencia statusFrequencia, @RequestParam("observacao") String observacao, Model model, RedirectAttributes redirectAttributes) {

		Turma turma =turmaService.find(Turma.class, idTurma);
		Estagiario estagiario = estagiarioService.find(Estagiario.class, idEstagiario);

		Frequencia frequencia = frequenciaService.getFrequenciaByDataByTurmaByEstagiario(data, idTurma, idEstagiario);
		
		if (frequencia == null) {
			frequencia = new Frequencia();
			frequencia.setTurma(turma);
			frequencia.setEstagiario(estagiario);
			frequencia.setData(data);
			frequencia.setHorario(new Date());
			frequencia.setTipoFrequencia(TipoFrequencia.NORMAL);
			frequencia.setStatusFrequencia(statusFrequencia);
			frequencia.setObservacao(observacao);

			frequenciaService.save(frequencia);
			redirectAttributes.addFlashAttribute("sucesso", "Frequência lançada com sucesso");
		} else{
			redirectAttributes.addFlashAttribute("error", "Não é possivel lançar a Frequência para esta data");
		}

		return "redirect:/supervisor/turma/" + idTurma + "/estagiario/" + idEstagiario + "/frequencia";
	}

	@RequestMapping(value = "/turma/{idTurma}/acompanhamento-avaliacao/estagiario/{idEstagiario}", method = RequestMethod.GET)
	public String listarAcompanhamento(Model model, HttpSession session, @PathVariable("idEstagiario") Long idEstagiario, @PathVariable("idTurma") Long idTurma) {
		model.addAttribute("avaliacaoEstagio", avaliacaoService.getAvaliacoesEstagioByEstagiarioIdAndTurmaById(idEstagiario, idTurma));
		model.addAttribute("turma", turmaService.find(Turma.class, idTurma));
		model.addAttribute("estagiario", estagiarioService.find(Estagiario.class, idEstagiario));
		model.addAttribute("submissoes", submissaoService.getSubmissoesByEstagiarioIdAndIdTurma(idEstagiario, idTurma));

		return "supervisor/acompanhamentoAvaliacao";
	}

	@RequestMapping(value = "/frequencia/realizar-observacao", method = RequestMethod.POST)
	public String frequenciaObservar(@RequestParam("pk") Long idFrequencia, @RequestParam("value") String observacao,
			Model model) {
		Frequencia frequencia = frequenciaService.find(Frequencia.class, idFrequencia);

		if (frequencia != null) {
			frequencia.setObservacao(observacao);
			frequenciaService.update(frequencia);
			return "supervisor/list-frequencia-estagiario";
		}

		return "";
	}

	@RequestMapping(value = "/frequencia/atualizar-status", method = RequestMethod.POST)
	public String atualizarStatus(@RequestParam("pk") Long idFrequencia,
			@RequestParam("value") StatusFrequencia status, Model model, RedirectAttributes redirectAttributes) {
		Frequencia frequencia = frequenciaService.find(Frequencia.class, idFrequencia);

		if (frequencia != null) {
			frequencia.setStatusFrequencia(status);
			frequenciaService.update(frequencia);
			return "supervisor/list-frequencia-estagiario";
		}

		return "";
	}
	
	
	
	

	private Pessoa getUsuarioLogado(HttpSession session) {
		if (session.getAttribute(Constants.USUARIO_LOGADO) == null) {
			Pessoa pessoa = pessoaService.getPessoaByCpf(SecurityContextHolder.getContext().getAuthentication()
					.getName());
			session.setAttribute(Constants.USUARIO_LOGADO, pessoa);
		}
		return (Pessoa) session.getAttribute(Constants.USUARIO_LOGADO);
	}
	
	@RequestMapping(value = "/estagiario/{id}", method = RequestMethod.GET)
	public String infoEstagiario(@PathVariable("id") Long id, Model model) {
		model.addAttribute("estagiario", estagiarioService.find(Estagiario.class, id));
		return "supervisor/info-estagiario";
	}

	
	
}
