package com.stanco.entity;

import com.stanco.enums.Status;
import com.stanco.enums.StatusConverter;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "vertical")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Vertical {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(
            name = "vertical_name",
            nullable = false
    )
    private String verticalName;

    @Convert(converter = StatusConverter.class)
    @Column(
            nullable = false,
            length = 2
    )
    private Status status = Status.active;

    @Column(
            name = "created_by",
            nullable = false
    )
    private String createdBy;

    @Column(name = "updated_by")
    private String updatedBy;

    @Column(name = "deleted_by")
    private String deletedBy;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;
}