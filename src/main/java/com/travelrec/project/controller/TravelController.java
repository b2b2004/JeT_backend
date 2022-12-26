package com.travelrec.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.travelrec.project.service.TravelService;

@Controller
@RestController
public class TravelController {
	
	@Autowired
	private TravelService travelService;
	
	@PostMapping("/board/areaTravel")
	public ResponseEntity<?> areaTravel(@RequestBody int pageNo){
		System.out.println("유명 관광지 추천 controller");
		return new ResponseEntity<>(travelService.유명관광지(pageNo),HttpStatus.OK);
	}
	
	@PostMapping("/board/JejuPlace/{num}")
	public ResponseEntity<?> jejuPlace(@PathVariable int num){
		System.out.println("관광지 정보 전송 controller");
		return new ResponseEntity<>(travelService.관광지(num),HttpStatus.OK);
	}
}

