package com.example.demo.Service;

import java.util.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.Dto.ChatDTO;
import com.example.demo.Dto.ChatroomDTO;
import com.example.demo.Entity.ChatEntity;
import com.example.demo.Entity.ChatroomEntity;
import com.example.demo.Repository.ChatRepository;
import com.example.demo.Repository.ChatroomRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatService {
	private final ChatRepository chatRepository;
	private final ChatroomRepository chatroomRepository;
	
	@Transactional
	public List<ChatroomDTO> chatroomfindAll(){
		// 모든 채팅방 조회
		List<ChatroomEntity> chatroomEntityList = chatroomRepository.findAll();
		List<ChatroomDTO> chatroomDTOList = new ArrayList<>();
		
		for(ChatroomEntity chatroomEntity: chatroomEntityList) {
			chatroomDTOList.add(ChatroomDTO.toChatroomDTO(chatroomEntity));
		}
		return chatroomDTOList;
	}
	
	
	
	@Transactional
	public ChatroomDTO findByRoomId(String roomId) {
		// 채팅방 아이디로 채팅방 조회
		Optional<ChatroomEntity> optionalChatroomEntity = chatroomRepository.findByRoomId(roomId);
		if(optionalChatroomEntity.isPresent()) {
			ChatroomEntity chatroomEntity = optionalChatroomEntity.get();
			ChatroomDTO chatroomDTO = ChatroomDTO.toChatroomDTO(chatroomEntity);
			
			return chatroomDTO;
		}else {
			return null;
		}
		
	}
	
	@Transactional
	public String saveChatroom(ChatroomDTO chatroomDTO) {
		// 채팅방 생성
		ChatroomEntity chatroomEntity = ChatroomEntity.toChatroomEntity(chatroomDTO.getRoomId(), chatroomDTO.getRoomName(),chatroomDTO.getHost());
		return chatroomRepository.save(chatroomEntity).getRoomId();	
		// 채팅방 생성후 채팅방의 문자열 코드 return
	}

	@Transactional
	public Long saveChat(ChatDTO chatDTO) {
		// 체팅메세지 저장
		Optional<ChatroomEntity> optionalchatroomEntity = chatroomRepository.findByChatroomId(chatDTO.getChatroomId());
		if(optionalchatroomEntity.isPresent()) {
			ChatroomEntity chatroomEntity = optionalchatroomEntity.get();
			ChatEntity chatEntity = ChatEntity.toChatEntity(chatDTO, chatroomEntity);
			
			return chatRepository.save(chatEntity).getId();
			}else {
				return null;
			}
	}

	@Transactional
	public List<ChatDTO> chatfindAllByChatroomId(Long chatroomId) {
		// 예전 채팅 불러오기
		ChatroomEntity chatroomEntity = chatroomRepository.findByChatroomId(chatroomId).get();
		List<ChatEntity> chatEntityList = chatRepository.findAllByChatroomEntityOrderByIdDesc(chatroomEntity);
		
		
		List<ChatDTO> chatDTOList = new ArrayList<>();
		for(ChatEntity chatEntity: chatEntityList) {
			ChatDTO chatDTO = ChatDTO.toChatDTO(chatEntity);
			chatDTOList.add(chatDTO);
		}
		return chatDTOList;
	}
	
	@Transactional
	public ChatroomDTO findByChatroomId(Long chatroomId) {
		ChatroomEntity chatroomEntity = chatroomRepository.findByChatroomId(chatroomId).get();
		ChatroomDTO chatroomDTO = ChatroomDTO.toChatroomDTO(chatroomEntity);
		return chatroomDTO;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
