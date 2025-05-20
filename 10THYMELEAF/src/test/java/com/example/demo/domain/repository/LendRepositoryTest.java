package com.example.demo.domain.repository;

import com.example.demo.domain.entity.Book;
import com.example.demo.domain.entity.Lend;
import com.example.demo.domain.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@SpringBootTest
class LendRepositoryTest {

    @Autowired
    private LendRepository lendRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookRepository bookRepository;


    @Test
    public void t1() throws Exception{

        //저장되어있는 도서코드를 가지는 Book
        Book book = bookRepository.findById(3333L).get();
        //저장되어있는 유저정보를 가지는 User
        User user = userRepository.findById("user2").get();

        Lend lend = new Lend();
        lend.setBook(book);
        lend.setUser(user);
        lendRepository.save(lend);
    }

    @Test
    public void t2() throws Exception{
        Lend lend = lendRepository.findById(1L).get();
        Book book = bookRepository.findById(4444L).get();
        lend.setBook(book);
        //Update
        lendRepository.save(lend);
    }

    @Test
    public void t3() throws Exception{
        lendRepository.deleteById(1L);
    }

    @Test
    public void t4() throws Exception{
    List<Lend> list = lendRepository.findLendsByUser("user1");
    list.stream().forEach(System.out::println);

    }

    @Test
    @Transactional(rollbackFor = Exception.class) //commit 을 미뤄야 하기 때문에 트랜잭션 써야함
    public void t5() throws Exception {
        System.out.println("------------FETCH 방식 테스트 시작--------------------");
        Optional<Lend> Lendoptional = lendRepository.findById(1L);
        System.out.println("------------findByID(1L)--------------------");

        Lend lend = Lendoptional.get();
        System.out.println("------------getUser()--------------------");
        User user = lend.getUser(); // LAZY Option 사용시 해당 시점에서 쿼리 실행
        System.out.println(user);

        System.out.println("------------FETCH 방식 테스트 종료--------------------");


    }
}