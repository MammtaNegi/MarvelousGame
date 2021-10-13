package com.marvelous.game.main;


import static org.assertj.core.api.Assertions.assertThat;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import com.marvelous.game.controller.MarvelousGameController;
import com.marvelous.game.model.GameCharacter;


@SpringBootTest
@RunWith(SpringRunner.class)
class GameControllerTest {

	@Autowired
	MarvelousGameController apiController;
	
	@Test
	void testCallAllApis() {
		String str = apiController.callAllApis();
		assertThat(str.length()).isGreaterThan(10);
	}

	@Test
	void testCallCharacter() {
		Map<String, GameCharacter> mp = apiController.callCharacter("Vision");
		assertThat(mp).size().isEqualTo(1);
	}

	@Test
	void testPowerCall() {
		long power = apiController.powerCall("Vision");
		assertThat(power).isGreaterThan(0);
	}

}
