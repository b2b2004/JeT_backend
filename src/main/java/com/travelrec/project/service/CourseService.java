package com.travelrec.project.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.travelrec.project.config.auth.PrincipalDetail;
import com.travelrec.project.dto.CourseDto;
import com.travelrec.project.dto.FoodDto;
import com.travelrec.project.dto.HouseDto;
import com.travelrec.project.dto.JejuDataDto;
import com.travelrec.project.dto.LikeCourseDto;
import com.travelrec.project.dto.LikePlaceDto;
import com.travelrec.project.dto.NeighborDto;
import com.travelrec.project.mapper.CourseMapper;

@Service
public class CourseService {

	@Autowired
	private  CourseMapper courseMapper;
	
	public Map<String, Object> 코스보여주기(int num) {
		Map<String, Object> course = courseMapper.findbycoursenum(num);
		
		ArrayList<Object> Longitude = new ArrayList<>();
		ArrayList<Object> Latitude = new ArrayList<>();
		ArrayList<Object> placeNo = new ArrayList<>();
		ArrayList<Object> name = new ArrayList<>();
		Map<String, Object> cr = new HashMap<String, Object>();
		
		// 코스 조회수 올리기
		CourseDto updateCourselookup = new CourseDto();
		int course_num = (int) course.get("course_num");
		updateCourselookup.setCourse_num(course_num);
		int update = 0;
		int lookup = 0;
		System.out.println(course.get("lookup_num"));
		if(course.get("lookup_num") == null)
		{
			lookup = 0;
		}else {
			lookup = (int) course.get("lookup_num");
			cr.put("lookup_num",  course.get("lookup_num"));
		}
		lookup++;
		updateCourselookup.setLookup_num(lookup);
		update = courseMapper.setlookup_num(updateCourselookup);
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
		cr.put("course_num",  course.get("course_num"));
		cr.put("like_num",  course.get("like_num"));

		return cr; 
	}
	
	public List<Map<String, Object>> 코스리스트보여주기(){
		List<Map<String, Object>> course = courseMapper.selectcourseList();
		System.out.println(course);
		return course;
	}
	
	public List<Map<String, Object>> 코스최신리스트보여주기(){
		List<Map<String, Object>> course = courseMapper.selectNewestcourseList();
		System.out.println(course);
		return course;
	}
	
	public List<?> 코스음식점리스트보여주기(String name){
		NeighborDto neighbor = courseMapper.selectFoodHouseList(name);
		String emp_food_1 = "";
		String[] emp_food_2 = null;
		List<Object> FoodList = new ArrayList<>();

		if(neighbor != null)
		{
			String emp = neighbor.getNeighbor_foodNo();
			if(!emp.equals("0"))
			{
				emp_food_1 = emp.substring(1, emp.length()-1);
				emp_food_2 = emp_food_1.split(", ");
				for(String foods : emp_food_2)
				{
					int a = Integer.parseInt(foods);
					FoodDto food = courseMapper.selectFood(a);
					if(food != null) {
						FoodList.add(food);
					}
				}
				System.out.println("================="+FoodList);
			}
		}
		System.out.println("음식점");
		return FoodList;
	}
	
	
	public List<?> 코스숙박리스트보여주기(String name){
		NeighborDto neighbor = courseMapper.selectFoodHouseList(name);
		String emp_house_1 = "";
		String[] emp_house_2 = null;
		List<Object> HouseList = new ArrayList<>();
		if(neighbor != null)
		{
		String emp = neighbor.getNeighbor_accommondationNo();
		if(!emp.equals("0"))
		{
			emp_house_1 = emp.substring(1, emp.length()-1);
			emp_house_2 = emp_house_1.split(", ");
			for(String houses : emp_house_2)
			{
				int a = Integer.parseInt(houses);
				HouseDto house = courseMapper.selectHouse(a);
				if(house != null) {
					HouseList.add(house);
				}
			}
			System.out.println("================="+HouseList);
		}
		}
		return HouseList;
	}
	
	public String 좋아하는장소찜및삭제(PrincipalDetail principal, int course_num) {
		 int insert = 0;
		 int delete = 0;
		 int update = 0;
		 int like_num= 0;
		 
		 LikeCourseDto ck = new LikeCourseDto();
		 LikeCourseDto likeCourseDto = new LikeCourseDto();
		 CourseDto course = null;
		 String userId = principal.getUser().getUserId();
		 
		 course = courseMapper.findbyCourse_num(course_num);
		 like_num = course.getLike_num();
		 likeCourseDto.setUserId(userId);
		 likeCourseDto.setCourse_num(course_num);
		 course.setCourse_num(course_num);
		 ck = courseMapper.selectOneLikeCourse(likeCourseDto);
		 if(ck == null) // 좋아요 한게 없을시
		 {
			 insert = courseMapper.insertLikeCourse(likeCourseDto);
			 if(insert > 0)
			 {
				 like_num++;
				 course.setLike_num(like_num);
				 update = courseMapper.setLike(course);
				 return "좋아요 등록 성공";
			 }else {
				 return "코스 좋아요 등록 성공";
			 }
		 }else {
			 delete = courseMapper.deleteLikeCoure(likeCourseDto);
			 if(delete > 0)
			 {
				 like_num--;
				 course.setLike_num(like_num);
				 courseMapper.setLike(course);
				 return "관심 목록 삭제 완료";
			 }else {
				 return "관심 목록 삭제 실패";
			 }
		 }
	}
	
	 public List<?> 좋아하는장소가져오기(PrincipalDetail principalDetail){
		 String userId = principalDetail.getUser().getUserId();
		 List<LikeCourseDto> likeCourse = courseMapper.selectLikeCourse(userId);
		 return likeCourse;
	 }
	 
	 public int 추천받은최신장소가져오기(PrincipalDetail principalDetail){
		 String userId = principalDetail.getUser().getUserId();
		 CourseDto course = null;
		 course = courseMapper.selectnewestMyCourse(userId);
		 if(course == null)
		 {
			 return 0;
		 }else {
				int course_num = course.getCourse_num();
				System.out.println(course_num);
				 return course_num; 
		 }
	 }
	 
	 public int 코스삭제(int course_num) {
		 int delete = 0;
		 delete = courseMapper.deleteCourse(course_num);
		 if(delete > 0) {
			 return 1;
		 }else {
			 return 2;
		 }
	 }
}
