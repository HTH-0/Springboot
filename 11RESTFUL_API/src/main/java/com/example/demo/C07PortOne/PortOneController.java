package com.example.demo.C07PortOne;

import com.example.demo.C04Kakao.C02KakaoLoginController;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

@Controller
@Slf4j
@RequestMapping("/portOne")
public class PortOneController {

    String RESTAPI_KEY = "";
    String RESTAPI_SECRET  = "";
    private PortOneTokenResponse portOneTokenResponse;

    @GetMapping("/index")
    public void index(){
        log.info("GET /portOne/index...");
    }
    @GetMapping("/getToken")
    @ResponseBody
    public void getToken(){
        log.info("GET /portOne/getToken....");
        //요청 정보 확인
        String url = "https://api.iamport.kr/users/getToken";
        //요청 헤더 설정
        HttpHeaders header = new HttpHeaders();
//        header.add("Content-Type","application/json"); //form x
        //요청 바디 설정
        MultiValueMap<String,String> params = new LinkedMultiValueMap<>();
        params.add("imp_key",RESTAPI_KEY);
        params.add("imp_secret",RESTAPI_SECRET);

        HttpEntity< MultiValueMap<String,String> > entity = new HttpEntity<>(params,header);

        //요청 후 응답확인
        RestTemplate rt = new RestTemplate();
        ResponseEntity<PortOneTokenResponse> response =
                rt.exchange(url, HttpMethod.POST,entity, PortOneTokenResponse.class);
        System.out.println(response.getBody());
        this.portOneTokenResponse =response.getBody();

    }
    //결제조회(다건조회)
    @GetMapping("/getPayments")
    @ResponseBody
    public void payments(){
        log.info("GET /portOne/getPayments...");
    }

    //결제취소

    //인증조회
    @Data
    private static class TokenData{
        public String access_token;
        public int now;
        public int expired_at;
    }
    @Data
    private static class PortOneTokenResponse{
        public int code;
        public Object message;
        public TokenData response;
    }
}
