package com.project.tobe.config;

import com.project.tobe.dto.UserRole;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity //시큐리티 설정파일을 시큐리티 필터에 등록
public class SecurityConfig {


  @Bean
  public BCryptPasswordEncoder encoder() {
    return new BCryptPasswordEncoder();  // 비밀번호 암호화를 위한 인코더
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        .csrf().disable()//csrf토큰 사용x

        .authorizeRequests() // 인증 및 권한 부여 규칙 설정
        .antMatchers("/login.user").permitAll() // 로그인 페이지는 인증 없이 접근 허용
        .antMatchers("/employee.do").hasAnyRole("S", "A") // "/employee.do" 경로는 "A","S" 역할을 가진 사용자만 접근 가능
        .antMatchers("/*.do").authenticated() // ".do"로 끝나는 모든 요청은 인증된 사용자만 접근 가능
        .anyRequest().permitAll() // 그외 요청은 인증 없이 접근 가능

        .and()
        .formLogin() // 로그인 설정
        .loginPage("/login.user") // 로그인 페이지 설정(사용자 정의)
        .usernameParameter("employeeId") //로그인폼에서 아이디필드 name 값을 employeeId 로 설정
        .passwordParameter("employeePw") //로그인폼에서 비밀번호필드 name 값을 employeePw 로 설정
        .loginProcessingUrl("/loginForm") //로그인 페이지를 가로채 시큐리티가 제공하는 클래스로 로그인을 연결
		.defaultSuccessUrl("/main.do") //로그인 성공 시 이동할 URL
        .failureUrl("/login.user?err=true") // 로그인 실패시 이동 URL
//        .failureHandler(new CustomAuthenticationFailureHandler()) // 커스텀 실패 핸들러 등록

        .and()
        .logout() // 로그아웃 설정
        .logoutUrl("/logout") // 로그아웃 페이지 설정
        .logoutSuccessUrl("/login.user") // 로그아웃 성공 시 이동할 URL
        .invalidateHttpSession(true)// 로그아웃 시 세션 무효화
        .deleteCookies("JSESSIONID") // 로그아웃 시 쿠키 삭제 (JSESSIONID)

        .and()
		.exceptionHandling() //인증(Authentication) 또는 권한 부여(Authorization) 관련 예외 처리
        .accessDeniedPage("/accessDenied"); //접근권한이 없는 요청은 accessDenied 페이지로 이동

    return http.build();
  }

}


