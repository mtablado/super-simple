package com.mt.jpmorgan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 *
 */

/**
 * @author miguel.tablado.leon
 *
 */
@SpringBootApplication
@EnableAspectJAutoProxy
public class SuperSimpleApp {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SpringApplication.run(SuperSimpleApp.class, args);
	}
}
