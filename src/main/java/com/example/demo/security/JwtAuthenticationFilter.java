package com.example.demo.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
   @Autowired
   private TokenProvider tokenProvider;
   
   @Override
   protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
         throws ServletException, IOException {
      try {
         // 요청에서 토큰 가져오기
         String token = parseBearerToken(request);
         log.info("Filter is running...");
         // 토큰 검사하기. JWT이므로 인가 서버에 요청하지 않고도 검증 가능.
         if(token != null && !token.equalsIgnoreCase("null")){
            // UserId 가져오기. 위조된 경우에는 예외 처리
            String userId = tokenProvider.validateAndGetUserId(token);
            log.info("Authenticated user ID : " + userId);
            
            // 인증 완료 : SecurityContextHolder에 등록해야 인증된 사용자라고 생각한다.
            AbstractAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                  userId, // 인증된 사용자의 정보. 문자열이 아니어도 아무거나 넣을 수 있음. 보통 UserDetails라는 오브젝트를 넣는데 여기서는 만들지 않았음.
                  null, 
                  AuthorityUtils.NO_AUTHORITIES);
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
            securityContext.setAuthentication(authentication);
            SecurityContextHolder.setContext(securityContext);
         }
      } catch (Exception ex) {
         logger.error("Could not set user authentication in security in security context", ex);
      }
      
      filterChain.doFilter(request, response);
      
   }
   // Authorization 헤더에서 Bearer 토큰을 추출하는 메서드
    private String parseBearerToken(HttpServletRequest request) {
       // Http 요청의 헤더를 파싱해 Bearer 토큰을 리턴한다.
        String bearerToken = request.getHeader("Authorization");
        
        // 토큰이 존재하고 "Bearer "로 시작하는지 확인
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7); // "Bearer " 접두사 제거 후 토큰 반환
        }
        
        return null; // 토큰이 없거나 형식이 맞지 않으면 null 반환
    }
}
