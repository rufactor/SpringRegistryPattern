package com.vlad.sieg.registry.pattern;

import org.springframework.stereotype.Component;

@Component("2")
public class PublicRegistryGenerator implements RegistryGenerator {

	@Override public String generate(RegistryConfig registryConfig) {
		return registryConfig.getName() + "\n public registry";
	}

	@Override public String getRegistryCode() {
		return "2";
	}
}
