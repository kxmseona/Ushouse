 package com.example.demo.Repository;

import java.util.Optional;

import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.util.Streamable;

import com.example.demo.Entity.MemberEntity;

public interface MemberRepository extends JpaRepository<MemberEntity, Long>{
	// 이메일로 회원 정보 조회(select * from member_table where member_email=?)
	Optional<MemberEntity> findByMemberEmail(String memberEmail);

	
	

	
}
