package com.marvelous.game.repository;

import java.util.Map;

import com.marvelous.game.model.GameCharacter;

public interface GameRepository {

	public String callAllApis(String urlString);
	public Map<String, GameCharacter> updatePowerOfAllCharacters(String inline);
	public void printCharactersMap();
	public Map<String, GameCharacter> addCharactersByRules(GameCharacter gameCharacter);
	public Map<String, GameCharacter> createOrAddCharacter(String characterName);
	public boolean removeLeastUsedCharacters();
	public boolean removeLeastPoweredCharacter();
	public long getCurrentPower(String characterName);
	public long getMinPower();
	public int getMinFrequency();
	public String callApis(String urlString);
	public GameCharacter findCharacter(String characterName);
}
