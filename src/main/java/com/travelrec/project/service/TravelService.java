package com.travelrec.project.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.travelrec.project.config.auth.PrincipalDetail;
import com.travelrec.project.dto.JejuDataDto;
import com.travelrec.project.dto.LikePlaceDto;
import com.travelrec.project.dto.PlaceReviewDto;
import com.travelrec.project.dto.PlaceReviewLikeDto;
import com.travelrec.project.mapper.JejuDataMapper;

@Service
public class TravelService {
	
	
	@Autowired
	private JejuDataMapper jejuDataMapper;
	
	public List<JejuDataDto> 유명관광지(int pageNo) {
		List<JejuDataDto> JDD=null;
		JDD = jejuDataMapper.findByAreaTravel(pageNo);
		System.out.println(JDD);
		return JDD;
	}
	
	public List<JejuDataDto> 이슈관광지(int pageNo) {
		List<JejuDataDto> JDD=null;
		JDD = jejuDataMapper.findByissue(pageNo);
		System.out.println(JDD);
		return JDD;
	}
	
	public JejuDataDto 관광지(int num) {
		int update =0;
		JejuDataDto Jeju = jejuDataMapper.findByJejuDataNo(num);
		int lookup = 0;
		lookup = Jeju.getReal_lookup_num();
		lookup++;
		Jeju.setReal_lookup_num(lookup);
		update = jejuDataMapper.setLookup(Jeju);
		return Jeju;
	}
	
	public JejuDataDto 코스관광지(String name) {
		JejuDataDto Jeju = jejuDataMapper.findbyname(name);
		return Jeju;
	}
	
	public int 관광지리뷰등록(PlaceReviewDto review, String userId, String username) {
		int insert = 0;
		LocalDate nowDate = LocalDate.now();
		LocalTime nowTime = LocalTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH-mm-ss");
		String formatedTimeNow = nowTime.format(formatter);
		String date = nowDate + " " + formatedTimeNow;
		review.setNowDate(date);
		review.setUserId(userId);
		review.setUsername(username);
		insert = jejuDataMapper.setReviewData(review);
		
		if(insert > 0)
		{
			return 1; // 로그인 성공
		}else {
			if(userId == null)
			{
				return 3; // 사용자 아이디 없음
			}
			
			return 2; // 로그인 실패
		}
	}
	
	public List<PlaceReviewDto> 리뷰불러오기(int num) {
		List<PlaceReviewDto> reviewList = null;
		reviewList = jejuDataMapper.findByJeJuPlaceReview(num);
		return reviewList;
	}
	
	 public String 관광지리뷰좋아요등록(PrincipalDetail principalDetail,  int review_num){
		 int insert = 0;
		 int delete = 0;
		 int like_num= 0;
		 
		 // userId reveiw_num
		 PlaceReviewLikeDto ck = new PlaceReviewLikeDto();
		 PlaceReviewLikeDto PRL =  new PlaceReviewLikeDto();
		 PlaceReviewDto PR = null;
		 String userId = principalDetail.getUser().getUserId();
		 
		 PR = jejuDataMapper.findByReviewNum(review_num);
		 like_num = PR.getReview_like_num();
		 PRL.setUserId(userId);
		 PRL.setReviewNo(review_num);
		 PR.setReview_num(review_num);
		 
		 ck = jejuDataMapper.selectOneLikePlaceReview(PRL);
		 if(ck == null) // 리뷰 좋아요가 없을시
		 {
			 insert = jejuDataMapper.insertReviewLike(PRL);
			 if(insert >0) {
				 like_num++;
				 PR.setReview_like_num(like_num);
				 jejuDataMapper.setReviewLikenum(PR);
				 return "좋아요 등록 성공";
			 }else {
				 return "좋아요 등록 실패";
			 }
			 
		 }else {
			 delete = jejuDataMapper.deleteReviewLike(PRL);
			 if(delete > 0)
			 {
				 like_num--;
				 PR.setReview_like_num(like_num);
				 jejuDataMapper.setReviewLikenum(PR);
				 return "좋아요 삭제 성공";
			 }else {
				 return "좋아요 삭제 실패";
			 }
		 }
	 }
	 
	 public List<?> 관광지리뷰좋아요가져오기(PrincipalDetail principalDetail){
		 String userId = principalDetail.getUser().getUserId();
		 List<PlaceReviewLikeDto> PlaceReviewLike = jejuDataMapper.selectPlaceReviewLike(userId);
		 return PlaceReviewLike;
	 }
	 
	 public int 리뷰삭제하기(int review_num) {
		 int delete = 0;
		 System.out.println("hi");
		 delete = jejuDataMapper.deleteReview(review_num);
		 if(delete > 0) {
			 return 1;
		 }else {
			 return 2;
		 }
	 }
}
