package com.rufactor.registry.pattern;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class EmailTemplate {

	String templateCode;

	String subject;
	
	String content;

}
