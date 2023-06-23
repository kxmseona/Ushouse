package com.example.demo.Controller;

import java.io.IOException;
import java.util.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
// ajax요청이므로 @ResoponseBody 어노테이션 사용

import com.example.demo.Dto.CommentDTO;
import com.example.demo.Service.CommentService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {
	
	private final CommentService commentService;
	
	@PostMapping("/save")
	public ResponseEntity<?> save(@ModelAttribute CommentDTO commentDTO) throws IOException {
		// ResponseEntity body뿐만아니라 header부분도 작성할수 있음 (http상태 표시 코드 등)
		System.out.println("commentDTO" + commentDTO);
		Long saveResult =  commentService.save(commentDTO);
		
		if(saveResult != null) {
			List<CommentDTO> commentDTOList = commentService.findAll(commentDTO.getBoardId());
			return new ResponseEntity<>(commentDTOList, HttpStatus.OK);
		}else {
			return new ResponseEntity<>("해당 게시글이 존재하지 않습니다.", HttpStatus.NOT_FOUND);
		}
	}
	
	
	
	

}
