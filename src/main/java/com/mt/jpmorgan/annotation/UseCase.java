package com.mt.jpmorgan.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
public @interface UseCase {

	public enum Type {
		POST, GET, DELETE, UPDATE
	}
	
	String name();
	
	String description();
	
	Type type() default Type.GET;
	
}
