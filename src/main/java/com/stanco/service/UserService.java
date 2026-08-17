package com.stanco.service;

import com.stanco.dto.request.CreateUserRequest;
import com.stanco.dto.request.UpdateUserRequest;
import com.stanco.dto.response.UserResponse;

import java.util.List;

public interface UserService {


    UserResponse createUser(
            CreateUserRequest request,
            String creatorEmpID
    );


    List<UserResponse> getAllUsers();


    UserResponse getUserById(
            Long id
    );


    UserResponse getUserByEmpID(
            String empID
    );


    UserResponse getMyDetails(
            String empID
    );


    UserResponse updateUser(
            Long id,
            UpdateUserRequest request,
            String updaterEmpID
    );


    void deleteUser(
            Long id
    );
}