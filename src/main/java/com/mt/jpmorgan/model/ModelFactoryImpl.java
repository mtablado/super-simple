package com.mt.jpmorgan.model;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
public class ModelFactoryImpl implements ModelFactory {

	@Override
	@Bean
	@Scope("prototype")
	public Stock createStock() {
		return new Stock();
	}

	@Override
	@Bean
	@Scope("prototype")
	public Trade createTrade() {
		return new Trade();
	}

}
