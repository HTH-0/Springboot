package com.example.demo.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="lend")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Lend {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //AI (자동증가 처리) Auto Implements
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER) //기본값
//    @ManyToOne(fetch = FetchType.LAZY) // 보통 이걸 많이 씀
    @JoinColumn(name="bookcode", foreignKey = @ForeignKey(name="FK_LEND_BOOK",
            foreignKeyDefinition = "FOREIGN KEY(bookcode) REFERENCES book(bookcode) ON DELETE CASCADE ON UPDATE CASCADE"))
    private Book book;

//    @ManyToOne(fetch = FetchType.EAGER)
    @ManyToOne(fetch = FetchType.LAZY) // 보통 이걸 많이 씀
    @JoinColumn(name="username", foreignKey = @ForeignKey(name="FK_LEND_USER",
            foreignKeyDefinition = "FOREIGN KEY(username) REFERENCES user(username) ON DELETE CASCADE ON UPDATE CASCADE"))
    private User user;

}
