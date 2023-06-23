package com.example.demo.Dto;

import com.example.demo.Entity.Gender;
import com.example.demo.Entity.MemberEntity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class MemberDTO {
	private Long id;
	private String memberEmail;
	private String memberPassword;
	private String memberName;
	private Gender Gender;
	
	public static MemberDTO toMemberDTO(MemberEntity memberEntity) {
		MemberDTO memberDTO = new MemberDTO();
		memberDTO.setId(memberEntity.getId());
		memberDTO.setMemberEmail(memberEntity.getMemberEmail());
		memberDTO.setMemberPassword(memberEntity.getMemberPassword());
		memberDTO.setMemberName(memberEntity.getMemberName());
		memberDTO.setGender(memberEntity.getGender());
		return memberDTO;
	}


}
