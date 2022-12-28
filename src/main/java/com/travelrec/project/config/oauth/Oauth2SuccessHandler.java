package com.travelrec.project.config.oauth;

import java.io.IOException;
import java.util.Date;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.travelrec.project.config.auth.PrincipalDetail;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class Oauth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        PrincipalDetail principalDetail = (PrincipalDetail) authentication.getPrincipal();
        String username = principalDetail.getUsername();
        String password = principalDetail.getPassword();

        //HMAC Hash암호 방식
        String jwtToken = JWT.create()
                .withSubject(username)
                .withExpiresAt(new Date(System.currentTimeMillis()+(600000*10))) //만료시간 10분 60000*10
                .withClaim("username",username)
                .withClaim("password", password)
                .sign(Algorithm.HMAC512("Jet"));

        System.out.println(jwtToken);
        response.setHeader("Authorization", "Bearer "+jwtToken);
        response.setContentType("application/json; charset=utf-8");
        String url = makeRedirectUrl("Bearer "+jwtToken);
        getRedirectStrategy().sendRedirect(request, response, url);
    }
    
    private String makeRedirectUrl(String token) {
        return UriComponentsBuilder.fromUriString("http://localhost:3000/Oauth2Login/"+token)
                .build().toUriString();
    }


}
