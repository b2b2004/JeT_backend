package com.travelrec.project.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.travelrec.project.config.auth.PrincipalDetail;
import com.travelrec.project.dto.PlaceReviewDto;
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
	
	@PostMapping("/board/issue")
	public ResponseEntity<?> issue(@RequestBody int pageNo){
		System.out.println("이슈 관광지 추천 controller");
		return new ResponseEntity<>(travelService.이슈관광지(pageNo),HttpStatus.OK);
	}
	
	@PostMapping("/board/JejuPlace/information/{num}")
	public ResponseEntity<?> jejuPlace(@PathVariable int num){
		System.out.println("관광지 정보 전송 controller");
		return new ResponseEntity<>(travelService.관광지(num),HttpStatus.OK);
	}
	
	@PostMapping("/board/JejuPlace/name")
	public ResponseEntity<?> jejuPlaceName(@RequestBody String name){
		System.out.println("관광지 정보 전송 controller");
		return new ResponseEntity<>(travelService.코스관광지(name),HttpStatus.OK);
	}
	
	@PostMapping("/board/JejuPlace/review")
	public ResponseEntity<?> jejuPlaceReview(Authentication authentication, @RequestBody  PlaceReviewDto data){
		System.out.println("관광지 리뷰 controller");
		PrincipalDetail principal = (PrincipalDetail) authentication.getPrincipal();
		String userId = principal.getUsername();
		String username = principal.getName();
		System.out.println(username);
		return new ResponseEntity<>(travelService.관광지리뷰등록(data, userId,username),HttpStatus.OK);
	}
	
	@GetMapping("/board/JejuPlace/review/{num}")
	public ResponseEntity<?> jejuPlaceReview(@PathVariable int num){
		System.out.println("관광지 리뷰 불러오기 controller");
		return new ResponseEntity<>(travelService.리뷰불러오기(num),HttpStatus.OK);
	}
	
	@PostMapping("/board/JejuPlace/likereview")
	public ResponseEntity<?> setLikePlacereview(Authentication authentication, @RequestBody int review_num){
		PrincipalDetail principal = (PrincipalDetail) authentication.getPrincipal();
		System.out.println("setLikePlace 실행 :");
		return new ResponseEntity<>(travelService.관광지리뷰좋아요등록(principal , review_num), HttpStatus.OK);
	}
	
	@GetMapping("/board/JejuPlace/likereview")
	public ResponseEntity<?> getLikePlacereview(Authentication authentication){
		PrincipalDetail principal = (PrincipalDetail) authentication.getPrincipal();
		System.out.println("리뷰 좋아요 가져오기");
		return new ResponseEntity<>(travelService.관광지리뷰좋아요가져오기(principal), HttpStatus.OK);
	}
	
}

