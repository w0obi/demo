package com.example.demo.model;

import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "Todo")
public class TodoEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO) // UUID 전략으로 변경
    @UuidGenerator  		// Hibernate 6+에서는 @GenericGenerator 대신 사용
	private String id;		//이 오브젝트 아이디
	private String userId;  //이 오브젝트를 생성한 유저의 아이디
	private String title;   //Todo 타이틀
	private boolean done;	//true - todo를 완료한 경우
}
