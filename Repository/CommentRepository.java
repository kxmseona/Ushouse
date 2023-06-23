package com.example.demo.Repository;

import java.util.*;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.Entity.BoardEntity;
import com.example.demo.Entity.CommentEntity;

public interface CommentRepository extends JpaRepository <CommentEntity, Long> {
	
	// 쿼리문 활용 select * from comment_table where board_id=? order by id desc;
	List<CommentEntity> findAllByBoardEntityOrderByIdDesc(BoardEntity boardEntity);
	// board_id가 매개변수로 넘어가는것이 아닌, boardEntity가 매개변수로 넘어가야한다. Entity가.(중요)
	
	
}
