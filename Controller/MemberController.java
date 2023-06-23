package com.example.demo.Controller;


import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.Dto.MemberDTO;
import com.example.demo.Dto.MypageDTO;
import com.example.demo.Dto.SignRequest;
import com.example.demo.Dto.SignResponse;

import com.example.demo.Service.MemberService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Controller
@RequiredArgsConstructor
@Log4j2
public class MemberController {
	//생성자 주입 - 서비스에 있는 비즈니스 로직(메서드 등등)을 이제 사용할 수 있게되는..권한이 생긴다
	private final MemberService memberService;
	
	@GetMapping("/member/terms1")
	public String terms1() {
		return"/member/terms1";
	}

	@GetMapping("/member/terms2")
	public String terms2() {
		return"/member/terms2";
	}
	
	@PostMapping("/main")
	public ResponseEntity<SignResponse> main(@RequestParam String memberEmail) throws Exception{
		
		return new ResponseEntity<>(memberService.getMember(memberEmail), HttpStatus.OK);
		
		
		
	}
	@GetMapping("/main")
	public String callmain() {
		return "/main";
	}
	
	//회원가입 페이지 출력 요청
	@GetMapping("/member/save")
	public String saveForm() {
		return "member/save";
	}
	
	@PostMapping("/member/save")
	public String save(@ModelAttribute SignRequest request) throws Exception {
		log.info("받아온 정보"+ request);
		boolean bool = memberService.save(request);
		
		if(bool == true) {
			return "/member/login";
		}else {
			return "/member/save";
		}
		
	}
	
	@GetMapping("/member/login")
	public String loginForm() {
		return "member/login";
	}
	
	
	@PostMapping("/member/login")
	public ResponseEntity<SignResponse> login(@ModelAttribute SignRequest request) throws Exception{
		
		return new ResponseEntity<>(memberService.login(request), HttpStatus.OK);
	}
	
	@GetMapping("/member/")
	public String findAll(Model model) {
		List<MemberDTO> memberDTOList = memberService.findAll();
		// 어떠한 html로 가져갈 데이터가 있다면 model을 사용
		// findAll() db에서 회원정보를 가지고오는 서비스의 메서드 호출
		model.addAttribute("memberList", memberDTOList);
		return "member/list";
		// model에 list를 담아서 list.html에 보낸다(리턴한다.)
		
	}
	@GetMapping("/member/{id}")
	public String findById(@PathVariable Long id, Model model) {
		MemberDTO memberDTO = memberService.findById(id);
		model.addAttribute("member",memberDTO);
		return "member/detail";
	}
	
	@GetMapping("/member/update")
	public String updateForm(HttpServletRequest request, Model model) {
		// 세션에 있는 이메일 정보를 가지고 와서 db에서 가져온 이메일과 일치하는 전체정보를 가지고 온다.
		// 담을 때는 set, 가져올 때는 get
		// get으로 가져온 것은 object 이다. string 보다 object가 크기가 크기 때문에 string에 담으려면
		// object를 string 으로 강제 형변환을 해 주어야 한다. (String)
		System.out.println(request.getHeader("Cookie"));
		
		Cookie[] cookies = request.getCookies();
		if(cookies != null) {
			for(Cookie c : cookies) {
				String name = c.getName();
				String value = c.getValue();
				if (name.equals("memberEmail")) {
					String memberEmail = value;
					MemberDTO memberDTO = memberService.updateForm(memberEmail);
					model.addAttribute("updateMember", memberDTO);
				}
			}
		}else{
			return null;
		}
		
		return "member/update";
	}
	//@PostMapping("/member/update")
	//public String update(@ModelAttribute MemberDTO memberDTO) {
		//memberService.update(memberDTO);
		//return "redirect:/member/" + memberDTO.getId();
		// 방법 1. 위에서 했던것처럼 model에 다시 담아서 return "detail" 해줘도 되고,
		// 방법 2. redirect를 사용하여 주소 작성후 우리가 가지고온 DTO의 id값을 불러와 주소를 지정해줘도 된다.
		
	//}
	
	@GetMapping("/member/delete/{id}")
	public String deleteById(@PathVariable Long id) {
		// 경로상으로 id값을 같이 넘겨 받았기 때문에 @PathVariable을 사용해야함
		memberService.deleteById(id);
		return "redirect:/member/";
		// redirect를 이용하면 주소로 html에 접근할 수 있다.
	}
	
	@GetMapping("/member/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		// 세션을 무효화 한다. .invalidate()
		return "index";
	}
	
	@PostMapping("/member/email-check")
	public @ResponseBody String emailCheck(@RequestParam("memberEmail") String memberEmail) {
		// ajax를 쓸때에는 @ResponseBody 어노테이션을 꼭 붙여야함
		// save.html에서 ajax를 이용해서 보내는 데이터의 파라미터의 이름이 memberEmail이여서 memberEmail을 넣어줌
		System.out.println("memberEmail = " + memberEmail);
		String checkResult = memberService.emailCheck(memberEmail);
		return checkResult;
	}
	
	
	
}
