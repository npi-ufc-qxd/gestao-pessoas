package br.ufc.quixada.npi.ge;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.jasperreports.JasperReportsMultiFormatView;

@Configuration
public class MvcConfig extends WebMvcConfigurerAdapter{

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("estagiario/inicio");
        registry.addViewController("/Acompanhamento").setViewName("estagiario/acompanhamento");
        registry.addViewController("/MeusDados").setViewName("estagiario/MeusDados");
        registry.addViewController("/login").setViewName("login");
    }
    
    @Bean(name = "TERMO_COMPROMISSO")
    public JasperReportsMultiFormatView reportTermo() {
    	JasperReportsMultiFormatView report = new JasperReportsMultiFormatView();
    	report.setUrl("classpath:TERMO_COMPROMISSO.jrxml");
    	return report;
    }
    
    @Bean(name = "DECLARACAO_ESTAGIO")
    public JasperReportsMultiFormatView reportDeclaracao() {
    	JasperReportsMultiFormatView report = new JasperReportsMultiFormatView();
    	report.setUrl("classpath:DECLARACAO_ESTAGIO.jrxml");
    	return report;
    }
    
}