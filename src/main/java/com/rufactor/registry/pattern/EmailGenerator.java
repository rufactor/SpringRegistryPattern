package com.rufactor.registry.pattern;

import org.springframework.beans.factory.annotation.Autowired;

public interface EmailGenerator {

	String generate(EmailTemplate emailTemplate);

	default String getRegistryCode() {
		RegistryCode annotation = getClass().getAnnotation(RegistryCode.class);
		if (annotation == null) {
			throw new IllegalStateException(
					"Class " + getClass().getName() + " must be annotated with @RegistryCode");
		}
		return annotation.value();
	}

	/**
	 * Setter injection. In all classes that implement this interface, this method will be called
	 * @param emailGeneratorService
	 */
	@Autowired
	default void registerMyself(EmailGeneratorService emailGeneratorService) {
		emailGeneratorService.register(getRegistryCode(),this);
	}

}



