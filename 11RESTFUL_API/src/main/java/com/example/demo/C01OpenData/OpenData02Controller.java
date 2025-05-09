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
public class OpenData02Controller {
    String url = "https://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getUltraSrtNcst";
    String serviceKey = "EOImiHluKlz27GGeiVTIRbHOfweO6OibvSIUxJlTJiMhrz2wg4dnkzSf1ZwWzkRGyB7IjKH0aR8SJJ6UUYQy8g==";
    String pageNo = "1";
    String numOfRows = "10";
    String dataType = "JSON";
    String base_date = "20250509";
    String base_time = "1600";
    String nx = "89";
    String ny = "90";
    @GetMapping("/forecast")
    public void forecast(Model model) {
        url += "?serviceKey=" + serviceKey;
        url += "&pageNo=" + pageNo;
        url += "&numOfRows=" + numOfRows;
        url += "&dataType=" + dataType;
        url += "&base_date=" + base_date;
        url += "&base_time=" + base_time;
        url += "&nx=" + nx;
        url += "&ny=" + ny;
        // 01 서버 요청 정보 확인(URL /KEY / etc Parameter)

        // 02 요청 헤더 설정

        // 03 요청 바디 설정

        // 04 요청작업 이후 결과확인
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<OpenData02Controller.Root> response =
                restTemplate.exchange(url, HttpMethod.GET, null, OpenData02Controller.Root.class);
        System.out.println(response);

        // 05 기타 가공처리
        OpenData02Controller.Root root = response.getBody();
        OpenData02Controller.Response res = root.getResponse();
        OpenData02Controller.Body body = res.getBody();
        OpenData02Controller.Items items = body.getItems();
        List<OpenData02Controller.Item> list = items.getItem();
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
            public String dataType;
            public Items items;
            public int pageNo;
            public int numOfRows;
            public int totalCount;
        }
        @Data
        private static class Header{
            public String resultCode;
            public String resultMsg;
        }
        @Data
        private static class Item{
            public String baseDate;
            public String baseTime;
            public String category;
            public int nx;
            public int ny;
            public String obsrValue;
        }
        @Data
        private static class Items{
            public ArrayList<Item> item;
        }
        @Data
        private static class Response{
            public Header header;
            public Body body;
        }
        @Data
        private static class Root{
            public Response response;
        }


        // ------------------------------

}
