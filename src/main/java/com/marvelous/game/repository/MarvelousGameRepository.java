package com.marvelous.game.repository;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Repository;

import com.marvelous.game.model.GameCharacter;

@Repository
public class MarvelousGameRepository implements GameRepository{

	String[] urls = { 
			"http://www.mocky.io/v2/5ecfd5dc3200006200e3d64b",
			"http://www.mocky.io/v2/5ecfd630320000f1aee3d64d", 
			"http://www.mocky.io/v2/5ecfd6473200009dc1e3d64e" };

	ConcurrentHashMap<String, GameCharacter> gameCharacters = new ConcurrentHashMap<String, GameCharacter>();

	private static final int LIMIT = 15;
	private static int count = 0;

	public MarvelousGameRepository() {

	}

	// call existing apis and returns the string equivalent of resource
	@Override
	public String callAllApis(String urlString) {
		String inline = "";
		String data = "";
		try {
			URL url = new URL(urlString);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.connect();
			int responsecode = conn.getResponseCode();
			if (responsecode != 200)
				throw new RuntimeException("HttpResponseCode: " + responsecode);
			else {
				Scanner sc = new Scanner(url.openStream());
				while (sc.hasNext()) {
					data = sc.nextLine();
					inline += data;
				}
				sc.close();
			}
		} catch (Exception e) {
			System.out.println("Caught an exception.");
			e.printStackTrace();
		}

		System.out.println("---------------------------------------------------");
		System.out.println(count++);
		System.out.println("---------------------------------------------------");
		updatePowerOfAllCharacters(inline);
		return inline;
	}
	

	// updates power of all characters present in map that were called by the user
	@Override
	public Map<String, GameCharacter> updatePowerOfAllCharacters(String inline) {
		JSONParser parse = new JSONParser();
		JSONObject jobj;
		try {
			jobj = (JSONObject) parse.parse(inline);
			JSONArray jCharactersArray = (JSONArray) jobj.get("character");
			for (int i = 0; i < jCharactersArray.size(); i++) {
				JSONObject jsonobj_1 = (JSONObject) jCharactersArray.get(i);
				String name = (String) jsonobj_1.get("name");
				long power = (long) jsonobj_1.get("max_power");
				if (gameCharacters.containsKey(name)) {
					gameCharacters.get(name).setPower(power);
				}
			}
			System.out.println("Updated Powers");
		} catch (ParseException e) {
			System.out.println("Caught an exception......................");
			e.printStackTrace();
		}
		//printCharactersMap();
		System.out.println("---------------------------------------------------");
		return gameCharacters;
	}

	
	// prints all the characters present in hash map that were called by the user
	@Override
	public void printCharactersMap() {
		System.out.println("Size of Characters map " + gameCharacters.size());
		Iterator<Map.Entry<String, GameCharacter>> iterator = gameCharacters.entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<String, GameCharacter> temporaryCharacter = iterator.next();
			System.out.println(temporaryCharacter.toString());
		}
		System.out.println("---------------------------------------------------");
	}

	// business rules and other rules to add character to the hash map
	@Override
	public Map<String, GameCharacter> addCharactersByRules(GameCharacter gameCharacter) {
		if (gameCharacters.size() < LIMIT) {
			System.out.println("Adding character :: "+gameCharacter.toString());
			if (gameCharacters.containsKey(gameCharacter.getName())) {
				gameCharacter.setFrequency(gameCharacters.get(gameCharacter.getName()).getFrequency() + 1);
				gameCharacters.replace(gameCharacter.getName(), gameCharacter);
			} else {
				gameCharacter.setFrequency(1);
				gameCharacters.put(gameCharacter.getName(), gameCharacter);
			} 
			gameCharacters.put(gameCharacter.getName(), gameCharacter);
		} else {
			System.out.println("Making space to add character");
			if (gameCharacters.containsKey(gameCharacter.getName())) {
				removeLeastUsedCharacters();
				int freq = 1;
				if (gameCharacters.containsKey(gameCharacter.getName())) {
					freq = gameCharacters.get(gameCharacter.getName()).getFrequency() + freq; 
				}
				gameCharacter.setFrequency(freq);
				gameCharacters.put(gameCharacter.getName(), gameCharacter);
			} else {
				removeLeastPoweredCharacter(); 
				gameCharacter.setFrequency(1);
				gameCharacters.put(gameCharacter.getName(), gameCharacter);
			}
		}
		System.out.println(gameCharacter.getName()+" was Added Successfully.");
		System.out.println("---------------------------------------------------");
		return gameCharacters;
	}

	// called to add a character to the map
	@Override
	public Map<String, GameCharacter> createOrAddCharacter(String characterName) {
		GameCharacter gameCharacter = findCharacter(characterName);
		if (gameCharacter != null) {
			addCharactersByRules(gameCharacter);
		} else {
			System.out.println(characterName+" not present in api :(  Will get that character in next version.");
		}
		System.out.println("---------------------------------------------------");
		return gameCharacters;
	}

	// removes all characters who are are used or called least number of times
	@Override
	public boolean removeLeastUsedCharacters() {
		int frequency = getMinFrequency();
		Iterator<Map.Entry<String, GameCharacter>> iterator = gameCharacters.entrySet().iterator();
		int counter = 0;
		GameCharacter gameCharacter;
		while (iterator.hasNext()) {
			Map.Entry<String, GameCharacter> temp = iterator.next();
			gameCharacter = temp.getValue();
			if (gameCharacter.getFrequency() == frequency) { //
				gameCharacters.remove(gameCharacter.getName());
				iterator.remove();
				++counter;
			}
		}
		System.out.println("Removed "+counter+" characters who were called "+frequency+" times only.");
		System.out.println("---------------------------------------------------");
		return true;
	}

	// removes all characters whose power is least
	@Override
	public boolean removeLeastPoweredCharacter() {
		long power = getMinPower();
		int counter = 0;
		Iterator<Map.Entry<String, GameCharacter>> iterator = gameCharacters.entrySet().iterator();
		GameCharacter gameCharacter;
		while (iterator.hasNext()) {
			Entry<String, GameCharacter> temp = iterator.next();
			gameCharacter = (GameCharacter) temp.getValue();
			if (gameCharacter.getPower() == power) { // gameCharacters.remove(gameCharacter.getName());
				iterator.remove();
				++counter;
			}
		}
		System.out.println("Removed "+counter+" characters who had power level "+power+" only.");
		System.out.println("---------------------------------------------------");
		return true;
	}

	// return the updated power level of the character
	@Override
	public long getCurrentPower(String characterName) {
		long power = 0;
		if (gameCharacters.containsKey(characterName)) {
			power = gameCharacters.get(characterName).getPower();
		} else {
			GameCharacter gameCharacter = findCharacter(characterName);
			if (gameCharacter != null) {
				power = gameCharacter.getPower();
			}
		}
		System.out.println("Current Power of "+characterName+" is "+power);
		System.out.println("---------------------------------------------------");
		return power;
	}

	// returns the power of the least powerful character
	@Override
	public long getMinPower() {
		Iterator<Map.Entry<String, GameCharacter>> iterator = gameCharacters.entrySet().iterator();
		Map.Entry<String, GameCharacter> temp;
		GameCharacter gameCharacter;
		long power = 100;
		while (iterator.hasNext()) {
			temp = iterator.next();
			gameCharacter = temp.getValue();
			if (gameCharacter.getPower() < power) {
				power = gameCharacter.getPower();
			}
		}
		System.out.println("Characters with power level "+power+" are considered least powered characters.");
		System.out.println("---------------------------------------------------");
		return power;
	}

	// returns frequency of the least used characters are character
	@Override
	public int getMinFrequency() {
		Iterator<Map.Entry<String, GameCharacter>> iterator = gameCharacters.entrySet().iterator();
		Map.Entry<String, GameCharacter> temp;
		GameCharacter gameCharacter;
		int frequency = 100;
		while (iterator.hasNext()) {
			temp = iterator.next();
			gameCharacter = (GameCharacter) temp.getValue();
			if (gameCharacter.getFrequency() < frequency) {
				frequency = gameCharacter.getFrequency();
			}
		}
		System.out.println("Characters who are called "+frequency+" times only are considered least called.");
		System.out.println("---------------------------------------------------");
		return frequency;
	}


	// returns the string equivalent of json data
	@Override
	public String callApis(String urlString) {
		String inline = "";
		String data = "";
		try {
			URL url = new URL(urlString);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.connect();
			int responsecode = conn.getResponseCode();
			if (responsecode != 200)
				throw new RuntimeException("HttpResponseCode: " + responsecode);
			else {
				Scanner sc = new Scanner(url.openStream());
				while (sc.hasNext()) {
					data = sc.nextLine();
					inline += data;
				}
				sc.close();
			}
		} catch (Exception e) {
			System.out.println("Caught an exception.");
			e.printStackTrace();
		}

		return inline;
	}

	// used to find a character from api. returns that character or null.
	@Override
	public GameCharacter findCharacter(String characterName) {
		int counter = 0;
		String inline = "";
		boolean flag = false;
		GameCharacter gameCharacter = null;
		while (counter < urls.length && !flag) {
			inline = callApis(urls[counter]);
			System.out.println("Searching for "+characterName+"....");
			JSONParser parse = new JSONParser();
			JSONObject jobj;
			try {
				jobj = (JSONObject) parse.parse(inline);
				JSONArray jCharactersArray = (JSONArray) jobj.get("character");
				for (int i = 0; i < jCharactersArray.size(); i++) {
					JSONObject jsonobj_1 = (JSONObject) jCharactersArray.get(i);
					String name = (String) jsonobj_1.get("name");
					long power = (long) jsonobj_1.get("max_power");
					if (name.equalsIgnoreCase(characterName)) {
						gameCharacter = new GameCharacter(name, power);
						flag = true;
						break;
					}
				}
			} catch (ParseException e) {
				System.out.println("Caught an exception......................");
				e.printStackTrace();
			}
			++counter;
		}
		System.out.println("---------------------------------------------------");
		if (flag) {
			System.out.println(characterName+" found.");
			return gameCharacter;
		} else {
			System.out.println(characterName+" is not present in game.");
			return null;
		}
	}

}
