package com.vlad.sieg.registry.pattern;

import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RegistryProducer {

	public RegistryService registryService;

	@Scheduled(fixedDelay = 3000)
	public void generateRegistries() {
		RegistryConfig registry1 = new RegistryConfig("1","Internal User Area");
		RegistryConfig registry2 = new RegistryConfig("2","Public User Area");

		registryService.generateRegistry(registry1);
		registryService.generateRegistry(registry2);
	}

}
