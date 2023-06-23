package com.example.demo.Entity;

import java.util.*;
import com.example.demo.Dto.CommentDTO;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Getter
@Setter
@Table(name = "comment_table")
public class CommentEntity extends BaseEntity{

	// 작성자, 댓글내용, 게시글번호, 댓글번호, 생성시간
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(length = 20, nullable = false)
	private String commentWriter;
	
	@Column
	private String commentContents;
	
	@Column
	private int fileAttached;
	
	@OneToMany(mappedBy = "commentEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
	private List<CommentFileEntity> commentFileEntityList = new ArrayList<>();
	
	// board_table과 comment_table은 참조 관계 즉, 1(board) 대 n(comment)관계
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "board_id")
	private BoardEntity boardEntity;
	// comment_table이 참조할 부보 엔티티(board_table)를 적어줌
	// 즉, 이말은 boardEntity의 정보가 commentEntity에 있어야 함. - 반드시
	// 그래서 DTO를 변환할때 DTO뿐만 아니라 BoardEntity도 같이 commentEntity에다 저장해줌

	public static CommentEntity toSaveEntity(CommentDTO commentDTO, BoardEntity boardEntity) {
		
		CommentEntity commentEntity = new CommentEntity();
		commentEntity.setCommentWriter(commentDTO.getCommentWriter());
		commentEntity.setCommentContents(commentDTO.getCommentContents());
		commentEntity.setBoardEntity(boardEntity);
		commentEntity.setFileAttached(0);
		return commentEntity;
		
	}
	
	public static CommentEntity toSaveFileEntity(CommentDTO commentDTO, BoardEntity boardEntity) {
		CommentEntity commentEntity = new CommentEntity();
		commentEntity.setCommentWriter(commentDTO.getCommentWriter());
		commentEntity.setCommentContents(commentDTO.getCommentContents());
		commentEntity.setBoardEntity(boardEntity);
		commentEntity.setFileAttached(1);
		return commentEntity;
		
	}

	
}
