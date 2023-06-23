package com.example.demo.Entity;


import com.example.demo.Dto.ChatDTO;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Table(name = "chat_table")
public class ChatEntity extends BaseEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="chatroom_id")
	private ChatroomEntity chatroomEntity;
	
	@Column
	private String writer;
	
	@Column
	private String chat;
	
	
	
	public static ChatEntity toChatEntity(ChatDTO chatDTO, ChatroomEntity chatroomEntity) {
		ChatEntity chatEntity = new ChatEntity();
		
		chatEntity.setChatroomEntity(chatroomEntity);
		
		chatEntity.setWriter(chatDTO.getWriter());
		chatEntity.setChat(chatDTO.getChat());
		return chatEntity;
		
	}








}
