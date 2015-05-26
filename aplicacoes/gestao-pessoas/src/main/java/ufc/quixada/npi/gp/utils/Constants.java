package ufc.quixada.npi.gp.utils;

public class Constants {

	public static final String USUARIO_LOGADO = "usuario";

	/** Páginas */

	public static final String PAGINA_LOGIN = "login";

	public static final String PAGINA_INICIAL_COORDENADOR = "coordenador/inicial";

	public static final String PAGINA_INICIAL_ESTAGIARIO = "estagiario/inicial";

	public static final String PAGINA_CADASTRAR_PROJETO = "coordenador/form-projeto";

	public static final String PAGINA_DETALHES_PROJETO = "coordenador/info-projeto";

	public static final String PAGINA_EDITAR_PROJETO = "coordenador/form-projeto";

	public static final String PAGINA_LISTAR_PROJETOS = "coordenador/list-projetos";

	public static final String PAGINA_MEU_PROJETO = "estagiario/meu-projeto";

	public static final String PAGINA_ADICONAR_MEMBROS_PROJETO = "coordenador/add-membros-projeto";

	public static final String PAGINA_CADASTRAR_PERIODO = "coordenador/form-periodo";

	public static final String PAGINA_DETALHES_PERIODO = "coordenador/info-periodo";

	public static final String PAGINA_EDITAR_PERIODO = "coordenador/form-periodo";

	public static final String PAGINA_LISTAR_PERIODOS = "coordenador/list-periodos";

	public static final String PAGINA_LISTAR_ESTAGIARIOS_PERIODO = "coordenador/list-estagiarios-periodo";

	public static final String PAGINA_CADASTRAR_FOLGA = "coordenador/form-folga";

	public static final String PAGINA_DETALHES_FOLGA = "";

	public static final String PAGINA_EDITAR_FOLGA = "coordenador/form-folga";

	public static final String PAGINA_LISTAR_FOLGAS = "coordenador/list-folga";

	public static final String PAGINA_AVALIACAO = "estagiario/avaliacao";

	public static final String PAGINA_TCE = "multiViewReport";

	public static final String PAGINA_DECLARACAO_ESTAGIO = "declaracaoEstagioIndividual";

	public static final String PAGINA_MEUS_DADOS = "estagiario/meus-dados";
	
	public static final String PAGINA_EDITAR_ESTAGIARIO = "estagiario/editar-estagiario";

	public static final String PAGINA_CADASTRAR_DOCUMENTOS = "";

	public static final String PAGINA_DETALHES_DOCUMENTOS = "estagiario/documentos";

	public static final String PAGINA_MINHA_PRESENCA = "estagiario/minha-presenca";

	public static final String PAGINA_LISTAR_FREQUENCIAS = "coordenador/list-frequencias";

	public static final String PAGINA_REPOSICAO = "coordenador/reposicao";

	public static final String PAGINA_CADASTRAR_ = "";

	public static final String PAGINA_EDITAR_ = "";

	public static final String PAGINA_LISTAR_ = "";

	/** Redirecionamentos */

	public static final String REDIRECT_PAGINA_LISTAR_PROJETOS = "redirect:/coordenador/projetos";

	public static final String REDIRECT_PAGINA_LISTAR_PERIODOS = "redirect:/coordenador/periodos";

	public static final String REDIRECT_PAGINA_INICIAL_COORDENADOR = "redirect:/coordenador/inicial";

	public static final String REDIRECT_PAGINA_INICIAL_ESTAGIARIO = "redirect:/estagiario/inicial";
	
	public static final String REDIRECT_MINHA_PRESENCA = "redirect:/frequencia/minha-presenca";
	
	public static final String REDIRECT_PAGINA_EDITAR_ESTAGIARIO = "redirect:/estagiario/editar-estagiario";

	public static final String REDIRECT_PAGINA_LISTAR_FREQUENCIAS = "redirect:/coordenador/list-frequencias";

	public static final String REDIRECT_PAGINA_REPOSICAO = "redirect:/coordenador/reposicao";

	public static final String REDIRECT_PAGINA_LISTAR_ = "redirect:/";

	public static final String REDIRECT_PAGINA_LOGIN = "redirect:/login";

	/** Mensagens */

	public static final String MENSAGEM_PRESENCA_NEGADA = "Permissão negada";

	public static final String MENSAGEM_CAMPO_OBRIGATORIO = "Campo obrigatório";

	public static final String MENSAGEM_PERIODO_INEXISTENTE = "Periodo inexistente";

	public static final String MENSAGEM_PERIODO_ATUALIZADO = "Periodo atualizado com sucesso";

	public static final String MENSAGEM_PERIODO_CADASTRADO = "Periodo cadastrado com sucesso";

	public static final String MENSAGEM_PERIODO_REMOVIDO = "Periodo removido com sucesso";

	public static final String MENSAGEM_PROJETO_INEXISTENTE = "Projeto inexistente";

	public static final String MENSAGEM_PROJETO_ATUALIZADO = "Projeto atualizado com sucesso";

	public static final String MENSAGEM_PROJETO_CADASTRADO = "Projeto cadastrado com sucesso";

	public static final String MENSAGEM_PROJETO_REMOVIDO = "Projeto removido com sucesso";

	public static final String MENSAGEM_FREQUENCIA_ATUALIZADO = "Frequencia atualizado com sucesso";

	public static final String MENSAGEM_FREQUENCIA_LANCADA = "Frequencia lançada com sucesso";

	public static final String MENSAGEM_DATA_FUTURA = "A data não pode ser anterior à data atual";

	public static final String MENSAGEM_DATA_INICIO_TERMINO = "A data de início deve ser anterior à data de término";

	public static final String MENSAGEM_DATA_INVALIDA = "Data inválida";

	public static final String MENSAGEM_ERRO_UPLOAD = "Ocorreu um erro no upload de arquivos";

	public static final String MENSAGEM_SUCESSO_UPLOAD = "Upload de arquivos realizado com sucesso";

}
