package com.example.demo.Entity;

import com.example.demo.Dto.NoteroomDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "noteroom_table")
public class NoteroomEntity extends BaseEntity{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	
	@Column
	private String sendPerson;

	@Column
	private String receivePerson;

	@Column
	private String noteroomName;

	
	public static NoteroomEntity tonoteroomEntity(NoteroomDTO noteroomDTO) {
		NoteroomEntity noteroomEntity = new NoteroomEntity();
		noteroomEntity.setSendPerson(noteroomDTO.getSendPerson());
		noteroomEntity.setReceivePerson(noteroomDTO.getReceivePerson());
		noteroomEntity.setNoteroomName(noteroomDTO.getNoteroomName());
		return noteroomEntity;
	}
	
}
