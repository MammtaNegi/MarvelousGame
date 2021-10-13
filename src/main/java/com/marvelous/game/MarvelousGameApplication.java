package com.marvelous.game;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.boot.SpringApplication;


@SpringBootApplication
@EnableScheduling
public class MarvelousGameApplication {

	public static void main(String[] args) {
		SpringApplication.run(MarvelousGameApplication.class, args);
		
	}
}