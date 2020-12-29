package com.rufactor.registry.pattern;

import org.springframework.stereotype.Component;

@Component
public class GreetingEmailGenerator implements EmailGenerator {

	@Override public String generate(EmailTemplate emailTemplate) {
		return emailTemplate.getSubject() + "\n Thank you for your registration!";
	}

	@Override public String getRegistryCode() {
		return "1";
	}
}
