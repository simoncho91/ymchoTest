package com.shinsegae_inc;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.shinsegae_inc.core.security.service.CryptoService;
import com.shinsegae_inc.core.security.service.KeyGeneratorService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {CryptoService.class, KeyGeneratorService.class}
        ,properties = "spring.config.location=classpath:/application.yml,classpath:/sitims.yml"
)
@ActiveProfiles("local")
public class JasyptEncryptTest {

    @Autowired
    protected CryptoService cryptoService;

	/** Log Info */
	private static final Logger LOGGER = LoggerFactory.getLogger(JasyptEncryptTest.class);

    boolean is256 = true;
    
	public JasyptEncryptTest() {}

	@Test
	public void encTest() {
		String originStr = "";
		LOGGER.info("encTest");

		originStr = "jdbc:log4jdbc:tibero:thin:@localhost:8629:tibero";
		LOGGER.info(originStr+ " -> "  + cryptoService.encZeroSalt(originStr, is256));
		originStr = "username";
		LOGGER.info(originStr+ " -> "  + cryptoService.encZeroSalt(originStr, is256));
		originStr = "password";
		LOGGER.info(originStr+ " -> "  + cryptoService.encZeroSalt(originStr, is256));
		
	}
	
	@Test
	public void decTest() {
		String encStr = "";
		LOGGER.info("decTest");
		
		//oracle
		encStr = "";
		
		//PostgreSQL
		encStr = "7Q/vdTjEQeA34YPfdWGNkL60aG0PUBE1hqn/glKA1kpvlSFjf0GiB0LkQ/vxSauvZo4uO1VTjHtCstJUU2UAjw==";
        LOGGER.info(encStr+ " -> "  + cryptoService.decZeroSalt(encStr, is256));

		encStr = "7Q/vdTjEQeA34YPfdWGNkD2fEQRSEVoGC4lZbP36su6tB//3HK01OOKb9GCqr41dGvTOHImAatriAdb4P9hosw==";
        LOGGER.info(encStr+ " -> "  + cryptoService.decZeroSalt(encStr, is256));
		
        
        
		encStr = "XN7wNy0sLAq7bpto9rN9Xg==";
        LOGGER.info(encStr+ " -> "  + cryptoService.decZeroSalt(encStr, is256));
	}
}
