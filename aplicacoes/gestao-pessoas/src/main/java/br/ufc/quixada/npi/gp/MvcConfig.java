package br.ufc.quixada.npi.gp;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class MvcConfig extends WebMvcConfigurerAdapter{

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("estagiario/inicio");
        registry.addViewController("/Acompanhamento").setViewName("estagiario/acompanhamento");
        registry.addViewController("/MeusDados").setViewName("estagiario/MeusDados");
        registry.addViewController("/login").setViewName("login");
    }

}