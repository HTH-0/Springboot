package com.example.demo.domain.repository;

import com.example.demo.domain.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void t1() {
        //INSERT
//        User user =
//                User.builder()
//                        .username("홍길동")
//                        .password("1234")
//                        .role("admin")
//                        .build();
////        userRepository.save(user);
//
//        //UPDATE
//        user.setUsername("홍길동");
//        user.setPassword("4321");
//        user.setRole("manager");
//
//        userRepository.save(user);

        //DELETE
//        userRepository.deleteById("홍길동");


    }

    @Test
    public void t2() throws Exception{
//        List<User> list =userRepository.selectByRole("ROLE_USER");
//        list.stream().forEach(System.out::println);

       List<User> list = userRepository.selectAllLikeUsername("1");
       list.stream().forEach(System.out::println);

       //되네
    }
}