package com.digitalhealth.service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.security.SecureRandom;
import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class EmailOtpService {
  private final String brevoApiKey;
  private final String from;
  private final SecureRandom random = new SecureRandom();
  private final ConcurrentHashMap<String, Challenge> challenges = new ConcurrentHashMap<>();
  private final HttpClient client = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(10)).build();

  public EmailOtpService(@Value("${BREVO_API_KEY:}") String brevoApiKey,
                         @Value("${app.mail.from:}") String from) {
    this.brevoApiKey = brevoApiKey;
    this.from = from;
  }

  public void send(String username, String email) {
    if (brevoApiKey.isBlank() || from == null || from.isBlank()) {
      throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE,
          "Email verification is not ready yet. Configure BREVO_API_KEY and MAIL_FROM in Render.");
    }
    String code = String.format("%06d", random.nextInt(1_000_000));
    String body = "{\"sender\":{\"email\":\"" + json(from) + "\",\"name\":\"Kerala Health\"},"
        + "\"to\":[{\"email\":\"" + json(email) + "\"}],"
        + "\"subject\":\"Kerala Health login verification code\","
        + "\"textContent\":\"Your Kerala Health verification code is: " + code
        + "\\n\\nIt expires in 10 minutes. Do not share this code.\"}";
    try {
      HttpRequest request = HttpRequest.newBuilder(URI.create("https://api.brevo.com/v3/smtp/email"))
          .timeout(Duration.ofSeconds(15))
          .header("accept", "application/json")
          .header("api-key", brevoApiKey)
          .header("content-type", "application/json")
          .POST(HttpRequest.BodyPublishers.ofString(body))
          .build();
      HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
      if (response.statusCode() < 200 || response.statusCode() >= 300) {
        throw new IllegalStateException("Brevo returned HTTP " + response.statusCode());
      }
      challenges.put(username.toLowerCase(), new Challenge(code, Instant.now().plusSeconds(600), 0));
    } catch (Exception e) {
      throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE,
          "Unable to send the verification email. Please try again later.");
    }
  }

  public void verify(String username, String code) {
    String key = username.toLowerCase();
    Challenge challenge = challenges.get(key);
    if (challenge == null || challenge.expiresAt.isBefore(Instant.now())) {
      challenges.remove(key);
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "The verification code has expired. Please log in again.");
    }
    if (challenge.attempts >= 5) {
      challenges.remove(key);
      throw new ResponseStatusException(HttpStatus.TOO_MANY_REQUESTS, "Too many invalid codes. Please log in again.");
    }
    if (!challenge.code.equals(code)) {
      challenges.put(key, new Challenge(challenge.code, challenge.expiresAt, challenge.attempts + 1));
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid verification code.");
    }
    challenges.remove(key);
  }

  private static String json(String value) {
    return value.replace("\\", "\\\\").replace("\"", "\\\"").replace("\n", "\\n");
  }

  private record Challenge(String code, Instant expiresAt, int attempts) {}
}
