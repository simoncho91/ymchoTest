package com.shinsegae_inc;


import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.shinsegae_inc.core.exception.SwafException;
import com.shinsegae_inc.core.security.service.CryptoService;
import com.shinsegae_inc.core.security.service.KeyGeneratorService;
import com.shinsegae_inc.swaf.common.utils.MailUtils;
import com.shinsegae_inc.swaf.common.utils.MailUtils.MailType;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {CryptoService.class, KeyGeneratorService.class}
        ,properties = "spring.config.location=classpath:/application.yml,classpath:/sitims.yml"
)
@ActiveProfiles("local")
public class mailTest {

    @Autowired
    protected CryptoService cryptoService;

	/** Log Info */
	private static final Logger LOGGER = LoggerFactory.getLogger(mailTest.class);

    boolean is256 = true;
	public mailTest() {}

    @Value("${globals.smtp.host}")
    private String host;        // host

    @Value("${globals.smtp.port}")
    private String port;        // port

    @Value("${globals.smtp.username}")
    private String userName;        // username

    @Value("${globals.smtp.password}")
    private String password;        // password

	@Test
	public void sendMailwithHtml() throws SwafException, MessagingException, UnsupportedEncodingException {
		
		LOGGER.info("Send Mail with HTML Test Start");
		Map<String,String> mailProps = new HashMap<String,String>();
		mailProps.put("host", host);
		mailProps.put("port", port);
		mailProps.put("userName", cryptoService.decAES(userName, true) );
		mailProps.put("password", cryptoService.decAES(password, true) );
		mailProps.put("fromAddr", "from_mail_address@shinsegae.com");
		mailProps.put("toAddr", "to_mail_addr@shinsegae.com");
		mailProps.put("ccAddr", "cc_mail_addr@shinsegae.com");
		mailProps.put("subject", "제목-HTML");
		mailProps.put("contents", "<h1>메일 내용</h1>");
		MailUtils.sendMail(MailType.HTML, mailProps);
		LOGGER.info("Send Mail with HTML Test Done");
		LOGGER.info("http://10.105.16.81:8080/ 에서 결과 확인 가능합니다.");
		
	}
	
	@Test
	public void sendMailwithText() throws SwafException, MessagingException, UnsupportedEncodingException {

		LOGGER.info("Send Mail with TEXT Test Start");
		Map<String,String> mailProps = new HashMap<String,String>();
		mailProps.put("host", host);
		mailProps.put("port", port);
		mailProps.put("userName", cryptoService.decAES(userName, true) );
		mailProps.put("password", cryptoService.decAES(password, true) );
		mailProps.put("fromAddr", "from_mail_address@shinsegae.com");
		mailProps.put("toAddr", "to_mail_addr@shinsegae.com");
		mailProps.put("ccAddr", "cc_mail_addr@shinsegae.com");
		mailProps.put("subject", "제목-TEXT");
		mailProps.put("contents", "메일 내용");
		MailUtils.sendMail(MailType.TEXT, mailProps);
		
		LOGGER.info("Send Mail with TEXT Test Done");
		LOGGER.info("http://10.105.16.81:8080/ 에서 결과 확인 가능합니다.");
		
	}



}
