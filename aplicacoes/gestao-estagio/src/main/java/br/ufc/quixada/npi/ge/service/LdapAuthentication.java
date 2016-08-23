package br.ufc.quixada.npi.ge.service;

import static br.ufc.quixada.npi.ldap.model.Constants.LOGIN_INVALIDO;

import java.util.Collection;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;

import br.ufc.quixada.npi.ge.service.PessoaService;
import br.ufc.quixada.npi.ldap.model.Usuario;
import br.ufc.quixada.npi.ldap.service.UsuarioService;

@Named
public class LdapAuthentication implements AuthenticationProvider {
	
	@Inject
	private UsuarioService usuarioService;
	
	@Inject
	private PessoaService pessoaService;
	
	@Override
	public Authentication authenticate(Authentication authentication)
			throws AuthenticationException {
		String username = authentication.getName();
        String password = (String) authentication.getCredentials();
 
        Usuario user = usuarioService.getByCpf(username);
 
        Collection<? extends GrantedAuthority> authorities;
        
        authorities = pessoaService.getPapeis(username);

        if(authorities.isEmpty() && user != null){
            authorities = user.getAuthorities();
        }
        
        if (user == null || !usuarioService.autentica(username, password) || authorities.isEmpty()) {
            throw new BadCredentialsException(LOGIN_INVALIDO);
        }
 
        return new UsernamePasswordAuthenticationToken(user, password, authorities);
	}

	@Override
	public boolean supports(Class<?> arg0) {
		return true;
	}

}
