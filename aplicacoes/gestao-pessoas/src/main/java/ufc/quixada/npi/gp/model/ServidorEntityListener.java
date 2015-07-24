package ufc.quixada.npi.gp.model;

import javax.persistence.PostLoad;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import br.ufc.quixada.npi.ldap.model.Usuario;
import br.ufc.quixada.npi.ldap.service.UsuarioService;

public class ServidorEntityListener implements ApplicationContextAware {
	
	private static ApplicationContext context;
	
	@PostLoad
	public void loadPessoa(Pessoa pessoa) {
		UsuarioService usuarioService = (UsuarioService) context.getBean(UsuarioService.class);
		Usuario usuario = usuarioService.getByCpf(pessoa.getCpf());
		pessoa.setNome(usuario.getNome());
		pessoa.setEmail(usuario.getEmail());
		pessoa.setSiape(usuario.getSiape());
	}
	
	public ApplicationContext getApplicationContext() {
        return context;
    }
 
    @Override
    public void setApplicationContext(ApplicationContext ctx) {
        context = ctx;
    }

}
