package com.watermelon.dateapp.domain.user;

import static jakarta.persistence.GenerationType.*;
import static lombok.AccessLevel.*;

import java.util.ArrayList;
import java.util.List;

import com.watermelon.dateapp.api.dto.CreateUserRequest;
import com.watermelon.dateapp.global.common.BaseEntity;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
@Builder
public class User extends BaseEntity {
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "user_id")
	Long id;

	@Embedded
	@AttributeOverride(name = "value", column = @Column(name = "name", nullable = false))
	private UserName userName;
	private Sex sex;
	private Integer age;
	private Double lastLatitude;
	private Double lastLongitude;

	//TODO Location 클래스 혹은 엔티티 만들기
	private String location;

	@OneToMany(mappedBy = "user")
	private List<UserTendency> userTendency = new ArrayList<>();

	public static User of(String username) {
		User user = new User();
		user.userName = new UserName(username);
		return user;
	}
}
