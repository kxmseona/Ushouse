package com.example.demo.Dto;

import org.springframework.web.socket.WebSocketSession;

import com.example.demo.Entity.ChatroomEntity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.*;

@Getter
@Setter
@ToString
public class ChatroomDTO {
	
	private Long chatroomId;
	private String host;
	private String roomId;
	private String roomName;
	private LocalDateTime chatroomCreatedTime;
	private Set<WebSocketSession> sessions = new HashSet<>();
	
	public static ChatroomDTO create(String roomName) {
		ChatroomDTO room = new ChatroomDTO();
		
		room.roomId = UUID.randomUUID().toString();
		room.roomName = roomName;
		return room;
	}
	
	public static ChatroomDTO toChatroomDTO(ChatroomEntity chatroomEntity) {
		ChatroomDTO chatroomDTO = new ChatroomDTO();
		
		chatroomDTO.setChatroomId(chatroomEntity.getChatroomId());
		chatroomDTO.setHost(chatroomEntity.getHost());
		chatroomDTO.setRoomId(chatroomEntity.getRoomId());
		chatroomDTO.setRoomName(chatroomEntity.getRoomName());
		chatroomDTO.setChatroomCreatedTime(chatroomEntity.getCreatedTime());
		return chatroomDTO;
	}
}
