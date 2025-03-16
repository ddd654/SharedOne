package com.project.tobe.repository;
import com.project.tobe.dto.EmployeeDTO;
import com.project.tobe.entity.Authority;
import com.project.tobe.entity.Customer;
import com.project.tobe.entity.Employee;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository // 생략해도 이미 JpaRepository 로 자동 빈 등록됨
public interface EmployeeRepository extends JpaRepository<Employee, String>, EmployeeCustomRepository {

    // JPA 쿼리 메서드 (SELECT * FROM employee WHERE empl_yn = :emplYn 자동생성해줌)
    List<Employee> findByEmplYn(@Param("emplYn") String emplYn);

    @Query("SELECT e FROM Employee e " +
            "JOIN e.authority a " +
            "WHERE e.emplYn = 'Y' " +
            "ORDER BY CASE " +
            "WHEN a.authorityGrade = 'S' THEN 1 " +
            "WHEN a.authorityGrade = 'A' THEN 2 " +
            "WHEN a.authorityGrade = 'B' THEN 3 " +
            "WHEN a.authorityGrade = 'C' THEN 4 " +
            "WHEN a.authorityGrade = 'D' THEN 5 " +
            "END")
    List<Employee> findEmployeesByAuthorityOrder2();



}
