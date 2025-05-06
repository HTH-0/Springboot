package com.example.demo.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//Lombok >> Getter/ Setter, toString, 생성자 자동으로 만들어진다 

//@Getter
//@Setter
//@ToString		

@AllArgsConstructor		// 모든 파라미터 받는 생성자
@NoArgsConstructor		// 디폴트 생성자
@Builder
@Data			// 전부 다
public class PersonDto {
	private String username;
	private int age;
	private String addr;
	
	
	
	
}
