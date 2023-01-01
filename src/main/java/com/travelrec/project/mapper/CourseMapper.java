package com.travelrec.project.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.travelrec.project.dto.CourseDto;

@Mapper
public interface CourseMapper {
	Map<String, Object> findbycoursenum(int num);
	List<Map<String, Object>> selectcourseList();
	
}
