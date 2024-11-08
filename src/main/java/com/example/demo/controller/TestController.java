package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.ResponseDTO;
import com.example.demo.dto.TestRequestBodyDTO;

@RestController
@RequestMapping("test")
public class TestController {

    @GetMapping
    public String TestController() {
        return "Hello World!";
    }
    @GetMapping("/testGetMapping")
    public String testControllerWithPath() {
        return "Hello World! testGetMapping";
    }
    @GetMapping("/{id}")
    public String testControllerWithPathVariable(@PathVariable(value="id", required = false) int id) {
    	return "Hello World ID " + id;
    }
    @GetMapping("/testRequestParam")
    public String testControllerRequestParam(@RequestParam(value="id", required = false) int id) {
    	return "Hello World! ID " + id;
    }
    @GetMapping("/testRequestBody")
    public String testControllerRequestBody(@RequestBody TestRequestBodyDTO testRequestBodyDTO) {
    	// 요청 Body의 JSON/XML 데이터가 TestRequestBodyDTO 객체로 변환되어 전달됨
    	return "Hello World! ID " + testRequestBodyDTO.getId() + " Message : " + testRequestBodyDTO.getMessage();
    }
    @GetMapping("/testResponseBody")
    public ResponseDTO<String> testControllerResponseBody()	{
    	List<String> list = new ArrayList<>();
    	list.add("Hello World! I'm ResponseDTO");
    	ResponseDTO<String> response = ResponseDTO.<String>builder().data(list).build();
    	return response;
    }
    @GetMapping("/testResponseEntity")
    public ResponseEntity<?> testControllerResponseEntity()	{
    	List<String> list = new ArrayList<>();
    	list.add("Hello World! I'm ResponseEntity. And you got 400.");
    	ResponseDTO<String> response = ResponseDTO.<String>builder().data(list).build();
    	return ResponseEntity.ok().body(response);
    }
    
}