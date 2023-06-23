package com.example.demo.Entity;

import com.example.demo.Dto.NoteDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "note_table")
public class NoteEntity extends BaseEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="noteroom_id")
	private NoteroomEntity noteroomEntity;
	
	@Column
	private String writer;
	
	@Column
	private String note;
	
	
	public static NoteEntity toNoteEntity(NoteDTO noteDTO, NoteroomEntity noteroomEntity) {
		NoteEntity noteEntity = new NoteEntity();
		
		noteEntity.setNoteroomEntity(noteroomEntity);
		noteEntity.setWriter(noteDTO.getWriter());
		noteEntity.setNote(noteDTO.getNote());
		return noteEntity;
	}
	
}
