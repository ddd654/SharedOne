package com.project.tobe.serviceimpl;


import com.project.tobe.dto.*;
import com.project.tobe.entity.Employee;
import com.project.tobe.mapper.EmployeeMapper;
import com.project.tobe.repository.EmployeeCustomRepository;
import com.project.tobe.repository.EmployeeRepository;
import com.project.tobe.security.EmployeeDetails;
import com.project.tobe.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service("employeeService")
public class EmployeeServiceImpl implements EmployeeService {

  @Autowired
  EmployeeMapper employeeMapper;

  @Autowired
  EmployeeRepository employeeRepository;

  @Autowired
  EmployeeCustomRepository employeeCustomRepository;

  @Autowired
  BCryptPasswordEncoder bCryptPasswordEncoder;


  //직원 전체 조회
  @Override
//  public List<EmployeeDTO> getAllList() {
//    return employeeMapper.getAllList();
//  }
//  public List<Employee> getAllList() {
//    return employeeRepository.findAll();
//  }
//  public List<Employee> getAllList() {
//    return employeeRepository.findByEmplYn("Y");
//  }
//  public List<Employee> getAllList() {
//    return employeeRepository.findEmployeesByAuthorityOrder2();
//  }
  public List<Employee> getAllList() {
    List<Employee> em = employeeCustomRepository.findEmployeesByAuthorityOrdered();
    for(Employee e : em){
      System.out.println(e.getEmployeeName() +"|" + e.getAuthority().getAuthorityName() + " " + e.getAuthority().getAuthorityGrade());
    }
    return employeeCustomRepository.findEmployeesByAuthorityOrdered();
  }

  //직원 검색 조회
  @Override
  public List<EmployeeDTO> getPickList(EmployeeDTO dto) {
    return employeeMapper.getPickList(dto);
  }


  //직원 등록
  @Override
  public void employeeRegist(List<EmployeeTestDTO> dto) {
//    System.out.println(employee);
    List<EmployeeTestDTO> list = new LinkedList<>();
    for (EmployeeTestDTO employee : dto) {
      //비밀번호 암호화 작업
      employee.setEmployeePw(bCryptPasswordEncoder.encode(employee.getEmployeePw()));
      // S 권한인 경우 직속 상사 없도록 null 값 삽입
      if(employee.getAuthorityGrade().equals("S")) {
        employee.setEmployeeManagerId(null);
      }
      list.add(employee);
    }

//    List<Employee> list2 = new LinkedList<>();
//    for (Employee e : employee) {
//      e.setEmployeePw(bCryptPasswordEncoder.encode(e.getEmployeePw()));
////       S 권한인 경우 직속 상사 없도록 null 값 삽입
//      if (e.getAuthority().getAuthorityGrade().equals("S")) {
//        e.setEmployeeManagerId(null);
//      }
//      e.setEmplYn("Y");
//      list2.add(e);
//    }
//    employeeRepository.saveAll(list2);
    employeeMapper.employeeRegistTest(list);
  }


  //직원 등록 시 아이디 중복 검사
  @Override
  public boolean employeeIdCheck(EmployeeDTO dto) {
    int result = employeeMapper.employeeIdCheck(dto);
    return result > 0 ;
  }

  //직원 비밀번호 변경
  @Override
  public void employeePwChange(EmployeeDTO dto) {
    if (dto.getEmployeePw() != null) {
      dto.setEmployeePw(bCryptPasswordEncoder.encode(dto.getEmployeePw()));
    }
    employeeMapper.employeePwChange(dto);
  }

  //로그인한 직원 정보
  @Override
  public AuthorityDto mypageAll(String employeeId) {
    return employeeMapper.mypageAll(employeeId);
  }

  //직원 수정
  @Override
  public void employeeUpdateMaster(EmployeeTestDTO dto) {
    // S 권한인 경우 직속 상사 없도록 null 값 삽입
    if (dto.getAuthorityGrade().equals("S")) {
      dto.setEmployeeManagerId(null);
    }
    employeeMapper.employeeUpdateMaster(dto);
  }

  //직원 선택 삭제
  @Override
  public void employeeDeleteTest(List<String> employeeIds) {
    employeeMapper.employeeDeleteTest(employeeIds);
  }

  //직원 삭제
  @Override
  public void employeeDeletePick(String employeeId) {
    employeeMapper.employeeDeletePick(employeeId);
  }

  // 본인보다 높은 권한의 직원 조회
  @Override
  public List<EmployeeDTO> getManagerList(String id) {
        return employeeMapper.getManagerList(id);
    }

  // 로그인한 직원의 월별 실적 조회
  @Override
  public List<SalesByMonth> getMySalesByMonth(String employeeId) {
      return employeeMapper.getMySalesByMonth(employeeId);
  }

  // 직원 이메일 조회
  @Override
  public String getEmail(String employeeId) {
    return employeeMapper.getEmail(employeeId);
  }

  // 마이페이지 정보 수정
  @Override
  public void employeeUpdateMypage(EmployeeDTO dto) {
    employeeMapper.employeeUpdateMypage(dto);
  }

  // 마이페이지 비밀번호 변경
  @Override
  public void employeeUpdateMypagePw(EmployeeDTO dto) {
    if (dto.getEmployeePw() != null) {
      dto.setEmployeePw(bCryptPasswordEncoder.encode(dto.getEmployeePw()));
    }
    employeeMapper.employeeUpdateMypagePw(dto);
  }


  @Override
  public Employee getUserById(String id) {
    if (id == null) {
      return null;
    }

    Optional<Employee> employee = employeeRepository.findById(id);

    return employee.orElse(null);
  }


  @Override
  public EmployeeDetails login(EmployeeDTO dto) {
    if (dto == null) {
      return null;
    }

    Optional<Employee> employee = employeeRepository.findById(dto.getEmployeeId());

    if (employee.isEmpty()) {
      return null;
    }

    if (!employee.get().getEmployeePw().equals(dto.getEmployeePw())) {
      return null;
    }

    return new EmployeeDetails(employee.orElse(null));
  }


}
