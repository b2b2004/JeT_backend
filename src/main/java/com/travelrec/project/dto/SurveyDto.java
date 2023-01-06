package com.travelrec.project.dto;

import lombok.Data;

@Data
public class SurveyDto {
	private String userId;
	private int survey_num;
	private int answer1;
	private int answer2;
	private int answer3;
	private int answer4;
	private int tendency_result;
}
