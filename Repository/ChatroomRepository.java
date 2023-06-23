package com.example.demo.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.Entity.ChatroomEntity;

public interface ChatroomRepository extends JpaRepository<ChatroomEntity, Long>{

	Optional<ChatroomEntity> findByRoomId(String roomId);

	Optional<ChatroomEntity> findByChatroomId(Long chatroomId);

}
