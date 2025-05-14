package com.example.demo.C05naver;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

@Controller
@Slf4j
@RequestMapping("/naver/search")
public class NaverSearchController {

    private String NAVER_CLIENT_ID="uG4vxueY2kxWOTEkMobm";
    private String NAVER_CLIENT_SECRET="eoh4vZdtAz";

    @GetMapping("/book/{keyword}")
    @ResponseBody
    public void book(@PathVariable("keyword") String keyword){
        log.info("GET /naver/search/book...");
        String url = "https://openapi.naver.com/v1/search/book.json?query=" + keyword;
        HttpHeaders header = new HttpHeaders();
        header.add("X-Naver-Client-Id", NAVER_CLIENT_ID);
        header.add("X-Naver-Client-Secret", NAVER_CLIENT_SECRET);

        HttpEntity entity = new HttpEntity<>(header);

        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> response =rt.exchange(url, HttpMethod.GET,entity, String.class);
        System.out.println(response.getBody());
    }

}
