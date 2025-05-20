package com.example.s3test.service;

import com.example.s3test.domain.User;
import com.example.s3test.dto.UserRegisterInfo;

public class UserConverter {
	public static User makeUserEntity(UserRegisterInfo.UserRegisterRequest request) {
		return User.builder()
			.userId(request.getUserId())
			.userPw(request.getUserPw())
			.userName(request.getUserName())
			.userEmail(request.getUserEmail())
			.build();
	}


	public static UserRegisterInfo.UserRegisterResponse makeUserRegisterResponse(User savedUser) {
		return UserRegisterInfo.UserRegisterResponse.builder()
			.id(savedUser.getId())
			.userName(savedUser.getUserName())
			.userEmail(savedUser.getUserEmail())
			.userId(savedUser.getUserId())
			.build();

	}
}
