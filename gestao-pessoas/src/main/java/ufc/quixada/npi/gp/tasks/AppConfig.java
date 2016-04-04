
package ufc.quixada.npi.gp.tasks;

import javax.inject.Inject;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import ufc.quixada.npi.gp.service.FrequenciaService;

@Configuration
@EnableAsync
@EnableScheduling
public class AppConfig {
	
	@Inject
	private FrequenciaService serviceFrequencia;
	
	@Scheduled(cron = "0 59 22 * * MON-FRI")
	public void atualizarStatus() {
		serviceFrequencia.atualizarStatus();
	}
}
