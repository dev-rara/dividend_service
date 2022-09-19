package com.zerobase.dividend_service.service;

import com.zerobase.dividend_service.model.Auth;
import com.zerobase.dividend_service.model.MemberEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface MemberService extends UserDetailsService {

	@Override
	UserDetails loadUserByUsername(String username);

	 MemberEntity register(Auth.SignUp member);

	 // 로그인시 검증
	 MemberEntity authenticate(Auth.SignIn member);
}
