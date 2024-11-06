package com.example.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = "username") })
public class UserEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String id; // 고유한 유저 아이디

	@Column(nullable = false) // 이후 OAuth를 사용하여 SSO를 구현할 것이기 때문에 null 허용.
	// 구현하지 않았다면, null로 허용하면 안됨.

	private String username; // 아이디로 사용할 유저네임, 이메일 or 그냥 문자열
	private String password; // 비밀번호
	private String role; // 사용자의 권한, Admin * NomalUser
	private String authProvider;// 이후 OAuth에서 사용할 유저 정보 제공자 : github
}
