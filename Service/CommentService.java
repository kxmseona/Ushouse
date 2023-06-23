
package com.example.demo.Service;

import java.io.File;
import java.util.*;
import java.io.IOException;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.Dto.CommentDTO;
import com.example.demo.Entity.BoardEntity;
import com.example.demo.Entity.CommentEntity;
import com.example.demo.Entity.CommentFileEntity;
import com.example.demo.Repository.BoardRepository;
import com.example.demo.Repository.CommentFileRepository;
import com.example.demo.Repository.CommentRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommentService {
	
	private final CommentRepository commentRepository;
	private final CommentFileRepository commentFileRepository;
	private final BoardRepository boardRepository;

	public Long save(CommentDTO commentDTO) throws IOException{
		// 부모 엔티티(board_table) 조회
		Optional<BoardEntity> optionalBoardEntity = boardRepository.findById(commentDTO.getBoardId());
		if(optionalBoardEntity.isPresent()) {
			// 만약 부모 객체가 board_Entity에 실제로 존재한다면, optional로 감싼것을 풀어주고 CommentEntity에 DTO를 Entity로 변환하여 저장한다.
			BoardEntity boardEntity = optionalBoardEntity.get(); // 즉, boardEntity에는 optional로 가지고온 게시글의 정보들이 저장되어있음
			if(commentDTO.getCommentFile() == null) {
				CommentEntity commentEntity = CommentEntity.toSaveEntity(commentDTO, boardEntity);
				Long saveId = commentRepository.save(commentEntity).getId();
				return saveId;
				
			}else {
				
				CommentEntity commentEntity = CommentEntity.toSaveFileEntity(commentDTO, boardEntity);
				Long saveId = commentRepository.save(commentEntity).getId();
				CommentEntity comment = commentRepository.findById(saveId).get();
				
				for(MultipartFile commentFile: commentDTO.getCommentFile()) {
					String originalFileName = commentFile.getOriginalFilename();
					String storedFileName = System.currentTimeMillis() + "_" + originalFileName;
					
					String savePath = "C:/ushouse_img/"+ storedFileName;
					commentFile.transferTo(new File(savePath));
					
					CommentFileEntity commentFileEntity = CommentFileEntity.toCommentFileEntity(comment, originalFileName, storedFileName);
					commentFileRepository.save(commentFileEntity);
				}
				return saveId;
			}
			
		
			
		}
		return null;
		
	}

	
	
	
	
	@Transactional
	public List<CommentDTO> findAll(Long boardId) {
		// 쿼리문 활용 select * from comment_table where board_id=? order by id desc;
		// board_id를 기준으로 가져온 아이디와 일치하는 해당 댓글들을 가지고 오고 
		// 이 댓글들의 id기준으로 내림차순(desc)으로 가지고 온다. - 최근에 작성한 댓글들이 먼저 보이게끔(즉 order by -정렬기준) 
		BoardEntity boardEntity = boardRepository.findById(boardId).get();
		// boardRepository에서 해당 id에 맞는 게시글 정보를 꺼내온다.
		List<CommentEntity> commentEntityList = commentRepository.findAllByBoardEntityOrderByIdDesc(boardEntity);
		// commentRepositoty에서 위의 쿼리문을 이용해서 만든 메서드를 호출
		// 즉, 위에서 가지고온 부모 게시글 정보를 가지고 있는 댓글들을 댓글 id기준 내림차순 형태의 list로 데리고 옴.
		
		//EntityList를 DTOList로 변환해주면 된다.
		List<CommentDTO> commentDTOList = new ArrayList<>();
		for(CommentEntity commentEntity: commentEntityList) {
			// CommentEntity타입의 commemtEntity이름으로 구성되어진 commentEntityList를 대상으로 한 for문
			CommentDTO commentDTO = CommentDTO.toCommentDTO(commentEntity);
			commentDTOList.add(commentDTO);
		}
		return commentDTOList;
	}

}
