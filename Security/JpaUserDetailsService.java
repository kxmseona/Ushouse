package com.example.demo.Security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.Entity.MemberEntity;
import com.example.demo.Repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JpaUserDetailsService implements UserDetailsService{
	
	private final MemberRepository memberRepository;
	
	@Override
	public UserDetails loadUserByUsername(String memberEmail) throws UsernameNotFoundException{
		
		MemberEntity memberEntity = memberRepository.findByMemberEmail(memberEmail).orElseThrow(
				() -> new UsernameNotFoundException("찾을수 없음!")
				);
		return new CustomUserDetails(memberEntity);
	}

}
