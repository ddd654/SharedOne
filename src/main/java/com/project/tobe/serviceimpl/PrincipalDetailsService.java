package com.project.tobe.serviceimpl;

import com.project.tobe.entity.Employee;
import com.project.tobe.repository.EmployeeRepository;
//import com.project.tobe.security.EmployeeDetails;
import com.project.tobe.security.EmployeeDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
//loginProcessingUrl()가 호출되면 loadUserByUsername이 자동 호출됨
public class PrincipalDetailsService implements UserDetailsService {

  private final EmployeeRepository employeeRepository;

  /**
   * 사용자의 아이디(username)를 통해 인증 정보를 로드하는 메서드입니다.
   * 스프링 시큐리티에서 로그인 요청 시 호출됩니다.
   */
  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

      // 데이터베이스에서 username을 기준으로 Employee 객체 조회
    Employee employee = employeeRepository.findById(username)
        .orElseThrow(() -> {
            // username 에 해당하는 사용자가 없으면 예외 발생
          return new UsernameNotFoundException("해당 유저를 찾을 수 없습니다.");
        });
    // 조회된 사용자 객체를 EmployeeDetails (유저 객체) 로 변환하여 반환
    // 시큐리티세션( new Authentication(new MyUserDetails()) ) 형식으로 전달 됨
    return new EmployeeDetails(employee);
  }
}
