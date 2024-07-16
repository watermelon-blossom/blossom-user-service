package com.watermelon.dateapp.repository;

import static com.watermelon.dateapp.domain.user.QUser.*;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.watermelon.dateapp.api.dto.SearchUserRequest;
import com.watermelon.dateapp.api.dto.UserResponse;
import com.watermelon.dateapp.domain.user.Sex;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserSearchRepositoryImpl implements UserSearchRepository {
	private final JPAQueryFactory queryFactory;

	@Override
	public Page<UserResponse> searchUsers(SearchUserRequest request, Pageable pageable) {
		JPAQuery<UserResponse> query = queryFactory
			.select(Projections.constructor(UserResponse.class, user))
			.from(user)
			.where(
				sexEq(request.sex()),
				ageSearch(request.startAge(), request.endAge()),
				distanceSearch(request.latitude(), request.longitude(), request.radius()).loe(request.radius()),
				locationEq(request.location())
			)
			.orderBy(user.createdDate.desc())
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize());

		List<UserResponse> content = query.fetch();

		JPAQuery<Long> count = queryFactory
			.select(user.count())
			.from(user)
			.where(
				sexEq(request.sex()),
				ageSearch(request.startAge(), request.endAge()),
				distanceSearch(request.latitude(), request.longitude(), request.radius()).loe(request.radius()),
				locationEq(request.location())
			)
			.orderBy(user.createdDate.desc())
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize());

		return PageableExecutionUtils.getPage(content, pageable, count::fetchOne);

	}

	private BooleanExpression sexEq(Sex sex) {
		return sex != null ? user.sex.eq(sex) : null;
	}

	private BooleanExpression ageSearch(Integer startAge, Integer endAge) {
		BooleanExpression result = null;
		if (startAge != null && endAge != null) {
			result = user.age.between(startAge, endAge);
		} else if (startAge != null) {
			result = user.age.goe(startAge);
		} else if (endAge != null) {
			result = user.age.loe(endAge);
		}
		return result;
	}

	private NumberExpression<Double> distanceSearch(Double latitudeValue, Double longitudeValue, Double radius) {
		// GIS 두 지점(위도, 경도)간의 거리 계산
		// 거리 = 6371 * ACos( Cos( Lat1 ) * Cos( Lat2 ) * Cos( Lon2 - Lon1 ) + Sin( Lat1 ) * Sin( Lat2 ) )
		return Expressions.numberTemplate(Double.class,
			"6371 * acos(cos(radians({0})) * cos(radians({2})) * cos(radians({3}) - radians({1})) + sin(radians({0})) * sin(radians({2})))",
			latitudeValue, longitudeValue, user.lastLatitude, user.lastLongitude);
	}

	private BooleanExpression locationEq(String location) {
		return location != null ? user.location.locationName.eq(location) : null;
	}

}
