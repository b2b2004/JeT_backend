package com.travelrec.project.filter;

import java.io.IOException;
import java.util.Date;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.travelrec.project.config.auth.PrincipalDetail;
import com.travelrec.project.dto.UserDto;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JwtAuthentication extends UsernamePasswordAuthenticationFilter {

private final AuthenticationManager authenticationManager;

    // login 요청을 하면 로그인 시도를 위해서 실행되는 함수
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

    	System.out.println("JwtAuthentication 실행");
        try{
            ObjectMapper om = new ObjectMapper();
            UserDto user = om.readValue(request.getInputStream(), UserDto.class);
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(user.getUserId(), user.getPassword());

            Authentication authentication = authenticationManager.authenticate(authenticationToken);
            //authentication 객체가 session 영역에 저장을 해야하고 그 방법이 return 해 주는것
            System.out.println(authentication);
            return authentication;

        }catch (IOException e){
            e.printStackTrace();
        }

        return null;
    }

    //attemptAuthentication 실행 후 인증이 정상적으로 되었으면 successfulAuthentication 함수가 실행됨
    //JWT 토큰을 만들어서 request 요청한 사용자에게 JWT 토큰을 response 해주면 됨.
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain chain, Authentication authResult) throws IOException, ServletException {
        PrincipalDetail principalDetail = (PrincipalDetail) authResult.getPrincipal();
        //HMAC Hash암호 방식
        String jwtToken = JWT.create()
                .withSubject(principalDetail.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis()+(60000*10))) //만료시간 10분
                .withClaim("username", principalDetail.getUsername())
                .withClaim("password", principalDetail.getPassword())
                .sign(Algorithm.HMAC512("Jet"));
          response.setHeader("Authorization", "Bearer "+jwtToken);
          response.setContentType("application/json; charset=utf-8");
    }
}
