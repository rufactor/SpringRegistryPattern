package com.rufactor.registry.pattern;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class EmailGeneratorService {

//	private Map<String,EmailGenerator> registries;
//
//	@Autowired
//	public EmailGeneratorService(List<EmailGenerator> emailGeneratorList) {
//		this.registries = emailGeneratorList.stream().collect(Collectors.toMap(EmailGenerator::getRegistryCode, emailGenerator-> emailGenerator));
//	}

	private Map<String, EmailGenerator> generatorsRegistry = new HashMap<>();

	public void register(String code, EmailGenerator emailGenerator) {
		generatorsRegistry.put(code, emailGenerator);
	}

	public void generateTemplate(EmailTemplate emailTemplate) {
		EmailGenerator emailGenerator = generatorsRegistry.get(emailTemplate.getTemplateCode());
		if (emailGenerator == null) {
			throw new UnsupportedOperationException("No generator for this config=" + emailTemplate.getContent() +  " exist.");
		}
		String html = emailGenerator.generate(emailTemplate);
		sendEmail(html);
	}

	private void sendEmail(String html) {
		System.out.println("Send email = " + html);
	}

}
