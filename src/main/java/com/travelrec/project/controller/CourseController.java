package com.travelrec.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.travelrec.project.config.auth.PrincipalDetail;
import com.travelrec.project.service.CourseService;

@Controller
public class CourseController {
	
	@Autowired
	private CourseService courseService;
	
	@GetMapping("/course/show/{num}")
	public ResponseEntity<?> courseShow(@PathVariable int num){
		System.out.println("courseShow 실행" + num);
		return new ResponseEntity<>(courseService.코스보여주기(num), HttpStatus.OK);
	}
	
	@GetMapping("/course/listshow")
	public ResponseEntity<?> listShow(){
		return new ResponseEntity<>(courseService.코스리스트보여주기(), HttpStatus.OK);
	}
	@GetMapping("/course/newestlistshow")
	public ResponseEntity<?> newestlistshow(){
		return new ResponseEntity<>(courseService.코스최신리스트보여주기(), HttpStatus.OK);
	}
	
	@PostMapping("/course/foodlist")
	public ResponseEntity<?> foodlist(@RequestBody String name){
		System.out.println("foodlist 실행");
		return new ResponseEntity<>(courseService.코스음식점리스트보여주기(name), HttpStatus.OK);
	}
	
	@PostMapping("/course/Houselist")
	public ResponseEntity<?> Houselist(@RequestBody String name){
		System.out.println("Houselist 실행");
		return new ResponseEntity<>(courseService.코스숙박리스트보여주기(name), HttpStatus.OK);
	}
	
	@PostMapping("/course/likeCourse")
	public ResponseEntity<?> setLikeCourse(Authentication authentication, @RequestBody int Course_num){
		PrincipalDetail principal = (PrincipalDetail) authentication.getPrincipal();
		System.out.println("setLikeCourse 실행 ");
		return new ResponseEntity<>(courseService.좋아하는장소찜및삭제(principal , Course_num), HttpStatus.OK);
	}
	
	@GetMapping("/course/newestMyCourse")
	public ResponseEntity<?> newestMyCourse(Authentication authentication){
		PrincipalDetail principal = (PrincipalDetail) authentication.getPrincipal();
		System.out.println("newestMyCourse 실행");
		return new ResponseEntity<>(courseService.추천받은최신장소가져오기(principal), HttpStatus.OK);
	}
	
	@PostMapping("/course/deleteCourse")
	public ResponseEntity<?> deleteCourse(@RequestBody int course_num){
		System.out.println("deleteCourse 실행");
		return new ResponseEntity<>(courseService.코스삭제(course_num), HttpStatus.OK);
	}
	
	
}
