package com.marvelous.game.services;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.marvelous.game.model.GameCharacter;
import com.marvelous.game.repository.GameRepository;


@Service
public class MarvelousGameServices {

	@Autowired
	private GameRepository gameRepository;
		
	public long getPower(String characterName) {
		return gameRepository.getCurrentPower(characterName);
	}
	
	public Map<String, GameCharacter> callCharacter(String characterName) {
		return gameRepository.createOrAddCharacter(characterName);
	}
		
	@Async("asyncExecutor")
	public String getInline1() {
		return  gameRepository.callAllApis("http://www.mocky.io/v2/5ecfd5dc3200006200e3d64b");
	}
	
	@Async("asyncExecutor")
	public String getInline2() {
		return  gameRepository.callAllApis("http://www.mocky.io/v2/5ecfd630320000f1aee3d64d");
	}
	
	@Async("asyncExecutor")
	public String getInline3() {
		return gameRepository.callAllApis("http://www.mocky.io/v2/5ecfd6473200009dc1e3d64e");
	}
	
	
}
