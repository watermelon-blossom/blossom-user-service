package com.watermelon.dateapp.domain.user;

import static jakarta.persistence.GenerationType.*;
import static lombok.AccessLevel.*;

import java.util.ArrayList;
import java.util.List;

import com.watermelon.dateapp.global.common.BaseEntity;

import jakarta.persistence.*;
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
	@Enumerated(EnumType.STRING)
	private Sex sex;
	private Integer age;
	private Double lastLatitude;
	private Double lastLongitude;

	//TODO Location 클래스 혹은 엔티티 만들기
	private String location;

	@OneToMany(mappedBy = "user")
	private List<UserTendency> userTendency = new ArrayList<>();
	@OneToMany(mappedBy = "user")
	private List<UserPhoto> userPhoto = new ArrayList<>();

	public static User of(String username, Sex sex, Integer age, Double lastLatitude, Double lastLongitude, String location) {
		User user = new User();
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

	public void updateUser(String userName, Double lastLatitude, Double lastLongitude, String location) {
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
}
