package com.example.demo.Service;

import java.util.*;

import org.springframework.stereotype.Service;

import com.example.demo.Dto.MypageDTO;
import com.example.demo.Dto.NoteDTO;
import com.example.demo.Dto.NoteroomDTO;
import com.example.demo.Entity.MypageEntity;
import com.example.demo.Entity.NoteEntity;
import com.example.demo.Entity.NoteroomEntity;
import com.example.demo.Repository.MypageRepository;
import com.example.demo.Repository.NoteRepository;
import com.example.demo.Repository.NoteroomRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MypageService {

	private final MypageRepository mypageRepository;
	private final NoteroomRepository noteroomRepository;
	private final NoteRepository noteRepository;

	public void savemypage(MypageDTO mypageDTO) {
		MypageEntity mypageEntity = MypageEntity.tomypageEntity(mypageDTO);
		mypageRepository.save(mypageEntity);
		
	}

	public List<MypageDTO> findAllByWriter(String memberEmail) {
		List<MypageEntity> mypageEntityList = mypageRepository.findAllByWriter(memberEmail);
		
		List<MypageDTO> mypageDTOList = new ArrayList<>();
		for(MypageEntity mypageEntity : mypageEntityList) {
			mypageDTOList.add(MypageDTO.toMypageDTO(mypageEntity));
		}
		return mypageDTOList;
		
	}

	public MypageDTO findById(Long id) {
		Optional<MypageEntity> optionalmypageEntity = mypageRepository.findById(id);
		
		if(optionalmypageEntity.isPresent()) {
			MypageEntity mypageEntity = optionalmypageEntity.get();
			MypageDTO mypageDTO = MypageDTO.toMypageDTO(mypageEntity);
			return mypageDTO;
		}else {
			return null;
		}
	}

	public Long savenoteroom(NoteroomDTO noteroomDTO) {
		NoteroomEntity noteroomEntity = NoteroomEntity.tonoteroomEntity(noteroomDTO);
		Long saveId = noteroomRepository.save(noteroomEntity).getId();
		return saveId;
	}



	public Long saveNote(NoteDTO noteDTO) {
		Optional<NoteroomEntity> optionalnoteroomEntity = noteroomRepository.findById(noteDTO.getNoteroomId());
		if(optionalnoteroomEntity.isPresent()) {
			NoteroomEntity noteroomEntity = optionalnoteroomEntity.get();
			NoteEntity noteEntity = NoteEntity.toNoteEntity(noteDTO, noteroomEntity);
			
			return noteRepository.save(noteEntity).getId();
			
		}else {
			return null;
		}
	}

	public List<NoteDTO> findByNoteroomId(Long noteroomId) {
		NoteroomEntity noteroomEntity = noteroomRepository.findById(noteroomId).get();
		List<NoteEntity> noteEntityList = noteRepository.findAllByNoteroomEntityOrderByIdDesc(noteroomEntity);
		
		List<NoteDTO> noteDTOList = new ArrayList<>();
		for(NoteEntity noteEntity: noteEntityList) {
			NoteDTO noteDTO = NoteDTO.toNoteDTO(noteEntity);
			noteDTOList.add(noteDTO);
		}
		return noteDTOList;
	}
	
	public List<NoteroomDTO> findAllBySendPerson(String memberEmail) {
		List<NoteroomEntity> noteroomEntityList = noteroomRepository.findAllBySendPerson(memberEmail);
		
		List<NoteroomDTO> noteroomDTOList = new ArrayList<>();
		for(NoteroomEntity noteroomEntity: noteroomEntityList) {
			noteroomDTOList.add(NoteroomDTO.toNoteroomDTO(noteroomEntity));
		}
		return noteroomDTOList;
	}
	
	public List<NoteroomDTO> findAllByReceivePerson(String memberEmail) {
		List<NoteroomEntity> noteroomEntityList = noteroomRepository.findAllByReceivePerson(memberEmail);
		
		List<NoteroomDTO> noteroomDTOList = new ArrayList<>();
		for(NoteroomEntity noteroomEntity: noteroomEntityList) {
			noteroomDTOList.add(NoteroomDTO.toNoteroomDTO(noteroomEntity));
			
		}
		return noteroomDTOList;
		
	}
	
	
	
	
}
