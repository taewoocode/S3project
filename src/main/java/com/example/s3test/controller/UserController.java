package com.example.s3test.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.s3test.dto.UserRegisterInfo;
import com.example.s3test.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController("/api/users")
@Slf4j
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;

	@PostMapping("/register")
	public ResponseEntity<UserRegisterInfo.UserRegisterResponse> registerUser(
		@Valid @RequestBody UserRegisterInfo.UserRegisterRequest request) {
		UserRegisterInfo.UserRegisterResponse userRegisterResponse = userService.registerUser(request);
		return ResponseEntity.ok(userRegisterResponse);
	}
}
