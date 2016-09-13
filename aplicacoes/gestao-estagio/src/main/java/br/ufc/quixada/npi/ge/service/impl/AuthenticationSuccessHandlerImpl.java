package br.ufc.quixada.npi.ge.service.impl;

import java.io.IOException;
import java.util.Collection;

import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import br.ufc.quixada.npi.ge.utils.Constants;

@Named
public class AuthenticationSuccessHandlerImpl implements AuthenticationSuccessHandler {

	private RedirectStrategy redirectStrategy;
	
	public AuthenticationSuccessHandlerImpl() {
		redirectStrategy = new DefaultRedirectStrategy();
	}

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
		handle(request, response, authentication);
		request.getSession().setMaxInactiveInterval(Constants.TEMPO_SESSAO);
	}

	private void handle(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
		redirectStrategy.sendRedirect(request, response, determineUrl(authentication));
	}

	private String determineUrl(Authentication authentication) {
		String url = "/";

		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

		for (GrantedAuthority grantedAuthority : authorities) {
			String papel = grantedAuthority.getAuthority();

			switch (papel) {
				case "ESTAGIARIO":
					url = "/Estagiario/";
					break;

				case "DISCENTE":
					url = "/CadastroEstagiario/";
					break;
						
				case "DOCENTE":
					url = "/CadastroSupervisor/";
					break;

				case "STA":
					url = "/CadastroSupervisor/";
					break;
				
				case "SUPERVISOR":
					url = "/Supervisao/";
					break;
				
				default:
					url = "/";
			}
		}
		
		return url;
	}
	
}