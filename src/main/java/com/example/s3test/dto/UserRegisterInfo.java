package com.example.s3test.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

public class UserRegisterInfo {

	@Getter
	@Builder
	@RequiredArgsConstructor
	@AllArgsConstructor
	public static class UserRegisterResponse {
		private final Long id;
		private final String userId;
		private final String userEmail;
		private final String userName;
	}

	@Getter
	@NoArgsConstructor
	@Builder
	@AllArgsConstructor
	public static class UserRegisterRequest {

		@NotBlank(message = "사용자 아이디는 필수입니다.")
		private String userId;

		@NotBlank(message = "비밀번호는 필수입니다.")
		private String userPw;

		@Email(message = "이메일 형식에 맞게 입력해주세요.")
		@NotBlank(message = "이메일은 필수입니다.")
		private String userEmail;

		@NotBlank(message = "사용자 이름은 필수입니다.")
		private String userName;
	}

}
