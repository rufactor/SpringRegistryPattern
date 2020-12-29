package com.rufactor.registry.pattern;

import org.springframework.stereotype.Component;

@Component
public class BirthdayEmailGenerator implements EmailGenerator {

	@Override public String generate(EmailTemplate emailTemplate) {
		return emailTemplate.getSubject() + "\n We wish you all the best!";
	}

	@Override public String getRegistryCode() {
		return "2";
	}
}
