package com.example.demo.Dto;

import com.example.demo.Entity.Gender;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class SignRequest {
	private Long id;
	private String memberEmail;
	private String memberPassword;
	private String memberName;
	private Gender Gender;
	
	
}
