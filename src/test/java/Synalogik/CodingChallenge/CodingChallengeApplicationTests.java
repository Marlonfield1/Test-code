package Synalogik.CodingChallenge;

import org.junit.jupiter.api.Test;
import  org.junit.*;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CodingChallengeApplicationTests {

	@Test
	void contextLoads() {
		FileReader application = new FileReader();
		String result = application.readFile("test.txt");
		System.out.println(result);
	}

}
