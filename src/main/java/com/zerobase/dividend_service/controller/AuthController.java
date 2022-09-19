package com.zerobase.dividend_service.controller;

import com.zerobase.dividend_service.model.Auth;
import com.zerobase.dividend_service.security.TokenProvider;
import com.zerobase.dividend_service.service.impl.MemberServiceImpl;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

	private final MemberServiceImpl memberServiceImpl;
	private final TokenProvider tokenProvider;

	@ApiOperation(value = "회원가입")
	@PostMapping("/signup")
	public ResponseEntity<?> signup(@RequestBody Auth.SignUp request) {
		var result = memberServiceImpl.register(request);

		return ResponseEntity.ok(result);
	}

	@ApiOperation(value = "로그인")
	@PostMapping("/signin")
	public ResponseEntity<?> signin(@RequestBody Auth.SignIn request) {

		var member = memberServiceImpl.authenticate(request);
		String token = tokenProvider.generateToken(member.getUsername(), member.getRoles());
		log.info("user login -> " + request.getUsername());

		return ResponseEntity.ok(token);
	}
}
