package com.project.tobe.service;

import com.project.tobe.dto.*;
import com.project.tobe.entity.Employee;
import com.project.tobe.security.EmployeeDetails;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EmployeeService {

    //직원 전체 조회
//    List<EmployeeDTO> getAllList();
    List<Employee> getAllList();

    //직원 검색 조회
    List<EmployeeDTO> getPickList(EmployeeDTO dto);

    //직원 등록
    void employeeRegist(List<EmployeeTestDTO> dto);

    //직원 등록 시 아이디 체크
    boolean employeeIdCheck(EmployeeDTO dto);

    //직원 수정
    void employeeUpdateMaster(EmployeeTestDTO dto);

    //직원 선택 삭제
    void employeeDeleteTest(List<String> employeeIds);

    //직원 비밀번호 변경
    void employeePwChange(EmployeeDTO dto);

    //직원 삭제
    void employeeDeletePick(String employeeId);

    //로그인한 직원 정보
    AuthorityDto mypageAll(String employeeId);

    // 로그인한 직원의 월별 실적 조회
    List<SalesByMonth> getMySalesByMonth(String employeeId);

    // 본인보다 높은 권한의 직원 조회
    List<EmployeeDTO> getManagerList(String id);

    // 직원 이메일 조회
    String getEmail(String inputConfirmer);

    // 마이페이지 정보 수정
    void employeeUpdateMypage(EmployeeDTO dto);

    // 마이페이지 비밀번호 변경
    void employeeUpdateMypagePw(EmployeeDTO dto);


    Employee getUserById(String id);

    EmployeeDetails login(EmployeeDTO dto);


}
