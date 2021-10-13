package com.marvelous.game.main;


import static org.assertj.core.api.Assertions.assertThat;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.marvelous.game.model.GameCharacter;
import com.marvelous.game.services.MarvelousGameServices;

@SpringBootTest
@RunWith(SpringRunner.class)
class ServiceTest {

	@Autowired
	MarvelousGameServices service;
	

	@Test
	void testCallCharacter() {
		Map<String, GameCharacter> mp = service.callCharacter("Ego");
		assertThat(mp).size().isGreaterThan(0);
	}
	
	@Test
	void testGetPower() {
		long power = service.getPower("Ego");
		assertThat(power).isGreaterThan(0);
		
	}

}
