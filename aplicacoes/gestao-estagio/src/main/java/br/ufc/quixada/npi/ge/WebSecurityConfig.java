package br.ufc.quixada.npi.ge;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import br.ufc.quixada.npi.ge.service.LdapAuthentication;
import br.ufc.quixada.npi.ge.service.impl.AuthenticationSuccessHandlerImpl;

@Configuration
@EnableWebSecurity
@ComponentScan(basePackages = { "br.ufc.quixada.npi.ldap" })
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private LdapAuthentication ldapAuthenticationProvider;

	@Override
    protected void configure(HttpSecurity http) throws Exception {
        http
		    .authorizeRequests()
		    	.antMatchers("/404", "/js**", "/css**", "/images**").permitAll()
		    	.antMatchers("/CadastroEstagiario/**").hasAnyAuthority("DISCENTE")
		    	.antMatchers("/CadastroSupervisor/**").hasAnyAuthority("DOCENTE", "STA")
		    	.antMatchers("/Estagiario/**").hasAnyAuthority("ESTAGIARIO")
		    	.antMatchers("/Supervisao/**").hasAnyAuthority("SUPERVISOR").and()
		    .formLogin()
		        .loginPage("/login").successHandler(new AuthenticationSuccessHandlerImpl()).permitAll().and()
		        .logout().logoutUrl("/logout").permitAll();
	}

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
    	auth.eraseCredentials(false).authenticationProvider(ldapAuthenticationProvider);
    }    
}