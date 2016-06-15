package br.ufc.quixada.npi.gp.controller;

import static br.ufc.quixada.npi.gp.utils.Constants.ACOMPANHAMENTO_ESTAGIARIO;
import static br.ufc.quixada.npi.gp.utils.Constants.AVALIAR_RELATORIO;
import static br.ufc.quixada.npi.gp.utils.Constants.DECLARACAO_ESTAGIO;
import static br.ufc.quixada.npi.gp.utils.Constants.DETALHES_FREQUENCIA_ESTAGIARIO;
import static br.ufc.quixada.npi.gp.utils.Constants.DETALHES_TURMA;
import static br.ufc.quixada.npi.gp.utils.Constants.FORMULARIO_ADICIONAR_AVALIACAO_RENDIMENTO;
import static br.ufc.quixada.npi.gp.utils.Constants.FORMULARIO_ADICIONAR_TURMA;
import static br.ufc.quixada.npi.gp.utils.Constants.FORMULARIO_AVALIAR_PLANO;
import static br.ufc.quixada.npi.gp.utils.Constants.FORMULARIO_EDITAR_AVALIACAO_RENDIMENTO;
import static br.ufc.quixada.npi.gp.utils.Constants.FORMULARIO_EDITAR_TURMA;
import static br.ufc.quixada.npi.gp.utils.Constants.MAPA_FREQUENCIAS;
import static br.ufc.quixada.npi.gp.utils.Constants.NOME_USUARIO;
import static br.ufc.quixada.npi.gp.utils.Constants.PAGINA_INICIAL_SUPERVISOR;
import static br.ufc.quixada.npi.gp.utils.Constants.REDIRECT_ACOMPANHAMENTO_ESTAGIARIO;
import static br.ufc.quixada.npi.gp.utils.Constants.REDIRECT_DETALHES_TURMA;
import static br.ufc.quixada.npi.gp.utils.Constants.REDIRECT_PAGINA_INICIAL_SUPERVISOR;
import static br.ufc.quixada.npi.gp.utils.Constants.TERMO_COMPROMISSO_ESTAGIO;
import static br.ufc.quixada.npi.gp.utils.Constants.VINCULOS_TURMA;
import static br.ufc.quixada.npi.gp.utils.Constants.FORMULARIO_EVENTO;
import static br.ufc.quixada.npi.gp.utils.Constants.FORMULARIO_EXPEDIENTE;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.ufc.quixada.npi.gp.model.AvaliacaoRendimento;
import br.ufc.quixada.npi.gp.model.Estagio;
import br.ufc.quixada.npi.gp.model.Evento;
import br.ufc.quixada.npi.gp.model.Expediente;
import br.ufc.quixada.npi.gp.model.Papel;
import br.ufc.quixada.npi.gp.model.Pessoa;
import br.ufc.quixada.npi.gp.model.Servidor;
import br.ufc.quixada.npi.gp.model.Submissao;
import br.ufc.quixada.npi.gp.model.Submissao.StatusEntrega;
import br.ufc.quixada.npi.gp.model.Submissao.TipoSubmissao;
import br.ufc.quixada.npi.gp.model.Turma;
import br.ufc.quixada.npi.gp.service.EstagioService;
import br.ufc.quixada.npi.gp.service.PessoaService;
import br.ufc.quixada.npi.gp.service.TurmaService;
import br.ufc.quixada.npi.gp.utils.UtilGestao;
import br.ufc.quixada.npi.ldap.model.Usuario;
import br.ufc.quixada.npi.ldap.service.UsuarioService;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Component
@Controller
@RequestMapping("Supervisor")
public class SupervisorController {

	@Autowired
	private PessoaService pessoaService;

	@Autowired
	private UsuarioService usuarioService;


	@Autowired
	private EstagioService estagioService;

	@Autowired
	private TurmaService turmaService;
	//
	
	private JRDataSource jrDatasource;


	@RequestMapping(value = { "", "/", "/Turmas" }, method = RequestMethod.GET)
	public String listarTurmas(Model model, HttpSession session) {
		inserirNomeUsuarioNaSessao(session);

		Pessoa pessoa = pessoaService.buscarPessoaPorCpf(getCpfUsuarioLogado());

		if (pessoa == null) {
			pessoa = new Pessoa();
			pessoa.setCpf(getCpfUsuarioLogado());
			pessoa.setPapeis(new ArrayList<Papel>());
			pessoa.getPapeis().add(pessoaService.buscarPapelPorNome("ROLE_SUPERVISOR"));

			pessoaService.adicionarPessoa(pessoa);

			Servidor servidor = new Servidor();
			servidor.setPessoa(pessoa);
			servidor.setSiape(usuarioService.getByCpf(getCpfUsuarioLogado()).getSiape());

			pessoaService.adicionarServidor(servidor);
		}

		return PAGINA_INICIAL_SUPERVISOR;
	}

	@RequestMapping(value = "/Turma/Adicionar", method = RequestMethod.GET)
	public String formularioAdicionarTurma(Model model) {
		// model.addAttribute("turma", new Turma());
		return FORMULARIO_ADICIONAR_TURMA;
	}

	@RequestMapping(value = "/Turma/Adicionar", method = RequestMethod.POST)
	public String adicionarTurma(Model model, @Valid @ModelAttribute("turma") Turma turma, BindingResult result,
			HttpSession session, RedirectAttributes redirect) {

		// model.addAttribute("action", "cadastrar");
		// turma.setHorarios(atualizarHorarios(turma));
		//
		// if (result.hasErrors()) {
		// model.addAttribute("dias", Dia.values());
		// return PAGINA_FORM_TURMA;
		// }
		//
		// String cpf =
		// SecurityContextHolder.getContext().getAuthentication().getName();
		// Pessoa pessoa = pessoaService.buscarPessoaPorCpf(cpf);
		//
		// turma.setSupervisor(pessoa);
		// turmaService.save(turma);

		redirect.addFlashAttribute("success", "Turma cadastrada com sucesso.");

		return REDIRECT_DETALHES_TURMA;
	}

	@RequestMapping(value = "/Turma/{idTurma}/Editar", method = RequestMethod.GET)
	public String formularioEditarTurma(@PathVariable("idTurma") Long idTurma, Model model, HttpSession session) {

		// String cpf =
		// SecurityContextHolder.getContext().getAuthentication().getName();
		// Pessoa pessoa = pessoaService.buscarPessoaPorCpf(cpf);
		//
		// model.addAttribute("turma",
		// turmaService.getTurmaByIdAndSupervisorById(idTurma, pessoa.getId()));

		return FORMULARIO_EDITAR_TURMA;
	}

	@RequestMapping(value = "/Turma/{idTurma}/Editar", method = RequestMethod.POST)
	public String editarTurma(Model model, @Valid @ModelAttribute("turma") Turma turma, BindingResult result,
			HttpSession session) {

		// if (result.hasErrors()) {
		// model.addAttribute("dias", Dia.values());
		// return FORMULARIO_EDITAR_ESTAGIARIO;
		// }
		//
		// Pessoa pessoa = getUsuarioLogado(session);
		// Turma turmaDoBanco =
		// turmaService.getTurmaByIdAndSupervisorById(turma.getId(),
		// pessoa.getId());
		//
		// turmaDoBanco.setNome(turma.getNome());
		// turmaDoBanco.setStatusTurma(turma.getStatusTurma());
		// turmaDoBanco.setTipoTurma(turma.getTipoTurma());
		// turmaDoBanco.setSemestre(turma.getSemestre());
		// turmaDoBanco.setInicio(turma.getInicio());
		// turmaDoBanco.setTermino(turma.getTermino());
		//
		// turmaService.update(turmaDoBanco);

		return REDIRECT_DETALHES_TURMA;
	}

	@RequestMapping(value = "/Turma/{idTurma}", method = RequestMethod.GET)
	public String visualizarDetalhesTurma(@PathVariable("idTurma") Long idTurma, RedirectAttributes redirect, Model model, HttpSession session) {

		Pessoa pessoa = pessoaService.buscarPessoaPorCpf(getCpfUsuarioLogado());

		Turma turma = turmaService.buscarTurmaPorId(idTurma);

		if (turma == null) {
			redirect.addFlashAttribute("error", "Turma não existe");
			return REDIRECT_PAGINA_INICIAL_SUPERVISOR;
		}

		List<Servidor> supervisores = turma.getSupervisores();
		for (Servidor servidor : supervisores) {
			if (servidor.getPessoa().getCpf().equals(pessoa.getCpf())) {
				model.addAttribute("turma", turma);
				return DETALHES_TURMA;
			}
		}

		redirect.addFlashAttribute("error", "Permissão negada");
		return REDIRECT_PAGINA_INICIAL_SUPERVISOR;

	}

	@RequestMapping(value = "/Turma/{idTurma}/AtualizarVinculos", method = RequestMethod.GET)
	public String formularioVinculosTurma(@PathVariable("idTurma") Long idTurma, Model model) {
		model.addAttribute("turma", turmaService.buscarTurmaPorId(idTurma));

		return VINCULOS_TURMA;
	}

	
	@RequestMapping(value = "/Turma/{idTurma}/TermosCompromisso", method = RequestMethod.GET)
	public String gerarTermoDeCompromisso(@PathVariable("idTurma") Long idTurma, Model model) throws JRException {

		 Turma turma = turmaService.buscarTurmaPorId(idTurma);

		 Usuario usuario = usuarioService.getByCpf(getCpfUsuarioLogado());

		 jrDatasource = new JRBeanCollectionDataSource(turma.getEstagios());
		
		 SimpleDateFormat dataFormatada = new SimpleDateFormat("dd/MM/yyyy");
		
		 model.addAttribute("NOME", usuario.getNome());
		 model.addAttribute("SIAPE", usuario.getSiape());
		 model.addAttribute("TELEFONE", usuario.getTelefone());
		 model.addAttribute("TURNO", UtilGestao.getTurnoExpediente(turma.getExpedientes().get(0)));
		 model.addAttribute("INICIO_ESTAGIO",
		 dataFormatada.format(turma.getInicio()));
		 model.addAttribute("FINAL_ESTAGIO",
		 dataFormatada.format(turma.getTermino()));
		 model.addAttribute("datasource", jrDatasource);
		 model.addAttribute("format", "pdf");
		
		 if (turma.getExpedientes() != null) {
			 model = configurarExpediente(turma.getExpedientes(), model);
		 }

		 return TERMO_COMPROMISSO_ESTAGIO;

	}

	@RequestMapping(value = "/Turma/{idTurma}/Declaracoes", method = RequestMethod.GET)
	public String gerarDeclaracaoEstagio(Model model, @PathVariable("idTurma") Long idTurma) throws JRException {
		 jrDatasource = new JRBeanCollectionDataSource(turmaService.buscarTurmaPorId(idTurma).getEstagios());

		 model.addAttribute("datasource", jrDatasource);
		 model.addAttribute("format", "pdf");

		return DECLARACAO_ESTAGIO;
	}

	@RequestMapping(value = "/Turma/{idTurma}/Expediente", method = RequestMethod.GET)
	public String formularioExpediente(Model model, @PathVariable("idTurma") Long idTurma) {
		model.addAttribute("expediente", new Expediente());
		model.addAttribute("turma", turmaService.buscarTurmaPorId(idTurma));
		
		return FORMULARIO_EXPEDIENTE;
	}

	@RequestMapping(value = "/Turma/{idTurma}/Expediente", method = RequestMethod.POST)
	public String adicionarExpediente(Model model, @PathVariable("idTurma") Long idTurma) {
		return REDIRECT_DETALHES_TURMA + idTurma + "/Expediente";
	}

	@RequestMapping(value = "/Turma/{idTurma}/Expediente/{idExpediente}/Excluir", method = RequestMethod.GET)
	public String excluirExpediente(Model model, @PathVariable("idTurma") Long idTurma) {
		return REDIRECT_DETALHES_TURMA + idTurma + "/Expediente";
	}

	@RequestMapping(value = "/Turma/{idTurma}/Evento", method = RequestMethod.GET)
	public String formularioEvento(Model model, @PathVariable("idTurma") Long idTurma) {
		model.addAttribute("evento", new Evento());
		model.addAttribute("turma", turmaService.buscarTurmaPorId(idTurma));

		return FORMULARIO_EVENTO;
	}

	@RequestMapping(value = "/Turma/{idTurma}/Evento", method = RequestMethod.POST)
	public String adicionarEvento(Model model, @PathVariable("idTurma") Long idTurma) {
		return REDIRECT_DETALHES_TURMA + idTurma + "/Evento";
	}

	@RequestMapping(value = "/Turma/{idTurma}/MapaFrequencia/{data}", method = RequestMethod.GET)
	public String listarFrequenciaTurma(@PathVariable("idTurma") Long idTurma, @PathVariable("data") String data,
			Model model, HttpSession session) {
		// Pessoa pessoa = getUsuarioLogado(session);
		// Date dataAtual = new Date();
		// List<Frequencia> frequencias =
		// frequenciaService.getFrequenciasByTurmaIdAndData(dataAtual, idTurma);
		// List<Estagiario> estagiarios =
		// frequenciaService.getEstagiariosSemFrequencia(dataAtual, idTurma);
		//
		// model.addAttribute("turma",
		// turmaService.getTurmaByIdAndSupervisorById(idTurma, pessoa.getId()));
		// model.addAttribute("frequencias", frequencias);
		// model.addAttribute("estagiarios", estagiarios);
		// model.addAttribute("dataAtual", dataAtual);

		// String cpf =
		// SecurityContextHolder.getContext().getAuthentication().getName();
		// Pessoa pessoa = pessoaService.buscarPessoaPorCpf(cpf);
		//
		// List<Frequencia> frequencias =
		// frequenciaService.getFrequenciasByTurmaIdAndData(data, idTurma);
		//
		// List<Estagiario> estagiarios =
		// frequenciaService.getEstagiariosSemFrequencia(data, idTurma);
		//
		// model.addAttribute("turma",
		// turmaService.getTurmaByIdAndSupervisorById(idTurma, pessoa.getId()));
		// model.addAttribute("turmas",
		// turmaService.getTurmasBySupervisorIdAndStatus(StatusTurma.ABERTA,
		// pessoa.getId()));
		// model.addAttribute("frequencias", frequencias);
		// model.addAttribute("estagiarios", estagiarios);
		// model.addAttribute("dataAtual", new Date());
		// return "supervisor/list-frequencias";

		return MAPA_FREQUENCIAS;
	}

	@RequestMapping( value = "/Turma/Acompanhamento/{idEstagio}", method = RequestMethod.GET)
	public String detalhesAcompanhamentoEstagiario(Model model, @PathVariable("idEstagio") Long idEstagio, RedirectAttributes redirect) {
		
		Pessoa pessoa = pessoaService.buscarPessoaPorCpf(getCpfUsuarioLogado());
		
		Estagio estagio = estagioService.buscarEstagioPorId(idEstagio);
		
		if(estagio == null){
			redirect.addFlashAttribute("error", "Estágio não existe");
			return REDIRECT_PAGINA_INICIAL_SUPERVISOR; 
		}
		
		List<Servidor> supervisores = estagio.getTurma().getSupervisores();
		for (Servidor servidor : supervisores) {
			if(servidor.getPessoa().getCpf().equals(pessoa.getCpf())){
				model.addAttribute("estagio", estagio);
				return ACOMPANHAMENTO_ESTAGIARIO;
			}
		}
		
		redirect.addFlashAttribute("error", "Permissão negada");
		
		return REDIRECT_PAGINA_INICIAL_SUPERVISOR; 

	}

	@RequestMapping(value = "/Turma/Acompanhamento/{idEstagio}/AvaliarPlano", method = RequestMethod.GET)
	public String formularioAvaliarPlanoEstagio(@PathVariable("idEstagio") Long idEstagio, Model model) {

		Submissao submissaoPlano = estagioService.buscarSubmissaoPorTipoSubmissaoEEstagioId(TipoSubmissao.PLANO_ESTAGIO, idEstagio);
		
		model.addAttribute("submissaoPlano", submissaoPlano);
		model.addAttribute("estagio", submissaoPlano.getEstagio());

		return FORMULARIO_AVALIAR_PLANO;
	}

	@RequestMapping(value = "/Turma/Acompanhamento/{idEstagio}/AvaliarPlano", method = RequestMethod.POST)
	public String avaliarPlanoEstagio(RedirectAttributes redirect, @PathVariable("idEstagio") Long idEstagio,
			@RequestParam("nota") Double nota, @RequestParam("status") Submissao.StatusEntrega status,
			@RequestParam("comentario") String comentario) {

		Submissao submissao = estagioService.buscarSubmissaoPorTipoSubmissaoEEstagioId(TipoSubmissao.PLANO_ESTAGIO,
				idEstagio);

		submissao.setStatusEntrega(status);
		submissao.setNota(nota);
		submissao.setComentario(comentario);

		estagioService.avaliarSubmissao(submissao);

		return REDIRECT_ACOMPANHAMENTO_ESTAGIARIO + idEstagio;
	}

	@RequestMapping(value = "/Turma/Acompanhamento/{idEstagio}/AvaliarRelatorio", method = RequestMethod.GET)
	public String avaliarRelatorio(RedirectAttributes redirect, @PathVariable("idEstagio") Long idEstagio, Model model) {

		Submissao submissaoRelatorio = estagioService.buscarSubmissaoPorTipoSubmissaoEEstagioId(TipoSubmissao.RELATORIO_FINAL_ESTAGIO, idEstagio);		
		
		model.addAttribute("submissaoRelatorio", submissaoRelatorio);
		model.addAttribute("estagio", submissaoRelatorio.getEstagio());

		return AVALIAR_RELATORIO;
	}

	@RequestMapping(value = "/Turma/Acompanhamento/{idEstagio}/AvaliarRelatorio", method = RequestMethod.POST)
	public String avaliarRelatorioFinalEstagio(RedirectAttributes redirect, @PathVariable("idEstagio") Long idEstagio,
			@RequestParam("nota") Double nota, @RequestParam("status") StatusEntrega status,
			@RequestParam("comentario") String comentario) {

		Submissao submissao = estagioService.buscarSubmissaoPorTipoSubmissaoEEstagioId(TipoSubmissao.RELATORIO_FINAL_ESTAGIO, idEstagio);
		
		submissao.setStatusEntrega(status);
		submissao.setNota(nota);
		submissao.setComentario(comentario);

		estagioService.avaliarSubmissao(submissao);

		return REDIRECT_ACOMPANHAMENTO_ESTAGIARIO + idEstagio;
	}

	@ModelAttribute("statusEntrega")
	public List<Submissao.StatusEntrega> statusEntrega() {
		return Arrays.asList(Submissao.StatusEntrega.values());
	}
	
	@RequestMapping(value="/Turma/Acompanhamento/{idEstagio}/DownloadRelatorio", method=RequestMethod.GET)
    @ResponseBody
	public HttpEntity<byte[]> downloadRelatorio(@PathVariable("idEstagio") Long idEstagio, RedirectAttributes redirectAttributes) {
        
        Submissao submissaoRelatorio = estagioService.buscarSubmissaoPorTipoSubmissaoEEstagioId(Submissao.TipoSubmissao.RELATORIO_FINAL_ESTAGIO, idEstagio);
        
//        if(submissaoPlano == null){
//            redirectAttributes.addFlashAttribute("error", "Acesso negado.");
//            return REDIRECT_PAGINA_INICIAL_ESTAGIARIO;
//        }
        
        byte[] relatorio = submissaoRelatorio.getDocumento().getArquivo();
        String[] tipo = submissaoRelatorio.getDocumento().getExtensao().split("/");
        
        HttpHeaders headers = new HttpHeaders();
		headers.setContentType(new MediaType(tipo[0], tipo[1]));
        headers.set("Content-Disposition", "attachment; filename=" + submissaoRelatorio.getDocumento().getNome());
        headers.setContentLength(relatorio.length);
        
        
        return new HttpEntity<byte[]>(relatorio, headers);
    }
	
	@RequestMapping(value="/Turma/Acompanhamento/{idEstagio}/DownloadPlano", method=RequestMethod.GET)
    @ResponseBody
	public HttpEntity<byte[]> downloadPlano(@PathVariable("idEstagio") Long idEstagio, RedirectAttributes redirectAttributes) {
        
        Submissao submissaoPlano = estagioService.buscarSubmissaoPorTipoSubmissaoEEstagioId(Submissao.TipoSubmissao.PLANO_ESTAGIO, idEstagio);
        
//        if(submissaoPlano == null){
//            redirectAttributes.addFlashAttribute("error", "Acesso negado.");
//            return REDIRECT_PAGINA_INICIAL_ESTAGIARIO;
//        }
        
        byte[] plano = submissaoPlano.getDocumento().getArquivo();
        String[] tipo = submissaoPlano.getDocumento().getExtensao().split("/");
        
        HttpHeaders headers = new HttpHeaders();
		headers.setContentType(new MediaType(tipo[0], tipo[1]));
        headers.set("Content-Disposition", "attachment; filename=" + submissaoPlano.getDocumento().getNome());
        headers.setContentLength(plano.length);
        
        
        return new HttpEntity<byte[]>(plano, headers);
    }

	// AVALIAÇÃO DE RENDIMENTO

	@RequestMapping(value = "/Turma/Acompanhamento/{idEstagio}/AvaliacaoRendimento", method = RequestMethod.GET)
	public String formularioAdicionarAvaliacaoRendimento(Model model, @PathVariable("idEstagio") Long idEstagio) {
		// model.addAttribute("avaliacaoEstagio",
		// avaliacaoService.getAvaliacoesEstagioByEstagiarioIdAndTurmaById(idEstagiario,
		// idTurma));
		// model.addAttribute("turma", turmaService.find(Turma.class, idTurma));
		// model.addAttribute("estagiario",
		// estagiarioService.find(Estagiario.class, idEstagiario));
		// model.addAttribute("submissoes",
		// turmaService.getSubmissoesByEstagiarioIdAndIdTurma(idEstagiario,
		// idTurma));

		Estagio estagio = estagioService.buscarEstagioPorId(idEstagio);
		// salvar o estágio depois de salvar avaliação; Achtung!!!
		model.addAttribute("avaliacaoRendimento", new AvaliacaoRendimento());
		model.addAttribute("estagio", estagio);

		return FORMULARIO_ADICIONAR_AVALIACAO_RENDIMENTO;
	}

	@ModelAttribute("frequencias")
    public List<AvaliacaoRendimento.Frequencia> todasFrequencias() {
        return Arrays.asList(AvaliacaoRendimento.Frequencia.values());
    }	
	
	@ModelAttribute("permanencias")
    public List<AvaliacaoRendimento.Permanencia> todasPermanencias() {
        return Arrays.asList(AvaliacaoRendimento.Permanencia.values());
    }
	
	@ModelAttribute("disciplinas")
    public List<AvaliacaoRendimento.DisciplinaQuantoAoCumprimentoDasNormas> todasDisciplinas() {
        return Arrays.asList(AvaliacaoRendimento.DisciplinaQuantoAoCumprimentoDasNormas.values());
    }
	
	@ModelAttribute("iniciativas")
    public List<AvaliacaoRendimento.Iniciativa> todasIniciativas() {
        return Arrays.asList(AvaliacaoRendimento.Iniciativa.values());
    }
	
	@ModelAttribute("qualidades")
    public List<AvaliacaoRendimento.QualidadeDoTrabalho> todasQualidades() {
        return Arrays.asList(AvaliacaoRendimento.QualidadeDoTrabalho.values());
    }
	
	@ModelAttribute("quantidades")
    public List<AvaliacaoRendimento.QuantidadeDeTrabalho> todasQuantidades() {
        return Arrays.asList(AvaliacaoRendimento.QuantidadeDeTrabalho.values());
    }
	
	@ModelAttribute("cumprimentos")
    public List<AvaliacaoRendimento.CumprimentoPrazos> todosCumprimentos() {
        return Arrays.asList(AvaliacaoRendimento.CumprimentoPrazos.values());
    }
	
	@ModelAttribute("relacionamentos")
    public List<AvaliacaoRendimento.RelacionamentoGerenciaEFuncionarios> todosRelacionamentos() {
        return Arrays.asList(AvaliacaoRendimento.RelacionamentoGerenciaEFuncionarios.values());
    }
	
	@ModelAttribute("trabalhos")
    public List<AvaliacaoRendimento.TrabalhoEmEquipe> todosTrabalhos() {
        return Arrays.asList(AvaliacaoRendimento.TrabalhoEmEquipe.values());
    }
    
	@ModelAttribute("comprometimentos")
    public List<AvaliacaoRendimento.ComprometimentoComTrabalho> todosComprometimentos() {
        return Arrays.asList(AvaliacaoRendimento.ComprometimentoComTrabalho.values());
    }
	
	@ModelAttribute("cuidados")
    public List<AvaliacaoRendimento.CuidadoMateriaisEEquipamentos> todosCuidados() {
        return Arrays.asList(AvaliacaoRendimento.CuidadoMateriaisEEquipamentos.values());
    }
	
	/*@ModelAttribute("todas")
    public List<AvaliacaoRendimento.> todas() {
        return Arrays.asList(AvaliacaoRendimento..values());
    }*/
	
	@RequestMapping(value = "/Turma/Acompanhamento/{idEstagio}/AvaliacaoRendimento", method = RequestMethod.POST)
	public String adicionarAvaliacaoRendimento(Model model, @RequestParam(value="arquivo", required = false) MultipartFile arquivo,
			@Valid @ModelAttribute("avaliacaoRendimento") AvaliacaoRendimento avaliacaoRendimento,
			RedirectAttributes redirect, @PathVariable("idEstagio") Long idEstagio) {
	
		Estagio estagio = estagioService.buscarEstagioPorId(idEstagio);
		avaliacaoRendimento.setEstagio(estagio);
		estagioService.adicionarAvaliacaoRendimento(avaliacaoRendimento);
		

		return REDIRECT_ACOMPANHAMENTO_ESTAGIARIO + idEstagio;
	}

	@RequestMapping(value = "/Turma/Acompanhamento/{idEstagio}/AvaliacaoRendimento/{idAvaliacaoRendimento}/Editar", method = RequestMethod.GET)
	public String formularioEditarAvaliacaoRendimento(Model model, @PathVariable("idEstagio") Long idEstagio) {
		// model.addAttribute("action", "editar");
		// model.addAttribute("avaliacaoRendimento",
		// avaliacaoService.find(AvaliacaoRendimento.class,
		// idAvaliacaoRendimento));
		// model.addAttribute("turma",
		// turmaService.getTurmaByIdAndEstagiarioId(idTurma, idEstagiario));
		// model.addAttribute("estagiario",
		// estagiarioService.find(Estagiario.class, idEstagiario));

		return FORMULARIO_EDITAR_AVALIACAO_RENDIMENTO;
	}

	@RequestMapping(value = "/Turma/Acompanhamento/{idEstagio}/AvaliacaoRendimento/{idAvaliacaoRendimento}/Editar", method = RequestMethod.POST)
	public String editarAvaliacaoRendimento(Model model,
			@Valid @ModelAttribute("avaliacaoRendimento") AvaliacaoRendimento avaliacaoRendimento,
			RedirectAttributes redirect, @PathVariable("idEstagio") Long idEstagio) {

		// model.addAttribute("action", "editar");
		// AvaliacaoRendimento avaliacaoDoBanco =
		// avaliacaoService.find(AvaliacaoRendimento.class,
		// avaliacaoRendimento.getId());
		// String cpf =
		// SecurityContextHolder.getContext().getAuthentication().getName();
		// Pessoa pessoa = pessoaService.buscarPessoaPorCpf(cpf);
		//
		// Estagiario estagiario = estagiarioService.find(Estagiario.class,
		// idEstagiario);
		// Turma turma = turmaService.getTurmaByIdAndEstagiarioId(idTurma,
		// idEstagiario);
		//
		// avaliacaoDoBanco.setSupervisor(pessoa);
		// avaliacaoDoBanco.setEstagiario(estagiario);
		// avaliacaoDoBanco.setTurma(turma);
		// avaliacaoDoBanco.setNota(avaliacaoRendimento.getNota());
		// avaliacaoDoBanco.setFatorAssiduidadeDisciplina(avaliacaoRendimento.getFatorAssiduidadeDisciplina());
		// avaliacaoDoBanco.setFatorIniciativaProdutividade(avaliacaoRendimento.getFatorIniciativaProdutividade());
		// avaliacaoDoBanco.setFatorRelacionamento(avaliacaoRendimento.getFatorRelacionamento());
		// avaliacaoDoBanco.setFatorResponsabilidade(avaliacaoRendimento.getFatorResponsabilidade());
		// avaliacaoDoBanco.setNotaSeminario(avaliacaoRendimento.getNotaSeminario());
		// avaliacaoDoBanco.setFatorComentarioSeminario(avaliacaoRendimento.getFatorComentarioSeminario());
		//
		// avaliacaoService.update(avaliacaoDoBanco);
		//
		return REDIRECT_ACOMPANHAMENTO_ESTAGIARIO + idEstagio;
	}

	@RequestMapping(value = "/Turma/Acompanhamento/{idEstagio}/Frequencia", method = RequestMethod.GET)
	public String detalhesFrequenciaEstagiario(Model model, @PathVariable("idEstagio") Long idEstagio) {
		return DETALHES_FREQUENCIA_ESTAGIARIO;
	}

	private void inserirNomeUsuarioNaSessao(HttpSession session) {
		if (session.getAttribute(NOME_USUARIO) == null) {
			session.setAttribute(NOME_USUARIO, usuarioService.getByCpf(getCpfUsuarioLogado()).getNome());
		}
	}

	private String getCpfUsuarioLogado() {
		return SecurityContextHolder.getContext().getAuthentication().getName();
	}


	private boolean isValidoArquivo(MultipartFile arquivo){
		if(arquivo != null && arquivo.getContentType().equals("application/pdf")){
			return true;
		}	
		return false;
	}

	

	private Model configurarExpediente(List<Expediente> expedientes, Model model) {
		SimpleDateFormat horaFormatada = new SimpleDateFormat("HH:mm");

		for (Expediente expediente: expedientes) {
			String descricaoExpediente = horaFormatada.format(expediente.getHoraInicio()) + " as " + horaFormatada.format(expediente.getHoraTermino());

			if (expediente.getDiaSemana().equals(Expediente.DiaDaSemana.SEGUNDA)) {
				model.addAttribute("EXPEDIENTE_SEGUNDA", descricaoExpediente);
			}
			if (expediente.getDiaSemana().equals(Expediente.DiaDaSemana.TERCA)) {
				model.addAttribute("EXPEDIENTE_TERCA", descricaoExpediente);
			}
			if (expediente.getDiaSemana().equals(Expediente.DiaDaSemana.QUARTA)) {
				model.addAttribute("EXPEDIENTE_QUARTA", descricaoExpediente);
			}
			if (expediente.getDiaSemana().equals(Expediente.DiaDaSemana.QUINTA)) {
				model.addAttribute("EXPEDIENTE_UINTA", descricaoExpediente);
			}
			if (expediente.getDiaSemana().equals(Expediente.DiaDaSemana.SEXTA)) {
				model.addAttribute("EXPEDIENTE_SEXTA", descricaoExpediente);
			}
		}
		return model;

	}	
	
	/**
	 *	OPERAÇÕES DE CRUD DO EVENTO DA TURMA
	 * 
	 * @RequestMapping(value = "/Turma/{idTurma}/Evento/Adicionar", method =
	 *                       RequestMethod.POST) public String
	 *                       postAdicionarEventos(@ModelAttribute("evento")
	 *                       Evento evento, @PathVariable("idTurma") Long
	 *                       idTurma, HttpSession session, RedirectAttributes
	 *                       redirect, Model model) {
	 *                       model.addAttribute("action", "cadastrar"); Pessoa
	 *                       supervisor = getUsuarioLogado(session); Turma turma
	 *                       =
	 *                       turmaService.getTurmaByIdAndSupervisorById(idTurma,
	 *                       supervisor.getId());
	 * 
	 *                       evento.setTurma(turma); turmaService.save(evento);
	 * 
	 *                       redirect.addFlashAttribute("success",
	 *                       "Evento cadastrado com sucesso.");
	 * 
	 *                       return "redirect:/supervisor/turma/" + idTurma +
	 *                       "/evento"; }
	 * 
	 * @RequestMapping(value="/Turma/{idTurma}/Evento/{idEvento}/Editar", method
	 *                                                                    =
	 *                                                                    RequestMethod
	 *                                                                    .POST)
	 *                                                                    public
	 *                                                                    String
	 *                                                                    postEditarEvento
	 *                                                                    (
	 *                                                                    @ModelAttribute
	 *                                                                    (
	 *                                                                    "evento")
	 *                                                                    Evento
	 *                                                                    evento,
	 *                                                                    RedirectAttributes
	 *                                                                    redirect
	 *                                                                    ){
	 *                                                                    turmaService
	 *                                                                    .
	 *                                                                    update
	 *                                                                    (
	 *                                                                    evento
	 *                                                                    );
	 *                                                                    redirect
	 *                                                                    .
	 *                                                                    addFlashAttribute
	 *                                                                    (
	 *                                                                    "success",
	 *                                                                    "Alterações realizadas com sucesso!"
	 *                                                                    );
	 *                                                                    return
	 *                                                                    "redirect:/supervisor/turma/"
	 *                                                                    +
	 *                                                                    evento
	 *                                                                    .
	 *                                                                    getTurma
	 *                                                                    ().
	 *                                                                    getId(
	 *                                                                    ) +
	 *                                                                    "/evento";
	 *                                                                    }
	 * 
	 * @RequestMapping(value = "/Turma/{idTurma}/Evento/{idEvento}/Excluir",
	 *                       method = RequestMethod.GET) public String
	 *                       getExcluirEvento(@PathVariable("idEvento") Long
	 *                       idEvento, @PathVariable("idTurma") Long idTurma,
	 *                       RedirectAttributes redirect) {
	 *                       turmaService.excluirEvento(turmaService.
	 *                       buscarEventoPorIdETurmaId(idEvento, idTurma));
	 *                       redirect.addFlashAttribute("success",
	 *                       "Evento excluído com sucesso!"); return
	 *                       "redirect:/supervisor/turma/" + idTurma +
	 *                       "/evento"; }
	 */

	/**
	 * @RequestMapping(
	 * value = "/Turma/{idTurma}/Acompanhamento/{idEstagio}/Frequencias", method =
	 *                       RequestMethod.GET) public String
	 *                       minhaPresenca(HttpSession session, Model
	 *                       model, @PathVariable("idTurma") Long
	 *                       idTurma, @PathVariable("idEstagiario") Long
	 *                       idEstagiario) {
	 * 
	 *                       Estagiario estagiario =
	 *                       estagiarioService.find(Estagiario.class,
	 *                       idEstagiario); Turma turma =
	 *                       turmaService.find(Turma.class, idTurma);
	 * 
	 *                       List<Frequencia> frequenciaCompleta = new ArrayList
	 *                       <Frequencia>(); List
	 *                       <Frequencia> frequenciaPendentes =
	 *                       frequenciaService.frequenciaPendente(turma,
	 *                       estagiario);
	 * 
	 *                       frequenciaCompleta =
	 *                       frequenciaService.gerarFrequencia(turma,
	 *                       estagiario); DadoConsolidado dadosConsolidados =
	 *                       frequenciaService.calcularDadosConsolidados(
	 *                       frequenciaCompleta);
	 * 
	 *                       System.out.println("Pendentes: " +
	 *                       frequenciaPendentes.size());
	 * 
	 *                       model.addAttribute("estagiario", estagiario);
	 *                       model.addAttribute("turma", turma);
	 *                       model.addAttribute("frequencias",
	 *                       frequenciaCompleta);
	 * 
	 *                       model.addAttribute("pendentes",
	 *                       frequenciaPendentes.size());
	 * 
	 *                       model.addAttribute("dadosConsolidados",
	 *                       dadosConsolidados);
	 *                       model.addAttribute("statusFrequencias",
	 *                       StatusFrequencia.values());
	 *                       model.addAttribute("dataAtual", new Date());
	 * 
	 *                       return "supervisor/list-frequencia-estagiario"; }
	 * 
	 * @RequestMapping(
	 * value = "/Turma/{idTurma}/Acompanhamento/{idEstagio}/Frequencias/Pendente",
	 *                       method = RequestMethod.POST) public String
	 *                       lancarFrequencia(@PathVariable("idEstagiario") Long
	 *                       idEstagiario, @PathVariable("idTurma") Long
	 *                       idTurma, @RequestParam("data") Date
	 *                       data, @RequestParam("statusFrequencia")
	 *                       StatusFrequencia
	 *                       statusFrequencia, @RequestParam("observacao")
	 *                       String observacao, Model model, RedirectAttributes
	 *                       redirectAttributes) {
	 * 
	 *                       Turma turma = turmaService.find(Turma.class,
	 *                       idTurma); Estagiario estagiario =
	 *                       estagiarioService.find(Estagiario.class,
	 *                       idEstagiario);
	 * 
	 *                       Frequencia frequencia = frequenciaService.
	 *                       getFrequenciaByDataByTurmaByEstagiario(data,
	 *                       idTurma, idEstagiario);
	 * 
	 *                       if (frequencia == null) {
	 * 
	 *                       if (statusFrequencia == null) {
	 *                       redirectAttributes.addFlashAttribute("error",
	 *                       "Escolha um Status Válido"); } else { frequencia =
	 *                       new Frequencia(); frequencia.setTurma(turma);
	 *                       frequencia.setEstagiario(estagiario);
	 *                       frequencia.setData(data); frequencia.setHorario(new
	 *                       Date());
	 *                       frequencia.setTipoFrequencia(TipoFrequencia.NORMAL)
	 *                       ; frequencia.setStatusFrequencia(statusFrequencia);
	 *                       frequencia.setObservacao(observacao);
	 *                       frequenciaService.save(frequencia);
	 *                       redirectAttributes.addFlashAttribute("sucesso",
	 *                       "Frequência lançada com sucesso"); }
	 * 
	 *                       } else {
	 *                       redirectAttributes.addFlashAttribute("error",
	 *                       "Não é possivel lançar a Frequência para esta data"
	 *                       ); }
	 * 
	 *                       return "redirect:/supervisor/turma/" + idTurma +
	 *                       "/estagiario/" + idEstagiario + "/frequencia"; }
	 * 
	 * @RequestMapping(
	 * value = "/Turma/{idTurma}/Acompanhamento/{idEstagio}/Frequencias/EditarStatus",
	 *                       method = RequestMethod.POST) public String
	 *                       atualizarStatus(@RequestParam("pk") Long
	 *                       idFrequencia, @RequestParam("value")
	 *                       StatusFrequencia status, Model model,
	 *                       RedirectAttributes redirectAttributes) { Frequencia
	 *                       frequencia =
	 *                       frequenciaService.find(Frequencia.class,
	 *                       idFrequencia);
	 * 
	 *                       if (frequencia != null) {
	 *                       frequencia.setStatusFrequencia(status);
	 *                       frequenciaService.update(frequencia); return
	 *                       "supervisor/list-frequencia-estagiario"; }
	 * 
	 *                       return ""; }
	 * 
	 * 
	 *                       private List
	 *                       <Estagiario> atualizarTurmaEstagiarios(List
	 *                       <Estagiario> estagiarios, Turma turma) { for
	 *                       (Estagiario estagiario : estagiarios) { if
	 *                       (estagiario.getTurmas() != null) { if
	 *                       (!estagiario.getTurmas().contains(turma)) {
	 *                       estagiario.getTurmas().add(turma); } } else {
	 *                       estagiario.setTurmas(new ArrayList<Turma>());
	 *                       estagiario.getTurmas().add(turma); } }
	 * 
	 *                       return estagiarios; }
	 * 
	 *                       private List<Horario> atualizarHorarios(Turma
	 *                       turma) { List<Horario> horariosAtualizados = new
	 *                       ArrayList<Horario>();
	 * 
	 *                       if (turma.getHorarios() == null) { return null; }
	 * 
	 *                       for (Horario horario : turma.getHorarios()) { if
	 *                       (horario.getDia() != null) {
	 *                       horariosAtualizados.add(horario); } } return
	 *                       horariosAtualizados; }
	 * 
	 *                       private Model configurarExpediente(List
	 *                       <Horario> horarios, Model model) { SimpleDateFormat
	 *                       horaFormatada = new SimpleDateFormat("HH:mm");
	 * 
	 *                       for (Horario horario : horarios) {
	 * 
	 *                       String expediente =
	 *                       horaFormatada.format(horario.getInicioExpediente())
	 *                       + " as " +
	 *                       horaFormatada.format(horario.getFinalExpediente());
	 * 
	 *                       if (horario.getDia().equals(Dia.SEGUNDA)) {
	 *                       model.addAttribute("EXPEDIENTE_SEGUNDA",
	 *                       expediente); } if
	 *                       (horario.getDia().equals(Dia.TERCA)) {
	 *                       model.addAttribute("EXPEDIENTE_TERCA", expediente);
	 *                       } if (horario.getDia().equals(Dia.QUARTA)) {
	 *                       model.addAttribute("EXPEDIENTE_QUARTA",
	 *                       expediente); } } return model;
	 * 
	 *                       }
	 * 
	 * 
	 *                       private List
	 *                       <Estagiario> getEstagiariosSelecionados(List
	 *                       <Estagiario> estagiarios) { List
	 *                       <Estagiario> estagiariosSelecionados = new
	 *                       ArrayList<Estagiario>();
	 * 
	 *                       for (Estagiario estagiario : estagiarios) { if
	 *                       (estagiario.getId() != null) { estagiario =
	 *                       estagiarioService.find(Estagiario.class,
	 *                       estagiario.getId());
	 * 
	 *                       estagiariosSelecionados.add(estagiario); } }
	 * 
	 *                       return estagiariosSelecionados; }
	 * 
	 */

}
