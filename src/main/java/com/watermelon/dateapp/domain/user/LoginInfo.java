package com.watermelon.dateapp.domain.user;

import static lombok.AccessLevel.*;

import java.util.List;

import com.watermelon.dateapp.global.converter.StringListConverter;

import jakarta.persistence.Convert;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
public class LoginInfo {

	@NotNull(message = "id cannot be null")
	@Size(min = 2, message = "id not be less than two character")
	private String id;

	@NotNull(message = "Password cannot be null")
	@Size(min = 6, message = "Password must be equals or greater than 8 characters")
	private String password;

	@Convert(converter = StringListConverter.class)
	private List<String> roles = List.of("ROLE_USER");

	public static LoginInfo of(String id, String password) {
		return new LoginInfo(id, password, List.of("ROLE_USER"));
	}
}
