package com.example.demo.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.Entity.ChatEntity;
import com.example.demo.Entity.ChatroomEntity;

public interface ChatRepository extends JpaRepository <ChatEntity, Long>{

	List<ChatEntity> findAllByChatroomEntityOrderByIdDesc(ChatroomEntity chatroomEntity);

	



}
