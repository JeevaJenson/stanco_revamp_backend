package com.stanco.controller;

import com.stanco.dto.request.VerticalRequest;
import com.stanco.dto.response.VerticalResponse;

import com.stanco.service.VerticalService;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/verticals")
@RequiredArgsConstructor
public class VerticalController {

    private final VerticalService service;


    @PostMapping
    public ResponseEntity<VerticalResponse> create(

            @Valid
            @RequestBody
            VerticalRequest request,

            Authentication authentication
    ) {

        String empID =
                authentication.getName();


        return ResponseEntity.ok(
                service.create(
                        request,
                        empID
                )
        );
    }


    @GetMapping
    public ResponseEntity<List<VerticalResponse>>
    getAll() {

        return ResponseEntity.ok(
                service.getAll()
        );
    }


    @GetMapping("/{id}")
    public ResponseEntity<VerticalResponse>
    getById(
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(
                service.getById(id)
        );
    }


    @GetMapping("/name/{verticalName}")
    public ResponseEntity<VerticalResponse>
    getByName(
            @PathVariable String verticalName
    ) {

        return ResponseEntity.ok(
                service.getByName(verticalName)
        );
    }


    @PutMapping("/{id}")
    public ResponseEntity<VerticalResponse>
    update(

            @PathVariable Long id,

            @Valid
            @RequestBody
            VerticalRequest request,

            Authentication authentication
    ) {

        String empID =
                authentication.getName();


        return ResponseEntity.ok(
                service.update(
                        id,
                        request,
                        empID
                )
        );
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String>
    delete(

            @PathVariable Long id,

            Authentication authentication
    ) {

        String empID =
                authentication.getName();


        service.delete(
                id,
                empID
        );


        return ResponseEntity.ok(
                "Vertical deleted successfully"
        );
    }
}