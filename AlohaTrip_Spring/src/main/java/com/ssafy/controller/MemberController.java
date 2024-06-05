package com.ssafy.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.dto.member.MemberDto;
import com.ssafy.dto.member.UpdateMemberDto;
import com.ssafy.service.MemberService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Tag(name = "MemberController", description = "사용자에 관련된 API 요청을 처리하는 Controller")
@RequiredArgsConstructor
@RequestMapping("/member")
@RestController
public class MemberController {
	
	private final MemberService memberService;

//	@Operation(summary = "회원가입", description = "회원가입을 처리합니다.")
//	@PostMapping("/signUp")
//	public ResponseEntity<MemberDto> signUp(SignUpDto signUpDto) {
//		int sidoCode;
//		if(signUpDto.getSido().equals("서울특별시")) {
//			sidoCode = 1;
//		}else {
//			sidoCode = 31;
//		}
//
//		int gugunCode = 0;
//		try {
//			gugunCode = memberService.getGugunNum(signUpDto.getGugun());
//			//memberId = memberService.lastMemberId() + 1; 	//미사용
//			String password = memberService.setPassword(signUpDto.getPassword(), signUpDto.getId());
//			String email = signUpDto.getEmailFront() + "@" + signUpDto.getEmailBack();
//			System.out.println(signUpDto.toString());
//
//			MemberDto newMember = new MemberDto(0, signUpDto.getName(), signUpDto.getId(), password, email, sidoCode, gugunCode);
//			memberService.memberRegister(newMember);
//
//			return ResponseEntity.ok(newMember);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		return null;
//	}
//
//	@Operation(summary = "로그인", description = "아이디와 비밀번호로 로그인을 처리합니다.")
//	@PostMapping("/login")
//	public ResponseEntity<String> login(@Param("id") String id, @Param("password") String password, HttpServletRequest request) {
//		MemberDto member;
//
//		try {
//			password = memberService.setPassword(password, id);
//			member = memberService.memberLogin(id, password);
//
//			if(member!=null) {
//				HttpSession session = request.getSession();
//                session.setAttribute("loginUser", member);
//                session.setAttribute("emailFront", member.getEmail().split("@")[0]);
//                session.setAttribute("emailBack", member.getEmail().split("@")[1]);
//                if(member.getSidoCode() == 1) {
//                	session.setAttribute("sido", "서울특별시");
//                }else {
//                	session.setAttribute("sido", "경기도");
//                }
//                System.out.println("구군 이름 확인");
//                String gugun = memberService.getGugunName(member.getGugunCode(), member.getSidoCode());
//                session.setAttribute("gugun", gugun);
//
//                return ResponseEntity.ok("로그인 성공!");
//			}else {
//				return new ResponseEntity<>("등록되지 않은 사용자입니다!", HttpStatus.UNAUTHORIZED);
//			}
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//		return null;
//	}
//
//	@Operation(summary = "로그아웃", description = "로그아웃을 처리합니다.")
//	@PostMapping("/logout")
//	public ResponseEntity<String> logout(HttpServletRequest request) {
//		try {
//			HttpSession session = request.getSession();
//			session.invalidate();
//
//			return ResponseEntity.ok("로그아웃 성공!");
//		} catch (Exception e) {
//			e.printStackTrace();
//			return new ResponseEntity<>("로그아웃 실패!", HttpStatus.INTERNAL_SERVER_ERROR);
//		}
//	}
//
	@Operation(summary = "내 정보 보기", description = "자신의 정보를 조회합니다.")
	@GetMapping("/{memberId}")
	public ResponseEntity<?> findById(@PathVariable Long memberId) {
		return ResponseEntity.ok(memberService.findById(memberId));
	}

	@Operation(summary = "회원 정보 수정", description = "회원 정보를 수정합니다.")
	@PutMapping("/update")
	public ResponseEntity<MemberDto> update(@RequestBody UpdateMemberDto updateMemberDto, HttpServletRequest request, HttpServletResponse response) {
		try {
			MemberDto member = new MemberDto(updateMemberDto.getMemberId(), updateMemberDto.getName(), updateMemberDto.getId(), 
					updateMemberDto.getPassword(), updateMemberDto.getNickname(), null, null, null, null);
			memberService.memberUpdate(member);
			
//			String redirectUrl = "http://localhost:5173/memberForm/memberDetail";
//			response.sendRedirect(redirectUrl);
            return ResponseEntity.ok(member);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity(null, HttpStatus.BAD_REQUEST);
		}

	}
//
//	@Operation(summary = "회원탈퇴", description = "회원 탈퇴 처리를 합니다.")
//	@DeleteMapping("/withdrawal/{memberId}")
//	public ResponseEntity<String> withdrawal(@PathVariable int memberId, HttpServletRequest request) {
//		try {
//			memberService.memberWithdrawal(memberId);
//			HttpSession session = request.getSession();
//			session.invalidate();
//
//			return ResponseEntity.ok("회원 탈퇴 완료!");
//		} catch (Exception e) {
//			e.printStackTrace();
//			return new ResponseEntity("회원 탈퇴 실패!", HttpStatus.BAD_REQUEST);
//		}
//	}
	
	
}
