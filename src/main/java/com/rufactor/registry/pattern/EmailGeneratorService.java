package com.rufactor.registry.pattern;

import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Service
public class EmailGeneratorService {

	private final ConcurrentMap<String, EmailGenerator> generatorsRegistry = new ConcurrentHashMap<>();

	public void register(String code, EmailGenerator emailGenerator) {
		EmailGenerator previous = generatorsRegistry.putIfAbsent(code, emailGenerator);
		if (previous != null) {
			throw new IllegalStateException(
					"Duplicate registry code '" + code + "' for " + emailGenerator.getClass().getName() +
					"; already registered by " + previous.getClass().getName());
		}
	}

	public void generateTemplate(EmailTemplate emailTemplate) {
		EmailGenerator emailGenerator = generatorsRegistry.get(emailTemplate.getTemplateCode());
		if (emailGenerator == null) {
			throw new UnsupportedOperationException(
					"No generator for template code='" + emailTemplate.getTemplateCode() + "' exists.");
		}
		String html = emailGenerator.generate(emailTemplate);
		sendEmail(html);
	}

	private void sendEmail(String html) {
		System.out.println("Send email = " + html);
	}

	/**
	 * Returns an unmodifiable view of the registry for testing purposes.
	 */
	public Collection<EmailGenerator> getGenerators() {
		return Collections.unmodifiableCollection(generatorsRegistry.values());
	}

}
