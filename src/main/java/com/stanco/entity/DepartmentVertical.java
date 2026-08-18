package com.stanco.entity;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "department_vertical", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "department_id",
                "vertical_id"
        })
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentVertical {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "vertical_id", nullable = false)
    private Vertical vertical;
}