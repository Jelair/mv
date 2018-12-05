package com.uduck.mv;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
public class MvApplication {

	public static void main(String[] args) {
		SpringApplication.run(MvApplication.class, args);
	}
}
