package com.example.demo.Dto;

import java.time.LocalDateTime;
import java.util.*;

import org.springframework.web.multipart.MultipartFile;

import com.example.demo.Entity.BoardEntity;
import com.example.demo.Entity.BoardFileEntity;
import com.example.demo.Repository.BoardMapping;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BoardDTO {
	private Long id;
	private String boardWriter;
	private String boardPass;
	private String boardTitle;
	private String boardContents;
	private String locationGu;
	private String locationDong;
	//private Long memberEmail;
	
	private int boardHits;
	private int likeHits;
	private LocalDateTime boardCreatedTime;
	private LocalDateTime boardUpdatedTime;
	
	private List<MultipartFile> boardFile;
	// 스프링에서 제공하는 인터페이스, 실제 파일을 담아서 controller에게 전달
	private List<String> originalFileName;
	// 원본파일 이름
	private List<String> storedFileName;
	// 서버저장용 파일 이름
	private int fileAttached;
	// 파일 첨부 여부(첨부 1, 미첨부 0)
	
	public BoardDTO(Long id, String boardWriter, String boardTitle, String locationGu, String locationDong, int boardHits,int likeHits, LocalDateTime boardCreatedTime) {
		this.id = id;
		this.boardWriter = boardWriter;
		this.boardTitle = boardTitle;
		this.boardHits = boardHits;
		this.likeHits = likeHits;
		this.locationGu = locationGu;
		this.locationDong = locationDong;
		this.boardCreatedTime = boardCreatedTime;
	}
	
	
	
	public static BoardDTO toBoardDTO(BoardEntity boardEntity) {
		BoardDTO boardDTO = new BoardDTO();
		boardDTO.setId(boardEntity.getId());
		boardDTO.setBoardWriter(boardEntity.getBoardWriter());
		boardDTO.setBoardPass(boardEntity.getBoardPass());
		boardDTO.setBoardTitle(boardEntity.getBoardTitle());
		boardDTO.setBoardContents(boardEntity.getBoardContents());
		boardDTO.setLocationGu(boardEntity.getLocationGu());
		boardDTO.setLocationDong(boardEntity.getLocationDong());
		boardDTO.setBoardHits(boardEntity.getBoardHits());
		boardDTO.setBoardCreatedTime(boardEntity.getCreatedTime());
		boardDTO.setBoardUpdatedTime(boardEntity.getUpdatedTime());
		if(boardEntity.getFileAttached() == 0) {
			// 첨부파일이 없는 경우
			boardDTO.setFileAttached(boardEntity.getFileAttached());
		} else {
			
			List<String> originalFileNameList = new ArrayList<>();
			List<String> storedFileNameList = new ArrayList<>();
			
			// 첨부파일이 있는 경우
			// 지금 필요한것은 board_table에 있는 게시글 정보와 board_file_table에 있는 파일 정보가 필요
			boardDTO.setFileAttached(boardEntity.getFileAttached());
			// 파일 이름을 가져가야 함
			// originalFileName과 storedFileName은 board_file_table에 들어있음
			// 그러나 나는 boardEntity만 DTO에 가져온 상태
			// boardFileEntity를 가져와야하는데 여기서 boardEntity와 boardFileEntity 간의 연관관계를 이용(spring의 장점 - join)
			// join 쿼리문 으로 써보면 
			// select * from board_table b(board 테이블의 약어), board_file_table bf(board_file테이블의 약어) where b.id=bf.board_id 
			// and where b.id=?
			// 위 작성한 join쿼리문을 spring jpa가 어떻게 해결을 해주냐면
			for(BoardFileEntity boardFileEntity: boardEntity.getBoardFileEntityList()) {
				originalFileNameList.add(boardFileEntity.getOriginalFileName());
				storedFileNameList.add(boardFileEntity.getStoredFileName());
				
			}
			boardDTO.setOriginalFileName(originalFileNameList);
			boardDTO.setStoredFileName(storedFileNameList);
			
		}
		
		return boardDTO;
	}



	public static BoardDTO toMylistBoardDTO(BoardMapping boardMapping) {
		BoardDTO boardDTO = new BoardDTO();
		boardDTO.setId(boardMapping.getId());
		boardDTO.setBoardWriter(boardMapping.getBoardWriter());
		boardDTO.setBoardTitle(boardMapping.getBoardTitle());
		boardDTO.setBoardCreatedTime(boardMapping.getCreatedTime());
		boardDTO.setBoardHits(boardMapping.getBoardHits());
		boardDTO.setLocationGu(boardMapping.getLocationGu());
		boardDTO.setLocationDong(boardMapping.getLocationDong());
		
		
		return boardDTO;
	}



	
	
}
