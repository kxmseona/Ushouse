package com.example.demo.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Table(name = "board_file_table")
public class BoardFileEntity extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column
	private String originalFileName;
	
	@Column
	private String storedFileName;
	 
	// board_table과 board_file_table은 1:n관계 이므로 @ManyToOne어노테이션을 사용한다.
	// FetchType.EAGER- 부모를 조회할때 자식을 같이 가지고옴(관련된 모든 정보를 가지고옴), FetchType.LAZY(필요한 정보를 필요할때 가지고옴)
	// 즉, EAGER을 사용할때 불필요한 쿼리들이 여러개 발생(비효율적), 반면 LAZY는 필요한 상황에 필요한 정보들을 내가 호출해서 쓸수있음(효율적)
	// @XXXToOne 어노테이션들은 기본이 즉시 로딩(eager), 꼭 lazy로 지정해서 사용하기
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "board_id")
	private BoardEntity boardEntity;
	// 반드시 부모 엔티티 타입과 이름으로 적어주어야 함
	
	public static BoardFileEntity toBoardFileEntity(BoardEntity boardEntity, String originalFileName, String storedFileName) {
		BoardFileEntity boardFileEntity = new BoardFileEntity();
		boardFileEntity.setOriginalFileName(originalFileName);
		boardFileEntity.setStoredFileName(storedFileName);
		boardFileEntity.setBoardEntity(boardEntity);
		// pk값이 아니라 부모 엔티티 객체를 넘겨주어야 한다.
		return boardFileEntity;
	}
	
	
}
