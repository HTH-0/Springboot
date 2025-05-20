package com.example.demo.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="book")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Book {
    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY) //AI (자동증가 처리) Auto Implements
    @Column(name="bookcode")    //언더바 처리 없이 그대로 저장됨
    private Long bookCode;     //카멜표기법으로 적어서 book_code 로 저장됨
   @Column(name="bookname")
    private String bookName;
    private String publisher;
    private String isbn;


}
