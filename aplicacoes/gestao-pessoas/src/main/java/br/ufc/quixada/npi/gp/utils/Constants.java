package br.ufc.quixada.npi.gp.utils;

public class Constants {

	public static final String NOME_USUARIO = "nomeUsuario";

	public static final String PAGINA_LOGIN = "login";
	
	public static final String PAGINA_SOBRE = "sobre";

	/** ESTAGIARIO */

	public static final String PAGINA_INICIAL_ESTAGIARIO = "estagiario/list-turmas";

	public static final String ACOMPANHAMENTO_ESTAGIO = "estagiario/acompanhamento-estagio";

	public static final String FORMULARIO_EDITAR_ESTAGIARIO = "estagiario/editar-estagiario";

	/** SUPERVISOR*/

	public static final String PAGINA_INICIAL_SUPERVISOR = "supervisor/list-turmas";
	
	public static final String DETALHES_TURMA = "supervisor/detalhes-turma";

	public static final String FORMULARIO_ADICIONAR_TURMA = "supervisor/adicionar-turma";

	public static final String FORMULARIO_EDITAR_TURMA = "supervisor/editar-turma";

	public static final String ACOMPANHAMENTO_ESTAGIARIO = "supervisor/acompanhamento-estagiario";

	public static final String FORMULARIO_ADICIONAR_AVALIACAO_RENDIMENTO = "supervisor/adicionar-avaliacao-rendimento";

	public static final String FORMULARIO_EDITAR_AVALIACAO_RENDIMENTO = "supervisor/editar-avaliacao-rendimento";

	public static final String PAGINA_FORM_VINCULOS = "supervisor/form-turma-vinculos";

	public static final String DETALHES_FREQUENCIA_ESTAGIARIO = "supervisor/detalhes-frequencia-estagiario";
	
	public static final String MAPA_FREQUENCIAS = "supervisor/mapa-frequencias";
	
	public static final String TERMO_COMPROMISSO_ESTAGIO = "TERMO_COMPROMISSO";

	public static final String DECLARACAO_ESTAGIO = "DECLARACAO_ESTAGIO";
	
	public static final String AVALIAR_RELATORIO = "supervisor/avaliar-relatorio";
	

	/** REDIRECIONAMENTOS */

	public static final String REDIRECT_PAGINA_INICIAL_ESTAGIARIO = "redirect:/Estagiario/";

	public static final String REDIRECT_PAGINA_INICIAL_SUPERVISOR = "redirect:/Supervisor/Turmas";

	public static final String REDIRECT_DETALHES_TURMA = "redirect:/Supervisor/detalhes-turma";
	
	public static final String REDIRECT_ACOMPANHAMENTO_ESTAGIARIO = "Supervisor/Turma/Acompanhamento/";
	
	public static final String REDIRECT_ACOMPANHAMENTO_ESTAGIO = "redirect:/Estagiario/Acompanhamento/";
	
	public static final String REDIRECT_PAGINA_LOGIN = "redirect:/login";

}
