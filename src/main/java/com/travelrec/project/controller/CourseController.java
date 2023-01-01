package com.travelrec.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

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
	
}
