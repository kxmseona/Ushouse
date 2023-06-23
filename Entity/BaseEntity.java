package com.example.demo.Entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.*;
import lombok.Getter;

// 시간관련 기능이 필요할때 이 엔티티로 확장해서 사용한다.(게시글 시간, 댓글시간 등)

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
public class BaseEntity {
	
	@CreationTimestamp
	@Column(updatable = false)
	private LocalDateTime createdTime;
	// 생성시간 컬럼 생성 업데이트 기능 끔 타입 LocalDateTime 이름 createdTime
	
	@UpdateTimestamp
	@Column(insertable = false)
	private LocalDateTime updatedTime;
	// 수정시간 컬럼 생성 생성(삽입) 기능 끔 타입 LocalDateTime 이름 updatedTime
}
