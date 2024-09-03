package com.watermelon.dateapp.security;

import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.watermelon.dateapp.api.dto.UserLoginRequest;
import com.watermelon.dateapp.service.UserService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	private final AuthenticationManager authenticationManager;
	private final UserService userService;

	@Value("${jwt.secret}")
	private String tokenSecret;

	@Value("${jwt.expiration_date}")
	private String tokenExpirationTime;

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request,
		HttpServletResponse response) throws
		AuthenticationException {
		log.info("Authentication Filter Attempt Auth");
		try {
			UserLoginRequest creds = new ObjectMapper().readValue(request.getInputStream(), UserLoginRequest.class);
			return authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(
					creds.loginId(),
					creds.loginPassword(),
					new ArrayList<>()));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request,
		HttpServletResponse response,
		FilterChain chain,
		Authentication authResult) throws IOException, ServletException {
		log.info("Authentication Filter successful Auth");
		byte[] secretKeyBytes = Base64.getEncoder().encode(tokenSecret.getBytes());

		SecretKey secretKey = Keys.hmacShaKeyFor(secretKeyBytes);

		Instant now = Instant.now();

		String token = Jwts.builder()
			.subject("highestbright98@naver.com")
			.expiration(Date.from(now.plusMillis(Long.parseLong(tokenExpirationTime))))
			.issuedAt(Date.from(now))
			.signWith(secretKey)
			.compact();

		response.addHeader("Authorization", "Bearer" + token);
	}

	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
		AuthenticationException failed) {

		// 실패 시 401 응답코드 보냄
		response.setStatus(401);
	}
}
