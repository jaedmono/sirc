package co.gov.movilidadbogota.core.util;

import java.net.URI;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.servlet.http.HttpServletRequest;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

public class SecurityUtils {

	private static final String[] HEADERS_TO_TRY = { "X-Forwarded-For", "Proxy-Client-IP", "WL-Proxy-Client-IP",
			"HTTP_X_FORWARDED_FOR", "HTTP_X_FORWARDED", "HTTP_X_CLUSTER_CLIENT_IP", "HTTP_CLIENT_IP",
			"HTTP_FORWARDED_FOR", "HTTP_FORWARDED", "HTTP_VIA", "REMOTE_ADDR" };

	public static String getClientIpAddress(HttpServletRequest request) {
		for (String header : HEADERS_TO_TRY) {
			String ip = request.getHeader(header);
			if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {
				return ip;
			}
		}
		return request.getRemoteAddr();
	}

	public static byte[] generateSalt() {
		try {
			return SecureRandom.getInstance("SHA1PRNG").generateSeed(16);
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}

	public static String hashPassword(char[] password, byte[] salt) {
		try {
			SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
			PBEKeySpec spec = new PBEKeySpec(password, salt, 1000, 128);
			SecretKey key = skf.generateSecret(spec);
			byte[] res = key.getEncoded();
			return Base64.getEncoder().encodeToString(res);
		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			throw new RuntimeException(e);
		}
	}

	public static String hashPassword(String password) {
		MessageDigest messageDigest;
		try {
			messageDigest = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException arg2) {
			throw new RuntimeException(arg2);
		}

		return Base64.getEncoder().encodeToString(messageDigest.digest(password.getBytes()));
	}

	public static boolean validatePasswordAndSalt(char[] password, String hashedPassword, String encodedSalt) {

		byte[] salt = Base64.getDecoder().decode(encodedSalt);

		return hashPassword(password, salt).equals(hashedPassword);
	}

	public static boolean validatePasswordAndSalt(String password, String hashedPassword) {
		return hashPassword(password).equals(hashedPassword);
	}

	public static String generateUrlSafeToken() {
		String emailConfirmationToken = Long.toString(new SecureRandom().nextLong());
		MessageDigest messageDigest;
		try {
			messageDigest = MessageDigest.getInstance("SHA-256");
			emailConfirmationToken = Base64.getEncoder()
					.encodeToString(messageDigest.digest(emailConfirmationToken.getBytes())).replaceAll("\\+", "-")
					.replaceAll("/", "_");
			return emailConfirmationToken;
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}

	public static String validateCaptcha(String gRecaptchaResponse, String secretKey,
			Function<ResponseEntity, String> invalidStatusCodeFunction, Supplier<String> invalidResponseSupplier,
			Function<RestClientException, String> exceptionFunction) {

		// Prepare request
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		MultiValueMap<String, String> requestMap = new LinkedMultiValueMap<>();
		requestMap.add("secret", secretKey);
		requestMap.add("response", gRecaptchaResponse);

		HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(requestMap, headers);

		RestTemplate restTemplate = new RestTemplate(new HttpComponentsClientHttpRequestFactory());

		try {
			// Send request
			ResponseEntity<Map<String, Object>> responseEntity = restTemplate.exchange(
					URI.create("https://www.google.com/recaptcha/api/siteverify"), HttpMethod.POST, requestEntity,
					new ParameterizedTypeReference<Map<String, Object>>() {
					});

			// Check status code
			if (!responseEntity.getStatusCode().equals(HttpStatus.OK)) {
				return invalidStatusCodeFunction.apply(responseEntity);
			}

			Map<String, Object> responseMap = responseEntity.getBody();

			if (!((Boolean) responseMap.get("success"))) {
				return invalidResponseSupplier.get();
			}
		} catch (RestClientException e) {
			return exceptionFunction.apply(e);
		}

		return null;
	}

}
