package com.travelrec.project.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.travelrec.project.dto.CourseDto;
import com.travelrec.project.mapper.CourseMapper;

@Service
public class CourseService {

	@Autowired
	private  CourseMapper courseMapper;
	
	public Map<String, Object> 코스보여주기(int num) {
		Map<String, Object> course = courseMapper.findbycoursenum(num);
		System.out.println(course);
		
		ArrayList<Object> Longitude = new ArrayList<>();
		ArrayList<Object> Latitude = new ArrayList<>();
		ArrayList<Object> placeNo = new ArrayList<>();
		ArrayList<Object> name = new ArrayList<>();
		Map<String, Object> cr = new HashMap<String, Object>();
		for(int i=0;i<7;i++)
		{
			Double ck = (Double) course.get("Longitude"+(i+1));
			if(!(ck == null))
			{
				name.add(course.get("name"+(i+1)));
				placeNo.add(course.get("place"+(i+1)));
				Longitude.add(course.get("Longitude"+(i+1)));
				Latitude.add(course.get("Latitude"+(i+1)));
			}
		} 
		cr.put("Longitude", Longitude);
		cr.put("Latitude", Latitude);
		cr.put("placeNo", placeNo);
		cr.put("name", name);
		cr.put("title",  course.get("title"));
		cr.put("content",  course.get("content"));
		cr.put("userId",  course.get("userId"));
		System.out.println(cr);

		return cr; 
	}
	
	public List<Map<String, Object>> 코스리스트보여주기(){
		List<Map<String, Object>> course = courseMapper.selectcourseList();
		System.out.println(course);
		return course;
	}
}
