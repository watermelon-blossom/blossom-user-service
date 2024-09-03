package com.watermelon.dateapp.service;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.watermelon.dateapp.domain.user.CustomUserDetails;
import com.watermelon.dateapp.global.error.ApplicationException;
import com.watermelon.dateapp.global.error.ErrorType;
import com.watermelon.dateapp.repository.UserRepository;
import com.watermelon.dateapp.security.JwtToken;
import com.watermelon.dateapp.security.JwtTokenProvider;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserLoginService implements UserDetailsService {
	private final UserRepository userRepository;
	private final AuthenticationManagerBuilder authenticationManagerBuilder;
	private final JwtTokenProvider jwtTokenProvider;
	private final BCryptPasswordEncoder passwordEncoder;

	@Transactional
	public JwtToken login(String loginId, String loginPassword) {
		// 1. loginId, loginPassword 를 기반으로 Authentication 객체 생성
		// 이때 authentication 은 인증 여부를 확인하는 authenticated 값이 false
		log.info("service.login >>> " + loginId + "," + loginPassword);
		UsernamePasswordAuthenticationToken authenticationToken =
			new UsernamePasswordAuthenticationToken(loginId, loginPassword);

		// 2. 실제 검증. authenticate() 메서드를 통해 요청된 Member 에 대한 검증 진행
		// authenticate 메서드가 실행될 때 CustomUserDetailsService 에서 만든 loadUserByUsername 메서드 실행
		Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

		// 3. 인증 정보를 기반으로 JWT 토큰 생성

		return jwtTokenProvider.generateToken(authentication);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return userRepository.findByLoginInfoId(username)
			.map(user-> new CustomUserDetails(user.getLoginInfo()))
			.orElseThrow(() -> new ApplicationException(ErrorType.USER_NOT_FOUND));
	}
}
