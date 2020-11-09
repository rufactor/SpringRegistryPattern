package com.vlad.sieg.registry.pattern;

import org.springframework.stereotype.Component;

@Component("1")
public class PrivateRegistryGenerator implements RegistryGenerator {

	@Override public String generate(RegistryConfig registryConfig) {
		return registryConfig.getName() + "\n private registry";
	}

	@Override public String getRegistryCode() {
		return "1";
	}
}
