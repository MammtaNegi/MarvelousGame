package com.marvelous.game.main;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.marvelous.game.model.GameCharacter;
import com.marvelous.game.repository.MarvelousGameRepository;


@SpringBootTest
@RunWith(SpringRunner.class)
class RepositoryTest {

	@Autowired
	MarvelousGameRepository repo;
	

	@Test
	void testCallAllApis() {
		String str  = repo.callAllApis("http://www.mocky.io/v2/5ecfd5dc3200006200e3d64b");
		assertNotNull(str);
		
	}

	
	@Test
	void testCreateOrAddCharacter() {
		Map<String, GameCharacter> mp = repo.createOrAddCharacter("Ego");
		assertThat(mp).size().isGreaterThan(0);
		
	}


	@Test
	void testGetCurrentPower() {
		long power = repo.getCurrentPower("Ego");
		assertThat(power).isGreaterThan(0);
	}

}

