package com.example.s3test.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.s3test.domain.User;
import com.example.s3test.dto.UserRegisterInfo;
import com.example.s3test.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService{

	private final UserRepository userRepository;

	@Override
	@Transactional
	public UserRegisterInfo.UserRegisterResponse registerUser(UserRegisterInfo.UserRegisterRequest request) {
		if (userRepository.existsByUserId(request.getUserId())) {
			throw new IllegalArgumentException("이미 존재하는 Id입니다.");
		}
		User user = UserConverter.makeUserEntity(request);
		User savedUser = userRepository.save(user);
		return UserConverter.makeUserRegisterResponse(savedUser);
	}
}
