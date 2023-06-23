package com.example.demo.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.Entity.BoardEntity;

public interface BoardRepository extends JpaRepository<BoardEntity, Long>{
	// update board_table set board_hits = board_hits+1 where id=?
	// 내가 조회하고자 하는 아이디의 게시물 조회수 속성에 원래있던 값에 1을 더해서 업데이트 한다.
	
	// 엔티티 기준으로 쿼리 작성 (테이블 명 대신 엔티티 명 사용 약어를 사용해야함 - boardEntity의 약어 b 사용)
	// 엔티티의 속성이름을 사용해서 쿼리를 작성해야함
	@Modifying
	@Query(value = "update BoardEntity b set b.boardHits=b.boardHits+1 where b.id=:id")
	void updateHits(@Param("id") Long id);

	
	List<BoardMapping> findAllByBoardWriter(String boardwriter);

	@Modifying
	@Query(value = "update BoardEntity b set b.likeHits=b.likeHits+1 where b.id=:boardId")
	void plusLike(@Param("boardId") Long boardId);

	@Modifying
	@Query(value = "update BoardEntity b set b.likeHits=b.likeHits-1 where b.id=:boardId")
	void minusLike(@Param("boardId") Long boardId);


	Optional<BoardEntity> findById(Long id);


	List<BoardEntity> findAllByLocationGu(String input);



	
}



