package com.example.demo.Entity;

import com.example.demo.Dto.BoardDTO;

import jakarta.persistence.*;
import lombok.*;
import java.util.*;

import org.hibernate.annotations.ColumnDefault;

@Entity
@Getter
@Setter
@Table(name = "board_table")
public class BoardEntity extends BaseEntity {
	
	// DB의 테이블 역할을 하는 클래스(DTO 사용범위-Controller,View/ Entity사용범위-Service,Repository에서 사용)
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(length = 20, nullable = false) // 컬럼의 크기는 20이고, not null지정
	private String boardWriter;
	
	@Column
	private String boardPass;
	
	@Column
	private String boardTitle;
	
	@Column(length = 500)
	private String boardContents;
	
	@Column
	private String locationGu;

	@Column
	private String locationDong;
	
	@Column
	private int boardHits;
	
	@ColumnDefault("0")
	private int likeHits;
	
	
	@Column
	private int fileAttached;
	// 저장할때 file이 있으면 1, file이 없으면 0이라는 값을 주도록
	
	// mappedBy는 어떤것과 매칭되냐 - BoardFileEntity에 fk로 참조하는 부모엔티티 이름 - boardEntity를 써주면 됨
	// 부모가 삭제되면 자식도 삭제 된다.(cascade)
	@OneToMany(mappedBy = "boardEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
	private List<BoardFileEntity> boardFileEntityList = new ArrayList<>();
	
	@OneToMany(mappedBy = "boardEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
	private List<CommentEntity> commentEntityList = new ArrayList<>();
	
	
	
	
	
			
			
	// file이 없는 경우 DTO를 Entity로 바꾸는 메서드(fileAttached값을 0으로 줌)
	public static BoardEntity tosaveEntity(BoardDTO boardDTO) {
		BoardEntity boardEntity = new BoardEntity();
		boardEntity.setBoardWriter(boardDTO.getBoardWriter());
		boardEntity.setBoardPass(boardDTO.getBoardPass());
		boardEntity.setBoardTitle(boardDTO.getBoardTitle());
		boardEntity.setBoardContents(boardDTO.getBoardContents());
		boardEntity.setLocationGu(boardDTO.getLocationGu());
		boardEntity.setLocationDong(boardDTO.getLocationDong());
		boardEntity.setBoardHits(0);
		boardEntity.setLikeHits(0);
		boardEntity.setFileAttached(0); // 첨부파일 없음
		return boardEntity;
	}



	public static BoardEntity toUpdateEntity(BoardDTO boardDTO) {
		BoardEntity boardEntity = new BoardEntity();
        boardEntity.setId(boardDTO.getId());
        boardEntity.setBoardWriter(boardDTO.getBoardWriter());
        boardEntity.setBoardPass(boardDTO.getBoardPass());
        boardEntity.setBoardTitle(boardDTO.getBoardTitle());
        boardEntity.setBoardContents(boardDTO.getBoardContents());
        boardEntity.setLocationGu(boardDTO.getLocationGu());
		boardEntity.setLocationDong(boardDTO.getLocationDong());
        boardEntity.setBoardHits(boardDTO.getBoardHits());
        boardEntity.setLikeHits(boardDTO.getLikeHits());
        return boardEntity;
        
		
	}



	public static BoardEntity toSaveFileEntity(BoardDTO boardDTO) {
		BoardEntity boardEntity = new BoardEntity();
		boardEntity.setBoardWriter(boardDTO.getBoardWriter());
		boardEntity.setBoardPass(boardDTO.getBoardPass());
		boardEntity.setBoardTitle(boardDTO.getBoardTitle());
		boardEntity.setBoardContents(boardDTO.getBoardContents());
		boardEntity.setLocationGu(boardDTO.getLocationGu());
		boardEntity.setLocationDong(boardDTO.getLocationDong());
		boardEntity.setBoardHits(0);
		boardEntity.setLikeHits(0);
		boardEntity.setFileAttached(1); // 파일 있음
		return boardEntity;
		
	}




	
	
	
	
}
