package com.ch.ebusiness;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EBusinessApplicationTests {

	@Test
	public void contextLoads() {
	}

	@Test
	public void pswTest()
	{
		System.out.println(new BCryptPasswordEncoder().encode("sjy666"));
	}
}
