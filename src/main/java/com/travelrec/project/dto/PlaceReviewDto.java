package com.travelrec.project.dto;

import lombok.Data;

@Data
public class PlaceReviewDto {

	public String userId;
	public int review_num;
	public int review_like_num;
	public int place_num;
	public String content;
	public String nowDate;
	public String username;
}
