package com.travelrec.project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.travelrec.project.dto.JejuDataDto;
import com.travelrec.project.mapper.JejuDataMapper;

@Service
public class TravelService {
	
	
	@Autowired
	private JejuDataMapper jejuDataMapper;
	
	public List<JejuDataDto> 유명관광지(int pageNo) {
		List<JejuDataDto> JDD=null; //네비게이션이 포함된 페이징 정보
		JDD = jejuDataMapper.findByAreaTravel(pageNo);
		System.out.println(JDD);
		return JDD;
	}
	
	public JejuDataDto 관광지(int num) {
		JejuDataDto Jeju = jejuDataMapper.findByJejuDataNo(num);
		return Jeju;
	}
	
	
}
