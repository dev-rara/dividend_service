package com.zerobase.dividend_service.service;

import com.zerobase.dividend_service.model.Auth;
import com.zerobase.dividend_service.model.MemberEntity;
import com.zerobase.dividend_service.persist.repository.MemberRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class MemberService implements UserDetailsService {

	private final PasswordEncoder passwordEncoder;
	private final MemberRepository memberRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return  memberRepository.findByUsername(username)
			.orElseThrow(() -> new UsernameNotFoundException("couldn't find user -> " + username));
 	}

	 public MemberEntity register(Auth.SignUp member) {
		boolean exists = memberRepository.existsByUsername(member.getUsername());
		if (exists) {
			throw new RuntimeException("already exists username");
		}
		member.setPassword(passwordEncoder.encode(member.getPassword()));

		 return memberRepository.save(member.toEntity());
	}

	 // 로그인시 검증
	 public MemberEntity authenticate(Auth.SignIn member) {
		return null;
	 }
}
