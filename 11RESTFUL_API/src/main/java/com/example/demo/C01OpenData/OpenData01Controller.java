package com.example.demo.C01OpenData;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Controller
@Slf4j
@RequestMapping("/openData")
public class OpenData01Controller {
    String url = "https://apis.data.go.kr/6270000/service/rest/dgincident";
    String serviceKey = "EOImiHluKlz27GGeiVTIRbHOfweO6OibvSIUxJlTJiMhrz2wg4dnkzSf1ZwWzkRGyB7IjKH0aR8SJJ6UUYQy8g==";
    String pageNo = "1";
    String numOfRows = "10";

    @GetMapping("/unexpected")
    public void unexpected(Model model){
        url += "?serviceKey=" + serviceKey;
        url += "&pageNo=" + pageNo;
        url += "&numOfRows=" +numOfRows;
        // 01 서버 요청 정보 확인(URL /KEY / etc Parameter)

        // 02 요청 헤더 설정

        // 03 요청 바디 설정

        // 04 요청작업 이후 결과확인
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Root> response =
                restTemplate.exchange(url, HttpMethod.GET, null, Root.class);
        System.out.println(response);

        // 05 기타 가공처리
        Root root = response.getBody();
        Body body = root.getBody();
        Items items = body.getItems();
        List<Item> list = items.getItem();
        list.stream().forEach(System.out::println);
        model.addAttribute("list", list);
       }

    // ------------------------------
    // import com.fasterxml.jackson.databind.ObjectMapper; // version 2.11.1
    // import com.fasterxml.jackson.annotation.JsonProperty; // version 2.11.1
    /* ObjectMapper om = new ObjectMapper();
    Root root = om.readValue(myJsonString, Root.class); */
    @Data
    private static class Body{
        public Items items;
        public String numOfRows;
        public String pageNo;
        public String totalCount;
    }

    @Data
    private static class Header{
        public String resultCode;
        public String resultMsg;
    }

    @Data
    private static class Item{
        @JsonProperty("LOCATION")
        public String lOCATION;
        @JsonProperty("INCIDENTTITLE")
        public String iNCIDENTTITLE;
        @JsonProperty("LOGDATE")
        public String lOGDATE;
        @JsonProperty("TROUBLEGRADE")
        public String tROUBLEGRADE;
        @JsonProperty("STARTDATE")
        public String sTARTDATE;
        @JsonProperty("INCIDENTSUBCODE")
        public String iNCIDENTSUBCODE;
        @JsonProperty("LINKID")
        public String lINKID;
        @JsonProperty("REPORTDATE")
        public String rEPORTDATE;
        @JsonProperty("ENDDATE")
        public String eNDDATE;
        @JsonProperty("COORDX")
        public double cOORDX;
        @JsonProperty("INCIDENTCODE")
        public String iNCIDENTCODE;
        @JsonProperty("INCIDENTID")
        public String iNCIDENTID;
        @JsonProperty("COORDY")
        public double cOORDY;
        @JsonProperty("TRAFFICGRADE")
        public String tRAFFICGRADE;
    }

    @Data
    private static class Items{
        public ArrayList<Item> item;
    }

    @Data
    private static class Root{
        public Body body;
        public Header header;
    }

    // ------------------------------
}
