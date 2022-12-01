package com.travelrec.project.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.travelrec.project.dto.UserDto;

@Mapper
public interface UserMapper {
    int insert(UserDto user);
    int update(UserDto user);
    UserDto findByUserId(String userId);
    int updatePw(String userId, String password);
}
