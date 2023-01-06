package com.travelrec.project.dto;

import lombok.Data;

@Data
public class JejuDataDto {
	private int JejuDataNo;
	private String name;
	private String tags;	
	private String img_src;	
	private String address;
	private double Latitude;	
	private double Longitude;
	private String phone;
	private int like_num;
	private int review_num;
	private String lookup_num;	
	private int real_lookup_num;
	private int real_like_num;
	private int share_num;
	private String moreinfo;	
	private String detail_img;	
	private String introduce;	
	private String detail_info;	
	private String usage_time;	
	private String rate_info;	
	private String place;	
	private String main_purpose;	
	private String etc_purpose;	
	private String avg_time;	
	private String difficulty;	
	private String Facilities;	
	private String weekday_open;
	private String weekday_close;	
	private String weekend_open;	
	private String weekend_close;	
	private String adult_price;
	private String teenager_price;
	private String children_price;
	private String tmp_tags;
	private Double time_sum;
}
