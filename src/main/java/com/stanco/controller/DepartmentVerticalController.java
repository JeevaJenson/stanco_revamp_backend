package com.stanco.controller;

import com.stanco.dto.response.DepartmentVerticalResponse;

import com.stanco.service.DepartmentVerticalService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/departments")
@RequiredArgsConstructor
public class DepartmentVerticalController {


    private final DepartmentVerticalService service;


    @GetMapping("/{departmentId}/verticals")
    public ResponseEntity<
            List<DepartmentVerticalResponse>
            > getVerticals(
            @PathVariable Long departmentId
    ) {

        return ResponseEntity.ok(
                service.getVerticalsByDepartment(
                        departmentId
                )
        );
    }



    @PostMapping(
            "/{departmentId}/verticals/{verticalId}"
    )
    public ResponseEntity<
            DepartmentVerticalResponse
            > addVertical(
            @PathVariable Long departmentId,
            @PathVariable Long verticalId
    ) {

        return ResponseEntity.ok(
                service.addVerticalToDepartment(
                        departmentId,
                        verticalId
                )
        );
    }


  

    @DeleteMapping(
            "/{departmentId}/verticals/{verticalId}"
    )
    public ResponseEntity<String>
    removeVertical(
            @PathVariable Long departmentId,
            @PathVariable Long verticalId
    ) {

        service.removeVerticalFromDepartment(
                departmentId,
                verticalId
        );


        return ResponseEntity.ok(
                "Vertical removed successfully"
        );
    }
}