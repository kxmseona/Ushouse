package com.example.demo.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.Entity.MypageEntity;



public interface MypageRepository extends JpaRepository<MypageEntity, Long>{

	List<MypageEntity> findAllByWriter(String memberEmail);

	
	

}
