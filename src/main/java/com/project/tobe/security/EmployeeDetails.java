package com.project.tobe.security;

import com.project.tobe.dto.EmployeeDTO;
import com.project.tobe.entity.Employee;
import com.project.tobe.dto.UserRole;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

public class EmployeeDetails implements UserDetails {

  // Employee 엔티티를 저장하기 위한 필드
  private Employee employee;
  private EmployeeDTO employeedto; // 사용되지 않지만, 필요에 따라 주석 해제하여 사용 가능

  // 생성자: Employee 객체를 인자로 받아 초기화
  public EmployeeDetails(Employee employee) {
    this.employee = employee;
  }

  // 권한 관련 작업을 위한 메서드
  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    Collection<GrantedAuthority> authorities = new ArrayList<>();

    try {
      // Employee의 authorityGrade를 UserRole Enum으로 변환
      UserRole role = UserRole.valueOf(employee.getAuthority().getAuthorityGrade());
      // 권한에 ROLE_ 접두사 붙여 추가
      authorities.add(new SimpleGrantedAuthority("ROLE_" + role.name()));
    } catch (IllegalArgumentException e) {
      // authorityGrade가 UserRole Enum에 정의되지 않은 경우 에러 출력
      System.err.println("Invalid authorityGrade value: " + employee.getAuthority().getAuthorityGrade());
    }

    return authorities;
  }

  // UserDetails 인터페이스 메서드: 비밀번호 반환
  @Override
  public String getPassword() {
    return employee.getEmployeePw();
  }

  // UserDetails 인터페이스 메서드: 사용자 이름 반환 (로그인 ID 사용)
  @Override
  public String getUsername() {
    return employee.getEmployeeId();
  }

  // 사용자의 닉네임 반환 (Employee의 이름을 반환)
  public String getNickname() {
    System.out.println(employee.toString());
    System.out.println(employee.getEmployeeName());
    return employee.getEmployeeName();
  }

  // 사용자의 권한 등급 반환
  public String getUserAuthorityGrade() {
    return employee.getAuthority().getAuthorityGrade();
  }

  // 계정이 만료되었는지 확인 (true: 만료되지 않음)
  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  // 계정이 잠겼는지 확인 (true: 잠기지 않음)
  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  // 비밀번호가 만료되었는지 확인 (true: 만료되지 않음)
  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  // 계정이 활성화(사용 가능)인지 확인 (true: 활성화)
  @Override
  public boolean isEnabled() {
    return true;
  }
}
