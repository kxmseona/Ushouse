package com.example.demo.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.Entity.NoteEntity;
import com.example.demo.Entity.NoteroomEntity;

public interface NoteRepository extends JpaRepository<NoteEntity, Long> {

	List<NoteEntity> findAllByNoteroomEntityOrderByIdDesc(NoteroomEntity noteroomEntity);

}
