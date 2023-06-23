package com.example.demo.Dto;

import java.time.LocalDateTime;
import java.util.*;
import org.springframework.web.multipart.MultipartFile;
import com.example.demo.Entity.CommentEntity;
import com.example.demo.Entity.CommentFileEntity;

import lombok.*;

@Getter
@Setter
@ToString
public class CommentDTO {
	// 작성자, 댓글내용, 게시글 번호를 감싸야함 + 생성시간
	private Long id;
	private String commentWriter;
	private String commentContents;
	private Long boardId;
	private LocalDateTime commentCreatedTime;
	
	private List<MultipartFile> commentFile;
	private List<String> originalFileName;
	private List<String> storedFileName;
	private int fileAttached;
	
	
	public static CommentDTO toCommentDTO(CommentEntity commentEntity) {
		CommentDTO commentDTO = new CommentDTO();
		commentDTO.setId(commentEntity.getId());
		commentDTO.setCommentWriter(commentEntity.getCommentWriter());
		commentDTO.setCommentContents(commentEntity.getCommentContents());
		commentDTO.setCommentCreatedTime(commentEntity.getCreatedTime());
		commentDTO.setBoardId(commentEntity.getBoardEntity().getId());
		
		if(commentEntity.getFileAttached() == 0) {
			commentDTO.setFileAttached(commentEntity.getFileAttached());
		}else {
			
			commentDTO.setFileAttached(commentEntity.getFileAttached());

			List<String> originalFileNameList = new ArrayList<>();
			List<String> storedFileNameList = new ArrayList<>();
			
			for(CommentFileEntity commentFileEntity: commentEntity.getCommentFileEntityList()) {
				originalFileNameList.add(commentFileEntity.getOriginalFileName());
				storedFileNameList.add(commentFileEntity.getStoredFileName());
			}
			commentDTO.setOriginalFileName(originalFileNameList);
			commentDTO.setStoredFileName(storedFileNameList);
			
		}
		
		return commentDTO;
	}


	
	
	
}
