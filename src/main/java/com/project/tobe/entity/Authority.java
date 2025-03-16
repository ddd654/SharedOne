package com.project.tobe.entity;

import lombok.*;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Table(name = "authority")
public class Authority {

    @Id
    private String authorityGrade;
    private String authorityName;
}
