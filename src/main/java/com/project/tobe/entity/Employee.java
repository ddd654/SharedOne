package com.project.tobe.entity;

import lombok.*;
import javax.persistence.*;
import java.time.LocalDate;

@Entity // 엔티티 클래스를 나타냄
@AllArgsConstructor // 생성자 자동 생성
@NoArgsConstructor //파라미터 없는 기본 생성자 자동 생성
@Getter //getter 자동 생성
@Setter //setter 자동 생성
@ToString //toString 메서드 자동 생성
@Builder //빌더 패턴으로 객체 생성 지원
@Table(name = "employee") // 존재하는 테이블명과 매핑
public class Employee {

    @Id
    private String employeeId;
    private String employeePw;
    private String employeeName;
    private String employeeTel;
    private String employeeEmail;
    private String employeeAddr;
    private String residentNum;
    private LocalDate hireDate;
    private Long salary;
    @Column(nullable = true)
    private String employeeManagerId;


    @Column(name = "emplYn", length = 1)
    private String emplYn;

    @ManyToOne // 다대일 관계
    @JoinColumn(name = "authority_grade") // 외래 키 컬럼 이름
    private Authority authority;
}
