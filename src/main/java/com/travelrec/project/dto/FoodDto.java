package com.travelrec.project.dto;

import lombok.Data;

@Data
public class FoodDto {
	private int store_no;
	private String store_name;
	private double star;
	private String address;
	private String category;
	private String phone;
	private String main_menu;
	private String foodimg_src;
	private double latitude;	
	private double longitude;
}
