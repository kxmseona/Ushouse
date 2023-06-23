package com.example.demo.Entity;


import com.example.demo.Dto.MypageDTO;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Table(name = "mypage_table")
public class MypageEntity extends BaseEntity{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	
	@Column
	private String writer;

	@Column
	private String age;

	@Column
	private String gender;

	@Column
	private String smoke;
	

	@Column
	private String location;
	

	@Column
	private String month;
	
	@Column
	private String oneline;

	@Column
	private String myContent;

	@Column
	private String themContent;
	
	
	public static MypageEntity tomypageEntity(MypageDTO mypageDTO) {
		MypageEntity mypageEntity = new MypageEntity();
		mypageEntity.setWriter(mypageDTO.getWriter());
		mypageEntity.setAge(mypageDTO.getAge());
		mypageEntity.setGender(mypageDTO.getGender());
		mypageEntity.setSmoke(mypageDTO.getSmoke());
		mypageEntity.setLocation(mypageDTO.getLocation());
		mypageEntity.setMonth(mypageDTO.getMonth());
		mypageEntity.setOneline(mypageDTO.getOneline());
		mypageEntity.setMyContent(mypageDTO.getMyContent());
		mypageEntity.setThemContent(mypageDTO.getThemContent());
		
		return mypageEntity;
	}


	
	
	
	
}




