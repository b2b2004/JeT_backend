package com.travelrec.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.travelrec.project.config.auth.PrincipalDetail;
import com.travelrec.project.dto.SurveyDto;
import com.travelrec.project.dto.UserDto;
import com.travelrec.project.mapper.UserMapper;
import com.travelrec.project.service.UserService;

@Controller
@RestController
public class UserController {
	
	@Autowired
	private UserMapper usermapper;
	
	@Autowired
	private UserService userService;
	
	
	@PostMapping("/user/signup")
	public ResponseEntity<?> signup(@RequestBody UserDto user){
		System.out.println("회원가입요청 = "+user);
		return new ResponseEntity<>(userService.회원가입(user), HttpStatus.OK);
	}
	
	@PostMapping("/user/loginuser")
	public ResponseEntity<?> ckloginuser(Authentication authentication){
		 PrincipalDetail principal = (PrincipalDetail) authentication.getPrincipal();
		return new ResponseEntity<>(userService.유저확인(principal), HttpStatus.OK);
	}
	
	@PostMapping("/user/ckId")
	public ResponseEntity<?> ckId(@RequestBody String userId){
		return new ResponseEntity<>(userService.아이디중복확인(userId), HttpStatus.OK);
	}
	
	@PostMapping("/user/findpw")
	public ResponseEntity<?> findPw(@RequestBody UserDto user){
		System.out.println("임시비밀번호 발송= "+user);
		return new ResponseEntity<>(userService.임시비밀번호발송(user), HttpStatus.OK);
	}
	
	@GetMapping("/user/deleteId")
	public ResponseEntity<?> deleteId(Authentication authentication){
		PrincipalDetail principal = (PrincipalDetail) authentication.getPrincipal();
		System.out.println("deleteId 실행");
		return new ResponseEntity<>(userService.회원탈퇴(principal), HttpStatus.OK);
	}
	
	@PostMapping("/user/ckPw")
	public ResponseEntity<?> ckPassword(Authentication authentication, @RequestBody String password){
		PrincipalDetail principal = (PrincipalDetail) authentication.getPrincipal();
		System.out.println("ckPassword 실행 :" + password);
		return new ResponseEntity<>(userService.비밀번호확인(principal , password), HttpStatus.OK);
	}
	
	@PostMapping("/user/chageUser")
	public ResponseEntity<?> chageUser(Authentication authentication, @RequestBody String email){
		UserDto user = new UserDto();
		PrincipalDetail principal = (PrincipalDetail) authentication.getPrincipal();
		user.setUserId(principal.getUsername());
		user.setEmail(email);
		System.out.println("hi"+ user);
		System.out.println("chageUser 실행 ");
		return new ResponseEntity<>(userService.회원정보수정(user), HttpStatus.OK);
	}
	
	@PostMapping("/user/chagePw")
	public ResponseEntity<?> chagePw(Authentication authentication, @RequestBody String password){
		PrincipalDetail principal = (PrincipalDetail) authentication.getPrincipal();
		System.out.println("chagePw 실행 ");
		return new ResponseEntity<>(userService.비밀번호수정(principal,password), HttpStatus.OK);
	}
	
	@PostMapping("/user/likePlace")
	public ResponseEntity<?> setLikePlace(Authentication authentication, @RequestBody String place){
		PrincipalDetail principal = (PrincipalDetail) authentication.getPrincipal();
		System.out.println("setLikePlace 실행 :" + place);
		return new ResponseEntity<>(userService.좋아하는장소찜및삭제(principal , place), HttpStatus.OK);
	}
	
	@GetMapping("/user/likePlace")
	public ResponseEntity<?> getLikePlace(Authentication authentication){
		PrincipalDetail principal = (PrincipalDetail) authentication.getPrincipal();
		System.out.println("getLikePlace 실행 :");
		return new ResponseEntity<>(userService.좋아하는장소가져오기(principal), HttpStatus.OK);
	}
	
	@GetMapping("/user/mypagelikePlace")
	public ResponseEntity<?> mypagelikePlace(Authentication authentication){
		PrincipalDetail principal = (PrincipalDetail) authentication.getPrincipal();
		System.out.println("mypagelikePlace 실행 :");
		return new ResponseEntity<>(userService.마이페이지좋아하는장소(principal), HttpStatus.OK);
	}
	
	@PostMapping("/user/getSurvey")
	public ResponseEntity<?> getSurvey(@RequestBody String userId){
		System.out.println("getSurvey 실행 :" + userId);
		return new ResponseEntity<>(userService.사용자성향불러오기(userId), HttpStatus.OK);
	}
	
	@PostMapping("/user/survey")
	public ResponseEntity<?> setSurvey(@RequestBody SurveyDto survey){
		System.out.println("setSurvey 실행 :" + survey);
		return new ResponseEntity<>(userService.사용자성향등록(survey), HttpStatus.OK);
	}
	
	@PostMapping("/user/surveyUpdate")
	public ResponseEntity<?> surveyUpdate(@RequestBody SurveyDto survey){
		System.out.println("setSurvey 실행 :" + survey);
		return new ResponseEntity<>(userService.사용자성향수정(survey), HttpStatus.OK);
	}
	
}
