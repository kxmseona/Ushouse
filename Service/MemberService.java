package com.example.demo.Service;

import java.util.*;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.Dto.MemberDTO;
import com.example.demo.Dto.MypageDTO;
import com.example.demo.Dto.SignResponse;
import com.example.demo.Dto.SignRequest;
import com.example.demo.Entity.Authority;
import com.example.demo.Entity.MemberEntity;
import com.example.demo.Repository.MemberRepository;
import com.example.demo.Security.JwtProvider;

import com.example.demo.Exception.*;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {
	private final MemberRepository memberRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtProvider jwtProvider;
	
	
	
	
	public boolean save(SignRequest request) throws Exception {
		try {
			MemberEntity memberEntity = MemberEntity.builder()
					.memberEmail(request.getMemberEmail())
					.memberPassword(passwordEncoder.encode(request.getMemberPassword()))
					.memberName(request.getMemberName())
					.Gender(request.getGender())
					.build();
					
			memberEntity.setRoles(Collections.singletonList(Authority.builder().name("ROLE_MEMBER").build()));
			memberRepository.save(memberEntity);
			}catch (Exception e) {
			          System.out.println(e.getMessage());
			          throw new Exception("잘못된 요청입니다.");
			      }
		return true;
		
	}
	
	public SignResponse getMember(String memberEmail) throws Exception{
		MemberEntity memberEntity = memberRepository.findByMemberEmail(memberEmail)
				.orElseThrow(() -> new Exception("계정을 찾을수 없습니다."));
		return new SignResponse(memberEntity);
	}
	
	public SignResponse login(SignRequest request) throws Exception {
		MemberEntity memberEntity = memberRepository.findByMemberEmail(request.getMemberEmail())
				.orElseThrow(() -> new BadCredentialsException("잘못된 계정정보입니다."));
		
		if (!passwordEncoder.matches(request.getMemberPassword(), memberEntity.getMemberPassword())) {
            throw new BadCredentialsException("잘못된 계정정보입니다.");
        }
		
		return SignResponse.builder()
				.id(memberEntity.getId())
				.memberEmail(memberEntity.getMemberEmail())
				.memberName(memberEntity.getMemberName())
				.gender(memberEntity.getGender())
				.roles(memberEntity.getRoles())
				.token(jwtProvider.createToken(memberEntity.getMemberEmail(), memberEntity.getRoles()))
				.build();
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	public List<MemberDTO> findAll() {
		// Repository는 Entity로 받기
		List<MemberEntity> memberEntityList = memberRepository.findAll();
		List<MemberDTO> memberDTOList = new ArrayList<>();
		for(MemberEntity memberEntity: memberEntityList) {
			memberDTOList.add(MemberDTO.toMemberDTO(memberEntity));
		}
		return memberDTOList;
	}
	
	public MemberDTO updateForm(String myEmail) {
		Optional<MemberEntity> optionalMemberEntity = memberRepository.findByMemberEmail(myEmail);
		if (optionalMemberEntity.isPresent()) {
			return MemberDTO.toMemberDTO(optionalMemberEntity.get());
		} else {
			return null;
		}
	}
	//public void update(MemberDTO memberDTO) {
		// save() 메서드 -- db에 해당 id값이 없으면 추가해주고(insert), 해당 id가 있으면 수정해줌(update)
		//memberRepository.save(MemberEntity.toUpdateMemberEntity(memberDTO));
		
		
	//}
	public void deleteById(Long id) {
		memberRepository.deleteById(id);
	}
	public String emailCheck(String memberEmail) {
		Optional<MemberEntity> byMemberEmail = memberRepository.findByMemberEmail(memberEmail);
		if (byMemberEmail.isPresent()) {
			// 조회결과가 있다 -> 사용할 수 없다.
			return null;
		} else {
			// 조회결과가 없다 -> 사용할 수 있다.
			return "ok";
		}
	
	}

	public MemberEntity getmemberName(String memberEmail) {
		Optional<MemberEntity> memberEntityoptional = memberRepository.findByMemberEmail(memberEmail);
		
		if(memberEntityoptional.isPresent()) {
			MemberEntity memberEntity = memberEntityoptional.get();
			return memberEntity;
		}else {
			return null;
		}
	}
	public MemberDTO findById(Long id) {
		// 기본적으로 Repository는 하나를 조회할때는 Optional로 감싸서 넘겨줌
		// 여러개변 list로
		Optional<MemberEntity> optionalMemberEntity = memberRepository.findById(id);
		if(optionalMemberEntity.isPresent()) {
			// optional 껍데기 .get()으로 벗겨서 엔티티만 MemberDto의 toMemberDTO메서드에 보내 DTO로 변환
			return MemberDTO.toMemberDTO(optionalMemberEntity.get());
		}else {
			return null;
		}
		
	}

	public MemberDTO findByMemberEmail(String receivePerson) {
		Optional<MemberEntity> optionalMemberEntity = memberRepository.findByMemberEmail(receivePerson);
		if(optionalMemberEntity.isPresent()) {
			return MemberDTO.toMemberDTO(optionalMemberEntity.get());
		}else {
			return null;
		}
	}

	
	
}
