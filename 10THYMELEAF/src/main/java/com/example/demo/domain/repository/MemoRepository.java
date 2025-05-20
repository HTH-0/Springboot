package com.example.demo.domain.repository;

import com.example.demo.domain.entity.Memo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemoRepository extends JpaRepository<Memo,Integer> {

}
