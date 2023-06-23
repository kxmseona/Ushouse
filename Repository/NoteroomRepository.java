package com.example.demo.Repository;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.Entity.NoteroomEntity;



public interface NoteroomRepository extends JpaRepository<NoteroomEntity, Long> {

	List<NoteroomEntity> findAllBySendPerson(String memberEmail);

	List<NoteroomEntity> findAllByReceivePerson(String memberEmail);

	

}
