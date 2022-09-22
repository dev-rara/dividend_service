package com.rara.dividend_service.service;

import com.rara.dividend_service.model.Auth.SignIn;
import com.rara.dividend_service.model.Auth.SignUp;
import com.rara.dividend_service.model.MemberEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface MemberService extends UserDetailsService {

	@Override
	UserDetails loadUserByUsername(String username);

	 MemberEntity register(SignUp member);

	 // 로그인시 검증
	 MemberEntity authenticate(SignIn member);
}
