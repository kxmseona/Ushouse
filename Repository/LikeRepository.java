package com.example.demo.Repository;

import java.util.*;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.Entity.LikeEntity;

public interface LikeRepository extends JpaRepository<LikeEntity, Long>{


	

	Optional<LikeEntity> findByBoardEntity_IdAndMemberEntity_Id(Long id,Long memberId);

	
	Optional<LikeEntity> findByMemberEntity_IdAndBoardEntity_Id(Long memberId, Long boardId);

	void deleteByBoardEntity_IdAndMemberEntity_Id(Long boardId, Long memberId);

	
}
