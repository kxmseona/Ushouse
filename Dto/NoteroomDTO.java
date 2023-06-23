package com.example.demo.Dto;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import org.springframework.web.socket.WebSocketSession;

import com.example.demo.Entity.NoteroomEntity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class NoteroomDTO {

	private Long noteroomId;
	private String sendPerson;
	private String receivePerson;
	private String noteroomName;
	private LocalDateTime noteroomCreatedTime;
	private Set<WebSocketSession> sessions = new HashSet<>();

	
	
	
	public static NoteroomDTO toNoteroomDTO(NoteroomEntity noteroomEntity) {
		NoteroomDTO noteroomDTO = new NoteroomDTO();
		
		noteroomDTO.setNoteroomId(noteroomEntity.getId());
		noteroomDTO.setSendPerson(noteroomEntity.getSendPerson());
		noteroomDTO.setReceivePerson(noteroomEntity.getReceivePerson());
		noteroomDTO.setNoteroomName(noteroomEntity.getNoteroomName());
		noteroomDTO.setNoteroomCreatedTime(noteroomEntity.getCreatedTime());
		return noteroomDTO;
	}
}
