package com.example.s3test.service;

import org.springframework.stereotype.Service;

import com.example.s3test.dto.UserRegisterInfo;

public interface UserService {

	/**
	 * 회원가입
	 * @return
	 */
	UserRegisterInfo.UserRegisterResponse registerUser(UserRegisterInfo.UserRegisterRequest);
}
