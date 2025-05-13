package com.example.demo.C01OpenData;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URLEncoder;

@RestController
@Slf4j
@RequestMapping("/weather")
public class OpenData04Controller {
    @GetMapping("/get/{lat}/{lon}")
    public void get(@PathVariable String lat, @PathVariable String lon){
        log.info("GET /open/weather/get...");

        String url = "https://api.openweathermap.org/data/2.5/weather";

        String appid="";
        String uri = UriComponentsBuilder
                .fromHttpUrl(url)
                .queryParam("appid", appid)
                .queryParam("lat",lat)
                .queryParam("lon",lon)
                .build(true)
                .toUriString();

        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> response = rt.exchange(uri, HttpMethod.GET, null, String.class);
        System.out.println(response);

    }


}
