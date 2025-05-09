package com.example.demo.domain.repository;

import com.example.demo.domain.entity.Book;
import com.example.demo.domain.entity.Memo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemoRepository extends JpaRepository<Memo, Integer>{


}

