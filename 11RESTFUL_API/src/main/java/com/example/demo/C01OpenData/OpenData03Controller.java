package com.example.demo.C01OpenData;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.xml.transform.Result;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/openData")
public class OpenData03Controller {

    @GetMapping("/bus/realtime")
    public void bus_realtime() throws UnsupportedEncodingException, JsonProcessingException {
//        String url = "https://apis.data.go.kr/6270000/dbmsapi01/getRealtime";
        String serviceKey = "EOImiHluKlz27GGeiVTIRbHOfweO6OibvSIUxJlTJiMhrz2wg4dnkzSf1ZwWzkRGyB7IjKH0aR8SJJ6UUYQy8g==";
        String bsId = "7001001400";
        String routeNo = "609";

        String url = "https://apis.data.go.kr/6270000/dbmsapi01/getRealtime?"
                + "serviceKey=" + serviceKey
                + "&bsId=" + bsId
                + "&routeNo=" + routeNo;

        System.out.println(url);

        //요청 헤더(x)
        //요청 바디(x)
        //요청 후 응답값 받기
        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> response = rt.exchange(url, HttpMethod.GET, null, String.class);
        String xmlBody = response.getBody();

        System.out.println(response);

        //가공처리

        // Jackson XmlMapper 사용
        XmlMapper xmlMapper = new XmlMapper();
        Result result = xmlMapper.readValue(xmlBody, Result.class); // ✅ Root → Result

        // 데이터 가공 처리
        List<ArrList> list = result.getBody().getItems().getArrList();
        list.forEach(System.out::println);
        //
    }
    @Data
    private static class Header {
        private boolean success;
        private int resultCode;
        private String resultMsg;
    }

    @Data
    private static class ArrList {
        private double routeId;
        private int routeNo;
        private int moveDir;
        private int bsGap;
        private String bsNm;
        private int vhcNo2;
        private String busTCd2;
        private String busTCd3;
        private String busAreaCd;
        private String arrState;
        private int prevBsGap;
    }

    @Data
    private static class Items {
        private int routeNo;
        private List<ArrList> arrList;
    }

    @Data
    private static class Body {
        private Items items;
        private int totalCount;
    }

    @Data
    private static class Result {
        private Header header;
        private Body body;
    }


}
