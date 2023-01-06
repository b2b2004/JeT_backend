package com.travelrec.project.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.github.pagehelper.PageInfo;
import com.travelrec.project.dto.JejuDataDto;
import com.travelrec.project.dto.PlaceReviewDto;
import com.travelrec.project.dto.PlaceReviewLikeDto;

@Mapper
public interface JejuDataMapper {

	List<JejuDataDto> findByAreaTravel(int pageNo);
	List<JejuDataDto> findByissue(int pageNo);
	JejuDataDto findByJejuDataNo(int num);
	JejuDataDto findbyname(String name);
	int setReviewData(PlaceReviewDto review);
	List<PlaceReviewDto> findByJeJuPlaceReview(int num);
	int setLookup(JejuDataDto Jeju);
	int setLike(JejuDataDto Jeju);
	
	int insertReviewLike(PlaceReviewLikeDto PRL);
	int setReviewLikenum(PlaceReviewDto PR); 
	int deleteReviewLike(PlaceReviewLikeDto PRL);
	PlaceReviewLikeDto selectOneLikePlaceReview(PlaceReviewLikeDto PRL);
	PlaceReviewDto findByReviewNum(int review_num);
	List<PlaceReviewLikeDto> selectPlaceReviewLike(String userId);
	
	int deleteReview(int review_num);
}
