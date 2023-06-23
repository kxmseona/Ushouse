package com.example.demo.Dto;

import com.example.demo.Entity.Gender;
import com.example.demo.Entity.MemberEntity;
import com.example.demo.Entity.Authority;
import java.util.*;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SignResponse {
	private Long id;
	private String memberEmail;
	private String memberName;
	private Gender gender;
	
	@Builder.Default
	private List<Authority> roles = new ArrayList<>();
	
	private String token;
	
	public SignResponse(MemberEntity memberEntity) {
		this.id = memberEntity.getId();
		this.memberEmail = memberEntity.getMemberEmail();
		this.memberName = memberEntity.getMemberName();
		this.gender = memberEntity.getGender();
		this.roles = memberEntity.getRoles();
	
	}
	
}
