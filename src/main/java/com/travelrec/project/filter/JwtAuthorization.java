package com.travelrec.project.filter;

import java.io.IOException;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.travelrec.project.config.auth.PrincipalDetail;
import com.travelrec.project.dto.UserDto;
import com.travelrec.project.mapper.UserMapper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

//시큐리티가 Filter 가지고 있는데 필터 중 BasicAuthenticationFilter 라는 것이 있음
//권한이나 인증이 필요한 특정 주소를 요청했을 때 위 필터를 무조건 타게 되어있음
//만약에 권한이 인증이 필요한 주소가 아니라면 이 필터를 타지 않음.

public class JwtAuthorization extends BasicAuthenticationFilter {

 private UserMapper userMapper;

 public JwtAuthorization(AuthenticationManager authenticationManager , UserMapper userMapper) {
     super(authenticationManager);
     this.userMapper = userMapper;

 }

 //인증이나 권한이 필요한 주소요청이 있을 때 해당 필터를 타게 됨
 @Override
 protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
     String jwtHeader = request.getHeader("Authorization");
 	System.out.println("JwtAuthorization 실행");
     // header가 있는지 확인
     if(jwtHeader == null || !jwtHeader.startsWith(("Bearer"))){
         chain.doFilter(request,response);
         return;
     }
     String jwtToken = request.getHeader("Authorization").replace("Bearer ","");
     String username = JWT.require(Algorithm.HMAC512("Jet")).build().verify(jwtToken)
             .getClaim("username").asString();
     if(username != null){
         UserDto userEntity = userMapper.findByUserId(username);
         PrincipalDetail principalDetail = new PrincipalDetail(userEntity);
         //JWT토큰 서명을 통해서 서명이 정상이면 Authentication 객체를 만들어준다
         Authentication authentication = new UsernamePasswordAuthenticationToken(principalDetail,null, principalDetail.getAuthorities());
         //강제로 시큐리티의 세션에 접근하여 Authentication 객체를 저장
         SecurityContextHolder.getContext().setAuthentication(authentication);
         chain.doFilter(request,response);
     }
 }

}
