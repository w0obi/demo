package com.example.demo.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.model.TodoEntity;

@Repository
public interface TodoRepository extends JpaRepository<TodoEntity, String>{
	/**
	 * 해당 메소드는 스프링 데이터 JPA가 메소드 이름을 파싱하여 쿼리를 작성해 실행함.*/
	
	@Query("SELECT t FROM TodoEntity t WHERE t.userId = ?1")
	public List<TodoEntity> findByUserId(String userId);
}
