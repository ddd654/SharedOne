package com.project.tobe.config;

// import com.project.tobe.dto.UserRole;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
// import org.springframework.security.core.userdetails.UserDetailsService;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
// import org.springframework.security.provisioning.InMemoryUserDetailsManager;
// import org.springframework.security.web.SecurityFilterChain;

import com.project.tobe.security.EmployeeDetails; // 사용자의 상세 정보 클래스를 import합니다.
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.authentication.AuthenticationManager;

@Configuration
@EnableWebSecurity // 시큐리티 설정파일을 시큐리티 필터에 등록
public class SecurityConfig {

  @Bean
  public BCryptPasswordEncoder encoder() {
    return new BCryptPasswordEncoder(); // 비밀번호 암호화를 위한 인코더
  }

  // 임시
  @Bean
  public UserDetailsService userDetailsService() {
    return username -> {
      // 임시 사용자 예시, 실제로는 데이터베이스에서 조회하는 로직을 작성해야 합니다.
      if (username.equals("testUser")) {
        // 사용자 정보를 EmployeeDetails로 반환
        return new EmployeeDetails("testUser", encoder().encode("testPass"), "USER");
      }
      throw new UsernameNotFoundException("User not found");
    };
  }

  // AuthenticationManager 설정을 추가
  @Bean
  public AuthenticationManager authManager(HttpSecurity http) throws Exception {
      AuthenticationManagerBuilder authenticationManagerBuilder = http
              .getSharedObject(AuthenticationManagerBuilder.class);
      authenticationManagerBuilder.userDetailsService(userDetailsService()).passwordEncoder(encoder());
      return authenticationManagerBuilder.build();
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
      http
          .csrf().disable() // csrf토큰 사용x
          .authorizeRequests() // 인증 및 권한 부여 규칙 설정
          .antMatchers("/login.user").permitAll() // 로그인 페이지는 인증 없이 접근 허용
          .antMatchers("/employee.do").hasAnyRole("S", "A") // "/employee.do" 경로는 "A","S" 역할을 가진 사용자만 접근 가능
          .anyRequest().permitAll() // 그외 요청은 인증 없이 접근 가능

          .and()
          .formLogin() // 로그인 설정
          .loginPage("/login.user") // 로그인 페이지 설정(사용자 정의)
          .usernameParameter("employeeId") // 로그인폼에서 아이디필드 name 값을 employeeId 로 설정
          .passwordParameter("employeePw") // 로그인폼에서 비밀번호필드 name 값을 employeePw 로 설정
          .loginProcessingUrl("/loginForm") // 로그인 페이지를 가로채 시큐리티가 제공하는 클래스로 로그인을 연결
          .defaultSuccessUrl("/main.do") // 로그인 성공 시 이동할 URL
          .failureUrl("/login.user?err=true") // 로그인 실패시 이동 URL

          .and()
          .logout() // 로그아웃 설정
          .logoutUrl("/logout") // 로그아웃 페이지 설정
          .logoutSuccessUrl("/login.user") // 로그아웃 성공 시 이동할 URL
          .invalidateHttpSession(true) // 로그아웃 시 세션 무효화
          .deleteCookies("JSESSIONID") // 로그아웃 시 쿠키 삭제 (JSESSIONID)

          .and()
          .exceptionHandling() // 인증(Authentication) 또는 권한 부여(Authorization) 관련 예외 처리
          .accessDeniedPage("/accessDenied"); // 접근권한이 없는 요청은 accessDenied 페이지로 이동

      // AuthenticationManager를 SecurityFilterChain에 연결
      http.authenticationManager(authManager());

      return http.build();
  }
}