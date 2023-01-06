package com.travelrec.project.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.travelrec.project.dto.LikePlaceDto;
import com.travelrec.project.dto.SurveyDto;
import com.travelrec.project.dto.UserDto;

@Mapper
public interface UserMapper {
    int insert(UserDto user);
    int update(UserDto user);
    UserDto findByUserId(String userId);
    int updatePw(String userId, String password);
    int chagePw(UserDto user);
    int deleteId(String userId);
    int updateUser(UserDto user);
    int insertLikePlace(LikePlaceDto likePlaceDto);
    int deleteLikePlace(LikePlaceDto likePlaceDto);
    List<LikePlaceDto> selectLikePlace(String userId);
    LikePlaceDto selectOneLikePlace(LikePlaceDto likePlaceDto);
    int insertSurvey(SurveyDto survey);
    int updateSurvey(SurveyDto survey);
    SurveyDto getSurvey(String userId);
    List<Map<String, Object>> selectLikePlaceMypage(String userId);
}
