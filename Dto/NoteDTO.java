package com.example.demo.Dto;

import java.time.LocalDateTime;

import com.example.demo.Entity.NoteEntity;

import lombok.*;

@Getter
@Setter
@ToString
public class NoteDTO {
	
	private Long id;
	private Long noteroomId;
	
	private String writer;
	private String note;
	private LocalDateTime noteCreatedTime;
	
	public static NoteDTO toNoteDTO(NoteEntity noteEntity) {
		NoteDTO noteDTO = new NoteDTO();
		
		noteDTO.setId(noteEntity.getId());
		noteDTO.setNoteroomId(noteEntity.getNoteroomEntity().getId());
		noteDTO.setWriter(noteEntity.getWriter());
		noteDTO.setNote(noteEntity.getNote());
		noteDTO.setNoteCreatedTime(noteEntity.getCreatedTime());
		return noteDTO;
	}

}
