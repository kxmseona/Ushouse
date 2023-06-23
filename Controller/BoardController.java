package com.example.demo.Controller;


import java.io.IOException;
import java.util.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.Dto.BoardDTO;
import com.example.demo.Dto.CommentDTO;
import com.example.demo.Service.BoardService;
import com.example.demo.Service.CommentService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

//@RequestMapping으로 공통된 상위 경로를 미리 선언해주면 해당 컨트롤러 아래의 주소에는 하위경로만 써주면 된다.
// 원래 썼던법/board/boardsave, @RequestMapping을 사용하면? /boardsave
@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
@Log4j2
public class BoardController {
	// @RequiredArgsConstructor 생성자를 이용하여 의존성 주입(서비스의 비즈니스 로직(메서드)을 호출하기 위해)
	private final BoardService boardService;
	private final CommentService commentService;
	
	@GetMapping("/boardsave")
	public String boardsaveForm() {
		return "board/boardsave";
	}
	
	@PostMapping("/boardsave")
	public String boardsave(@ModelAttribute BoardDTO boardDTO) throws IOException {
		System.out.println("boardDTO = " + boardDTO);
		boardService.boardsave(boardDTO);
		// DTO 사용범위 : View, Controller/ Entity 사용범위 : Service, Repository
		return "/main";
		
	}
	
	@GetMapping("/boardmylist")
	public String mylistform() {
		return "board/boardmylist";
	}
	
	@PostMapping("/boardmylist")
	public @ResponseBody List<BoardDTO> mylist(@RequestParam("boardwriter") String boardwriter, Model model) {
		System.out.println("boardwriter :" + boardwriter);
		List<BoardDTO> boardDTOList = boardService.mylist(boardwriter);
		model.addAttribute("boardDTOList",boardDTOList);
		return boardDTOList;
		
	}
	
	
    
	
	
	
	
	
	
	@GetMapping("/") // 주소 "/board/"
	public String findAll(Model model) {
		// 하나가 아니기 때문에 service에서 DTO로 변환된 객체들을 DTO List형태로 받은후
		// DTOList를 다시 Model에 담아 view에게 보내준다.
		List<BoardDTO> boardDTOList = boardService.findAll();
		model.addAttribute("boardList", boardDTOList);
		return "board/boardlist";
	}
	
	
	
	@GetMapping("/{id}")
	public String findById(@PathVariable Long id, HttpServletRequest request, HttpServletResponse response, Model model,
							@PageableDefault(page=1) Pageable pageable) {
		/*
		 * 해당 게시글의 조회수를 하나 올리고 메서드 updateHits에 해당게시물의 id값 담아서 메서드 호출
		 * 게시글 데이터를 가져와서 boarddetail.html에 출력 메서드 findById에 해당게시물의 id값 담아서 메서드 호출
		 * */
		
		System.out.println(request.getHeader("Cookie"));
		
		Cookie[] cookies = request.getCookies();
		if(cookies != null) {
			for(Cookie c : cookies) {
				String name = c.getName();
				String value = c.getValue();
				if (name.equals("memberEmail")) {
					String memberEmail = value;
					int like = boardService.findLike(id, memberEmail);
					model.addAttribute("like", like);
					log.info("와따");
				}
			}
		}else{
			return null;
		}
		
		
		boardService.updateHits(id);
		BoardDTO boardDTO = boardService.findById(id);
		
		
		// 댓글목록 가져오기(게시글 번호 board_id를 이용하여 그 아이디에 해당하는 DTO List를 commentService에서 불러옴)
		List<CommentDTO> commentDTOList = commentService.findAll(id);
		
	    
		// memberId 세션값 가져오기
		model.addAttribute("commentList", commentDTOList);
		
		model.addAttribute("board", boardDTO);
		
		model.addAttribute("page", pageable.getPageNumber());
	
		
		
		
		return "/board/boarddetail";
	}
	
	// 좋아요
	@PostMapping("/like")
	public @ResponseBody int like(Long boardId, String memberEmail) {
		
		int result = boardService.saveLike(boardId,memberEmail);
		return result;
	}
	
	
	
	
	
	
	
	
	
	
	@GetMapping("/boardupdate/{id}")
	public String boardupdateForm(@PathVariable Long id, Model model) {
		BoardDTO boardDTO = boardService.findById(id);
		// 가져온 데이터를 DTO에 담은 후
		model.addAttribute("boardUpdate", boardDTO);
		// DTO를 다시 model에 담아 html로 보낸다.
		return "board/boardupdate";
		
	}
	
	@PostMapping("/boardupdate")
	public String update(@ModelAttribute BoardDTO boardDTO, Model model) {
		BoardDTO board = boardService.update(boardDTO);
		model.addAttribute("board", board);
		return "board/boarddetail";
	}
	
	@GetMapping("/boarddelete/{id}")
	public String delete(@PathVariable Long id) {
		boardService.delete(id);
		return "redirect:/board/";
	}
	
	//페이지 번호를 따로 언급을 하지 않았을 경우에는 페이지 1번을 지정해서 보여주겠다.(기본세팅) (page = 1)
	@GetMapping("/paging")
	public String paging(@PageableDefault(page = 1) Pageable pageable, Model model) {
		//pageable.getPageNumber();
		// BoardDTO를 객체로하는 boardList의 page에 boardService의 계산된 이용가능한 페이징의 번호들을 부여하는 메서드 호출?
		Page<BoardDTO> boardList = boardService.paging(pageable);
		// 메서드 이름은 paging 페이지 값을 가져오기 위한 파라미터의 이름은 pageable
		
		int blockLimit = 3;
		int startPage = (((int)(Math.ceil((double)pageable.getPageNumber() / blockLimit))) - 1) * blockLimit + 1; // 1 4 7 10
        int endPage = ((startPage + blockLimit - 1) < boardList.getTotalPages()) ? startPage + blockLimit - 1 : boardList.getTotalPages();
		// 현재 사용자가 3페이지 123
		// 현재 사용자가 7페이지 789
		// 현재 사용자가 4페이지 456
		// 보여지는 페이지 갯수 3개
		// 총 8개의 페이지 라면 9를 보여주지말고 8을 보여줘라 즉, endPage로 계산한 페이지 값말고 그보다 작은 전체페이지의 개수를 보여줘라
        // ((startPage + blockLimit - 1) < boardList.getTotalPages())를 만족하면 startPage + blockLimit - 1을 도출
        // 만약 위의 조건을 만족하지 않는다면 boardList.getTotalPages(); 도출
		
        model.addAttribute("boardList",boardList);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        return "board/boardlist";
	}
	
	@PostMapping("/location")
	public @ResponseBody List<BoardDTO> findlocation(@RequestParam String input) {
		log.info("받아온 지역: "+ input);
		List<BoardDTO> boardList = boardService.findlocation(input);
		return boardList;
	}
	
}
