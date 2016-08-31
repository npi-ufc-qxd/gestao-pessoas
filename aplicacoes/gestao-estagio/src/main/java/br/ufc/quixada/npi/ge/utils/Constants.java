package br.ufc.quixada.npi.ge.utils;

public class Constants {

	public static final String NOME_USUARIO = "nomeUsuario";

	public static final String PAGINA_LOGIN = "login";
	
	public static final int TEMPO_SESSAO = 20 * 60;
	
	public static final String PAGINA_SOBRE = "sobre";
	
	public static final String USUARIO_LOGADO = "usuario";

	/** ESTAGIARIO */

	public static final String PAGINA_INICIAL_ESTAGIARIO = "estagiario/list-turmas";

	public static final String ACOMPANHAMENTO_ESTAGIO = "estagiario/acompanhamento-estagio";

	public static final String FORMULARIO_EDITAR_ESTAGIARIO = "estagiario/editar-estagiario";

	public static final String FORMULARIO_CADASTRO_ESTAGIARIO = "estagiario/cadastrar-estagiario";

	/** SUPERVISOR*/

	public static final String PAGINA_INICIAL_SUPERVISOR = "supervisao/list-turmas";
	
	public static final String DETALHES_TURMA = "supervisao/detalhes-turma";

	public static final String FORMULARIO_ADICIONAR_TURMA = "supervisao/adicionar-turma";

	public static final String FORMULARIO_EDITAR_TURMA = "supervisao/editar-turma";

	public static final String ACOMPANHAMENTO_ESTAGIARIO = "supervisao/acompanhamento-estagiario";
	
	public static final String FORMULARIO_AVALIAR_PLANO = "supervisao/adicionar-avaliacao-plano";

	public static final String FORMULARIO_ADICIONAR_AVALIACAO_RENDIMENTO = "supervisao/adicionar-avaliacao-rendimento";

	public static final String FORMULARIO_EDITAR_AVALIACAO_RENDIMENTO = "supervisao/editar-avaliacao-rendimento";

	public static final String PAGINA_FORM_VINCULOS = "supervisao/form-turma-vinculos";

	public static final String DETALHES_FREQUENCIA_ESTAGIARIO = "supervisao/detalhes-frequencia-estagiario";
	
	public static final String MAPA_FREQUENCIAS = "supervisao/mapa-frequencias";
	
	public static final String TERMO_COMPROMISSO_ESTAGIO = "TERMO_COMPROMISSO";

	public static final String DECLARACAO_ESTAGIO = "DECLARACAO_ESTAGIO";

	public static final String VINCULOS_TURMA = "supervisao/vinculos-turma";
	
	public static final String AVALIAR_RELATORIO = "supervisao/avaliar-relatorio";
	
	public static final String FORMULARIO_EVENTO = "supervisao/formulario-evento";
	
	public static final String FORMULARIO_EXPEDIENTE = "supervisao/formulario-expediente";	
	
	public static final String FORMULARIO_EXPEDIENTE_ESTAGIO = "supervisao/formulario-expediente-estagio";	
	
	public static final String GERENCIAR_FREQUENCIAS = "supervisao/gerenciar-frequencias";	

	/** REDIRECIONAMENTOS */

	public static final String REDIRECT_PAGINA_INICIAL_ESTAGIARIO = "redirect:/Estagiario/";

	public static final String REDIRECT_PAGINA_INICIAL_SUPERVISOR = "redirect:/Supervisao/Turmas";

	public static final String REDIRECT_DETALHES_TURMA = "redirect:/Supervisao/Turma/";
	
	public static final String REDIRECT_ACOMPANHAMENTO_ESTAGIARIO = "redirect:/Supervisao/Acompanhamento/";
	
	public static final String REDIRECT_ACOMPANHAMENTO_ESTAGIO = "redirect:/Estagiario/Acompanhamento/";
	
	public static final String REDIRECT_PAGINA_LOGIN = "redirect:/login";

	public static final String REDIRECT_VINCULOS_TURMA = "redirect:/supervisao/vinculos-turma";

}
