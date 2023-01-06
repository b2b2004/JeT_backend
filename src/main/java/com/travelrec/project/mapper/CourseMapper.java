package com.travelrec.project.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.travelrec.project.dto.CourseDto;
import com.travelrec.project.dto.FoodDto;
import com.travelrec.project.dto.HouseDto;
import com.travelrec.project.dto.LikeCourseDto;
import com.travelrec.project.dto.NeighborDto;

@Mapper
public interface CourseMapper {
	Map<String, Object> findbycoursenum(int num);
	List<Map<String, Object>> selectcourseList();
	List<Map<String, Object>> selectNewestcourseList();
	NeighborDto selectFoodHouseList(String name);
	FoodDto selectFood(int num);
	HouseDto selectHouse(int num);
	int setlookup_num(CourseDto course);
	CourseDto findbyCourse_num(int Course_num);
	int insertLikeCourse(LikeCourseDto likecourse);
	int deleteLikeCoure(LikeCourseDto likecourse);
	LikeCourseDto selectOneLikeCourse(LikeCourseDto likecourse);
	int setLike(CourseDto course);
	List<LikeCourseDto> selectLikeCourse(String userId);
	CourseDto selectnewestMyCourse(String userId);
	int deleteCourse(int course_num);
	
}
