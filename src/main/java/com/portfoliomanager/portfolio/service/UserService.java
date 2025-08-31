package com.portfoliomanager.portfolio.service;

import com.portfoliomanager.portfolio.dto.request.CreateUserRequest;
import com.portfoliomanager.portfolio.dto.request.UpdateUserRequest;
import com.portfoliomanager.portfolio.dto.response.UserResponse;
import com.portfoliomanager.portfolio.exception.DuplicateUserException;
import com.portfoliomanager.portfolio.exception.UserNotFoundException;
import com.portfoliomanager.portfolio.model.User;
import com.portfoliomanager.portfolio.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

/**
 * Service layer for User operations.
 * <p>
 * Design decisions:
 * - All business logic centralized here
 * - Proper transaction boundaries
 * - Comprehensive error handling
 * - Logging for observability
 * - Return DTOs, never entities
 */

@Service
@Transactional(readOnly = true)
public class UserService {
    private static final Logger log = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /*
     * Find user by ID
     */
    public Optional<UserResponse> findById(Long id) {
        log.debug("Finding user by id: {}", id);
        return userRepository.findById(id).map(UserResponse::from);
    }

    /*
     * find user by Email
     * */

    public Optional<UserResponse> findByEmail(String email) {
        log.debug("Finding user by Email:{}", email);
        return userRepository.findByEmail(email).map(UserResponse::from);
    }

    /*
     * Get all users with pagination and sorting
     */
    public Page<UserResponse> findAllUsers(int page, int size, String sortBy, String sortDirection) {
        log.debug("Finding users - page: {}, size: {}, sortBy: {}, direction: {}",
                page, size, sortBy, sortDirection);

        // Create sort object
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);

        return userRepository.findAll(pageable)
                .map(UserResponse::from);
    }


    /*
     * Get only active users
     */
    public Page<UserResponse> findActiveUsers(int page, int size) {
        log.debug("Finding active users - page: {}, size: {}", page, size);

        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());

        return userRepository.findIsActiveTrue(pageable)
                .map(UserResponse::from);
    }

    /*
     * Search users by name
     */
    public List<UserResponse> searchUsersByName(String name) {
        log.debug("Searching users by name: {}", name);

        return userRepository.findByFullNameContainingIgnoreCase(name)
                .stream()
                .map(UserResponse::from)
                .toList();
    }

    /**
     * Create new user
     */
    @Transactional // Write operation needs read-write transaction
    public UserResponse createUser(CreateUserRequest request) {
        log.info("Creating new user with email: {}", request.email());

        // Business validation
        if (userRepository.existByEmail(request.email())) {
            throw new DuplicateUserException(request.email());
        }

        // Create and save user
        User user = new User(request.email(), request.firstName(), request.lastName());
        User savedUser = userRepository.save(user);

        log.info("Successfully created user with ID: {}", savedUser.getId());
        return UserResponse.from(savedUser);
    }

    /**
     * Update existing user
     */
    @Transactional
    public Optional<UserResponse> updateUser(Long id, UpdateUserRequest request) {
        log.info("Updating user with ID: {}", id);

        return userRepository.findById(id)
                .map(user -> {
                    // Apply updates only if values are provided
                    if (request.firstName() != null) {
                        user.setFirstName(request.firstName());
                    }
                    if (request.lastName() != null) {
                        user.setLastName(request.lastName());
                    }
                    if (request.isActive() != null) {
                        user.setIsActive(request.isActive());
                    }

                    User savedUser = userRepository.save(user);
                    log.info("Successfully updated user with ID: {}", savedUser.getId());

                    return UserResponse.from(savedUser);
                });
    }

    /**
     * Deactivate user (soft delete)
     */
    @Transactional
    public void deactivateUser(Long id) {
        log.info("Deactivating user with ID: {}", id);

        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        user.deactivate();
        userRepository.save(user);

        log.info("Successfully deactivated user with ID: {}", id);
    }

    /**
     * Hard delete user (admin operation)
     */
    @Transactional
    public void deleteUser(Long id) {
        log.warn("Hard deleting user with ID: {}", id);

        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException(id);
        }

        userRepository.deleteById(id);
        log.warn("Successfully deleted user with ID: {}", id);
    }

    /**
     * Get user count (business metric)
     */
    public long getActiveUserCount() {
        return userRepository.countByIsActiveTrue();
    }

    /**
     * Check if user exists
     */
    public boolean existsById(Long id) {
        return userRepository.existsById(id);
    }

    /**
     * Check if email is available
     */
    public boolean isEmailAvailable(String email) {
        return !userRepository.existByEmail(email);
    }


}
