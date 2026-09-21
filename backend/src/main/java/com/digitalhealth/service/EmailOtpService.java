package com.digitalhealth.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.security.SecureRandom;
import java.time.Instant;
import java.util.concurrent.ConcurrentHashMap;

@Service public class EmailOtpService {
 private static final Logger log=LoggerFactory.getLogger(EmailOtpService.class);
 private final JavaMailSender mail; private final String host,from; private final SecureRandom random=new SecureRandom(); private final ConcurrentHashMap<String,Challenge> challenges=new ConcurrentHashMap<>();
 public EmailOtpService(JavaMailSender mail,@Value("${spring.mail.host:}") String host,@Value("${app.mail.from:}") String from){this.mail=mail;this.host=host;this.from=from;}
 public void send(String username,String email){if(host==null||host.isBlank()||from==null||from.isBlank())throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE,"Email OTP is not configured. Set MAIL_USERNAME, MAIL_PASSWORD, and MAIL_FROM before logging in.");String code=String.format("%06d",random.nextInt(1_000_000));challenges.put(username.toLowerCase(),new Challenge(code,Instant.now().plusSeconds(600),0));SimpleMailMessage message=new SimpleMailMessage();message.setFrom(from);message.setTo(email);message.setSubject("Kerala Health login verification code");message.setText("Your Kerala Health verification code is: "+code+"\n\nIt expires in 10 minutes. Do not share this code.");try{mail.send(message);}catch(Exception e){challenges.remove(username.toLowerCase());String details=String.valueOf(e).toLowerCase();log.warn("OTP email could not be sent: {}",e.toString());if(details.contains("authentication")||details.contains("535")||details.contains("password not accepted"))throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE,"Gmail rejected the email credentials. Replace MAIL_PASSWORD with a current Gmail App Password and redeploy.");if(details.contains("timeout")||details.contains("connect"))throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE,"The server could not reach Gmail in time. Please wait a minute and try again.");throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE,"Gmail could not send the verification email. Check MAIL_USERNAME, MAIL_PASSWORD, and MAIL_FROM in Render.");}}
 public void verify(String username,String code){String key=username.toLowerCase();Challenge c=challenges.get(key);if(c==null||c.expiresAt.isBefore(Instant.now())){challenges.remove(key);throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"The verification code has expired. Please log in again.");}if(c.attempts>=5){challenges.remove(key);throw new ResponseStatusException(HttpStatus.TOO_MANY_REQUESTS,"Too many invalid codes. Please log in again.");}if(!c.code.equals(code)){challenges.put(key,new Challenge(c.code,c.expiresAt,c.attempts+1));throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Invalid verification code.");}challenges.remove(key);} private record Challenge(String code,Instant expiresAt,int attempts){}
}
