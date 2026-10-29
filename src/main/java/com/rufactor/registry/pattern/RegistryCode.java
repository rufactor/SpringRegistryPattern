package com.rufactor.registry.pattern;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation that specifies the registry code for an {@link EmailGenerator} implementation.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface RegistryCode {
	/**
	 * @return the unique registry code for this generator
	 */
	String value();
}
