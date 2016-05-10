package ufc.quixada.npi.gp.controller;

import static ufc.quixada.npi.gp.utils.Constants.PAGINA_DECLARACAO_ESTAGIO;
import static ufc.quixada.npi.gp.utils.Constants.PAGINA_FORM_TURMA;
import static ufc.quixada.npi.gp.utils.Constants.PAGINA_INFO_TURMA;
import static ufc.quixada.npi.gp.utils.Constants.PAGINA_MAPA_FREQUENCIAS;
import static ufc.quixada.npi.gp.utils.Constants.PAGINA_FORM_VINCULOS;
import static ufc.quixada.npi.gp.utils.Constants.PAGINA_TCE;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.ufc.quixada.npi.ldap.model.Usuario;
import br.ufc.quixada.npi.ldap.service.UsuarioService;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import ufc.quixada.npi.gp.model.Estagiario;
import ufc.quixada.npi.gp.model.Frequencia;
import ufc.quixada.npi.gp.model.Horario;
import ufc.quixada.npi.gp.model.Papel;
import ufc.quixada.npi.gp.model.Pessoa;
import ufc.quixada.npi.gp.model.Servidor;
import ufc.quixada.npi.gp.model.Turma;
import ufc.quixada.npi.gp.model.enums.Dia;
import ufc.quixada.npi.gp.model.enums.StatusFrequencia;
import ufc.quixada.npi.gp.model.enums.StatusTurma;
import ufc.quixada.npi.gp.model.enums.TipoFrequencia;
import ufc.quixada.npi.gp.service.AvaliacaoService;
import ufc.quixada.npi.gp.service.DadoConsolidado;
import ufc.quixada.npi.gp.service.EstagiarioService;
import ufc.quixada.npi.gp.service.EventoService;
import ufc.quixada.npi.gp.service.FrequenciaService;
import ufc.quixada.npi.gp.service.HorarioService;
import ufc.quixada.npi.gp.service.PapelService;
import ufc.quixada.npi.gp.service.PessoaService;
import ufc.quixada.npi.gp.service.ServidorService;
import ufc.quixada.npi.gp.service.TurmaService;
import ufc.quixada.npi.gp.utils.Constants;
import ufc.quixada.npi.gp.utils.UtilGestao;

@Component
@Controller
@RequestMapping("Supervisor")
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
	private EventoService eventoService;
	
	@Inject
	private HorarioService horarioService;
	
	private JRDataSource jrDatasource;
	
	@RequestMapping(value = { "", "/", "/Turmas" }, method = RequestMethod.GET)
	public String getTurmas(Model model, HttpSession session) {

		String cpf = SecurityContextHolder.getContext().getAuthentication().getName();
		Pessoa usuarioLogado = getUsuarioLogado(session);
		model.addAttribute("turmas", turmaService.getTurmasBySupervisorId(usuarioLogado.getId()));

		if (!pessoaService.isPessoa(cpf)) {

			Papel papel = papelService.getPapel("ROLE_SUPERVISOR");

			Pessoa pessoa = new Pessoa(cpf);
			pessoa.setPapeis(new ArrayList<Papel>());
			pessoa.getPapeis().add(papel);

			pessoaService.save(pessoa);

			Servidor servidor = new Servidor(pessoa, usuarioService.getByCpf(cpf).getSiape());
			servidorService.save(servidor);
		}
		
		return "supervisor/list-turmas";
	}

	@RequestMapping(value = "/Turma/{idTurma}", method = RequestMethod.GET)
	public String getInfoTurma(@PathVariable("idTurma") Long idTurma, Model model, HttpSession session) {
		Pessoa pessoa = getUsuarioLogado(session);
		
		model.addAttribute("turma", turmaService.getTurmaByIdAndSupervisorById(idTurma, pessoa.getId()));
		turmaService.getTurmaByIdAndSupervisorById(idTurma, pessoa.getId());
		
		
		List<Estagiario> aniversariantes = estagiarioService.getAniversariantesMesByTurmaId(idTurma);
		model.addAttribute("aniversariantes", aniversariantes);	
		
		return PAGINA_INFO_TURMA;
	}
	
	@RequestMapping(value = "/Turma/Adicionar", method = RequestMethod.GET)
	public String getAdicionarTurma(Model model) {
		
		model.addAttribute("action", "cadastrar");
		model.addAttribute("turma", new Turma());
		model.addAttribute("dias", Dia.values());
		
		return PAGINA_FORM_TURMA;
	}
	
	@RequestMapping(value = "/Turma/Adicionar", method = RequestMethod.POST)
	public String postAdicionarTurma(Model model, @Valid @ModelAttribute("turma") Turma turma, BindingResult result,
			HttpSession session, RedirectAttributes redirect) {
		
		model.addAttribute("action", "cadastrar");
		turma.setHorarios(atualizarHorarios(turma));

		if (result.hasErrors()) {
			model.addAttribute("dias", Dia.values());
			return PAGINA_FORM_TURMA;
		}

		Pessoa pessoa = getUsuarioLogado(session);

		turma.setSupervisor(pessoa);
		turmaService.save(turma);

		redirect.addFlashAttribute("success", "Turma cadastrada com sucesso.");

		return "redirect:/supervisor/turmas";
	}

	@RequestMapping(value = "/Turma/{idTurma}/Editar", method = RequestMethod.GET)
	public String getEditarTurma(@PathVariable("idTurma") Long idTurma, Model model, HttpSession session) {
		model.addAttribute("action", "editar");

		Pessoa pessoa = getUsuarioLogado(session);

		model.addAttribute("turma", turmaService.getTurmaByIdAndSupervisorById(idTurma, pessoa.getId()));
		model.addAttribute("dias", Dia.values());

		return PAGINA_FORM_TURMA;
	}

	@RequestMapping(value = "/Turma/{idTurma}/Editar", method = RequestMethod.POST)
	public String postEditarTurma(Model model, @Valid @ModelAttribute("turma") Turma turma, BindingResult result,
			HttpSession session) {

		model.addAttribute("action", "editar");

		if (result.hasErrors()) {
			model.addAttribute("dias", Dia.values());
			return PAGINA_FORM_TURMA;
		}

		Pessoa pessoa = getUsuarioLogado(session);
		Turma turmaDoBanco = turmaService.getTurmaByIdAndSupervisorById(turma.getId(), pessoa.getId());

		turmaDoBanco.setNome(turma.getNome());
		turmaDoBanco.setStatusTurma(turma.getStatusTurma());
		turmaDoBanco.setTipoTurma(turma.getTipoTurma());
		turmaDoBanco.setSemestre(turma.getSemestre());
		turmaDoBanco.setInicio(turma.getInicio());
		turmaDoBanco.setTermino(turma.getTermino());

		turmaService.update(turmaDoBanco);

		return "redirect:/supervisor/turmas";
	}

	@RequestMapping(value = "/Turma/{idTurma}/tce", method = RequestMethod.GET)
	public String gerarTermoDeCompromisso(@PathVariable("idTurma") Long idTurma, Model model) throws JRException {

		Turma turma = turmaService.find(Turma.class, idTurma);

		Usuario usuario = usuarioService.getByCpf(turma.getSupervisor().getCpf());

		jrDatasource = new JRBeanCollectionDataSource(turma.getEstagiarios());

		SimpleDateFormat dataFormatada = new SimpleDateFormat("dd/MM/yyyy");

		model.addAttribute("NOME", usuario.getNome());
		model.addAttribute("SIAPE", usuario.getSiape());
		model.addAttribute("TELEFONE", usuario.getTelefone());
		model.addAttribute("TURNO", UtilGestao.getTurnoExpediente(turma.getHorarios().get(0)));
		model.addAttribute("INICIO_ESTAGIO", dataFormatada.format(turma.getInicio()));
		model.addAttribute("FINAL_ESTAGIO", dataFormatada.format(turma.getTermino()));
		model.addAttribute("datasource", jrDatasource);
		model.addAttribute("format", "pdf");

		if (turma.getHorarios() != null) {
			model = configurarExpediente(turma.getHorarios(), model);
		}

		return PAGINA_TCE;
	}

	@RequestMapping(value = "/Turma/{idTurma}/declaracoes", method = RequestMethod.GET)
	public String gerarDeclaracaoEstagio(Model model, @PathVariable("idTurma") Long idTurma) throws JRException {
		jrDatasource = new JRBeanCollectionDataSource(estagiarioService.getEstagiarioByTurmaId(idTurma));

		model.addAttribute("datasource", jrDatasource);
		model.addAttribute("format", "pdf");
		return PAGINA_DECLARACAO_ESTAGIO;
	}

	/* VERIFICAR UTILIZAÇÃO 

	@RequestMapping(value = "/Turma/{idTurma}/Expediente/Adicionar", method = RequestMethod.POST)
	public String postAdicionarExpediente(Model model, @Valid @ModelAttribute("horario") Horario horario,
			@PathVariable("idTurma") Long idTurma, BindingResult result, HttpSession session,
			RedirectAttributes redirect) {

		if (result.hasErrors()) {
			model.addAttribute("dias", Dia.values());
			return "supervisor/form-horario";
		}

		Pessoa supervisor = getUsuarioLogado(session);
		Turma turma = turmaService.getTurmaByIdAndSupervisorById(idTurma, supervisor.getId());
		horario.setTurma(turma);

		horarioService.save(horario);

		redirect.addFlashAttribute("success", "Horário cadastrado com sucesso.");

		return "redirect:/supervisor/turma/" + idTurma + "/expediente";
	}
	
	@RequestMapping(value = "/Turma/{idTurma}/Expediente/{idHorario}/Excluir", method = RequestMethod.GET)
	public String getExcluirExpediente(@PathVariable("idHorario") Long idHorario,
			@PathVariable("idTurma") Long idTurma,
			RedirectAttributes redirect) {

		horarioService.delete(horarioService.find(Horario.class, idHorario));

		redirect.addFlashAttribute("success", "Horário excluído com sucesso.");

		return "redirect:/supervisor/turma/" + idTurma + "/expediente";
	}

	@RequestMapping(value = "/Turma/{idTurma}/Evento/Adicionar", method = RequestMethod.POST)
	public String postAdicionarEventos(@ModelAttribute("evento") Evento evento, @PathVariable("idTurma") Long idTurma,
			HttpSession session, RedirectAttributes redirect, Model model) {
		model.addAttribute("action", "cadastrar");
		Pessoa supervisor = getUsuarioLogado(session);
		Turma turma = turmaService.getTurmaByIdAndSupervisorById(idTurma, supervisor.getId());

		evento.setTurma(turma);
		eventoService.save(evento);

		redirect.addFlashAttribute("success", "Evento cadastrado com sucesso.");

		return "redirect:/supervisor/turma/" + idTurma + "/evento";
	}
	
	@RequestMapping(value="/Turma/{idTurma}/Evento/{idEvento}/Editar", method = RequestMethod.POST)
	public String postEditarEvento(@ModelAttribute("evento") Evento evento, RedirectAttributes redirect){
		eventoService.update(evento);
		redirect.addFlashAttribute("success", "Alterações realizadas com sucesso!");
		return "redirect:/supervisor/turma/" + evento.getTurma().getId() + "/evento";	
	}
	
	@RequestMapping(value="/Turma/{idTurma}/Evento/{idEvento}/Excluir", method = RequestMethod.GET)
	public String getExcluirEvento(@PathVariable("idEvento") Long idEvento, 
		@PathVariable("idTurma") Long idTurma,
		RedirectAttributes redirect){
	eventoService.delete(eventoService.find(Evento.class, idEvento));
	
	redirect.addFlashAttribute("success", "Evento excluído com sucesso!");
	
	return "redirect:/supervisor/turma/" + idTurma + "/evento";
	}


*/
	@RequestMapping(value = "/Turma/{idTurma}/Vincular", method = RequestMethod.GET)
	public String paginaVincularEstagiarioTurma(Model model, HttpSession session, @PathVariable("idTurma") Long idTurma) {
		Pessoa pessoa = getUsuarioLogado(session);

		model.addAttribute("turma", turmaService.getTurmaByIdAndSupervisorById(idTurma, pessoa.getId()));
		model.addAttribute("estagiariosDaTurma", estagiarioService.getEstagiarioByTurmaId(idTurma));
		model.addAttribute("outrosEstagiarios", estagiarioService.getEstagiarioByNotTurmaIdOrSemTurma(idTurma));

		return PAGINA_FORM_VINCULOS;
	}


	@RequestMapping(value = "/Turma/{idTurma}/vincular", method = RequestMethod.POST)
	public String atualizarVinculoEstagiarioTurma(Model model, HttpSession session,
			@ModelAttribute("turma") Turma turma) {
		Pessoa pessoa = getUsuarioLogado(session);

		Turma turmaDoBanco = turmaService.getTurmaByIdAndSupervisorById(turma.getId(), pessoa.getId());

		List<Estagiario> estagiariosSelecionados = new ArrayList<Estagiario>();

		if (turma.getEstagiarios() != null) {
			estagiariosSelecionados = getEstagiariosSelecionados(turma.getEstagiarios());
			estagiariosSelecionados = atualizarTurmaEstagiarios(estagiariosSelecionados, turmaDoBanco);
		}

		turmaDoBanco.setEstagiarios(estagiariosSelecionados);

		turmaService.update(turmaDoBanco);

		model.addAttribute("turma", turmaDoBanco);
		model.addAttribute("estagiarios", estagiarioService.find(Estagiario.class));

		return "redirect:/supervisor/turma/" + turmaDoBanco.getId();
	}
	
	@RequestMapping(value = "/Turma/{idTurma}/MapaFrequencia", method = RequestMethod.GET)
	public String listarFrequenciaTurma(@PathVariable("idTurma") Long idTurma, Model model, HttpSession session) {
		Pessoa pessoa = getUsuarioLogado(session);
		Date dataAtual = new Date();
		List<Frequencia> frequencias = frequenciaService.getFrequenciasByTurmaIdAndData(dataAtual, idTurma);
		List<Estagiario> estagiarios = frequenciaService.getEstagiariosSemFrequencia(dataAtual, idTurma);

		model.addAttribute("turma", turmaService.getTurmaByIdAndSupervisorById(idTurma, pessoa.getId()));
		model.addAttribute("frequencias", frequencias);
		model.addAttribute("estagiarios", estagiarios);
		model.addAttribute("dataAtual", dataAtual);

		return PAGINA_MAPA_FREQUENCIAS;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
/*	
	
	@RequestMapping( value = "{idTurma}/submissao/{idSubmissao}/estagiario/{idEstagiario}/salvar-submissao-estagiario", method = RequestMethod.POST)
	public String salvarSubmisssaoEstagiario(Model model, @Valid @ModelAttribute("submissao") Submissao submissao,
			HttpSession session, RedirectAttributes redirect, @PathVariable("idEstagiario") Long idEstagiario,
			@PathVariable("idTurma") Long idTurma, @PathVariable("idSubmissao") Long idSubmissao){
		
		Submissao submissaoDoBanco = turmaService.getSubmissaoById(submissao.getId());
		Estagiario estagiario = estagiarioService.find(Estagiario.class, idEstagiario);
		Turma turma = turmaService.getTurmaByIdAndEstagiarioId(idTurma, idEstagiario);

		submissaoDoBanco.setEstagiario(estagiario);
		submissaoDoBanco.setNota(submissao.getNota());
		submissaoDoBanco.setStatusEntrega(submissao.getStatusEntrega());
		submissaoDoBanco.setComentario(submissao.getComentario());

		turma.getSubmissoes().add(submissaoDoBanco);
		turmaService.update(turma);
		
		return "redirect:/supervisor/turma/{idTurma}/submissao/{idSubmissao}/estagiario/{idEstagiario}/avaliar-submissao-estagiario";
	}
	
	@RequestMapping(value = "{idTurma}/acompanhamento-avaliacao/estagiario/{idEstagiario}/adicionar/", method = RequestMethod.GET)

	public String novaAvaliacaoEstagio(Model model, @PathVariable("idEstagiario") Long idEstagiario, @PathVariable("idTurma") Long idTurma) {	
		Turma turma = turmaService.getTurmaByIdAndEstagiarioId(idTurma, idEstagiario);
		boolean showTurmaNPI = true;
		if(turma.getTipoTurma().getLabel() == "Empresa"){
			showTurmaNPI = false;
		}

		model.addAttribute("action", "cadastrar");
		model.addAttribute("avaliacaoRendimento", new AvaliacaoRendimento());

		model.addAttribute("turma",turma);
		model.addAttribute("estagiario",estagiarioService.find(Estagiario.class, idEstagiario));

		
		model.addAttribute("frequencias",Frequencias.values());
		model.addAttribute("permanencias",Permanencia.values());
		model.addAttribute("disciplinas",Disciplina.values());
		model.addAttribute("quantidades",QuantidadeDeTrabalho.values());
		model.addAttribute("qualidades",QualidadeDeTrabalho.values());
		model.addAttribute("cumprimentos",CumprimentoPrazos.values());
		model.addAttribute("frequencias",Frequencias.values());
		model.addAttribute("iniciativas",Iniciativa.values());
		model.addAttribute("comprometimentos",Comprometimento.values());
		model.addAttribute("cuidados",CuidadoMateriais.values());
		model.addAttribute("relacionamentos",Relacionamento.values());
		model.addAttribute("trabalhos",TrabalhoEmEquipe.values());
		model.addAttribute("showTurmaNPI", showTurmaNPI);
		return "supervisor/form-avaliacao-estagio";
	}

	@RequestMapping(value = "{idTurma}/avaliacao/{idAvaliacaoRendimento}/estagiario/{idEstagiario}/editar", method = RequestMethod.GET)
	public String paginaEditarAvaliacaoEstagio(@PathVariable("idEstagiario") Long idEstagiario,
			@PathVariable("idTurma") Long idTurma, @PathVariable("idAvaliacaoRendimento") Long idAvaliacaoRendimento,
			Model model, HttpSession session) {
		model.addAttribute("action", "editar");
		model.addAttribute("avaliacaoRendimento",
				avaliacaoService.find(AvaliacaoRendimento.class, idAvaliacaoRendimento));
		model.addAttribute("turma", turmaService.getTurmaByIdAndEstagiarioId(idTurma, idEstagiario));
		model.addAttribute("estagiario", estagiarioService.find(Estagiario.class, idEstagiario));
		return "supervisor/form-avaliacao-estagio";
	}

	@RequestMapping(value = "{idTurma}/avaliacao/{idAvaliacaoRendimento}/estagiario/{idEstagiario}/editar", method = RequestMethod.POST)
	public String editarAvaliacaoEstagio(Model model,
			@Valid @ModelAttribute("avaliacaoRendimento") AvaliacaoRendimento avaliacaoRendimento, HttpSession session,
			RedirectAttributes redirect, @PathVariable("idEstagiario") Long idEstagiario,
			@PathVariable("idTurma") Long idTurma) {

		model.addAttribute("action", "editar");
		AvaliacaoRendimento avaliacaoDoBanco = avaliacaoService.find(AvaliacaoRendimento.class,
				avaliacaoRendimento.getId());
		Pessoa pessoa = getUsuarioLogado(session);
		Estagiario estagiario = estagiarioService.find(Estagiario.class, idEstagiario);
		Turma turma = turmaService.getTurmaByIdAndEstagiarioId(idTurma, idEstagiario);

		avaliacaoDoBanco.setSupervisor(pessoa);
		avaliacaoDoBanco.setEstagiario(estagiario);
		avaliacaoDoBanco.setTurma(turma);
		avaliacaoDoBanco.setNota(avaliacaoRendimento.getNota());
		avaliacaoDoBanco.setFatorAssiduidadeDisciplina(avaliacaoRendimento.getFatorAssiduidadeDisciplina());
		avaliacaoDoBanco.setFatorIniciativaProdutividade(avaliacaoRendimento.getFatorIniciativaProdutividade());
		avaliacaoDoBanco.setFatorRelacionamento(avaliacaoRendimento.getFatorRelacionamento());
		avaliacaoDoBanco.setFatorResponsabilidade(avaliacaoRendimento.getFatorResponsabilidade());
		avaliacaoDoBanco.setNotaSeminario(avaliacaoRendimento.getNotaSeminario());
		avaliacaoDoBanco.setFatorComentarioSeminario(avaliacaoRendimento.getFatorComentarioSeminario());
		
		avaliacaoService.update(avaliacaoDoBanco);

		return "redirect:/supervisor/turma/{idTurma}/acompanhamento-avaliacao/estagiario/{idEstagiario}";
	}

	@RequestMapping(value = "{idTurma}/submissao/{idSubmissao}/estagiario/{idEstagiario}/avaliar-submissao-estagiario", method = RequestMethod.GET)
	public String avaliarSubmissaoEstagiario(Model model, @Valid @ModelAttribute("submissoes") Submissao submissao,
			HttpSession session, RedirectAttributes redirect, @PathVariable("idEstagiario") Long idEstagiario,
			@PathVariable("idTurma") Long idTurma, @PathVariable("idSubmissao") Long idSubmissao) {
		
		model.addAttribute("turma", turmaService.getTurmaByIdAndEstagiarioId(idTurma, idEstagiario));
		model.addAttribute("submissao", turmaService.getSubmissaoById(idSubmissao));
		model.addAttribute("estagiario", estagiarioService.find(Estagiario.class, idEstagiario));
		model.addAttribute("Submissao", new Submissao());
		
		return "supervisor/avaliar-submissao";
	}
	
	@RequestMapping(value = "{idTurma}/acompanhamento-avaliacao/estagiario/{idEstagiario}/adicionar/", method = RequestMethod.POST)
	public String adicionarAvaliacaoEstagio(Model model,
			@Valid @ModelAttribute("avaliacaoRendimento") AvaliacaoRendimento avaliacaoRendimento, HttpSession session,
			RedirectAttributes redirect, @PathVariable("idEstagiario") Long idEstagiario,
			@PathVariable("idTurma") Long idTurma, @Valid @RequestParam("nota") Double nota, @Valid @RequestParam("rendimento") MultipartFile rendimento){

		if(!rendimento.getContentType().equals("application/pdf")){
			redirect.addFlashAttribute("error", "Escolha um arquivo pdf.");
			return "redirect:/supervisor/turma/{idTurma}/acompanhamento-avaliacao/estagiario/{idEstagiario}";
		}
		
		model.addAttribute("action", "cadastrar");
		Pessoa pessoa = getUsuarioLogado(session);
		Estagiario estagiario = estagiarioService.find(Estagiario.class, idEstagiario);
		Turma turma = turmaService.getTurmaByIdAndEstagiarioId(idTurma, idEstagiario);

		Tipo tipo = Tipo.AVALIACAO_RENDIMENTO;
		
		try {
			turmaService.submeterDocumento(estagiario, turma, tipo, rendimento);
		} catch (IOException e) {
			return "redirect:/500";
		}
		
		Submissao submissao = turmaService.getSubmissaoByEstagiarioIdAndIdTurmaAndTipo(idEstagiario, idTurma, tipo);
		Documento documento = submissao.getDocumento();
		avaliacaoRendimento.setSupervisor(pessoa);
		avaliacaoRendimento.setTurma(turma);
		avaliacaoRendimento.setEstagiario(estagiario);
		avaliacaoRendimento.setNota(nota);
		avaliacaoRendimento.setDocumento(documento);
		avaliacaoService.save(avaliacaoRendimento);
		redirect.addFlashAttribute("success", "Avaliação cadastrada com sucesso.");

		return "redirect:/supervisor/turma/{idTurma}/acompanhamento-avaliacao/estagiario/{idEstagiario}";
	}
	
	@RequestMapping(value = "/turma/{idTurma}/acompanhamento-avaliacao/estagiario/{idEstagiario}", method = RequestMethod.GET)
	public String listarAcompanhamento(Model model, HttpSession session,
			@PathVariable("idEstagiario") Long idEstagiario, @PathVariable("idTurma") Long idTurma) {
		model.addAttribute("avaliacaoEstagio",
				avaliacaoService.getAvaliacoesEstagioByEstagiarioIdAndTurmaById(idEstagiario, idTurma));
		model.addAttribute("turma", turmaService.find(Turma.class, idTurma));
		model.addAttribute("estagiario", estagiarioService.find(Estagiario.class, idEstagiario));
		model.addAttribute("submissoes", turmaService.getSubmissoesByEstagiarioIdAndIdTurma(idEstagiario, idTurma));

		return "supervisor/acompanhamentoAvaliacao";
	}
	
	
*/
	


	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	



	@RequestMapping(value = "/{idTurma}/estagiario/{idEstagiario}/frequencia", method = RequestMethod.GET)
	public String minhaPresenca(HttpSession session, Model model, @PathVariable("idTurma") Long idTurma,
			@PathVariable("idEstagiario") Long idEstagiario) {

		Estagiario estagiario = estagiarioService.find(Estagiario.class, idEstagiario);
		Turma turma = turmaService.find(Turma.class, idTurma);
		
		List<Frequencia> frequenciaCompleta = new ArrayList<Frequencia>();
		List<Frequencia> frequenciaPendentes =  frequenciaService.frequenciaPendente(turma, estagiario);
		
		frequenciaCompleta = frequenciaService.gerarFrequencia(turma, estagiario);
		DadoConsolidado dadosConsolidados = frequenciaService.calcularDadosConsolidados(frequenciaCompleta);
		
		System.out.println("Pendentes: " + frequenciaPendentes.size());
		
		model.addAttribute("estagiario", estagiario);
		model.addAttribute("turma", turma);
		model.addAttribute("frequencias", frequenciaCompleta);
		
		model.addAttribute("pendentes", frequenciaPendentes.size());
		
		model.addAttribute("dadosConsolidados", dadosConsolidados);
		model.addAttribute("statusFrequencias", StatusFrequencia.values());
		model.addAttribute("dataAtual", new Date());

		return "supervisor/list-frequencia-estagiario";
	}

	@RequestMapping(value = "/{idTurma}/frequencias", method = RequestMethod.POST)
	public String listarFrequenciaTurmaData(@PathVariable("idTurma") Long idTurma, @RequestParam("data") Date data,
			Model model, HttpSession session) {
		Pessoa pessoa = getUsuarioLogado(session);

		List<Frequencia> frequencias = frequenciaService.getFrequenciasByTurmaIdAndData(data, idTurma);
		
		List<Estagiario> estagiarios = frequenciaService.getEstagiariosSemFrequencia(data, idTurma);
		
		model.addAttribute("turma", turmaService.getTurmaByIdAndSupervisorById(idTurma, pessoa.getId()));
		model.addAttribute("turmas", turmaService.getTurmasBySupervisorIdAndStatus(StatusTurma.ABERTA, pessoa.getId()));
		model.addAttribute("frequencias", frequencias);
		model.addAttribute("estagiarios", estagiarios);
		model.addAttribute("dataAtual", new Date());
		return "supervisor/list-frequencias";
	}
	

	
	@RequestMapping(value = "/estagiario/{idEstagiario}/turma/{idTurma}/frequencia/pendente", method = RequestMethod.POST)
	public String lancarFrequencia(@PathVariable("idEstagiario") Long idEstagiario,
			@PathVariable("idTurma") Long idTurma, @RequestParam("data") Date data,
			@RequestParam("statusFrequencia") StatusFrequencia statusFrequencia,
			@RequestParam("observacao") String observacao, Model model, RedirectAttributes redirectAttributes) {

		Turma turma = turmaService.find(Turma.class, idTurma);
		Estagiario estagiario = estagiarioService.find(Estagiario.class, idEstagiario);

		Frequencia frequencia = frequenciaService.getFrequenciaByDataByTurmaByEstagiario(data, idTurma, idEstagiario);

		if (frequencia == null) {

			if (statusFrequencia == null) {
				redirectAttributes.addFlashAttribute("error", "Escolha um Status Válido");
			} else {
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
			}

		} else {
			redirectAttributes.addFlashAttribute("error", "Não é possivel lançar a Frequência para esta data");
		}

		return "redirect:/supervisor/turma/" + idTurma + "/estagiario/" + idEstagiario + "/frequencia";
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
	public String atualizarStatus(@RequestParam("pk") Long idFrequencia, @RequestParam("value") StatusFrequencia status,
			Model model, RedirectAttributes redirectAttributes) {
		Frequencia frequencia = frequenciaService.find(Frequencia.class, idFrequencia);

		if (frequencia != null) {
			frequencia.setStatusFrequencia(status);
			frequenciaService.update(frequencia);
			return "supervisor/list-frequencia-estagiario";
		}

		return "";
	}


	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@RequestMapping(value = "/estagiario/{id}", method = RequestMethod.GET)
	public String infoEstagiario(@PathVariable("id") Long id, Model model) {
		model.addAttribute("estagiario", estagiarioService.find(Estagiario.class, id));
		return "supervisor/info-estagiario";
	}



	
	
	
	
	
	
	
	
	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	private List<Estagiario> atualizarTurmaEstagiarios(List<Estagiario> estagiarios, Turma turma) {
		for (Estagiario estagiario : estagiarios) {
			if (estagiario.getTurmas() != null) {
				if (!estagiario.getTurmas().contains(turma)) {
					estagiario.getTurmas().add(turma);
				}
			} else {
				estagiario.setTurmas(new ArrayList<Turma>());
				estagiario.getTurmas().add(turma);
			}
		}

		return estagiarios;
	}

	private List<Horario> atualizarHorarios(Turma turma) {
		List<Horario> horariosAtualizados = new ArrayList<Horario>();

		if (turma.getHorarios() == null) {
			return null;
		}

		for (Horario horario : turma.getHorarios()) {
			if (horario.getDia() != null) {
				horariosAtualizados.add(horario);
			}
		}
		return horariosAtualizados;
	}

	private Model configurarExpediente(List<Horario> horarios, Model model) {
		SimpleDateFormat horaFormatada = new SimpleDateFormat("HH:mm");

		for (Horario horario : horarios) {

			String expediente = horaFormatada.format(horario.getInicioExpediente()) + " as "
					+ horaFormatada.format(horario.getFinalExpediente());

			if (horario.getDia().equals(Dia.SEGUNDA)) {
				model.addAttribute("EXPEDIENTE_SEGUNDA", expediente);
			}
			if (horario.getDia().equals(Dia.TERCA)) {
				model.addAttribute("EXPEDIENTE_TERCA", expediente);
			}
			if (horario.getDia().equals(Dia.QUARTA)) {
				model.addAttribute("EXPEDIENTE_QUARTA", expediente);
			}
		}
		return model;

	}


	private List<Estagiario> getEstagiariosSelecionados(List<Estagiario> estagiarios) {
		List<Estagiario> estagiariosSelecionados = new ArrayList<Estagiario>();

		for (Estagiario estagiario : estagiarios) {
			if (estagiario.getId() != null) {
				estagiario = estagiarioService.find(Estagiario.class, estagiario.getId());

				estagiariosSelecionados.add(estagiario);
			}
		}

		return estagiariosSelecionados;
	}

	private Pessoa getUsuarioLogado(HttpSession session) {
		if (session.getAttribute(Constants.USUARIO_LOGADO) == null) {
			Pessoa pessoa = pessoaService
					.getPessoaByCpf(SecurityContextHolder.getContext().getAuthentication().getName());
			session.setAttribute(Constants.USUARIO_LOGADO, pessoa);
		}
		return (Pessoa) session.getAttribute(Constants.USUARIO_LOGADO);
	}
}
