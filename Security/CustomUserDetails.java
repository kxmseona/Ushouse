package com.example.demo.Security;


import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.demo.Entity.MemberEntity;

@SuppressWarnings("serial")
public class CustomUserDetails implements UserDetails{
	
	private final MemberEntity memberEntity;
	
	public CustomUserDetails(MemberEntity memberEntity) {
		this.memberEntity = memberEntity;
	}
	
	public final MemberEntity getMemberEntity() {
		return memberEntity;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities(){
		return memberEntity.getRoles().stream().map(o -> new SimpleGrantedAuthority(o.getName()
				)).collect(Collectors.toList());
	}
	
	@Override
	public String getPassword() {
		return memberEntity.getMemberPassword();
	}
	

	public String getUsername() {
		return memberEntity.getMemberEmail();
	}
	
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}
	
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}
	
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
	
	@Override
    public boolean isEnabled() {
        return true;
    }

	
	
	
	
	
	
	
	
	
	
	
	
	

}
























