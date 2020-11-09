package com.vlad.sieg.registry.pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RegistryService {

//	private Map<String,RegistryGenerator> registries;
//
//	@Autowired
//	public RegistryService(List<RegistryGenerator> registryGeneratorList) {
//		this.registries = registryGeneratorList.stream().collect(Collectors.toMap(RegistryGenerator::getRegistryCode,registryGenerator-> registryGenerator));
//	}

	private Map<String,RegistryGenerator> registries = new HashMap<>();

	public void register(String code, RegistryGenerator registryGenerator) {
		registries.put(code,registryGenerator);
	}

	public void generateRegistry(RegistryConfig registryConfig) {
		RegistryGenerator registryGenerator = registries.get(registryConfig.getCode());
		if (registryGenerator == null) {
			throw new UnsupportedOperationException("No generator for this config=" + registryConfig.getName() +  " exist.");
		}
		String registryCode = registryGenerator.generate(registryConfig);
		saveRegistry(registryCode);
	}

	private void saveRegistry(String registryCode) {
		System.out.println("Saved = " + registryCode);
	}

}
