package com.rufactor.registry.pattern;

import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EmailTemplateProducer {

	public EmailGeneratorService emailGeneratorService;

	@Scheduled(fixedDelay = 3000)
	public void generateRegistries() {
		EmailTemplate registry1 = new EmailTemplate("1","Registration finished","");
		EmailTemplate registry2 = new EmailTemplate("2","Happy Birthday","");

		emailGeneratorService.generateTemplate(registry1);
		emailGeneratorService.generateTemplate(registry2);
	}

}
