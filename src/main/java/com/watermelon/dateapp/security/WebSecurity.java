package com.watermelon.dateapp.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.watermelon.dateapp.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurity {
	private final UserService userService;
	private final JwtTokenProvider jwtTokenProvider;
	private final AuthenticationConfiguration authenticationConfiguration;

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
		return configuration.getAuthenticationManager();
	}

	@Bean
	protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		http
			.httpBasic(AbstractHttpConfigurer::disable)
			.formLogin(AbstractHttpConfigurer::disable)
			.cors(cors -> cors.configurationSource(corsConfigurationSource()))
			.csrf(AbstractHttpConfigurer::disable)
			.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

		http
			.authorizeHttpRequests(
				request -> request
					// .requestMatchers("/user-service/users/login", "/user-service/users","/error").permitAll()
					.requestMatchers(new AntPathRequestMatcher("/users/login", "POST")).permitAll()
					.requestMatchers(new AntPathRequestMatcher("/users", "POST")).permitAll()
					.requestMatchers(new AntPathRequestMatcher("/error")).permitAll()
					.anyRequest().authenticated()
			);

		// custom Auth filters
		http.
			addFilterBefore(getAuthorizationFilter(jwtTokenProvider), JwtAuthenticationFilter.class);
		http
			.addFilterAt(getAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
		http
			.headers(header -> header.frameOptions(
				HeadersConfigurer.FrameOptionsConfig::sameOrigin));

		return http.build();
	}

	private JwtAuthenticationFilter getAuthenticationFilter() throws Exception {
		JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(
			authenticationManager(authenticationConfiguration), userService);
		jwtAuthenticationFilter.setFilterProcessesUrl("/user-service/users/login");
		return jwtAuthenticationFilter;
	}

	private JwtAuthorizationFilter getAuthorizationFilter(JwtTokenProvider jwtTokenProvider) {
		return new JwtAuthorizationFilter(jwtTokenProvider);
	}

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();

		configuration.addAllowedOrigin("*");
		configuration.addAllowedHeader("*");
		configuration.addAllowedMethod("*");
		configuration.setAllowCredentials(true);

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}
}


