package com.stanco.service;

import com.stanco.dto.request.VerticalRequest;
import com.stanco.dto.response.VerticalResponse;

import java.util.List;

public interface VerticalService {

    VerticalResponse create(
            VerticalRequest request,
            String createdBy
    );

    List<VerticalResponse> getAll();

    VerticalResponse getById(
            Long id
    );

    VerticalResponse getByName(
            String verticalName
    );

    VerticalResponse update(
            Long id,
            VerticalRequest request,
            String updatedBy
    );

    void delete(
            Long id,
            String deletedBy
    );
}