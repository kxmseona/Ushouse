package com.example.demo.Dto;

import com.example.demo.Entity.ChatEntity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ChatDTO {
	
	private Long id;
	private Long chatroomId;
	
	//private String roomId;
	private String writer;
	private String chat;
	
	public static ChatDTO toChatDTO(ChatEntity chatEntity) {
		ChatDTO chatDTO = new ChatDTO();
		
		chatDTO.setId(chatEntity.getId());
		chatDTO.setChatroomId(chatEntity.getChatroomEntity().getChatroomId());
		//chatDTO.setRoomId(chatEntity.getChatroomEntity().getRoomId());
		chatDTO.setWriter(chatEntity.getWriter());
		chatDTO.setChat(chatEntity.getChat());
		
		return chatDTO;
		
	}
}
