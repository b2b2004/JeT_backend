package com.travelrec.project.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.github.pagehelper.PageInfo;
import com.travelrec.project.dto.JejuDataDto;

@Mapper
public interface JejuDataMapper {

	List<JejuDataDto> findByAreaTravel(int pageNo);
	JejuDataDto findByJejuDataNo(int num);
	
}
