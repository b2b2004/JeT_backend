package com.travelrec.project.dto;

import lombok.Data;

@Data
public class HouseDto {
	private int accommodation_id;
	private String name;
	private String type;
	private String img_src;
	private String address;
	private String service;
	private String guide;
	private double rating;
	private String phone;
	private double latitude;
	private double longitude;
}
