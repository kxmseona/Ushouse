package com.example.demo.Controller;

import java.util.*;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.Dto.ChatDTO;
import com.example.demo.Dto.ChatroomDTO;
import com.example.demo.Dto.NoteroomDTO;
import com.example.demo.Service.ChatService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Controller
@RequiredArgsConstructor
@RequestMapping("/chat")
@Log4j2
public class ChatController {
	private final ChatService chatService;
	
	// 채팅방 목록 조회
	@GetMapping("/rooms")
	public String rooms(Model model){
		List<ChatroomDTO> chatroomDTOList = chatService.chatroomfindAll();
		model.addAttribute("chatroomList",chatroomDTOList);
		log.info("모든 채팅방 조회");
		return "/chat/rooms";
	}
	
	@PostMapping("/rooms")
	public @ResponseBody ChatroomDTO findRoom(@RequestParam("roomId") String roomId, Model model) {
		log.info("채팅방 검색, 방 코드:" + roomId);
		// 채팅방 조회
		ChatroomDTO chatroomDTO = chatService.findByRoomId(roomId);
		model.addAttribute("chatroomDTO", chatroomDTO);
		return chatroomDTO;
	}
	
	@GetMapping("/create")
	public String create(Model model) {
		return"/chat/create";
	}
	
	@PostMapping("/create")
	public String createform(@ModelAttribute ChatroomDTO chatroomDTO, Model model) {
		log.info("채팅방 생성" + chatroomDTO.getRoomName());
		String roomId = chatService.saveChatroom(chatroomDTO);
		model.addAttribute("roomId", roomId);
		// 채팅방 생성 후 채팅방의 문자열 코드 return
		return "/chat/rooms";
	}
	
	@GetMapping("/room")
	public String openroom() {
		
		return "/chat/room";
	}
	
	@GetMapping("/room/{chatroomId}")
	public String findBychatroomId(@PathVariable Long chatroomId, Model model) {
		//List<ChatDTO> chatDTOList = chatService.chatfindAllByChatroomId(chatroomId);
		ChatroomDTO chatroomDTO = chatService.findByChatroomId(chatroomId);
		ChatroomDTO roominfo = chatroomDTO;
		model.addAttribute("roominfo", roominfo);
		//model.addAttribute("chatDTOList", chatDTOList);
		return "/chat/room";
	}
	

	
	@GetMapping("/singo")
	public String opensingo() {
		return "/chat/singo";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
