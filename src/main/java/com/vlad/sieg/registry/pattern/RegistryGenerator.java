package com.vlad.sieg.registry.pattern;

import org.springframework.beans.factory.annotation.Autowired;

public interface RegistryGenerator {

	String generate(RegistryConfig registryConfig);

	String getRegistryCode();

	/**
	 * Setter injection. In all classes that implements this interface this method will be called
	 * @param registryService
	 */
	@Autowired
	default void registerMyself(RegistryService registryService) {
		registryService.register(getRegistryCode(),this);
	}

}



