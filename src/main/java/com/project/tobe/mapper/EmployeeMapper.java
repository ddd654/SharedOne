package com.project.tobe.mapper;

import com.project.tobe.dto.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EmployeeMapper {

   //직원 전체 조회
   List<EmployeeDTO> getAllList();

   //직원 검색 조회
   List<EmployeeDTO> getPickList(EmployeeDTO dto);

   //직원 등록
   void employeeRegistTest(List<EmployeeTestDTO> dto);

   //직원 등록 시 아이디 체크
   int employeeIdCheck(EmployeeDTO dto);

   //직원 수정
   void employeeUpdateMaster(EmployeeTestDTO dto);

   //직원 선택 삭제
   void employeeDeleteTest(List<String> employeeIds);

   //직원 비밀번호 변경
   void employeePwChange(EmployeeDTO dto);

   //직원 삭제
   void employeeDeletePick(String employeeId);

   // 마이페이지 정보 수정
   void employeeUpdateMypage(EmployeeDTO dto);

   // 마이페이지 비밀번호 변경
   void employeeUpdateMypagePw(EmployeeDTO dto);

   //로그인한 직원 정보
   AuthorityDto mypageAll(String employeeId);

   // 로그인한 직원의 월별 실적 조회
   List<SalesByMonth> getMySalesByMonth(String employeeId);

   // 본인보다 높은 권한의 직원 조회
   List<EmployeeDTO> getManagerList(String id);

   // 직원 이메일 조회
   String getEmail(String inputConfirmer);

}

