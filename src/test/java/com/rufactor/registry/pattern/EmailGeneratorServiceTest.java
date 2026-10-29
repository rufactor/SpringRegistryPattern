package com.rufactor.registry.pattern;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmailGeneratorServiceTest {

	private EmailGeneratorService service;

	private EmailGenerator greetingGenerator;
	private EmailGenerator birthdayGenerator;

	@BeforeEach
	void setUp() {
		service = new EmailGeneratorService();

		greetingGenerator = new EmailGenerator() {
			@Override
			public String generate(EmailTemplate emailTemplate) {
				return emailTemplate.getSubject() + "\n Thank you for your registration!";
			}
			@Override
			public String getRegistryCode() {
				return "1";
			}
		};

		birthdayGenerator = new EmailGenerator() {
			@Override
			public String generate(EmailTemplate emailTemplate) {
				return emailTemplate.getSubject() + "\n We wish you all the best!";
			}
			@Override
			public String getRegistryCode() {
				return "2";
			}
		};

		// Simulate self-registration: each generator registers itself
		service.register(greetingGenerator.getRegistryCode(), greetingGenerator);
		service.register(birthdayGenerator.getRegistryCode(), birthdayGenerator);
	}

	@Test
	void shouldRegisterAllGenerators() {
		assertEquals(2, service.getGenerators().size());
	}

	@Test
	void shouldGenerateGreetingTemplate() {
		EmailTemplate template = new EmailTemplate("1", "Welcome", "body");
		assertDoesNotThrow(() -> service.generateTemplate(template));
	}

	@Test
	void shouldGenerateBirthdayTemplate() {
		EmailTemplate template = new EmailTemplate("2", "Happy Birthday", "body");
		assertDoesNotThrow(() -> service.generateTemplate(template));
	}

	@Test
	void shouldThrowWhenTemplateCodeNotFound() {
		EmailTemplate template = new EmailTemplate("99", "Unknown", "body");
		UnsupportedOperationException ex = assertThrows(
				UnsupportedOperationException.class,
				() -> service.generateTemplate(template)
		);
		assertTrue(ex.getMessage().contains("99"));
		assertTrue(ex.getMessage().contains("template code"));
	}

	@Test
	void shouldDetectDuplicateRegistryCodes() {
		EmailGenerator duplicateGenerator = new EmailGenerator() {
			@Override
			public String generate(EmailTemplate emailTemplate) {
				return "duplicate";
			}
			@Override
			public String getRegistryCode() {
				return "1";
			}
		};

		IllegalStateException ex = assertThrows(
				IllegalStateException.class,
				() -> service.register("1", duplicateGenerator)
		);
		assertTrue(ex.getMessage().contains("Duplicate registry code"));
		assertTrue(ex.getMessage().contains("1"));
	}

	@Test
	void shouldWorkWithEmptyRegistry() {
		EmailGeneratorService emptyService = new EmailGeneratorService();
		assertEquals(0, emptyService.getGenerators().size());

		EmailTemplate template = new EmailTemplate("1", "Subject", "body");
		assertThrows(
				UnsupportedOperationException.class,
				() -> emptyService.generateTemplate(template)
		);
	}

	@Test
	void getGeneratorsShouldReturnUnmodifiableCollection() {
		assertThrows(UnsupportedOperationException.class, () -> service.getGenerators().clear());
	}
}
