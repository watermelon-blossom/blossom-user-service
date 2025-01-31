package com.watermelon.dateapp.domain.user;

import static jakarta.persistence.FetchType.*;
import static jakarta.persistence.GenerationType.*;
import static lombok.AccessLevel.*;

import java.util.ArrayList;
import java.util.List;

import com.watermelon.dateapp.api.dto.QuestionInfo;
import com.watermelon.dateapp.global.common.BaseEntity;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
public class User extends BaseEntity{
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "user_id")
	Long id;

	@Embedded
	@AttributeOverrides({
		@AttributeOverride(name = "id", column = @Column(name = "user_login_id")),
		@AttributeOverride(name = "password", column = @Column(name = "user_login_password")),
		@AttributeOverride(name = "roles", column = @Column(name = "user_roles"))
	})
	private LoginInfo loginInfo;

	@Embedded
	@AttributeOverride(name = "value", column = @Column(name = "name", nullable = false))
	private UserName userName;

	@Enumerated(EnumType.STRING)
	private Sex sex;
	private Integer age;
	private String job;
	private String description;

	//TODO Extract as GeoInfo, add Domain Business logic
	private Double lastLatitude;
	private Double lastLongitude;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "location_id")
	private UserLocation location;

	@ManyToOne(fetch = LAZY)
	private Tendency tendency;

	@OneToMany(mappedBy = "user")
	private List<UserPhoto> userPhoto = new ArrayList<>();
	@OneToMany(mappedBy = "user")
	private List<UserQuestion> userQuestion = new ArrayList<>();

	public static User of(
		String userId,
		String userPassword,
		String username,
		Sex sex,
		Integer age,
		Double lastLatitude,
		Double lastLongitude,
		UserLocation location) {
		User user = new User();
		user.loginInfo = LoginInfo.of(userId, userPassword);
		user.userName = new UserName(username);
		user.sex = sex;
		user.age = age;
		user.lastLatitude = lastLatitude;
		user.lastLongitude = lastLongitude;
		user.location = location;
		return user;
	}

	public String getUserName() {
		return userName.getValue();
	}

	public void updateUser(String userName, Double lastLatitude, Double lastLongitude, UserLocation location) {
		this.userName = new UserName(userName);
		this.lastLatitude = lastLatitude;
		this.lastLongitude = lastLongitude;
		this.location = location;
	}

	public List<String> getUserPhotoFileNames() {
		List<String> photos = new ArrayList<>();
		for (UserPhoto photo : userPhoto) {
			photos.add(photo.getStoreFileName());
		}
		return photos;
	}

	public List<QuestionInfo> getQuestionInfos() {
		List<QuestionInfo> questionInfos = new ArrayList<>();
		for (UserQuestion userQuestion : userQuestion) {
			String question = userQuestion.getQuestion().getQuestion();
			String answer = userQuestion.getAnswer();
            questionInfos.add(new QuestionInfo(question, answer));
		}
		return questionInfos;
	}
}
