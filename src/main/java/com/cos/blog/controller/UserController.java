package com.cos.blog.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

@Controller
public class UserController {
    @GetMapping("/auth/joinform")
    public String joinForm(){
        return "user/joinForm";
    }

    @GetMapping("/auth/loginform")
    public String loginForm(){
        return "user/loginForm";
    }

    @GetMapping("/user/updateform")
    public String updateForm(){
        return "user/updateForm";
    }

    @GetMapping("/auth/kakao/callback")
    public @ResponseBody String kakaoCallback(String code){// Data를 리턴해주는 컨트롤러 함수
        // POST 방식으로 key=value 데이터를 요청
        // grant_type	String	authorization_code로 고정
        // client_id	String	앱 REST API 키
        // redirect_uri	String	인가 코드가 리다이렉트된 URI
        // code	String	인가 코드 받기 요청으로 얻은 인가 코드

        // Retrofit2
        // OkHttp
        // RestTemplate 가 있따.
        
        RestTemplate rt = new RestTemplate();
        
        // Header 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
        
        // Body 생성
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", "7bf0bbb96ccb254d3033bf173c4f3561");
        params.add("redirect_uri", "http://localhost:8000/auth/kakao/callback");
        params.add("code", code);
        
        // Header와 Body를 하나의 오브젝트에 담기
        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest =
                new HttpEntity<>(params, headers);

        ResponseEntity<String> response = rt.exchange(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                kakaoTokenRequest,
                String.class
        );

        return "카카오 토큰 요청 완료:  " + response;
    }
}
