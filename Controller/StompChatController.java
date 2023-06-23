package com.example.demo.Controller;

import java.util.*;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.demo.Dto.ChatDTO;
import com.example.demo.Dto.NoteDTO;
import com.example.demo.Service.ChatService;
import com.example.demo.Service.MypageService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Controller
@RequiredArgsConstructor
@Log4j2
public class StompChatController {

	private final SimpMessagingTemplate template;
	private final ChatService chatService;
	private final MypageService mypageService;
	
	@MessageMapping(value = "/chat/enter")
	public void enter(ChatDTO chatDTO) {
		log.info("soket연결 성공!");
		
		chatDTO.setChat(chatDTO.getWriter() + "님이 채팅방에 참여하였습니다.");
		
		//List<ChatDTO> chatDTOList = chatService.chatfindAllByChatroomId(chatDTO.getChatroomId());
		//if(chatDTOList != null) {
			// chatDTOList가 null이 아니라면 chatService에서 가져온 DTO를 chatDTO에 setting
		//	for(ChatDTO c : chatDTOList) {
		//		chatDTO.setWriter(c.getWriter());
		//		chatDTO.setChat(c.getChat());
		//	}
		//}
		
		template.convertAndSend("/sub/chat/room/" + chatDTO.getChatroomId(), chatDTO);
		
		chatService.saveChat(chatDTO);
		
	
	}
	
	
	@MessageMapping(value = "/chat/message")
	public void message(ChatDTO chatDTO) {
		template.convertAndSend("/sub/chat/room/" + chatDTO.getChatroomId(), chatDTO);
		
		chatService.saveChat(chatDTO);
		
	}
	
	
	@MessageMapping(value = "/chat/noteroom")
	public void noteroom(NoteDTO noteDTO ) {
		log.info("soket연결 성공!");
		
		noteDTO.setNote(noteDTO.getWriter() + "님이 채팅방에 참여하였습니다.");
		
		//List<ChatDTO> chatDTOList = chatService.chatfindAllByChatroomId(chatDTO.getChatroomId());
		//if(chatDTOList != null) {
			// chatDTOList가 null이 아니라면 chatService에서 가져온 DTO를 chatDTO에 setting
		//	for(ChatDTO c : chatDTOList) {
		//		chatDTO.setWriter(c.getWriter());
		//		chatDTO.setChat(c.getChat());
		//	}
		//}
		
		template.convertAndSend("/sub/mypage/note/" + noteDTO.getNoteroomId(), noteDTO);
		
		mypageService.saveNote(noteDTO);
		
	}
	@MessageMapping(value = "/chat/note")
	public void note(NoteDTO noteDTO) {	
		template.convertAndSend("/sub/mypage/note/" + noteDTO.getNoteroomId(), noteDTO);
		
		mypageService.saveNote(noteDTO);
		
	}
	
}
