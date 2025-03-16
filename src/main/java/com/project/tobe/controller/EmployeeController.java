package com.project.tobe.controller;


import com.project.tobe.dto.*;
import com.project.tobe.dto.EmployeeDTO;
import com.project.tobe.entity.Employee;
import com.project.tobe.security.EmployeeDetails;
import com.project.tobe.service.EmployeeService;
import com.project.tobe.util.PageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

  @Autowired
  @Qualifier("employeeService")
  private EmployeeService employeeService;

  //직원 전체 조회
  @GetMapping("/employeeALL")
//  public List<EmployeeDTO> employeeALL() {
//    return employeeService.getAllList();
//  }
    public List<Employee> employeeALL() {
      return employeeService.getAllList();
  }

  //직원 검색 조회
  @PostMapping("/employeeSearch")
  public List<EmployeeDTO> employeePick(@RequestBody EmployeeDTO dto) {
    return employeeService.getPickList(dto);
  }

  @PostMapping("/employeeRegist")
  public void employeeRegist(@RequestBody List<EmployeeTestDTO> dto) {
    employeeService.employeeRegist(dto);
  }

  //직원 등록 시 아이디 중복 검사
  @PostMapping("/employeeIdCheck")
  public boolean employeeIdCheck(@RequestBody EmployeeDTO dto) {
    return employeeService.employeeIdCheck(dto);
  }

  //직원 수정
  @PostMapping("/employeeUpdate")
  public void employeeUpdateMaster(@RequestBody EmployeeTestDTO dto) {
    employeeService.employeeUpdateMaster(dto);
  }

  //직원 선택 삭제
  @PostMapping("/employeeDelete")
  public void employeeDeleteTest(@RequestBody List<String> employeeIds) { employeeService.employeeDeleteTest(employeeIds); }

  //직원 삭제
  @PostMapping("/employeeDeletePick")
  public void employeeDeletePick(@RequestBody String employeeId) {
    employeeService.employeeDeletePick(employeeId);
  }

  //직원 비밀번호 변경
  @PostMapping("/employeePwChange")
  public void employeePwChange(@RequestBody EmployeeDTO dto) {
    employeeService.employeePwChange(dto);
  }


  //현재 로그인 정보 가져오기
  @GetMapping("/user-info")
  public ResponseEntity<?> employeeUserInfo(Authentication authentication) {

    if (authentication == null) { // 인증 객체가 없다면
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
    }

    //인증객체 안에 principal값을 얻으면 유저객체가 나옴
    EmployeeDetails user = (EmployeeDetails)authentication.getPrincipal();
    System.out.println("로그인 정보 (이름):" + user.getNickname());
    System.out.println("로그인 정보 (아이디):" + user.getUsername());
    System.out.println("로그인 정보 (비밀번호) :" + user.getPassword());
    System.out.println("로그인 정보 (권한) :" + user.getUserAuthorityGrade());

    // 사용자 이름과 권한을 반환
    Map<String, Object> response = new HashMap<>();
    response.put("userName", user.getNickname());
    response.put("userId", user.getUsername());
    response.put("userPw", user.getPassword());
    response.put("userAuthorityGrade", user.getUserAuthorityGrade());

    return ResponseEntity.ok(response);
  }
}
