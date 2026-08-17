package com.stanco.serviceimpl;

import com.stanco.dto.request.CreateUserRequest;
import com.stanco.dto.request.UpdateUserRequest;
import com.stanco.dto.response.UserResponse;

import com.stanco.entity.Team;
import com.stanco.entity.User;

import com.stanco.enums.RoleType;
import com.stanco.enums.Status;

import com.stanco.repository.TeamRepository;
import com.stanco.repository.UserRepository;

import com.stanco.service.UserService;

import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl
        implements UserService {


    private final UserRepository userRepository;

    private final TeamRepository teamRepository;

    private final PasswordEncoder passwordEncoder;


    // =========================================================
    // CREATE USER
    // =========================================================

    @Override
    public UserResponse createUser(
            CreateUserRequest request,
            String creatorEmpID) {


        User creator =
                userRepository
                        .findByEmpID(creatorEmpID)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Creator not found: "
                                                + creatorEmpID
                                )
                        );


        String creatorRole =
                creator.getRoleType();


        // -----------------------------------------------------
        // ROLE VALIDATION
        // -----------------------------------------------------

        if (request.getRoleType() == null
                || request.getRoleType().isBlank()) {

            throw new RuntimeException(
                    "Role type is required"
            );
        }


        RoleType requestedRole;


        try {

            requestedRole =
                    RoleType.valueOf(
                            request.getRoleType()
                    );

        } catch (IllegalArgumentException e) {

            throw new RuntimeException(
                    "Invalid role type: "
                            + request.getRoleType()
            );
        }


        validateRoleHierarchy(
                creatorRole,
                requestedRole
        );


        // -----------------------------------------------------
        // DUPLICATE EMP ID
        // -----------------------------------------------------

        if (userRepository
                .existsByEmpID(
                        request.getEmpID()
                )) {

            throw new RuntimeException(
                    "Employee ID already exists"
            );
        }


        // -----------------------------------------------------
        // DUPLICATE EMAIL
        // -----------------------------------------------------

        if (userRepository
                .existsByEmail(
                        request.getEmail()
                )) {

            throw new RuntimeException(
                    "Email already exists"
            );
        }


        // -----------------------------------------------------
        // VALIDATE ACTIVE TEAM
        // -----------------------------------------------------

        String teamName =
                validateActiveTeam(
                        request.getTeam()
                );


        // -----------------------------------------------------
        // CREATE USER
        // -----------------------------------------------------

        User user =
                new User();


        user.setEmpID(
                request.getEmpID()
        );


        user.setName(
                request.getName()
        );


        user.setDesignation(
                request.getDesignation()
        );


        user.setBusiness(
                request.getBusiness()
        );


        user.setDepartment(
                request.getDepartment()
        );


        user.setLobDivision(
                request.getLobDivision()
        );


        user.setEmail(
                request.getEmail()
        );


        user.setMobileNo(
                request.getMobileNo()
        );


        user.setRoleType(
                requestedRole.name()
        );


        user.setSupervisor(
                creator.getEmpID()
        );


        user.setProfileStatus(
                request.getProfileStatus() != null
                        ? request.getProfileStatus()
                        : Status.active
        );


        user.setPassword(
                passwordEncoder.encode(
                        request.getPassword()
                )
        );


        user.setTeam(
                teamName
        );


        user.setColorCode(
                request.getColorCode() != null
                        ? request.getColorCode()
                        : ""
        );


        user.setCreatedAt(
                LocalDateTime.now()
        );


        user.setUpdatedAt(
                LocalDateTime.now()
        );


        User savedUser =
                userRepository.save(user);


        return mapToResponse(
                savedUser
        );
    }


    // =========================================================
    // GET ALL USERS
    // =========================================================

    @Override
    public List<UserResponse>
    getAllUsers() {


        return userRepository
                .findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }


    // =========================================================
    // GET USER BY ID
    // =========================================================

    @Override
    public UserResponse getUserById(
            Long id) {


        User user =
                userRepository
                        .findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Employee not found: "
                                                + id
                                )
                        );


        return mapToResponse(user);
    }


    // =========================================================
    // GET USER BY EMP ID
    // =========================================================

    @Override
    public UserResponse getUserByEmpID(
            String empID) {


        User user =
                userRepository
                        .findByEmpID(empID)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Employee not found: "
                                                + empID
                                )
                        );


        return mapToResponse(user);
    }


    // =========================================================
    // GET MY DETAILS
    // =========================================================

    @Override
    public UserResponse getMyDetails(
            String empID) {


        User user =
                userRepository
                        .findByEmpID(empID)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Employee not found: "
                                                + empID
                                )
                        );


        return mapToResponse(user);
    }


    // =========================================================
    // UPDATE USER
    // =========================================================

    @Override
    public UserResponse updateUser(
            Long id,
            UpdateUserRequest request,
            String updaterEmpID) {


        userRepository
                .findByEmpID(updaterEmpID)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Updater not found: "
                                        + updaterEmpID
                        )
                );


        User user =
                userRepository
                        .findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Employee not found: "
                                                + id
                                )
                        );


        // -----------------------------------------------------
        // EMP ID DUPLICATE
        // -----------------------------------------------------

        userRepository
                .findByEmpID(
                        request.getEmpID()
                )
                .ifPresent(existing -> {

                    if (!existing
                            .getId()
                            .equals(id)) {

                        throw new RuntimeException(
                                "Employee ID already exists"
                        );
                    }
                });


        // -----------------------------------------------------
        // EMAIL DUPLICATE
        // -----------------------------------------------------

        userRepository
                .findByEmail(
                        request.getEmail()
                )
                .ifPresent(existing -> {

                    if (!existing
                            .getId()
                            .equals(id)) {

                        throw new RuntimeException(
                                "Email already exists"
                        );
                    }
                });


        // -----------------------------------------------------
        // ACTIVE TEAM VALIDATION
        // -----------------------------------------------------

        String teamName =
                validateActiveTeam(
                        request.getTeam()
                );


        // -----------------------------------------------------
        // UPDATE USER
        // -----------------------------------------------------

        user.setEmpID(
                request.getEmpID()
        );


        user.setName(
                request.getName()
        );


        user.setDesignation(
                request.getDesignation()
        );


        user.setBusiness(
                request.getBusiness()
        );


        user.setDepartment(
                request.getDepartment()
        );


        user.setLobDivision(
                request.getLobDivision()
        );


        user.setEmail(
                request.getEmail()
        );


        user.setMobileNo(
                request.getMobileNo()
        );


        user.setRoleType(
                request.getRoleType()
        );


        if (request.getProfileStatus() != null) {

            user.setProfileStatus(
                    request.getProfileStatus()
            );
        }


        user.setTeam(
                teamName
        );


        user.setColorCode(
                request.getColorCode() != null
                        ? request.getColorCode()
                        : ""
        );


        // -----------------------------------------------------
        // PASSWORD
        // -----------------------------------------------------

        if (request.getPassword() != null
                && !request.getPassword().isBlank()) {

            user.setPassword(
                    passwordEncoder.encode(
                            request.getPassword()
                    )
            );
        }


        user.setUpdatedAt(
                LocalDateTime.now()
        );


        User savedUser =
                userRepository.save(user);


        return mapToResponse(
                savedUser
        );
    }


    // =========================================================
    // DELETE USER
    // =========================================================

    @Override
    public void deleteUser(
            Long id) {


        User user =
                userRepository
                        .findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Employee not found: "
                                                + id
                                )
                        );


        userRepository.delete(user);
    }


    // =========================================================
    // VALIDATE ACTIVE TEAM
    //
    // Only status = 1 allowed
    // =========================================================

    private String validateActiveTeam(
            String teamName) {


        if (teamName == null
                || teamName.isBlank()) {

            throw new RuntimeException(
                    "Team is required"
            );
        }


        String cleanedTeamName =
                teamName.trim();


        Team team =
                teamRepository
                        .findByNameIgnoreCase(
                                cleanedTeamName
                        )
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Team not found: "
                                                + cleanedTeamName
                                )
                        );


        if (team.getStatus() == null
                || team.getStatus() != 1) {

            throw new RuntimeException(
                    "Selected team is inactive: "
                            + cleanedTeamName
            );
        }


        return team.getName();
    }


    // =========================================================
    // ROLE HIERARCHY
    // =========================================================

    private void validateRoleHierarchy(
            String creatorRole,
            RoleType requestedRole) {


        if ("super_admin".equals(
                creatorRole)) {


            if (requestedRole !=
                    RoleType.admin) {

                throw new RuntimeException(
                        "Super Admin can create Admin only"
                );
            }


            return;
        }


        if ("admin".equals(
                creatorRole)) {


            if (requestedRole !=
                    RoleType.delivery_lead) {

                throw new RuntimeException(
                        "Admin can create Hiring Manager only"
                );
            }


            return;
        }


        if ("delivery_lead".equals(
                creatorRole)) {


            if (requestedRole !=
                    RoleType.recruiter) {

                throw new RuntimeException(
                        "Hiring Manager can create Recruiter only"
                );
            }


            return;
        }


        if ("recruiter".equals(
                creatorRole)) {


            throw new RuntimeException(
                    "Recruiter is not allowed to create users"
            );
        }


        throw new RuntimeException(
                "Invalid creator role: "
                        + creatorRole
        );
    }


   private UserResponse mapToResponse(
        User user) {

    Integer teamStatus = 0;


    

    if (user.getTeam() != null
            && !user.getTeam().isBlank()) {

        teamStatus =
                teamRepository
                        .findByNameIgnoreCase(
                                user.getTeam().trim()
                        )
                        .map(Team::getStatus)
                        .orElse(0);
    }



    return new UserResponse(

            user.getId(),

            user.getEmpID(),

            user.getName(),

            user.getDesignation(),

            user.getBusiness(),

            user.getDepartment(),

            user.getLobDivision(),

            user.getSupervisor(),

            user.getEmail(),

            user.getMobileNo(),

            user.getRoleType(),

            user.getProfileStatus(),

            user.getTeam(),

            teamStatus,

            user.getColorCode()
    );
}

        }