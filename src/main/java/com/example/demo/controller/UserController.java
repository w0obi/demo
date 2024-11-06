package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.ResponseDTO;
import com.example.demo.dto.UserDTO;
import com.example.demo.model.UserEntity;
import com.example.demo.security.TokenProvider;
import com.example.demo.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/auth")
public class UserController {
   @Autowired
   private UserService userService;
   
   @Autowired
   private TokenProvider tokenProvider;
   
   // 빈으로 작성해도 된다.
   private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
   
   @PostMapping("/signup")
   public ResponseEntity<?> registerUser(@RequestBody UserDTO userDTO){
      try {
         if(userDTO == null || userDTO.getPassword() == null) {
            throw new RuntimeException("Invalid Password Value");
         }
         
         // 요청을 이용해 저장할 유저 만들기
         UserEntity user = UserEntity.builder()
                        .username(userDTO.getUsername())
                        .password(passwordEncoder.encode(userDTO.getPassword()))
                        .build();
         
         // 서비스를 이용해 리포지토리에 유저 저장
         UserEntity registerdUser = userService.create(user);
         UserDTO responseUserDTO = UserDTO.builder()
                              .id(registerdUser.getId())
                              .username(registerdUser.getUsername())
                              .build();
         
         return ResponseEntity.ok().body(responseUserDTO);
      }catch (Exception e) {
         ResponseDTO responseDTO = ResponseDTO.builder()
                              .error(e.getMessage())
                              .build();
         
         return ResponseEntity.badRequest().body(responseDTO);
      }
   }
   
   @PostMapping("/signin")
   public ResponseEntity<?> authenticate(@RequestBody UserDTO userDTO){
      UserEntity user = userService.getByCredentials(
            userDTO.getUsername(),
            userDTO.getPassword(),
            passwordEncoder);
      if(user != null) {
         final String token = tokenProvider.create(user);
         final UserDTO responseUserDTO = UserDTO.builder()
                                    .username(user.getUsername())
                                    .id(user.getId())
                                    .token(token)
                                    .build();
         
         return ResponseEntity.ok().body(responseUserDTO);
      }else {
         ResponseDTO responseDTO = ResponseDTO.builder()
                              .error("Login Failed")
                              .build();
         return ResponseEntity
               .badRequest()
               .body(responseDTO);
      }
   }
   
   
}
