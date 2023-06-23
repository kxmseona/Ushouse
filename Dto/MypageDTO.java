package com.example.demo.Dto;

import java.time.LocalDateTime;

import com.example.demo.Entity.MypageEntity;


import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class MypageDTO {
	private Long id;
	
	private String writer;
	private String age;
	private String gender;
	private String smoke;
	private String location;
	private String month;
	private String oneline;
	
	private String myContent;
	private String themContent;
	
	private LocalDateTime mypageCreatedTime;
	
	public MypageDTO(Long id, String writer, String age, String gender, String smoke,String location, String month, String oneline ,String myContent,String themContent, LocalDateTime mypageCreatedTime ) {
		this.id = id;
		this.writer = writer;
		this.age = age;
		this.gender = gender;
		this.smoke = smoke;
		this.location = location;
		this.month = month;
		this.oneline = oneline;
		this.myContent = myContent;
		this.themContent = themContent; 
		this.mypageCreatedTime = mypageCreatedTime;
	}
	
	
	public static MypageDTO toMypageDTO(MypageEntity mypageEntity) {
		MypageDTO mypageDTO = new MypageDTO();
		mypageDTO.setId(mypageEntity.getId());
		mypageDTO.setWriter(mypageEntity.getWriter());
		mypageDTO.setAge(mypageEntity.getAge());
		mypageDTO.setGender(mypageEntity.getGender());
		mypageDTO.setSmoke(mypageEntity.getSmoke());
		mypageDTO.setLocation(mypageEntity.getLocation());
		mypageDTO.setMonth(mypageEntity.getMonth());
		mypageDTO.setOneline(mypageEntity.getOneline());
		mypageDTO.setMyContent(mypageEntity.getMyContent());
		mypageDTO.setThemContent(mypageEntity.getThemContent());
		mypageDTO.setMypageCreatedTime(mypageEntity.getCreatedTime());
		 
		return mypageDTO;
	}
	
	
}
