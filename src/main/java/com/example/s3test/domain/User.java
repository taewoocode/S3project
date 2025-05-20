package com.example.s3test.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, columnDefinition = "VARCHAR(255) COMMENT '사용자 아이디'")
    private String userId;

    @Column(nullable = false, columnDefinition = "VARCHAR(255) COMMENT '사용자 비밀번호'")
    private String userPw;

    @Column(nullable = false, columnDefinition = "VARCHAR(255) COMMENT '사용자 이메일'")
    private String userEmail;

    @Column(nullable = false, columnDefinition = "VARCHAR(255) COMMENT '사용자 이름'")
    private String userName;

    @Builder
    public User(String userId, String userPw, String userEmail, String userName) {
        this.userId = userId;
        this.userPw = userPw;
        this.userEmail = userEmail;
        this.userName = userName;
    }
}
sssssstest