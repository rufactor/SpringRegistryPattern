package com.rufactor.registry.pattern;

import org.springframework.beans.factory.annotation.Autowired;

public interface EmailGenerator {

	String generate(EmailTemplate emailTemplate);

	String getRegistryCode();

	/**
	 * Setter injection. In all classes that implements this interface this method will be called
	 * @param emailGeneratorService
	 */
	@Autowired
	default void registerMyself(EmailGeneratorService emailGeneratorService) {
		emailGeneratorService.register(getRegistryCode(),this);
	}

}



