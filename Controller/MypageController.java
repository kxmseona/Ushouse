package com.example.demo.Controller;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.Dto.MemberDTO;
import com.example.demo.Dto.MypageDTO;
import com.example.demo.Dto.NoteDTO;
import com.example.demo.Dto.NoteroomDTO;
import com.example.demo.Entity.MemberEntity;
import com.example.demo.Service.MemberService;
import com.example.demo.Service.MypageService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Controller
@RequiredArgsConstructor
@Log4j2
public class MypageController {
	private final MypageService mypageService;
	private final MemberService memberService;
	
	@GetMapping("/mypage/first")
	public String mypagefirst() {
		return "/mypage/mypagefirst";
	}
	
	
	@GetMapping("/mypage")
	public String mypage() {
		return "/mypage/mypage";
	}

	@PostMapping("/mypage")
	public String savemypage(@ModelAttribute MypageDTO mypageDTO) throws IOException {
		System.out.println("mypageDTO = " + mypageDTO);
		
		mypageService.savemypage(mypageDTO);
		log.info("프로필 저장 성공");
		return "/mypage/mypagefirst";
	}
	
	@GetMapping("/mypagelist")
	public String openlist(HttpServletRequest request, Model model) {
		System.out.println(request.getHeader("Cookie"));
		
		Cookie[] cookies = request.getCookies();
		if(cookies != null) {
			for(Cookie c : cookies) {
				String name = c.getName();
				String value = c.getValue();
				if (name.equals("memberEmail")) {
					String memberEmail = value;
					List<MypageDTO> mypageDTOList = mypageService.findAllByWriter(memberEmail);
					model.addAttribute("mypageDTOList", mypageDTOList);
				}
			}
		}else{
			return null;
		}
		
		return "/mypage/mypagelist";
	}
	
	@GetMapping("/mypage/{id}")
	public String findById(@PathVariable Long id, Model model) {
		
		MypageDTO mypageDTO = mypageService.findById(id);
		model.addAttribute("mypage" , mypageDTO);
		
		return"/mypage/detail";
		
	}
	
	
	
	@GetMapping("/mypage/noteroom")
	public String opennoteroom(HttpServletRequest request, Model model) {
		System.out.println(request.getHeader("Cookie"));
		
		Cookie[] cookies = request.getCookies();
		if(cookies != null) {
			for(Cookie c : cookies) {
				String name = c.getName();
				String value = c.getValue();
				if (name.equals("memberEmail")) {
					String memberEmail = value;
					List<NoteroomDTO> noteroomDTOList = mypageService.findAllBySendPerson(memberEmail);
					model.addAttribute("noteroomDTOList", noteroomDTOList);
					List<NoteroomDTO> roomDTOList = mypageService.findAllByReceivePerson(memberEmail);
					model.addAttribute("roomDTOList", roomDTOList);
					
				}
			}
		}else{
			return null;
		}
		
		return "/mypage/noteroom";
	}
	
	@PostMapping("/mypage/noteroom")
	public @ResponseBody String noteroom(@ModelAttribute NoteroomDTO noteroomDTO) {
		String receive= noteroomDTO.getReceivePerson();
		
		mypageService.savenoteroom(noteroomDTO);
		log.info("쪽지함 생성 성공");
		return receive;
		
	}
	
	@GetMapping("/mypage/note/{noteroomId}")
	public String findBynoteroomId(@PathVariable Long noteroomId, Model model) {
		List<NoteDTO> noteDTOList = mypageService.findByNoteroomId(noteroomId);
		model.addAttribute("noteDTOList",noteDTOList);
		model.addAttribute("noteroomId", noteroomId);
		return "/mypage/note";
	}
	
	
	
	
	
	
	
}


