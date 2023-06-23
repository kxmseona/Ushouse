package com.example.demo.Dto;

import com.example.demo.Entity.LikeEntity;

import lombok.*;

@Getter
@Setter
@ToString
public class LikeDTO {
	
	private Long LikeId;
	private Long MemberId;
	private Long BoardId;
	
	public static LikeDTO toLikeDTO(LikeEntity likeEntity) {
		LikeDTO likeDTO = new LikeDTO();
		likeDTO.setLikeId(likeEntity.getLike_id());
		likeDTO.setMemberId(likeEntity.getMemberEntity().getId());
		likeDTO.setBoardId(likeEntity.getBoardEntity().getId());
		
		return likeDTO;
	}

}
