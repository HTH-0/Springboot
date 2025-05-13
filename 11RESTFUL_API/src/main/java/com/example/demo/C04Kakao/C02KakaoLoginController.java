package com.example.demo.C04Kakao;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import java.util.Date;

@Controller
@Slf4j
@RequestMapping("/kakao")
public class C02KakaoLoginController {

    String REDIRECT_URI="http://localhost:8090/kakao/callback";
    String CLIENT_ID="";
    String LOGOUT_REDIRECT_URI="http://localhost:8090/kakao/login";

    //
    KakaoTokenResponse kakaoTokenResponse ;
    KakaoProfileResponse kakaoProfileResponse;

    //카카오 로그인 STEP 1
    @GetMapping("/login")
//    @GetMapping("/getCode")
    public String getCode(){
        log.info("GET /kakao/getCode...");
        return "redirect:https://kauth.kakao.com/oauth/authorize?client_id="+CLIENT_ID+"&redirect_uri="+REDIRECT_URI+"&response_type=code";
    }
    //카카오 로그인 STEP 2
    @GetMapping("/callback")
    public String callback(@RequestParam("code") String code){
        log.info("GET /kakao/callback..." + code);
        //요청 정보 확인
        String url = "https://kauth.kakao.com/oauth/token";
        //요청 헤더 설정
        HttpHeaders header = new HttpHeaders();
        header.add("Content-Type","application/x-www-form-urlencoded;charset=utf-8");
        //요청 바디 설정
        MultiValueMap<String,String> params = new LinkedMultiValueMap<>();
        params.add("grant_type","authorization_code");
        params.add("client_id",CLIENT_ID);
        params.add("redirect_uri",REDIRECT_URI);
        params.add("code",code);

        HttpEntity< MultiValueMap<String,String> > entity = new HttpEntity<>(params,header);

        //요청 후 응답확인
        RestTemplate rt = new RestTemplate();
        ResponseEntity<KakaoTokenResponse> response =
        rt.exchange(url, HttpMethod.POST,entity, KakaoTokenResponse.class);

        System.out.println(response);
        this.kakaoTokenResponse = response.getBody();

        return "redirect:/kakao/main";

    }

    @GetMapping("/main")
    public void main(Model model){
        log.info("GET /kakao/main..");
        //정보확인
        String url="https://kapi.kakao.com/v2/user/me";
        //요청헤더
        //요청 헤더 설정
        HttpHeaders header = new HttpHeaders();
        header.add("Authorization","Bearer "+this.kakaoTokenResponse.getAccess_token());
        header.add("Content-Type","application/x-www-form-urlencoded;charset=utf-8");
        //요청바디

        //ENTITY
        HttpEntity entity = new HttpEntity<>(header);

        //요청->응답
        RestTemplate rt = new RestTemplate();
        ResponseEntity<KakaoProfileResponse> response =rt.exchange(url,HttpMethod.POST,entity,KakaoProfileResponse.class);
        System.out.println(response.getBody());
        this.kakaoProfileResponse = response.getBody();
        model.addAttribute("profile",this.kakaoProfileResponse);

    }

    @GetMapping("/logout")
    @ResponseBody
    public void logout(){
        log.info("GET /kakao/logout");
        //정보
        String url = "https://kapi.kakao.com/v1/user/logout";
        //요청헤더
        HttpHeaders header = new HttpHeaders();
        header.add("Authorization","Bearer "+this.kakaoTokenResponse.getAccess_token());
        //요청바디
        //ENTITY
        HttpEntity entity = new HttpEntity<>(header);

        //요청->응답
        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> response =rt.exchange(url,HttpMethod.POST,entity,String.class);
    }

    @GetMapping("/unlink")
    @ResponseBody
    public void unlink(){
        log.info("GET /kakao/unlink....");
        //정보
        String url ="https://kapi.kakao.com/v1/user/unlink";
        //요청헤더
        HttpHeaders header = new HttpHeaders();
        header.add("Authorization","Bearer "+this.kakaoTokenResponse.getAccess_token());

        //ENTITY
        HttpEntity entity = new HttpEntity<>(header);

        //요청->응답
        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> response =rt.exchange(url,HttpMethod.POST,entity,String.class);
    }

    @GetMapping("/logoutAll")
    public String logoutAll(){
        log.info("GET /kakao/logoutAll...");
        return "redirect:https://kauth.kakao.com/oauth/logout?client_id="+CLIENT_ID+"&logout_redirect_uri="+LOGOUT_REDIRECT_URI;
    }

    //------------------------
    //KakaoTokenResponse
    //------------------------
    @Data
    private static class KakaoTokenResponse{
        public String access_token;
        public String token_type;
        public String refresh_token;
        public int expires_in;
        public String scope;
        public int refresh_token_expires_in;
    }

    //------------------------
    //KakaoProfileResponse
    //------------------------
    @Data
    private static class KakaoAccount{
        public boolean profile_nickname_needs_agreement;
        public boolean profile_image_needs_agreement;
        public Profile profile;
        public boolean has_email;
        public boolean email_needs_agreement;
        public boolean is_email_valid;
        public boolean is_email_verified;
        public String email;
    }
    @Data
    private static class Profile{
        public String nickname;
        public String thumbnail_image_url;
        public String profile_image_url;
        public boolean is_default_image;
        public boolean is_default_nickname;
    }
    @Data
    private static class Properties{
        public String nickname;
        public String profile_image;
        public String thumbnail_image;
    }
    @Data
    private static class KakaoProfileResponse{
        public long id;
        public Date connected_at;
        public Properties properties;
        public KakaoAccount kakao_account;
    }



}
