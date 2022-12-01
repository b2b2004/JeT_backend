package com.travelrec.project.dto;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Builder;
import lombok.Data;

@Data
public class UserDto {
    private int userNo;
    private String userId;
    private String password;
    private String email;
    private String username;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private Date signup;

    
    public UserDto() {}
    @Builder
    public UserDto(String userId, String username, String password, String email,Date signup) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.email = email;
        this.signup = signup;
    }
}
