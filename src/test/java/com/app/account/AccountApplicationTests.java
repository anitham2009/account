package com.app.account;

import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AccountApplicationTests {

	@Test
	void contextLoads() {
		AccountApplication.main(new String[] {});
		assertTrue(true);
	}

}
