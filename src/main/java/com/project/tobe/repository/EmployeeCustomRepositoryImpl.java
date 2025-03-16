package com.project.tobe.repository;

import com.project.tobe.entity.Employee;
import com.project.tobe.entity.Authority;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.util.List;

@Repository
@Primary
public class EmployeeCustomRepositoryImpl implements EmployeeCustomRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Employee> findEmployeesByAuthorityOrdered() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Employee> cq = cb.createQuery(Employee.class);
        Root<Employee> employee = cq.from(Employee.class);

        // Authority 엔티티와의 Join
        Join<Employee, Authority> authority = employee.join("authority", JoinType.INNER);

        // authorityGrade에 대한 정렬 기준
        Expression<Object> authorityOrder = cb.selectCase()
                .when(cb.equal(authority.get("authorityGrade"), "S"), 1)
                .when(cb.equal(authority.get("authorityGrade"), "A"), 2)
                .when(cb.equal(authority.get("authorityGrade"), "B"), 3)
                .when(cb.equal(authority.get("authorityGrade"), "C"), 4)
                .when(cb.equal(authority.get("authorityGrade"), "D"), 5);

        // 쿼리 작성
        cq.select(employee)
                .where(cb.equal(employee.get("emplYn"), "Y")) // 직원 상태가 'Y'인 경우만 필터링
                .orderBy(cb.asc(authorityOrder)); // authorityGrade에 따른 정렬

        // 쿼리 실행 및 결과 반환
        return entityManager.createQuery(cq).getResultList();
    }
}
