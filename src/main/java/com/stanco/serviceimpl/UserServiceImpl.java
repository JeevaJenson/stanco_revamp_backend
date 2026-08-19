package com.stanco.serviceimpl;

import com.stanco.dto.request.CreateUserRequest;
import com.stanco.dto.request.UpdateUserRequest;
import com.stanco.dto.response.UserResponse;

import com.stanco.entity.User;

import com.stanco.enums.Status;

import com.stanco.repository.UserRepository;
import com.stanco.service.UserService;

import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

        private final UserRepository userRepository;

        private final PasswordEncoder passwordEncoder;

        @Override
        public UserResponse createUser(
                        CreateUserRequest request,
                        String creatorEmpID) {

                User creator = userRepository
                                .findByEmpID(creatorEmpID)
                                .orElseThrow(() -> new RuntimeException(
                                                "Creator not found: "
                                                                + creatorEmpID));

                String creatorRole = creator.getRoleType();

                String requestedRole = request.getRoleType();

                if (requestedRole == null ||
                                requestedRole.trim().isEmpty()) {

                        throw new RuntimeException(
                                        "Role is required");
                }

                requestedRole = requestedRole
                                .trim()
                                .toLowerCase();

                validateRoleHierarchy(
                                creatorRole,
                                requestedRole);

                String empID = request
                                .getEmpID()
                                .trim();

                if (userRepository.existsByEmpID(empID)) {

                        throw new RuntimeException(
                                        "Employee ID already exists: "
                                                        + empID);
                }

                String email = request
                                .getEmail()
                                .trim()
                                .toLowerCase();

                if (userRepository.existsByEmail(email)) {

                        throw new RuntimeException(
                                        "Email already exists: "
                                                        + email);
                }

                String requestedTeam = request.getTeam();

                if (requestedTeam == null ||
                                requestedTeam.trim().isEmpty()) {

                        throw new RuntimeException(
                                        "Team is required");
                }

                requestedTeam = requestedTeam.trim();

                if ("hiring_manager"
                                .equalsIgnoreCase(creatorRole)) {

                        if (creator.getTeam() == null ||
                                        creator.getTeam()
                                                        .trim()
                                                        .isEmpty()) {

                                throw new RuntimeException(
                                                "Hiring Manager team is not assigned");
                        }

                        if (!creator
                                        .getTeam()
                                        .equalsIgnoreCase(
                                                        requestedTeam)) {

                                throw new RuntimeException(
                                                "Hiring Manager can create users only in their own team");
                        }
                }

                if ("super_admin"
                                .equalsIgnoreCase(creatorRole)
                                &&
                                !"hiring_manager"
                                                .equalsIgnoreCase(
                                                                requestedRole)) {

                        throw new RuntimeException(
                                        "Super Admin can create Hiring Manager only");
                }

                User user = new User();

                user.setEmpID(
                                empID);

                user.setName(
                                request
                                                .getName()
                                                .trim());

                user.setDesignation(
                                request
                                                .getDesignation()
                                                .trim());

                user.setBusiness(
                                request.getBusiness());

                user.setDepartment(
                                request.getDepartment());

                user.setLobDivision(
                                request.getLobDivision());

                user.setSupervisor(
                                request.getSupervisor());

                user.setEmail(
                                email);

                user.setMobileNo(
                                request.getMobileNo());

                user.setRoleType(
                                requestedRole);

                user.setTeam(
                                requestedTeam);

                user.setColorCode(
                                request.getColorCode());

                /*
                 * Default password for every newly created user
                 *
                 * Plain password:
                 * 123456
                 *
                 * Database:
                 * BCrypt encrypted password
                 */
                final String DEFAULT_PASSWORD = "123456";

                user.setPassword(
                                passwordEncoder.encode(
                                                DEFAULT_PASSWORD));

                user.setProfileStatus(
                                request.getProfileStatus() != null
                                                ? request.getProfileStatus()
                                                : Status.active);

                user.setTeamStatus(
                                1);

                user.setCreatedBy(
                                creatorEmpID);

                LocalDateTime now = LocalDateTime.now();

                user.setCreatedAt(
                                now);

                user.setUpdatedAt(
                                now);

                User savedUser = userRepository.save(
                                user);

                return mapToResponse(
                                savedUser);
        }

        @Override
        @Transactional(readOnly = true)
        public List<UserResponse> getAllUsers(
                        String empID) {

                User loggedInUser = userRepository
                                .findByEmpID(empID)
                                .orElseThrow(() -> new RuntimeException(
                                                "Logged-in user not found: "
                                                                + empID));

                String role = loggedInUser.getRoleType();

                if ("super_admin"
                                .equalsIgnoreCase(role)) {

                        return userRepository
                                        .findAll()
                                        .stream()
                                        .map(this::mapToResponse)
                                        .toList();
                }

                String team = loggedInUser.getTeam();

                if (team == null ||
                                team.trim().isEmpty()) {

                        return List.of();
                }

                if ("hiring_manager"
                                .equalsIgnoreCase(role)) {

                        return userRepository
                                        .findByTeamIgnoreCase(team)
                                        .stream()
                                        .filter(user ->

                                        "hiring_manager"
                                                        .equalsIgnoreCase(
                                                                        user.getRoleType())

                                                        ||

                                                        "admin"
                                                                        .equalsIgnoreCase(
                                                                                        user.getRoleType())

                                                        ||

                                                        "recruiter"
                                                                        .equalsIgnoreCase(
                                                                                        user.getRoleType())

                                        )
                                        .map(this::mapToResponse)
                                        .toList();
                }

                if ("admin"
                                .equalsIgnoreCase(role)) {

                        return userRepository
                                        .findByTeamIgnoreCase(team)
                                        .stream()
                                        .filter(user ->

                                        "admin"
                                                        .equalsIgnoreCase(
                                                                        user.getRoleType())

                                                        ||

                                                        "recruiter"
                                                                        .equalsIgnoreCase(
                                                                                        user.getRoleType())

                                        )
                                        .map(this::mapToResponse)
                                        .toList();
                }

                if ("recruiter"
                                .equalsIgnoreCase(role)) {

                        return userRepository
                                        .findByTeamIgnoreCase(team)
                                        .stream()
                                        .filter(user -> "recruiter"
                                                        .equalsIgnoreCase(
                                                                        user.getRoleType()))
                                        .map(this::mapToResponse)
                                        .toList();
                }

                return List.of();
        }

        @Override
        @Transactional(readOnly = true)
        public UserResponse getUserById(
                        Long id) {

                User user = userRepository
                                .findById(id)
                                .orElseThrow(() -> new RuntimeException(
                                                "User not found: "
                                                                + id));

                return mapToResponse(
                                user);
        }

        @Override
        @Transactional(readOnly = true)
        public UserResponse getUserByEmpID(
                        String empID) {

                User user = userRepository
                                .findByEmpID(empID)
                                .orElseThrow(() -> new RuntimeException(
                                                "User not found: "
                                                                + empID));

                return mapToResponse(
                                user);
        }

        @Override
        @Transactional(readOnly = true)
        public UserResponse getMyDetails(
                        String empID) {

                User user = userRepository
                                .findByEmpID(empID)
                                .orElseThrow(() -> new RuntimeException(
                                                "User not found: "
                                                                + empID));

                return mapToResponse(
                                user);
        }

        @Override
        public UserResponse updateUser(

                        Long id,

                        UpdateUserRequest request,

                        String updaterEmpID

        ) {

                User updater = userRepository
                                .findByEmpID(updaterEmpID)
                                .orElseThrow(() -> new RuntimeException(
                                                "Updater not found: "
                                                                + updaterEmpID));

                User user = userRepository
                                .findById(id)
                                .orElseThrow(() -> new RuntimeException(
                                                "User not found: "
                                                                + id));

                String updaterRole = updater.getRoleType();

                if ("super_admin"
                                .equalsIgnoreCase(
                                                updaterRole)) {

                        updateUserFields(
                                        user,
                                        request,
                                        updaterEmpID);

                        return mapToResponse(
                                        userRepository.save(user));
                }

                if (updater.getTeam() == null ||
                                user.getTeam() == null) {

                        throw new RuntimeException(
                                        "Team information is missing");
                }

                if (!updater
                                .getTeam()
                                .equalsIgnoreCase(
                                                user.getTeam())) {

                        throw new RuntimeException(
                                        "You can update users only from your own team");
                }

                if ("hiring_manager"
                                .equalsIgnoreCase(
                                                updaterRole)) {

                        String targetRole = user.getRoleType();

                        if (!"admin"
                                        .equalsIgnoreCase(
                                                        targetRole)
                                        &&
                                        !"recruiter"
                                                        .equalsIgnoreCase(
                                                                        targetRole)) {

                                throw new RuntimeException(
                                                "Hiring Manager can update Admin or Recruiter only");
                        }

                        String newRole = request.getRoleType();

                        if (newRole != null &&
                                        !"admin"
                                                        .equalsIgnoreCase(
                                                                        newRole)
                                        &&
                                        !"recruiter"
                                                        .equalsIgnoreCase(
                                                                        newRole)) {

                                throw new RuntimeException(
                                                "Hiring Manager can assign only Admin or Recruiter role");
                        }

                        request.setTeam(
                                        updater.getTeam());

                        updateUserFields(
                                        user,
                                        request,
                                        updaterEmpID);

                        return mapToResponse(
                                        userRepository.save(user));
                }

                if ("admin"
                                .equalsIgnoreCase(
                                                updaterRole)) {

                        String targetRole = user.getRoleType();

                        if (!"admin"
                                        .equalsIgnoreCase(
                                                        targetRole)
                                        &&
                                        !"recruiter"
                                                        .equalsIgnoreCase(
                                                                        targetRole)) {

                                throw new RuntimeException(
                                                "Admin cannot update this user");
                        }

                        request.setTeam(
                                        updater.getTeam());

                        updateUserFields(
                                        user,
                                        request,
                                        updaterEmpID);

                        return mapToResponse(
                                        userRepository.save(user));
                }

                throw new RuntimeException(
                                "You do not have permission to update users");
        }

        @Override
        public void deleteUser(
                        Long id) {

                User user = userRepository
                                .findById(id)
                                .orElseThrow(() -> new RuntimeException(
                                                "User not found: "
                                                                + id));

                user.setProfileStatus(
                                Status.inactive);

                user.setTeamStatus(
                                0);

                user.setDeletedAt(
                                LocalDateTime.now());

                userRepository.save(
                                user);
        }

        private void updateUserFields(

                        User user,

                        UpdateUserRequest request,

                        String updatedBy

        ) {

                user.setEmpID(
                                request
                                                .getEmpID()
                                                .trim());

                user.setName(
                                request
                                                .getName()
                                                .trim());

                user.setDesignation(
                                request
                                                .getDesignation()
                                                .trim());

                user.setBusiness(
                                request.getBusiness());

                user.setDepartment(
                                request.getDepartment());

                user.setLobDivision(
                                request.getLobDivision());

                user.setSupervisor(
                                request.getSupervisor());

                user.setEmail(
                                request
                                                .getEmail()
                                                .trim()
                                                .toLowerCase());

                user.setMobileNo(
                                request.getMobileNo());

                if (request.getRoleType() != null &&
                                !request
                                                .getRoleType()
                                                .trim()
                                                .isEmpty()) {

                        user.setRoleType(
                                        request
                                                        .getRoleType()
                                                        .trim()
                                                        .toLowerCase());
                }

                if (request.getTeam() != null &&
                                !request
                                                .getTeam()
                                                .trim()
                                                .isEmpty()) {

                        user.setTeam(
                                        request
                                                        .getTeam()
                                                        .trim());
                }

                if (request.getColorCode() != null) {

                        user.setColorCode(
                                        request.getColorCode());
                }

                /*
                 * Update password only when
                 * password is provided.
                 */
                if (request.getPassword() != null &&
                                !request
                                                .getPassword()
                                                .trim()
                                                .isEmpty()) {

                        user.setPassword(
                                        passwordEncoder.encode(
                                                        request
                                                                        .getPassword()
                                                                        .trim()));
                }

                if (request.getProfileStatus() != null) {

                        user.setProfileStatus(
                                        request.getProfileStatus());

                        if (request.getProfileStatus() == Status.active) {

                                user.setDeletedAt(
                                                null);

                                user.setTeamStatus(
                                                1);
                        }

                        if (request.getProfileStatus() == Status.inactive) {

                                user.setDeletedAt(
                                                LocalDateTime.now());

                                user.setTeamStatus(
                                                0);
                        }
                }

                user.setUpdatedBy(
                                updatedBy);

                user.setUpdatedAt(
                                LocalDateTime.now());
        }

        private void validateRoleHierarchy(

                        String creatorRole,

                        String requestedRole

        ) {

                if ("super_admin"
                                .equalsIgnoreCase(
                                                creatorRole)) {

                        if (!"hiring_manager"
                                        .equalsIgnoreCase(
                                                        requestedRole)) {

                                throw new RuntimeException(
                                                "Super Admin can create Hiring Manager only");
                        }

                        return;
                }

                if ("hiring_manager"
                                .equalsIgnoreCase(
                                                creatorRole)) {

                        if (!"admin"
                                        .equalsIgnoreCase(
                                                        requestedRole)
                                        &&
                                        !"recruiter"
                                                        .equalsIgnoreCase(
                                                                        requestedRole)) {

                                throw new RuntimeException(
                                                "Hiring Manager can create Admin or Recruiter only");
                        }

                        return;
                }

                if ("admin"
                                .equalsIgnoreCase(
                                                creatorRole)) {

                        throw new RuntimeException(
                                        "Admin is not allowed to create users");
                }

                if ("recruiter"
                                .equalsIgnoreCase(
                                                creatorRole)) {

                        throw new RuntimeException(
                                        "Recruiter is not allowed to create users");
                }

                throw new RuntimeException(
                                "Invalid creator role: "
                                                + creatorRole);
        }

        private UserResponse mapToResponse(
                        User user) {

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

                                user.getTeamStatus(),

                                user.getColorCode());
        }
}