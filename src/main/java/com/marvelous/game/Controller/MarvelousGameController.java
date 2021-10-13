package com.marvelous.game.controller;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import com.marvelous.game.model.GameCharacter;
import com.marvelous.game.services.MarvelousGameServices;


@RestController
public class MarvelousGameController {

	@Autowired
	MarvelousGameServices gameService;

	static Map<String, GameCharacter> gameCharacters = new HashMap<String, GameCharacter>();

	@Scheduled(fixedRate = 30000)
	@GetMapping("/call3")
	public String callAllApis() {
		String inline = "";
		inline += gameService.getInline1();
		inline += gameService.getInline2();
		inline += gameService.getInline3();
		return inline;
	}
	
	@GetMapping("/callCharacter/{name}")
	public  Map<String, GameCharacter> callCharacter(@PathVariable String name) {
		gameCharacters = gameService.callCharacter(name);
		return gameCharacters;
	}
	
	@GetMapping("/getPower/{name}")
	public long powerCall(@PathVariable String name) {
		
		return  gameService.getPower(name);
	}
}
