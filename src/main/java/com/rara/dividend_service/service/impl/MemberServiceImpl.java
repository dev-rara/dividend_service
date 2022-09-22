package com.rara.dividend_service.service.impl;

import com.rara.dividend_service.exception.impl.AlreadyExistUserException;
import com.rara.dividend_service.exception.impl.NotMatchPasswordException;
import com.rara.dividend_service.model.Auth.SignIn;
import com.rara.dividend_service.model.Auth.SignUp;
import com.rara.dividend_service.model.MemberEntity;
import com.rara.dividend_service.persist.repository.MemberRepository;
import com.rara.dividend_service.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class MemberServiceImpl implements MemberService {

	private final PasswordEncoder passwordEncoder;
	private final MemberRepository memberRepository;

	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return  memberRepository.findByUsername(username)
			.orElseThrow(() -> new UsernameNotFoundException(username + " 회원을 찾을 수 없습니다."));
 	}

	 public MemberEntity register(SignUp member) {
		boolean exists = memberRepository.existsByUsername(member.getUsername());
		if (exists) {
			throw new AlreadyExistUserException();
		}
		member.setPassword(passwordEncoder.encode(member.getPassword()));

		 return memberRepository.save(member.toEntity());
	}

	 // 로그인시 검증
	 public MemberEntity authenticate(SignIn member) {

		var user = memberRepository.findByUsername(member.getUsername())
											.orElseThrow(() -> new UsernameNotFoundException("존재하지 않은 ID 입니다."));
		if(!passwordEncoder.matches(member.getPassword(), user.getPassword())) {
			throw new NotMatchPasswordException();
		}

		return user;
	 }
}
