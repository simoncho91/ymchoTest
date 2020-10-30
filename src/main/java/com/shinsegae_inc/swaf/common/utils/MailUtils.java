package com.shinsegae_inc.swaf.common.utils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.shinsegae_inc.core.exception.SwafException;

public class MailUtils {

	public static enum MailType {
		TEXT,HTML
	}
	
	/**
	 * 메일 전송 유틸
	 * 
	 * mailProps 필수 내용
	 * host 		: SMTP 서버 주소
	 * port 		: SMTP port
	 * username		: SMTP ID
	 * password		: SMTP PASSWORD
	 * fromAddr		: 보내는 사람 메일 주소
	 * toAddr		: 받는 사람 메일 주소, 복수 수신자는 ,로 구분
	 * ccAddr		: 참조자 메일주소, 복수 수신자는,로 구분
	 * subject		: 메일 제목
	 * contents		: 메일 내용
	 * 
	 * @param MailType
	 * @param Map<String,String>
	 * @return boolean
	 * @throws SwafException
	 */	
	public static boolean sendMail(MailType type, Map<String,String> mailProps) throws SwafException, MessagingException, UnsupportedEncodingException {
		
    	Properties props = System.getProperties();
    	props.put("mail.transport.protocol", "smtp");
    	props.put("mail.smtp.port", mailProps.get("port")); 
    	props.put("mail.smtp.starttls.enable", "true");
    	props.put("mail.smtp.auth", "true");

    	Session session = Session.getDefaultInstance(props);

        MimeMessage msg = new MimeMessage(session);

        msg.setFrom(new InternetAddress(mailProps.get("fromAddr"),mailProps.get("fromName")));
        
        String[] toAddrs = mailProps.get("toAddr").split(";");
        Address[] toArr = new Address[toAddrs.length];
        for(int i=0; i < toAddrs.length; i++) {
        	toArr[i] = new InternetAddress(toAddrs[i]);
        }
        msg.setRecipients(Message.RecipientType.TO,toArr);
        
        String ccAddr = mailProps.get("ccAddr");
        if( ccAddr != null && !ccAddr.isEmpty() ) {
	        String[] ccAddrs = mailProps.get("ccAddr").split(";");
	        if( ccAddrs.length > 0 ) {
		        Address[] ccArr = new Address[ccAddrs.length];
		        for(int i=0; i < ccAddrs.length; i++) {
		        	ccArr[i] = new InternetAddress(ccAddrs[i]);
		        }
		        msg.setRecipients(Message.RecipientType.CC, ccArr);	        	
	        }
        }
        
        msg.setSubject(mailProps.get("subject"));
        switch(type) {
	        case TEXT:
	        	msg.setText(mailProps.get("contents"));
	        	break;
	        case HTML:
	        	msg.setDataHandler(new DataHandler(new HTMLDataSource(mailProps.get("contents"))));
	        	break;
	        default:
	        	break;
        }
    
        String configSet = mailProps.get("configSet");
        if( configSet != null && !configSet.isEmpty() )
        	msg.setHeader("X-SES-CONFIGURATION-SET", configSet);
        
    	Transport transport = session.getTransport();
        try
        {
            transport.connect(mailProps.get("host"), mailProps.get("userName"), mailProps.get("password"));
            transport.sendMessage(msg, msg.getAllRecipients());
        }
        finally
        {
            transport.close();
        }
        
        return true;

    }

    static class HTMLDataSource implements DataSource {

        private String html;

        public HTMLDataSource(String htmlString) {
            html = htmlString;
        }

        public InputStream getInputStream() throws IOException {
            if (html == null) throw new IOException("html message is null!");
            return new ByteArrayInputStream(html.getBytes());
        }

        public OutputStream getOutputStream() throws IOException {
            throw new IOException("This DataHandler can not write HTML");
        }

        public String getContentType() {
            return "text/html";
        }

        public String getName() {
            return "HTMLDataSource";
        }
    }
    
}
