package com.example.demo.Entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.*;



@Entity
@Getter
@Setter
@Table(name = "chatroom_table")
public class ChatroomEntity extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long chatroomId;
	
	@Column
	private String roomId;
	
	@Column
	private String roomName;
	// 채팅방 이름
	
	@Column
	private String host;
	// 방장
	
	
	@OneToMany(mappedBy = "chatroomEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
	private List<ChatEntity> chatEntityList = new ArrayList<>();
	// 채팅들
	
	
	public static ChatroomEntity toChatroomEntity(String roomId, String roomName, String host) {
		ChatroomEntity chatroomEntity = new ChatroomEntity();
		chatroomEntity.setRoomName(roomName);
		chatroomEntity.setRoomId(roomId);
		chatroomEntity.setHost(host);
		return chatroomEntity;
		
	}
}
